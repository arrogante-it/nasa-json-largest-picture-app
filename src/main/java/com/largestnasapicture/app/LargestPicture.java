package com.largestnasapicture.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LargestPicture {

    public static final String url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=10&api_key=DEMO_KEY";

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String jsonValueToString = response.body();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        PhotosIncluder photosIncluder = objectMapper.readValue(jsonValueToString.getBytes(), PhotosIncluder.class);

        int maxContentLength = Integer.MIN_VALUE;

        for (int i = 0; i < photosIncluder.getPhotos().length; i++) {
            String img_srcURI = photosIncluder.getPhotos()[i].getImg_src();
            HttpRequest requestInner = HttpRequest.newBuilder().uri(URI.create(img_srcURI))
                    .GET()
                    .build();
            HttpClient clientInner = HttpClient.newBuilder().followRedirects(HttpClient.Redirect.ALWAYS).build();
            HttpResponse responseInner = clientInner.send(requestInner, HttpResponse.BodyHandlers.ofString());

            int contentLength = Integer.parseInt(responseInner.headers().firstValue("Content-Length").orElse("Unknown"));
            if (maxContentLength < contentLength) {
                maxContentLength = contentLength;
            }
        }

        System.out.println(maxContentLength);
    }
}
