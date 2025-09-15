package game.offline.ponggame;

import android.widget.ImageView;

public class AnimationManager {
    private static AnimationManager instance;
    private ImageView animationView;

    private AnimationManager() { }

    public static synchronized AnimationManager getInstance() {
        if (instance == null) {
            instance = new AnimationManager();
        }
        return instance;
    }

    public void setAnimationView(ImageView view) {
        this.animationView = view;
    }

    public ImageView getAnimationView() {
        return animationView;
    }
}
