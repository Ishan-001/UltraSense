package jakobkarolus.de.ultrasense;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import jakobkarolus.de.ultrasense.view.CalibrationFragment;

/**
 * Activity used for calibrating detection parameters by the user
 *
 * <br><br>
 * Created by Jakob on 17.08.2015.
 */
public class CalibrationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calibration);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new CalibrationFragment())
                    .commit();
        }

    }
}
