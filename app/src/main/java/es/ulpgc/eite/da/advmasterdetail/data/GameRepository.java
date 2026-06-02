package es.ulpgc.eite.da.advmasterdetail.data;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.database.GameDao;
import es.ulpgc.eite.da.advmasterdetail.database.GameDatabase;

public class GameRepository implements GameRepositoryContract {

    public static String TAG = GameRepository.class.getSimpleName();

    public static final String DB_FILE = "gamevault.db";
    public static final String JSON_FILE = "games.json";
    public static final String JSON_ROOT = "games";

    private static GameRepository INSTANCE;

    private Context context;
    private GameDatabase database;

    public static GameRepositoryContract getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new GameRepository(context);
        }

        return INSTANCE;
    }

    private GameRepository(Context context) {
        this.context = context;

        database = Room.databaseBuilder(
                context,
                GameDatabase.class,
                DB_FILE
        ).build();
    }

    @Override
    public void loadGames(final FetchGamesDataCallback callback) {

        AsyncTask.execute(() -> {

            boolean error = false;

            /*
             * Solo se lee el JSON si la tabla games está vacía.
             * Si ya hay videojuegos en Room, NO se vuelve a leer games.json.
             */
            if (getGameDao().countGames() == 0) {
                error = !loadGamesFromJSON(loadJSONFromAsset());
            }

            if (callback != null) {
                callback.onGamesDataFetched(error);
            }
        });
    }

    @Override
    public void getGameList(final GetGameListCallback callback) {

        AsyncTask.execute(() -> {
            if (callback != null) {
                callback.setGameList(getGameDao().loadGames());
            }
        });
    }

    @Override
    public void getGame(final int id, final GetGameCallback callback) {

        AsyncTask.execute(() -> {
            if (callback != null) {
                callback.setGame(getGameDao().loadGame(id));
            }
        });
    }

    private GameDao getGameDao() {
        return database.gameDao();
    }

    private boolean loadGamesFromJSON(String json) {
        Log.e(TAG, "loadGamesFromJSON()");

        if (json == null) {
            return false;
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();

        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray(JSON_ROOT);

            if (jsonArray.length() > 0) {

                final List<GameItem> games = Arrays.asList(
                        gson.fromJson(jsonArray.toString(), GameItem[].class)
                );

                for (GameItem game : games) {
                    getGameDao().insertGame(game);
                }

                return true;
            }

        } catch (JSONException error) {
            Log.e(TAG, "error: " + error);
        }

        return false;
    }

    private String loadJSONFromAsset() {

        String json = null;

        try {

            InputStream inputStream = context.getAssets().open(JSON_FILE);
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }

            reader.close();
            json = stringBuilder.toString();

        } catch (IOException error) {
            Log.e(TAG, "error: " + error);
        }

        return json;
    }
}