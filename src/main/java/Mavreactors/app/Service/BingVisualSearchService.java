package Mavreactors.app.Service;

import java.util.*;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

// HttpClient libraries

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

    public String searchByImage(String imageBase64) throws IOException {
        File file = new File( "path.jpg" );
        byte[] bytes = Base64.decodeBase64(imageBase64);
        FileUtils.writeByteArrayToFile( file, bytes );

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpEntity entity = MultipartEntityBuilder
                .create()
                //.addBinaryBody("image", file)
                .addBinaryBody("image", new File("C:\\Users\\Mauricio Monroy\\Downloads\\prueba.jpg"))
                .build();

        HttpPost httpPost = new HttpPost(endpoint);
        httpPost.setHeader("Ocp-Apim-Subscription-Key", subscriptionKey);
        httpPost.setEntity(entity);

        System.out.println(subscriptionKey);
        System.out.println(endpoint);

        HttpResponse response = httpClient.execute(httpPost);
        InputStream stream = response.getEntity().getContent();

        String json = new Scanner(stream).useDelimiter("\\A").next();
        return prettify(json);
    }

    public static String prettify(String json_text) {
        JsonParser parser = new JsonParser();
        JsonObject json = parser.parse(json_text).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(json);
    }
}

