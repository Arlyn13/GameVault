package es.ulpgc.eite.da.advmasterdetail.app;

import android.content.Context;
import android.content.SharedPreferences;

import es.ulpgc.eite.da.advmasterdetail.data.UserItem;

public class SessionManager {

    private static final String PREFS_NAME = "gamevault_session";

    private static final String KEY_LOGGED_IN = "logged_in";
    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USER_NAME = "user_name";
    private static final String KEY_USER_EMAIL = "user_email";

    private SharedPreferences preferences;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveLoggedUser(UserItem user) {
        preferences.edit()
                .putBoolean(KEY_LOGGED_IN, true)
                .putInt(KEY_USER_ID, user.id)
                .putString(KEY_USER_NAME, user.name)
                .putString(KEY_USER_EMAIL, user.email)
                .apply();
    }

    public void clearSession() {
        preferences.edit().clear().apply();
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(KEY_LOGGED_IN, false);
    }

    public int getUserId() {
        return preferences.getInt(KEY_USER_ID, -1);
    }

    public String getUserName() {
        return preferences.getString(KEY_USER_NAME, "");
    }

    public String getUserEmail() {
        return preferences.getString(KEY_USER_EMAIL, "");
    }
}