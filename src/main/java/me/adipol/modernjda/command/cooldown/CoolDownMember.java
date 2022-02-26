package me.adipol.modernjda.command.cooldown;

import lombok.Getter;

@Getter
public class CoolDownMember {

    private final String userId;
    private final String command;
    private final CommandCoolDown commandCoolDown;

    public CoolDownMember(String userId, String command, int coolDown) {
        this.userId = userId;
        this.command = command;

        commandCoolDown = new CommandCoolDown(coolDown);

        commandCoolDown.setCoolDown();
    }
}
