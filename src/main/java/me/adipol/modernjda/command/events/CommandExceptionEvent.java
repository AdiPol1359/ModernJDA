package me.adipol.modernjda.command.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.adipol.modernjda.command.AbstractCommand;
import me.adipol.modernjda.event.CustomEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@AllArgsConstructor
@Getter
public class CommandExceptionEvent extends CustomEvent {
    private final Exception exception;
    private final AbstractCommand command;
    private final MessageReceivedEvent event;
}