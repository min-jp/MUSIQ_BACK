package teamummmm.musiq.spotify;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;

@Service
@PropertySource("classpath:application-secret.properties")
public class SpotifyToken {
    @Value("${spotify.client.id}")
    private String clientId;
    @Value("${spotify.client.secret}")
    private String clientSecret;

    private String accessToken;  // 엑세스 토큰

    @Scheduled(fixedRate = 3540000)  // 59분마다 실행
    private void generateToken() {  // 토큰 생성
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .build();  // SpotifyApi 생성

        final ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials()
                .build();  // ClientCredentialsRequest 생성

        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();  // 실행
            accessToken = clientCredentials.getAccessToken();  // 엑세스 토큰
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("\nError: " + e.getMessage());
        }
    }

    public String getToken() {  // 토큰 가져오기
        return accessToken;
    }
}
