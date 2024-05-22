package Mavreactors.app.Service;

import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// HttpClient libraries

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BingVisualSearchService {

    @Value("${bing.search.api.endpoint}")
    private String endpoint;

    @Value("${bing.search.api.key}")
    private String subscriptionKey;

    public String searchByImage(String imageBase64) throws IOException, UnirestException {

        File file = new File("path.jpg");
        byte[] bytes = Base64.decodeBase64(imageBase64);

        // Llama a la API de Bing Visual Search
        Unirest.setTimeouts(0, 0);
        com.mashape.unirest.http.HttpResponse<String> response = Unirest.post(endpoint)
                .header("Ocp-Apim-Subscription-Key", subscriptionKey)
                .field("image", file)
                .asString();

        return prettify(response.getBody());
    }

    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(json_text).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}

