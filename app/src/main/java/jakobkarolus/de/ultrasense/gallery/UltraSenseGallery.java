package jakobkarolus.de.ultrasense.gallery;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import jakobkarolus.de.ultrasense.R;
import jakobkarolus.de.ultrasense.example.UltraSenseExampleFragment;

/**
 * Example activity showing picture gallery scenario of UltraSense
 * <br><br>
 * Created by Jakob on 06.09.2015.
 */
public class UltraSenseGallery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse_radar);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new UltraSenseGalleryFragment())
                    .commit();
        }

    }
}
