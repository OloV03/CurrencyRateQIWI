package com.IlyaJukov.FreshQIWI.HTTP;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Formatter;
import java.util.HashMap;

public class ControllerHTTP {

    public Document get(String uri){
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

        Path responsePath = null;
        try {
            responsePath = client.send(request, HttpResponse.BodyHandlers.ofFile(Path.of("response.xml"))).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return getResponseFile(responsePath);
    }

    public Document get(String uri, HashMap<String, String> queryParameters) {
        uri = addQueryParameters(uri, queryParameters).trim();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(uri))
                .build();

        Path responsePath = null;
        try {
            responsePath = client.send(request, HttpResponse.BodyHandlers.ofFile(Path.of("cur.xml"))).body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return getResponseFile(responsePath);
    }

    public HashMap<String, String> newParams() {
        HashMap<String, String> params = new HashMap<>();
        return params;
    }

    private String addQueryParameters(String uri, HashMap<String, String> parameters) {
        StringBuilder result = new StringBuilder(uri);
        result.append("?");

        for (String key : parameters.keySet()) {
            result.append(new Formatter().format("%s=%s", key, parameters.get(key)));
            result.append("&");
        }
        result.deleteCharAt(result.length() - 1);

        return result.toString();
    }

    private Document getResponseFile(Path path) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            return db.parse(path.toString());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
