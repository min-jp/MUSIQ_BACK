package teamummmm.musiq.spotify;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
@PropertySource("classpath:application-secret.properties")
public class SpotifyToken {
    /*
    // json 파일 사용하는 경우
    private static final FileReader fileReader;  // json 파일 읽어옴

    static {
        FileReader reader = null;
        try {
            reader = new FileReader("src/main/resources/spotify-api-key.json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        fileReader = reader;
    }

    private final JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();  //json object로 변환

    private final String clientId = jsonObject.get("client_id").getAsString();
    private final String clientSecret = jsonObject.get("client_secret").getAsString();
     */


    // 스프링 사용하는 경우
    @Value("${spotify.client.id}")
    private String clientId;
    @Value("${spotify.client.secret}")
    private String clientSecret;

    // TODO
    //  토큰 저장, 스케줄링 추가
    // 토큰 가져옴
    public String getToken() {
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();  // SpotifyApi 생성

        final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();  // ClientCredentialsRequest 생성

        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();  // 실행
            return clientCredentials.getAccessToken();  // 엑세스 토큰
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
