package me.adipol.modernjda.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import me.adipol.modernjda.config.Config;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

@UtilityClass
public class ConfigUtil {

    private static final Representer representer;
    private static final DumperOptions dumperOptions;

    static {
        representer = new Representer();
        dumperOptions = new DumperOptions();

        dumperOptions.setIndent(2);
        dumperOptions.setPrettyFlow(false);
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
    }

    public static <T extends Config> T loadConfig(String name, Class<T> clazz) {
        return loadConfig(name, clazz, false);
    }

    @SneakyThrows
    public static <T extends Config> T loadConfig(String name, Class<T> clazz, boolean override) {
        representer.addClassTag(clazz, Tag.MAP);

        name = name + ".yml";

        File file = new File(name);
        Yaml yaml = new Yaml(new Constructor(clazz), representer, dumperOptions);

        if(!file.exists() || override) {
            yaml.dump(clazz.getConstructor().newInstance(), new PrintWriter(name));
        }

        return yaml.load(new FileInputStream(file));
    }
}
