package me.adipol.modernjda.request;

import lombok.Builder;
import lombok.Getter;
import lombok.SneakyThrows;

import java.net.URI;
import java.net.http.HttpClient;

@Builder
@Getter
public class HttpRequest {
    private final String url;
    private final RequestMethod method;

    @SneakyThrows
    public void makeRequest() {
        HttpClient client = HttpClient.newHttpClient();
        java.net.http.HttpRequest request = java.net.http.HttpRequest.newBuilder(new URI(url))
                .GET()
                .build();

        var test = client.send(request, null);
    }
}
