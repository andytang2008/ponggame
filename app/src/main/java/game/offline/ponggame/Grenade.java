package game.offline.ponggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.DisplayMetrics;

public class Grenade {
    private float x;
    private float y;
    private int width;
    private int height;
    private Bitmap bitmap;
    private boolean isVisible;

    // Constants
    private float velocityX; // Velocity in the horizontal direction
    private float velocityY; // Velocity in the vertical direction
    private static final float INITIAL_VELOCITY_X = -5.0f; // Initial horizontal velocity (negative to go left)
    private static final float INITIAL_VELOCITY_Y = -10.0f; // Initial upward velocity (negative to go up)
    private static final float GRAVITY = 0.9f;
    private int screenHeight;
    private int screenWidth;

    public Grenade(Context context, float x, float y) {
        this.x = x;
        this.y = y;
        this.isVisible = true;
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.coin);
        //   this.bitmap = makeTransparent(this.bitmap, Color.WHITE); // Replace Color.WHITE with the background color to make transparent
        this.width = bitmap.getWidth();
        this.height = bitmap.getHeight();
        //  this.velocityX = INITIAL_VELOCITY_X;
        this.velocityY = INITIAL_VELOCITY_Y;
        this.velocityX = (Math.random() < 0.5 ? -1 : 1) * INITIAL_VELOCITY_X;
        // Get screen height and width dynamically
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;
    }

    public void update(int screenHeight) {
        // Log.d("GoldCoin", "Update method called");
        if (isVisible) {
            // Apply gravity
            velocityY += GRAVITY;

            // Update position
            x += velocityX;
            y += velocityY;
            //Log.d("GoldCoin", "Updating position: x = " + x + ", y = " + y);

            // Boundary checks for horizontal movement
            if (x < 0) {
                x = 0;
                velocityX = -velocityX; // Reverse direction
            } else if (x + width > screenWidth) {
                x = screenWidth - width;
                velocityX = -velocityX; // Reverse direction
            }

            // Hide the coin if it goes out of the screen bounds vertically
            if (y > screenHeight) {
                isVisible = false;
            }
        }
    }

    public void draw(Canvas canvas, Paint paint) {
        if (isVisible) {
            canvas.drawBitmap(bitmap, x, y, paint);
        }
    }

    public RectF getBounds() {
        return new RectF(x, y, x + width, y + height);
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}