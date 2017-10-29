package newsongs.fr.newsongs.Models;

/**
 * Created by antoine on 17/10/17.
 */

public class Musique {
    private int idmusique;
    private String titre;
    private String auteur;
    private String datecreation;
    private String urlpreview;

    public int getIdmusique() {
        return idmusique;
    }

    public void setIdmusique(int idmusique) {
        this.idmusique = idmusique;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public String getUrlpreview() {
        return urlpreview;
    }

    public void setUrlpreview(String urlpreview) {
        this.urlpreview = urlpreview;
    }
}
