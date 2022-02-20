package me.adipol.modernjda.command;

import lombok.Getter;
import me.adipol.modernjda.command.defaults.VersionCommand;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommandManager {

    private final List<CommandMap> commandMaps = new ArrayList<>();

    public CommandManager() {
        CommandMap commandMap = new CommandMap("!");

        commandMap.registerCommand(new VersionCommand());
        commandMaps.add(commandMap);
    }

    public void registerCommandMap(CommandMap... commandMaps) {
        List.of(commandMaps).forEach(commandMap -> {
            if(this.commandMaps.stream().anyMatch(map -> map.getPrefix().equals(commandMap.getPrefix()))) {
                throw new RuntimeException("You can't add command map with the same prefix! (" + commandMap.getPrefix() + ")");
            }

            this.commandMaps.add(commandMap);
        });
    }

    public CommandMap getDefaultCommandMap() {
        return commandMaps.get(0);
    }
}
