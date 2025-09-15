package game.offline.ponggame;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView animatedView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animatedView = findViewById(R.id.animatedGifView);
        AnimationManager.getInstance().setAnimationView(animatedView);
    }

    public ImageView getAnimatedView() {
        return animatedView;
    }
    public void startAnimation() {
        // Start animation or set image resources here
    }
}