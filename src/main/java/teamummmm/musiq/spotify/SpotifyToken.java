package teamummmm.musiq.spotify;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
