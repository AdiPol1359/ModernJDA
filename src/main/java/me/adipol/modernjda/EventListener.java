package me.adipol.modernjda;

import lombok.AllArgsConstructor;
import me.adipol.modernjda.command.AbstractCommand;
import me.adipol.modernjda.command.CommandInfo;
import me.adipol.modernjda.command.CommandManager;
import me.adipol.modernjda.command.cooldown.CommandCoolDown;
import me.adipol.modernjda.command.cooldown.CoolDownMember;
import me.adipol.modernjda.command.events.*;
import me.adipol.modernjda.event.EventManager;
import me.adipol.modernjda.util.MemberUtil;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.GenericEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class EventListener extends ListenerAdapter {

    private final CommandManager commandManager;
    private final EventManager eventManager;

    private final List<CoolDownMember> coolDownMembers = new ArrayList<>();

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getAuthor().isBot() || event.getMember() == null) return;

        Member member = event.getMember();
        String message = event.getMessage().getContentRaw();
        String[] args = message.split(" ");

        eventManager.callEvent(event);
        commandManager.getCommandMaps().stream().filter(map -> message.startsWith(map.getPrefix())).findFirst().ifPresent(commandMap -> {
            String name = args[0].substring(commandMap.getPrefix().length());
            Optional<AbstractCommand> optionalCommand = commandMap.getCommand(name);

            if(optionalCommand.isEmpty()) {
                eventManager.callEvent(new CommandNotFoundEvent(commandMap, event));
                return;
            }

            AbstractCommand command = optionalCommand.get();
            CommandInfo commandInfo = command.getAnnotation();

            if(commandMap.getPermissions().length > 0 || commandInfo.permissions().length > 0) {
                if(!MemberUtil.hasRole(member, commandMap.getPermissions()) && !MemberUtil.hasRole(member, commandInfo.permissions())) {
                    eventManager.callEvent(new CommandMissingPermissionEvent(commandMap, command, event));
                    return;
                }
            }

            if(commandInfo.coolDown() > 0) {
                switch(commandInfo.coolDownScope()) {
                    case COMMAND -> {
                        CommandCoolDown commandCoolDown = command.getCommandCoolDown();

                        if(commandCoolDown.hasCoolDown()) {
                            eventManager.callEvent(new CommandCoolDownEvent(-commandCoolDown.getCoolDown(), command, event));
                            return;
                        }

                        commandCoolDown.setCoolDown();
                    }
                    case MEMBER -> {
                        String userId = member.getId();
                        Optional<CoolDownMember> coolDownMember = coolDownMembers.stream().filter(c -> c.getUserId().equals(userId) && c.getCommand().equals(args[0])).findFirst();

                        if(coolDownMember.isPresent()) {
                            CommandCoolDown commandCoolDown = coolDownMember.get().getCommandCoolDown();

                            if(commandCoolDown.hasCoolDown()) {
                                eventManager.callEvent(new CommandCoolDownEvent(-commandCoolDown.getCoolDown(), command, event));
                                return;
                            }

                            commandCoolDown.setCoolDown();
                        } else {
                            coolDownMembers.add(new CoolDownMember(userId, args[0], commandInfo.coolDown()));
                        }
                    }
                }
            }

            CommandExecuteEvent commandExecuteEvent = new CommandExecuteEvent(commandMap, command, event);

            eventManager.callEvent(commandExecuteEvent);

            if(!commandExecuteEvent.isCancelled()) {
                try {
                    command.handleCommand(member, event.getChannel(), event, Arrays.copyOfRange(args, 1, args.length));
                } catch(Exception ex) {
                    eventManager.callEvent(new CommandExceptionEvent(ex, command, event));
                }
            }
        });
    }

    @Override
    public void onGenericEvent(@NotNull GenericEvent event) {
        if(event instanceof MessageReceivedEvent) {
            return;
        }

        eventManager.callEvent(event);
    }
}
