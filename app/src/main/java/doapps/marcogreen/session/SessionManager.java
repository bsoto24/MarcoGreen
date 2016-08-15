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
    public static final String CLEANED_GRAMS = "cleanedGrams";

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

    public long getDataMilliseconds() {
        return preferences.getLong(DATA_MILLISECONDS, 0);
    }

    public void setDataMilliseconds(long dataMilliseconds) {
        editor.putLong(DATA_MILLISECONDS, dataMilliseconds);
        editor.commit();
    }

    public float getCleanedGrams() {
        return preferences.getFloat(CLEANED_GRAMS, 0);
    }

    public void setCleanedGrams(float cleanedGrams) {
        editor.putFloat(CLEANED_GRAMS, cleanedGrams);
        editor.commit();
    }


}
