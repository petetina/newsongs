package newsongs.fr.newsongs.Models;

import java.util.List;

/**
 * Created by antoine on 17/10/17.
 */

public class Playlist {
    private long idplaylist;
    private String nom;
    private String datecreation;
    private String urlimage;
    private List<Musique> musiques;

    public long getIdplaylist() {
        return idplaylist;
    }

    public void setIdplaylist(long idplaylist) {
        this.idplaylist = idplaylist;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(String datecreation) {
        this.datecreation = datecreation;
    }

    public String getUrlimage() {
        return urlimage;
    }

    public void setUrlimage(String urlimage) {
        this.urlimage = urlimage;
    }

    public List<Musique> getMusiques() {
        return musiques;
    }

    public void setMusiques(List<Musique> musiques) {
        this.musiques = musiques;
    }
}
