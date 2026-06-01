package es.ulpgc.eite.da.advmasterdetail.data;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(
        tableName = "game_likes",
        primaryKeys = {"user_id", "game_id"},
        foreignKeys = {
                @ForeignKey(
                        entity = UserItem.class,
                        parentColumns = "id",
                        childColumns = "user_id",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = GameItem.class,
                        parentColumns = "id",
                        childColumns = "game_id",
                        onDelete = CASCADE
                )
        },
        indices = {
                @Index("user_id"),
                @Index("game_id")
        }
)
public class FavoriteGameItem {

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "game_id")
    public int gameId;
}