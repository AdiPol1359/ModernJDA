package me.adipol.modernjda.event;

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

    public <T> void callEvent(T t) {
        listeners.forEach(listener -> {
            List.of(listener.getClass().getMethods()).forEach(method -> {
                if(method.getAnnotation(EventHandler.class) == null) {
                    return;
                }

                if(method.getParameters().length > 0 && method.getParameters()[0].getType().equals(t.getClass())) {
                    try {
                        method.invoke(listener, t);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
        });
    }
}
