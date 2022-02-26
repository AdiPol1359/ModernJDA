package me.adipol.modernjda.cooldown;

import java.util.Date;

public abstract class CoolDown {

    private long coolDownTime = 0;

    public long getCoolDown() {
        return new Date().getTime() - coolDownTime;
    }

    public boolean hasCoolDown() {
        return getCoolDown() < 0;
    }

    public void setCoolDown() {
        coolDownTime = new Date().getTime() + getAnnotation().coolDown();
    }
}
