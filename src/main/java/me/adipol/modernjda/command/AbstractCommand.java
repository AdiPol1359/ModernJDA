package me.adipol.modernjda.command;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class AbstractCommand {

    public CommandInfo getAnnotation() {
        CommandInfo commandInfo = getClass().getAnnotation(CommandInfo.class);

        if(commandInfo == null) {
            throw new RuntimeException("Cannot find CommandInfo annotation!");
        }

        return commandInfo;
    }

    public abstract void handleCommand(Member member, MessageChannel channel, MessageReceivedEvent event, String[] args);
}
