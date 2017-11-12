package newsongs.fr.newsongs.API;

import java.util.List;

import newsongs.fr.newsongs.Models.Reponse;
import newsongs.fr.newsongs.Models.Utilisateur;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UtilisateurClient {

    @GET("login/{pseudo}/{motdepasse}")
    Call<Integer> login(@Path("pseudo")String pseudo, @Path("motdepasse")String motdepasse);

    @GET("utilisateur/{idutilisateur}/pseudo/{pseudo}")
    Call<List<Utilisateur>> findAllByPseudo(@Path("idutilisateur")int idutilisateur,@Path("pseudo") String pseudo);

    @FormUrlEncoded
    @POST("utilisateur")
    Call<Reponse> createUser(@Field("mail") String mail, @Field("pseudo") String pseudo, @Field("motdepasse") String motdepasse);

    @FormUrlEncoded
    @POST("utilisateur")
    Call<Reponse> createUser(@Field("mail") String mail, @Field("pseudo") String pseudo, @Field("motdepasse") String motdepasse, @Field("idutilisateurdeezer") long idutilisateurdeezer);

}
