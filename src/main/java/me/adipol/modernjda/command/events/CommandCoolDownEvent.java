package me.adipol.modernjda.command.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.adipol.modernjda.command.AbstractCommand;
import me.adipol.modernjda.event.CustomEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

@AllArgsConstructor
@Getter
public class CommandCoolDownEvent extends CustomEvent {
    private final long coolDown;
    private final AbstractCommand command;
    private final MessageReceivedEvent messageReceivedEvent;
}
