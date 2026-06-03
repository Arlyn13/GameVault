package es.ulpgc.eite.da.advmasterdetail.data;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "games")
public class GameItem {

    @PrimaryKey
    public int id;

    public String title;
    public String genre;
    public String platform;
    public int year;
    public String developer;
    public String description;
    public String image;

    @Ignore
    public int totalFavorites;

    @Ignore
    public boolean favorite;

    @Override
    public String toString() {
        return title;
    }
}