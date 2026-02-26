public class TranscriptRequest {
    String audio_url;
    String[] speech_models;

    TranscriptRequest(String audioUrl,
                      String[] speechModels) {
        this.audio_url = audioUrl;
        this.speech_models = speechModels;
    }
}
