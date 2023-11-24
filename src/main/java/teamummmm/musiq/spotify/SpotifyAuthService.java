package teamummmm.musiq.spotify;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.model_objects.specification.User;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.net.URI;

@Service
@PropertySource("classpath:application-secret.properties")
public class SpotifyAuthService {

    @Value("${spotify.client.id}")
    private String clientId;
    @Value("${spotify.client.secret}")
    private String clientSecret;
    /*
    @Value("${spotify.client.redirect-uri}")
    private String redirectUriString;
    */
    private final String redirectUriString="http://localhost:8080/auth/callback";  // 로컬환경 테스트
    private final URI redirectUri = SpotifyHttpManager.makeUri(redirectUriString);  // 리다이렉트 URI

    public String spotifyAuthCodeUri() {  // 로그인 uri
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();  // SpotifyApi 객체 생성

        final AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
//              .state("x4xkmn9pu3j6ukrs8n")
//              .scope("user-read-birthdate,user-read-email")
//              .show_dialog(true)
                .build();  // AuthorizationCodeUriRequest 객체 생성

        final URI uri = authorizationCodeUriRequest.execute();  // 실행

        return uri.toString();  // URI 리턴
    }

    public SpotifyApi spotifyAuthCode(final String code) {  // token 발행
        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(clientId)
                .setClientSecret(clientSecret)
                .setRedirectUri(redirectUri)
                .build();  // SpotifyApi 객체 생성

        final AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
                .build();  // code로 AuthorizationCodeRequest 객체 생성

        try {
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();  // 실헹

            return SpotifyApi.builder()
                    .setAccessToken(authorizationCodeCredentials.getAccessToken())  // 엑세스 토큰
                    .setRefreshToken(authorizationCodeCredentials.getRefreshToken())  // 리프레시 토큰
                    .build();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("\nError: " + e.getMessage());
            return null;
        }
    }

    public User getSpotifyUser(final SpotifyApi userSpotifyApi) {  // 스포티파이 유저 정보 가져오기
        final GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = userSpotifyApi.getCurrentUsersProfile()
                .build();  // GetCurrentUsersProfileRequest 객체 생성

        try {
            return getCurrentUsersProfileRequest.execute();  // 실행 후 유저 리턴
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("\nError: " + e.getMessage());
            return null;
        }
    }
}
