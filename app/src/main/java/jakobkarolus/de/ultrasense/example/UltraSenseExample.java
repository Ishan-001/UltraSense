package jakobkarolus.de.ultrasense.example;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import jakobkarolus.de.ultrasense.R;

/**
 * Example activity showing scenarios of UltraSense
 * <br><br>
 * Created by Jakob on 10.08.2015.
 */
public class UltraSenseExample extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse_radar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new UltraSenseExampleFragment())
                    .commit();
        }

    }
}
