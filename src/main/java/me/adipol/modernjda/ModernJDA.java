package me.adipol.modernjda;

import lombok.Getter;
import me.adipol.modernjda.command.CommandManager;
import me.adipol.modernjda.event.EventManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

@Getter
public class ModernJDA {

    @Getter
    private static ModernJDA instance;

    private final JDABuilder builder;

    private final CommandManager commandManager;
    private final EventManager eventManager;

    private JDA jda;

    public ModernJDA(String token) {
        instance = this;

        builder = JDABuilder.createDefault(token);

        commandManager = new CommandManager();
        eventManager = new EventManager();
    }

    public void setActivity(Activity activity) {
        if(jda == null) {
            builder.setActivity(activity);
        } else {
            jda.getPresence().setActivity(activity);
        }
    }

    public void run() {
        try {
            jda = builder.build().awaitReady();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        jda.addEventListener(new EventListener(commandManager, eventManager));
    }
}
