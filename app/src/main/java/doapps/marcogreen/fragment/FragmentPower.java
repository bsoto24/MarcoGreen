package doapps.marcogreen.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Calendar;

import doapps.marcogreen.R;
import doapps.marcogreen.entity.User;
import doapps.marcogreen.session.SessionManager;
import doapps.marcogreen.settings.Constants;
import doapps.marcogreen.widget.WaterProgress;


/**
 * Created by Bryam Soto on 10/08/2016.
 */
public class FragmentPower extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private View root;
    private WaterProgress waterProgress;
    private Button btnClean;
    private TextView tvScore, tvCleanedGrams, tvTitle, tvGrade, tvCleanedDays;
    private SessionManager sessionManager;
    private int progress = 99, cleanedDays, seconds;
    private boolean flagLoad, flagClean;
    private float contamination, cleanedGrams, minutes, decCont = 0;
    private String lastCleanedDate;
    private User user;

    private Thread loadThread = new Thread() {
        @Override
        public void run() {
            while (true) {
                if (!flagClean) {
                    seconds = sessionManager.getSecondsActive();
                    minutes = seconds / Constants.SECONDS_MINUTE;
                    progress = (int) (minutes * 100) / Constants.MINUTES_DAY;
                    contamination = (float) (minutes * Constants.CO2);
                    try {
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvScore.setText(new DecimalFormat("##.##").format(contamination) + " gr. de CO2\ncontaminado");
                                    if (progress < 99 && flagLoad) {
                                        waterProgress.setProgress(progress);
                                    } else {
                                        waterProgress.setProgress(99);
                                        flagLoad = false;
                                    }
                                }
                            });
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    };

    private Thread cleanThread = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    if (progress > 0 && flagClean && !flagLoad) {
                        progress--;
                        contamination = contamination - decCont;
                        if (getActivity() != null)
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    waterProgress.setProgress(progress);
                                    tvScore.setText(new DecimalFormat("##.##").format(contamination) + " gr. de CO2\ncontaminado");
                                }
                            });
                    } else {
                        flagClean = false;
                        flagLoad = true;
                    }
                    sleep(60);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (root == null) {
            root = inflater.inflate(R.layout.fragment_power, container, false);
        }
        ViewGroup parent = (ViewGroup) root.getParent();
        if (parent != null) {
            parent.removeView(root);
        }
        waterProgress = (WaterProgress) root.findViewById(R.id.water_progress);
        btnClean = (Button) root.findViewById(R.id.btn_clean);
        tvScore = (TextView) root.findViewById(R.id.tv_score);
        tvCleanedGrams = (TextView) root.findViewById(R.id.tv_cleaned_grams);
        tvCleanedDays = (TextView) root.findViewById(R.id.tv_cleaned_days);
        tvTitle = (TextView) root.findViewById(R.id.tv_title);
        tvGrade = (TextView) root.findViewById(R.id.tv_grade);

        sessionManager = SessionManager.getInstance(getContext());

        cleanedGrams = sessionManager.getCleanedGrams();
        cleanedDays = sessionManager.getCleanedDays();
        lastCleanedDate = sessionManager.getCleanedDate();

        loadUserInfo();
        runThreads();
        onClickMethods();

        return root;
    }

    private void onClickMethods() {
        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!flagClean) {
                    flagClean = true;
                    flagLoad = false;
                    if (progress > 99) {
                        progress = 99;
                    }
                    cleanedGrams = (float) (cleanedGrams + contamination);
                    tvCleanedGrams.setText(new DecimalFormat("##.##").format(cleanedGrams));
                    sessionManager.setCleanedGrams(cleanedGrams);
                    decCont = contamination / 100;
                    sessionManager.setSecondsActive(0);
                    Calendar calendar = Calendar.getInstance();
                    String cleanedDate = calendar.get(Calendar.MONTH) + " " + calendar.get(Calendar.DAY_OF_MONTH) + " " + calendar.get(Calendar.YEAR);
                    if (!cleanedDate.equals(lastCleanedDate)) {
                        cleanedDays++;
                        sessionManager.setCleanedDays(cleanedDays);
                        sessionManager.setCleanedDate(cleanedDate);
                    }
                    loadUserInfo();
                }
            }
        });
    }

    private void runThreads() {
        flagLoad = true;
        flagClean = false;
        loadContainer();
        cleanContainer();
    }

    private void loadUserInfo() {
        if (progress > 99) {
            progress = 99;
        }
        user = new User(cleanedDays, getContext());
        tvTitle.setText(user.getTitle());
        tvGrade.setText("GRADO " + user.getGrade());
        tvCleanedDays.setText(user.getCleanedDays() + "");
        tvCleanedGrams.setText(new DecimalFormat("##.##").format(cleanedGrams));
    }

    /**
     * Thread methods
     **/
    private void loadContainer() {
        try{
            loadThread.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void cleanContainer() {
        try {
            cleanThread.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
