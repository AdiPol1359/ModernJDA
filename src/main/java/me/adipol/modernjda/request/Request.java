package me.adipol.modernjda.request;

import com.google.gson.Gson;
import lombok.Builder;
import lombok.Singular;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

@Builder
public class Request {

    private final String url;
    private final RequestMethod method;

    @Singular
    private final Map<String, String> headers;

    @Singular
    private final Map<String, String> bodies;

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @SneakyThrows
    private HttpRequest buildHttpRequest() {
        HttpRequest.Builder builder = HttpRequest.newBuilder(new URI(url));

        switch(method) {
            case GET -> builder.GET();
            case DELETE -> builder.DELETE();
            case POST, PUT -> {
                HttpRequest.BodyPublisher bodyPublisher = bodies.size() > 0 ?
                        HttpRequest.BodyPublishers.ofString(gson.toJson(bodies)) :
                        HttpRequest.BodyPublishers.noBody();

                builder.method(method.toString(), bodyPublisher);
            }
        }

        headers.forEach(builder::header);

        return builder.build();
    }

    @SneakyThrows
    public <T> T send(Class<T> clazz) {
        HttpResponse<String> response = client.send(buildHttpRequest(), HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(response.body(), clazz);
    }

    public <T> void sendAsync(Class<T> clazz) {
        //TODO
    }
}
