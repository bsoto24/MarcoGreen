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

import java.util.Calendar;

import doapps.marcogreen.R;
import doapps.marcogreen.session.SessionManager;
import doapps.marcogreen.widget.WaterProgress;


/**
 * Created by Bryam Soto on 10/08/2016.
 */
public class FragmentPower extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private WaterProgress waterProgress;
    private Button btnClean;
    private View root;
    private TextView tvScore, tvContamination;
    private int score = 1;
    private SessionManager sessionManager;
    private double tiempoInicial, milisegundos;
    private int segundos, minutos;

    private boolean flagLoad, flagClean;

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
        tvContamination = (TextView) root.findViewById(R.id.tv_contamination);

        sessionManager = SessionManager.getInstance(getContext());
        tiempoInicial = sessionManager.getDataMilliseconds();

        waterProgress.setScore(100);
        waterProgress.setProgress(score);

        flagLoad = true;
        flagClean = false;

        initCounter();
        loadContainer();
        cleanContainer();

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flagClean = true;
                flagLoad = false;
            }
        });

        return root;
    }

    /**Thread methods**/
    private void loadContainer() {
        Thread loadThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (score < 99 && flagLoad && !flagClean) {
                                    waterProgress.setProgress(score);
                                    tvScore.setText(score+"/100");
                                    score++;
                                } else {
                                    flagLoad = false;
                                }
                            }
                        });
                        sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        loadThread.start();
    }

    private void cleanContainer() {
        Thread tClean = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (score > 0 && flagClean && !flagLoad) {
                                    waterProgress.setProgress(score);
                                    tvScore.setText(score+"/100");
                                    score--;
                                } else {
                                    flagClean = false;
                                    flagLoad = true;
                                }
                            }
                        });
                        sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        tClean.start();
    }

    private void initCounter(){
        Thread counterThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        milisegundos = Calendar.getInstance().getTimeInMillis() - tiempoInicial;
                        segundos = (int) milisegundos / 1000;
                        minutos = (int) milisegundos / (60 * 1000);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tvContamination.setText("min: " + minutos + "\nseg: " + segundos);
                            }
                        });
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        counterThread.start();
    }
}
