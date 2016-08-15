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
import doapps.marcogreen.session.SessionManager;
import doapps.marcogreen.utils.Constants;
import doapps.marcogreen.widget.WaterProgress;


/**
 * Created by Bryam Soto on 10/08/2016.
 */
public class FragmentPower extends Fragment {

    private final String TAG = getClass().getSimpleName();

    private View root;
    private WaterProgress waterProgress;
    private Button btnClean;
    private TextView tvScore, tvGramos;
    private SessionManager sessionManager;
    private Long tiempoInicial, tiempoActual, milisegundos;
    private int progress = 99, segundos, minutos;
    private boolean flagLoad, flagClean;
    private double contamination, decCont = 0;
    private float gramosLimpiados;

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
        tvGramos = (TextView) root.findViewById(R.id.tv_gramos);

        sessionManager = SessionManager.getInstance(getContext());
        tiempoInicial = sessionManager.getDataMilliseconds();
        gramosLimpiados = sessionManager.getCleanedGrams();

        tvGramos.setText(new DecimalFormat("##.##").format(gramosLimpiados));

        waterProgress.setProgress(progress);

        flagLoad = true;
        flagClean = false;
        loadContainer();
        cleanContainer();

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.setDataMilliseconds(Calendar.getInstance().getTimeInMillis());
                tiempoInicial = sessionManager.getDataMilliseconds();
                flagClean = true;
                flagLoad = false;
                decCont = contamination / 100;
                gramosLimpiados = (float) (gramosLimpiados + contamination);
                sessionManager.setCleanedGrams(gramosLimpiados);
                tvGramos.setText(new DecimalFormat("##.##").format(gramosLimpiados));
                if (progress > 99) {
                    progress = 99;
                }
            }
        });

        return root;
    }

    /**
     * Thread methods
     **/
    private void loadContainer() {
        Thread loadThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    if (!flagClean) {
                        tiempoActual = Calendar.getInstance().getTimeInMillis();
                        milisegundos = tiempoActual - tiempoInicial;
                        segundos = (int) (milisegundos / 1000);
                        minutos = (int) (milisegundos / (60 * 1000));
                        progress = (int) (segundos * 100) / 60;
                        contamination = segundos * Constants.CO2;
                        try {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tvScore.setText(new DecimalFormat("##.##").format(contamination) + " gr. de CO2\ncontaminado");
                                    if (progress < 99 && flagLoad) {
                                        waterProgress.setProgress(progress);
                                    } else {
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
        loadThread.start();
    }

    private void cleanContainer() {
        Thread cleanThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    Log.e(TAG, contamination + "");
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (progress > 0 && flagClean && !flagLoad) {
                                    progress--;
                                    contamination = contamination - decCont;
                                    waterProgress.setProgress(progress);
                                    tvScore.setText(new DecimalFormat("##.##").format(contamination) + " gr. de CO2\ncontaminado");
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
        cleanThread.start();
    }

}
