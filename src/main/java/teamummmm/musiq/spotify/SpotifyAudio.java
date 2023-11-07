package teamummmm.musiq.spotify;

import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AudioFeatures;
import se.michaelthelin.spotify.requests.data.tracks.GetAudioFeaturesForTrackRequest;


import java.io.IOException;
@Service
@RequiredArgsConstructor
public class SpotifyAudio {
    private final SpotifyToken spotifyToken;

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
            e.printStackTrace();
            return null;
        }
    }
}
