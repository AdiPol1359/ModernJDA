package me.adipol.modernjda.command.cooldown;

import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
public class CommandCoolDown {

    private final int coolDown;

    private long coolDownTime = 0;

    public long getCoolDown() {
        return new Date().getTime() - coolDownTime;
    }

    public boolean hasCoolDown() {
        return getCoolDown() < 0;
    }

    public void setCoolDown() {
        coolDownTime = new Date().getTime() + coolDown;
    }
}
