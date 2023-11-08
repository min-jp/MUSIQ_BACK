package teamummmm.musiq.spotify;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.apache.hc.core5.http.ParseException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SpotifySearch {
    private final SpotifyToken spotifyToken;

    public Paging<Track> searchTracks(String q) {
        final String accessToken = spotifyToken.getToken();

        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();

        final SearchTracksRequest searchTracksRequest = spotifyApi
                .searchTracks(q)
                .market(CountryCode.KR)
                .build();

        try {
            return searchTracksRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
