package newsongs.fr.newsongs.API;

import java.util.List;

import newsongs.fr.newsongs.Models.Playlist;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by antoine on 20/10/17.
 */

public interface PlaylistClient {
    @GET("utilisateur/{id}/playlists")
    Call<List<Playlist>> getPlaylists(@Path("id") int idutilisateur);
}
