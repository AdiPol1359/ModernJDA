package me.adipol.modernjda;

import lombok.Getter;
import lombok.SneakyThrows;
import me.adipol.modernjda.command.CommandManager;
import me.adipol.modernjda.event.EventManager;
import me.adipol.modernjda.util.VersionInfo;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.List;

@Getter
public class ModernJDA {

    @Getter
    private static ModernJDA instance;

    private final JDABuilder builder;

    private final CommandManager commandManager;
    private final EventManager eventManager;

    private final Representer representer;
    private final DumperOptions dumperOptions;

    private final List<GatewayIntent> intents;
    private final List<CacheFlag> flags;

    private JDA jda;

    public ModernJDA(String token, boolean checkUpdate, List<GatewayIntent> intents, List<CacheFlag> flags) {
        instance = this;

        builder = JDABuilder.createDefault(token);

        commandManager = new CommandManager();
        eventManager = new EventManager();

        representer = new Representer();
        dumperOptions = new DumperOptions();

        this.intents = intents;
        this.flags = flags;

        dumperOptions.setIndent(2);
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

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

    public <T> T loadConfig(String name, Class<T> clazz) {
        return loadConfig(name, clazz, false);
    }

    @SneakyThrows
    public <T> T loadConfig(String name, Class<T> clazz, boolean copy) {
        representer.addClassTag(clazz, Tag.MAP);

        name = name + ".yml";

        File file = new File(name);
        Yaml yaml = new Yaml(new Constructor(clazz), representer, dumperOptions);

        if(!file.exists() || copy) {
            yaml.dump(clazz.getConstructor().newInstance(), new PrintWriter(name));
        }

        return yaml.load(new FileInputStream(file));
    }

    public void run() {
        try {
            intents.forEach(builder::enableIntents);
            flags.forEach(builder::enableCache);

            jda = builder.build().awaitReady();
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

        jda.addEventListener(new EventListener(commandManager, eventManager));
    }
}
