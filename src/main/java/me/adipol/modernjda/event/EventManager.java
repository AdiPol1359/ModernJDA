package me.adipol.modernjda.event;

import net.dv8tion.jda.api.events.GenericEvent;

import java.util.ArrayList;
import java.util.List;

public class EventManager {

    private final List<Listener> listeners;

    public EventManager() {
        listeners = new ArrayList<>();
    }

    public void registerListener(Listener... listener) {
        listeners.addAll(List.of(listener));
    }

    public <T extends GenericEvent> void callEvent(T event) {
        executeEvent(event);
    }

    public <T extends CustomEvent> void callEvent(T event) {
        executeEvent(event);
    }

    private <T> void executeEvent(T event) {
        listeners.forEach(listener -> {
            List.of(listener.getClass().getMethods()).forEach(method -> {
                if(method.getAnnotation(EventHandler.class) == null) {
                    return;
                }

                if(method.getParameters().length > 0 && method.getParameters()[0].getType().equals(event.getClass())) {
                    try {
                        method.invoke(listener, event);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });
    }
}
