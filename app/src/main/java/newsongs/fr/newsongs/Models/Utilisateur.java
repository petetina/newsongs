package newsongs.fr.newsongs.Models;

/**
 * Created by antoine on 07/10/17.
 */

public class Utilisateur {
    private int idutilisateur;
    private int getIdutilisateurdeezer;
    private String mail;
    private String pseudo;
    private String motdepasse;

    public int getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(int idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public int getGetIdutilisateurdeezer() {
        return getIdutilisateurdeezer;
    }

    public void setGetIdutilisateurdeezer(int getIdutilisateurdeezer) {
        this.getIdutilisateurdeezer = getIdutilisateurdeezer;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getMotdepasse() {
        return motdepasse;
    }

    public void setMotdepasse(String motdepasse) {
        this.motdepasse = motdepasse;
    }
}