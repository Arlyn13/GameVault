package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import es.ulpgc.eite.da.advmasterdetail.data.FavoriteGameItem;
import es.ulpgc.eite.da.advmasterdetail.data.GameItem;
import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

@Database(
        entities = {
                UserItem.class,
                GameItem.class,
                FavoriteGameItem.class
        },
        version = 1,
        exportSchema = false
)
public abstract class GameDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract GameDao gameDao();

    public abstract FavoriteGameDao favoriteGameDao();
}