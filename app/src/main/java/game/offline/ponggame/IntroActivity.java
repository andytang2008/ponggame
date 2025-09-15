package game.offline.ponggame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    public void startGameOnClick(View view) {
        Intent intent = new Intent(this, MainActivity.class); // Start your main game activity
        startActivity(intent);
        finish(); // Finish the intro activity to prevent going back to it on back press
    }

    public void openPrivacyPolicy(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/dunkenzhao/BricksBreak/blob/main/Privacy_Policy_v1.0.md"));
        startActivity(browserIntent);
    }
}
