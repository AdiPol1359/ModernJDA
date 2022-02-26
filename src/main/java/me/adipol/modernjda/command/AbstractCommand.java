package me.adipol.modernjda.command;

import lombok.Getter;
import me.adipol.modernjda.command.cooldown.CommandCoolDown;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class AbstractCommand {

    @Getter
    private final CommandCoolDown commandCoolDown = new CommandCoolDown(getAnnotation().coolDown());

    public CommandInfo getAnnotation() {
        CommandInfo commandInfo = getClass().getAnnotation(CommandInfo.class);

        if(commandInfo == null) {
            throw new RuntimeException("Cannot find CommandInfo annotation!");
        }

        return commandInfo;
    }

    public abstract void handleCommand(Member member, MessageChannel channel, MessageReceivedEvent event, String[] args) throws Exception;
}
