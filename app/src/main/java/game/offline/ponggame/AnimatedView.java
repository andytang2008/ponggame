package game.offline.ponggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

public class AnimatedView extends View {

    private Bitmap[] frames;
    private int currentFrameIndex = 0;
    private long lastFrameChangeTime = 0;
    private static final long FRAME_DURATION = 100;
    private boolean isAnimating = false;

    public AnimatedView(Context context) {
        super(context);
        init();
    }

    public AnimatedView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        frames = new Bitmap[] {
                BitmapFactory.decodeResource(getResources(), R.drawable.explosion_frame1),
                BitmapFactory.decodeResource(getResources(), R.drawable.explosion_frame2),
                BitmapFactory.decodeResource(getResources(), R.drawable.explosion_frame3),
                // Add more frames as needed
        };
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (isAnimating) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastFrameChangeTime > FRAME_DURATION) {
                currentFrameIndex = (currentFrameIndex + 1) % frames.length;
                lastFrameChangeTime = currentTime;
            }

            Bitmap currentFrame = frames[currentFrameIndex];
            canvas.drawBitmap(currentFrame, 0, 0, null);

            invalidate();
        }
    }

    public void startAnimation() {
        isAnimating = true;
        lastFrameChangeTime = System.currentTimeMillis();
        invalidate(); // Trigger redraw
    }

    public void stopAnimation() {
        isAnimating = false;
        invalidate(); // Optionally stop drawing
    }
}

