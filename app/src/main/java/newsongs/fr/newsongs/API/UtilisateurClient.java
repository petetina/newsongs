package newsongs.fr.newsongs.API;

import java.util.List;

import newsongs.fr.newsongs.Models.Reponse;
import newsongs.fr.newsongs.Models.Utilisateur;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UtilisateurClient {

    @GET("utilisateur")
    Call<List<Utilisateur>> findAll();

    @GET("utilisateur/{id}")
    Call<Utilisateur> findById(@Path("id") int id);

    @GET("utilisateur/{id}/friends")
    Call<List<Utilisateur>> getFriendsById(@Path("id") int id);

    @FormUrlEncoded
    @POST("utilisateur")
    Call<Reponse> createUser(@Field("mail") String mail, @Field("pseudo") String pseudo, @Field("motdepasse") String motdepasse);
}
