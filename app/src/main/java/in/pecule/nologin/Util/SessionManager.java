package in.pecule.nologin.Util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import in.pecule.nologin.Activity.LoginActivity;
import in.pecule.nologin.BO.User;

/**
 * Created by makarand on 11/24/17.
 */

public class SessionManager {
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_EMAIL = "email";
    private static final String PREF_NAME = "NoLoginPref";
    private static final String IS_LOGIN = "isLoggedIn";
    private static final String DEVICE_ID = "deviceId";

    int PRIVATE_MODE = 0;

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void login(User user){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putInt(KEY_AGE, user.getAge());
        editor.commit();
    }

    public User getLogin(){
        return new User(
                pref.getString(KEY_NAME, ""),
                pref.getString(KEY_PHONE, ""),
                pref.getString(KEY_EMAIL, ""),
                pref.getInt(KEY_AGE, 0)
        );
    }
    public boolean checkLogin() {
        if (!this.isLoggedIn()) {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            context.startActivity(intent);
            ((Activity) context).finish();

            return false;
        } else {
            return true;
        }
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logout(){
        editor.clear();
        editor.commit();
    }

}
