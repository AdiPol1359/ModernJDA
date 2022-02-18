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

    public void registerCommandMap(CommandMap... commandMap) {
        commandMaps.addAll(List.of(commandMap));
    }

    public CommandMap getDefaultCommandMap() {
        return commandMaps.get(0);
    }
}
