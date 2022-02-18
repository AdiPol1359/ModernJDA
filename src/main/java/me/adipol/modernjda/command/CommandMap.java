package me.adipol.modernjda.command;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Getter
public class CommandMap {

    private final List<AbstractCommand> commands;

    @Setter
    private String prefix;
    private String[] permissions;

    public CommandMap(String prefix) {
        commands = new ArrayList<>();
        permissions = new String[0];

        this.prefix = prefix;
    }

    public void registerCommand(AbstractCommand... commands) {
        this.commands.addAll(List.of(commands));
    }

    public void setPermission(String... permissions) {
        this.permissions = permissions;
    }

    public Optional<AbstractCommand> getCommand(String name) {
        return commands.stream().filter(command -> command.getAnnotation().name().equals(name) || Arrays.asList(command.getAnnotation().aliases()).contains(name)).findFirst();
    }
}
