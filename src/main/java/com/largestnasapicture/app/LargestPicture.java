package com.largestnasapicture.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class LargestPicture {

    private static final String nasaUrlStr = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=10&api_key=DEMO_KEY";
    private static String maxImageUrlStr = null;
    private static int maxContentLength = 0;

    public static void main(String[] args) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(nasaUrlStr)).build();
        HttpClient client = HttpClient.newBuilder().build();


        HttpResponse<String> response = null;

        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonValueToString = response.body();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            PhotosIncluder photosIncluder = objectMapper.readValue(jsonValueToString.getBytes(), PhotosIncluder.class);

            for (int i = 0; i < photosIncluder.getPhotos().length; i++) {
                maxImageUrlStr = photosIncluder.getPhotos()[i].getImgSrc();
                HttpRequest requestInner = HttpRequest.newBuilder().uri(URI.create(maxImageUrlStr))
                        .GET()
                        .build();
                HttpClient clientInner = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
                HttpResponse responseInner = clientInner.send(requestInner, HttpResponse.BodyHandlers.ofString());

                int contentLength = Integer.parseInt(responseInner.headers().firstValue("Content-Length").orElse("Unknown"));
                if (maxContentLength < contentLength) {
                    maxContentLength = contentLength;
                }
            }
        } catch (IOException | InterruptedException e) {
            log.error("Exception occurred {}", e.getMessage());
        }
        log.info("\nMax Image URL: {};\nMax Content-Length: {}", maxImageUrlStr, maxContentLength);
    }
}
