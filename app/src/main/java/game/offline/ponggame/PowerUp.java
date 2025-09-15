package game.offline.ponggame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class PowerUp {
    private int x, y;
    private int width, height;
    private Bitmap bitmap;
    private RectF rect;

    public PowerUp(int x, int y, int width, int height, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.bitmap = bitmap;
        this.rect = new RectF(x, y, x + width, y + height);
    }

    public void update() {
        // Example: move the power-up down the screen
        y += 155; // Adjust speed as needed
        rect.set(x, y, x + width, y + height);
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(bitmap, x, y, paint);
    }

    public RectF getRect() {
        return rect;
    }

    // Getter methods for position
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Additional methods for power-up behavior can be added here
}