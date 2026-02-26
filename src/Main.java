import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static final String BASE_URL = "https://api.eu.assemblyai.com";
    private static final String API_KEY = "YOUR_ASSEMBLYAI_API_KEY";
    private static final String AUDIO = "https://github.com/liogedz/JavaAPI/blob/main/Jim_Carrey_Speech.mp3?raw=true";
    private static final String[] MODELS = {"universal-3-pro", "universal-2"};
    private static final Gson GSON = new GsonBuilder()
            .serializeNulls()
            .create();

    public static void main(String[] args) throws Exception {

        String url = BASE_URL + "/v2/transcript";
        TranscriptRequest request = new TranscriptRequest(AUDIO,
                MODELS);
        String jsonBody = GSON.toJson(request);

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization",
                        API_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> postResponse = client.send(postRequest,
                HttpResponse.BodyHandlers.ofString());

        TranscriptResponse postResult = GSON.fromJson(postResponse.body(),
                TranscriptResponse.class);

        String transcriptId = postResult.id;
        HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create(url + "/" + transcriptId))
                .header("Authorization",
                        API_KEY)
                .build();

        ScheduledExecutorService scheduler =
                Executors.newSingleThreadScheduledExecutor();

        scheduler.scheduleAtFixedRate(() -> {
                    try {
                        HttpResponse<String> response =
                                client.send(getRequest,
                                        HttpResponse.BodyHandlers.ofString());

                        TranscriptResponse tr =
                                GSON.fromJson(response.body(),
                                        TranscriptResponse.class);

                        System.out.println("Status: " + tr.status);

                        if ("completed".equals(tr.status)) {
                            System.out.println("Text:\n" + tr.text);
                            scheduler.shutdown();
                        } else if ("error".equals(tr.status)) {
                            System.err.println("AssemblyAI error: " + tr.error);
                            scheduler.shutdown();
                        }

                    } catch (Exception e) {
                        System.err.println("Polling failed, retrying: " + e.getMessage());
                    }
                },
                0,
                1,
                TimeUnit.SECONDS);
    }
}
