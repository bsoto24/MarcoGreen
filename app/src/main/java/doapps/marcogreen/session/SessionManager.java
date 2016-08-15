package doapps.marcogreen.session;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

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

    public static final String DATE_MILLISECONDS = "dateMilliseconds";
    public static final String CLEANED_GRAMS = "cleanedGrams";
    public static final String CLEANED_DATE = "cleanedDate";
    public static final String CLEANED_DAYS = "cleanedDays";

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
        return preferences.getLong(DATE_MILLISECONDS, 0);
    }

    public void setDataMilliseconds(long dataMilliseconds) {
        editor.putLong(DATE_MILLISECONDS, dataMilliseconds);
        editor.commit();
    }

    public float getCleanedGrams() {
        return preferences.getFloat(CLEANED_GRAMS, 0);
    }

    public void setCleanedGrams(float cleanedGrams) {
        editor.putFloat(CLEANED_GRAMS, cleanedGrams);
        editor.commit();
    }

    public String getCleanedDate() {
        return preferences.getString(CLEANED_DATE, "");
    }

    public void setCleanedDate(String cleanedDay) {
        editor.putString(CLEANED_DATE, cleanedDay);
        editor.commit();
    }

    public int getCleanedDays() {
        return preferences.getInt(CLEANED_DAYS, 0);
    }

    public void setCleanedDays(int cleanedDays) {
        editor.putInt(CLEANED_DAYS, cleanedDays);
        editor.commit();
    }

}
