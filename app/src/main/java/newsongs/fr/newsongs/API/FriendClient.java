package newsongs.fr.newsongs.API;

import java.util.List;

import newsongs.fr.newsongs.Models.Reponse;
import newsongs.fr.newsongs.Models.Utilisateur;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by antoine on 02/11/17.
 */

public interface FriendClient {

    @GET("utilisateur/{id}/friends")
    Call<List<Utilisateur>> getFriendsById(@Path("id") int id);

    @GET("utilisateur/{id}/invitations")
    Call<List<Utilisateur>> getInvitations(@Path("id") int id);

    @POST("utilisateur/{idutilisateur1}/friend/{idutilisateur2}")
    Call<Reponse> inviteFriend(@Path("idutilisateur1")int idutilisateur1, @Path("idutilisateur2")int idutilisateur2);

    @PUT("utilisateur/{idutilisateur1}/friend/accept/{idutilisateur2}")
    Call<Reponse> acceptInvitation(@Path("idutilisateur1")int idutilisateur1, @Path("idutilisateur2")int idutilisateur2);

    //Cette méthode est utilisé aussi bien pour supprimer un ami de notre liste d'amis,
    //mais aussi pour refuser une invitation
    @DELETE("utilisateur/{id}/friend/{idf}")
    Call<Reponse> deleteFriend(@Path("id") int idUser, @Path("idf") int idFriend);
}
