package doapps.marcogreen.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Bryam Soto on 09/08/2016.
 */
public class SessionManager {
    private static final String PREFERENCE_NAME = "FreeTechCO2";
    private int PRIVATE_MODE = 0;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private static SessionManager sessionManager = null;

    public static final String DATA_MILLISECONDS = "dataMilliseconds";

    private SessionManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREFERENCE_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public static SessionManager getInstance(Context context) {
        if (sessionManager == null) {
            sessionManager = new SessionManager(context);
        }
        return sessionManager;
    }

    public float getDataMilliseconds() {
        return preferences.getFloat(DATA_MILLISECONDS, 0);
    }

    public void setDataMilliseconds(float dataMilliseconds) {
        editor.putFloat(DATA_MILLISECONDS, dataMilliseconds);
        editor.commit();
    }

}
