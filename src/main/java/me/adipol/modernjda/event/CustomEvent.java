package me.adipol.modernjda.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomEvent {
    private boolean cancelled = false;
}
