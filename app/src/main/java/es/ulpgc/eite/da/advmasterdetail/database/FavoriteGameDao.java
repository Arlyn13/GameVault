package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.data.FavoriteGameItem;
import es.ulpgc.eite.da.advmasterdetail.data.GameItem;

@Dao
public interface FavoriteGameDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(FavoriteGameItem favorite);

    @Query("DELETE FROM game_likes WHERE user_id = :userId AND game_id = :gameId")
    void deleteFavorite(int userId, int gameId);

    @Query("SELECT COUNT(*) FROM game_likes WHERE user_id = :userId AND game_id = :gameId")
    int isFavorite(int userId, int gameId);

    @Query("SELECT COUNT(*) FROM game_likes WHERE game_id = :gameId")
    int countLikes(int gameId);

    @Query(
            "SELECT games.* FROM games " +
                    "INNER JOIN game_likes ON games.id = game_likes.game_id " +
                    "WHERE game_likes.user_id = :userId"
    )
    List<GameItem> loadFavoriteGames(int userId);
}