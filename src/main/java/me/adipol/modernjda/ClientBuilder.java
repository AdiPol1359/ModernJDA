package me.adipol.modernjda;

import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.ArrayList;
import java.util.List;

public class ClientBuilder {

    private final List<GatewayIntent> intents = new ArrayList<>();
    private final List<CacheFlag> flags = new ArrayList<>();

    private boolean updates = true;

    private String token;

    public ClientBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public ClientBuilder checkUpdate(boolean updates) {
        this.updates = updates;
        return this;
    }

    public ClientBuilder enableIntent(GatewayIntent intent) {
        intents.add(intent);
        return this;
    }

    public ClientBuilder enableCache(CacheFlag flag) {
        flags.add(flag);
        return this;
    }

    public ModernJDA build() {
        if(token == null) {
            throw new RuntimeException("You need to set the token before build client!");
        }

        return new ModernJDA(token, updates, intents, flags);
    }
}
