package teamummmm.musiq.spotify;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
import org.apache.hc.core5.http.ParseException;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SpotifyService {
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
            System.out.println("\nError: " + e.getMessage());
            return null;
        }
    }

    public AudioFeatures getAudioFeatures(String musicId) {
        final String accessToken = spotifyToken.getToken();

        final SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(accessToken)
                .build();

        final GetAudioFeaturesForTrackRequest getAudioFeaturesForTrackRequest = spotifyApi
                .getAudioFeaturesForTrack(musicId)
                .build();

        try {
            return getAudioFeaturesForTrackRequest.execute();
        } catch (IOException | SpotifyWebApiException | ParseException e) {
            System.out.println("\nError: " + e.getMessage());
            return null;
        }
    }
}
