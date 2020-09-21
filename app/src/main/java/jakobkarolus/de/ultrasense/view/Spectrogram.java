package jakobkarolus.de.ultrasense.view;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import jakobkarolus.de.ultrasense.R;
import jakobkarolus.de.ultrasense.algorithm.StftManager;

/**
 * Spectrogram view
 *
 * <br><br>
 * Created by Jakob on 12.08.2015.
 */
public class Spectrogram extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        menu.findItem(R.id.action_compute_stft).setVisible(false);
        menu.findItem(R.id.action_show_last).setVisible(false);

    }

    /**
     * create a new instance given the current STFT
     * @param stftManager reference to the StftManager
     * @return Spectrogram Fragment
     */
    public static Spectrogram newInstance(StftManager stftManager){
        Spectrogram spec = new Spectrogram();
        Bundle bundle = new Bundle();
        bundle.putSerializable("STFT_MANAGER", stftManager);
        spec.setArguments(bundle);
        return spec;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        assert getArguments() != null;
        StftManager stftManager = (StftManager) getArguments().getSerializable("STFT_MANAGER");
        View rootView = inflater.inflate(R.layout.fragment_spectrogram, container, false);
        TouchImageView spec = (TouchImageView) rootView.findViewById(R.id.spectrogram);

        assert stftManager != null;
        Bitmap lastSpectrogram = createBitmap(convertToGreyscale(stftManager.getCurrentSTFT()));
        spec.setImageBitmap(lastSpectrogram);

        return rootView;
    }

    /**
     *
     * @param stft current STFT
     * @return two entry double array; [0] is max, [1] is min
     */
    private double[] findMaxMin(double[][] stft) {
        double currentMax = Double.MIN_VALUE;
        double currentMin = Double.MAX_VALUE;

        for (double[] doubles : stft) {
            for (int j = 0; j < doubles.length; j++) {
                if (doubles[j] > currentMax)
                    currentMax = doubles[j];
                if (doubles[j] < currentMin)
                    currentMin = doubles[j];
            }
        }
        return new double[]{currentMax, currentMin};
    }

    private int[][] convertToGreyscale(double[][] stft) {
        double[] maxMin = findMaxMin(stft);
        double maxValue = maxMin[0];
        double minValue = maxMin[1];
        int[][] spec = new int[stft.length][stft[0].length];
        for (int i = 0; i < stft.length; i++) {
            for (int j = 0; j < stft[i].length; j++) {
                int value = Math.max(0, Math.min(255, (int) (((stft[i][j] - minValue) / (maxValue - minValue)) * 255.0)));
                value = 255-value;
                spec[i][j] = Color.rgb(value, value, value);
            }
        }
        return spec;
    }


    private Bitmap createBitmap(int[][] data){
        int height = data[0].length;
        int width = data.length;
        //Toast.makeText(PulseRadar.this, "#frequencies: " + height + ", #timesteps: " + width, Toast.LENGTH_LONG).show();

        int widthFactor = 16;
        int[] arrayCol = new int[width*height*widthFactor/2];
        int counter = 0;
        for(int i = height/2-1; i >= 0; i--) {
            for (int[] datum : data) {

                for (int k = 0; k < widthFactor; k++)
                    arrayCol[counter + k] = datum[i];

                counter += widthFactor;
            }
        }
        return Bitmap.createBitmap(arrayCol, width * widthFactor, height / 2, Bitmap.Config.ARGB_8888);
    }
}