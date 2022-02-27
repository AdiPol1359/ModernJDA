package me.adipol.modernjda;

import lombok.Getter;
import me.adipol.modernjda.command.CommandManager;
import me.adipol.modernjda.event.EventManager;
import me.adipol.modernjda.util.VersionInfo;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.List;

@Getter
public class ModernJDA {

    @Getter
    private static ModernJDA instance;

    private final JDABuilder builder;

    private final CommandManager commandManager;
    private final EventManager eventManager;

    private final List<GatewayIntent> intents;
    private final List<CacheFlag> cacheFlags;

    private JDA jda;

    public ModernJDA(String token, boolean checkUpdate, List<GatewayIntent> intents, List<CacheFlag> cacheFlags) {
        instance = this;

        builder = JDABuilder.createDefault(token);

        commandManager = new CommandManager();
        eventManager = new EventManager();

        this.intents = intents;
        this.cacheFlags = cacheFlags;

        if(checkUpdate && VersionInfo.checkUpdate()) {
            System.out.println("]============== ( ModernJDA ) ==============[");
            System.out.println("* A new version of ModernJDA is available!  *");
            System.out.println("* https://github.com/AdiPol1359/ModernJDA   *");
            System.out.println("]============== ( ModernJDA ) ==============[");
            System.out.println(" ");
        }
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
            intents.forEach(builder::enableIntents);
            cacheFlags.forEach(builder::enableCache);

            jda = builder.build().awaitReady();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        jda.addEventListener(new EventListener(commandManager, eventManager));
    }
}
