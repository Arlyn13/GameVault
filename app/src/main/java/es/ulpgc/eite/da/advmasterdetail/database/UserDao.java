package es.ulpgc.eite.da.advmasterdetail.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    long insertUser(UserItem user);

    @Query("SELECT * FROM users")
    List<UserItem> loadUsers();

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    UserItem loadUserByEmail(String email);

    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    UserItem login(String email, String password);
}