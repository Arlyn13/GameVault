package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.data.GameItem;

@Dao
public interface GameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGame(GameItem game);

    @Update
    void updateGame(GameItem game);

    @Delete
    void deleteGame(GameItem game);

    @Query("SELECT * FROM games")
    List<GameItem> loadGames();

    @Query("SELECT * FROM games WHERE id = :id LIMIT 1")
    GameItem loadGame(int id);

    @Query("SELECT COUNT(*) FROM games")
    int countGames();
}