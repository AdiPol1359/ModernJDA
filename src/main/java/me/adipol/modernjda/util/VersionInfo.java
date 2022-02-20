package me.adipol.modernjda.util;

import com.google.gson.JsonArray;
import me.adipol.modernjda.request.Request;
import me.adipol.modernjda.request.RequestMethod;

public interface VersionInfo {
    String VERSION = "1.0.0";

    static boolean checkUpdate() {
        JsonArray jsonArray = Request.builder()
                .url("https://api.github.com/repos/dzikoysk/reposilite/releases")
                .method(RequestMethod.GET)
                .build()
                .send(JsonArray.class);

        return !jsonArray.get(0).getAsJsonObject().get("tag_name").getAsString().equals(VERSION);
    }
}
