package newsongs.fr.newsongs.API;

import java.util.List;

import newsongs.fr.newsongs.Models.Reponse;
import newsongs.fr.newsongs.Models.Utilisateur;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UtilisateurClient {
    @GET("utilisateur")
    Call<List<Utilisateur>> findAll();

    @GET("utilisateur/{id}")
    Call<Utilisateur> findById(@Path("id") int id);

    @POST("utilisateur")
    Call<Reponse> createUser();
}
