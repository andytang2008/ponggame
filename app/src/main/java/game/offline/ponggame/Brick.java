package game.offline.ponggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Brick {
    private RectF rect;
    private Bitmap brickBitmap;
    private String brickType;
    private int hitCount;
    public Brick(Context context, float left, float top, float right, float bottom, String brickType) {
        this.rect = new RectF(left, top, right, bottom);
        this.brickType = brickType;
     //  this.hitCount = determineHitCount(brickType);
     //  this.brickBitmap = loadBrickBitmap(context, brickType);
        setBitmapForBrickType(context);

    }

      public void setBitmapForBrickType(Context context) {
        switch (brickType) {
            case "steel":
                this.brickBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick_steel);
                this.hitCount = 2;
                break;
            case "copper":
                this.brickBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick_copper);
                this.hitCount = 3;
                break;
            case "clay":
            default:
                this.brickBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick);
                this.hitCount = 1;
                break;
        }
    }
    public void decreaseHitCount() {
        if (hitCount > 0) {
            hitCount--;
        }
    }

    public void updateBitmapForHit(Context context) {
        // Update the bitmap based on the remaining hit count
        if ("steel".equals(brickType) && hitCount == 1) {
            brickBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick_steel_crack);
        } else if ("copper".equals(brickType) && hitCount == 2) {
            brickBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick_copper_crack);
        } else if ("copper".equals(brickType) && hitCount == 1) {
        brickBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick_copper_crack2);
        }
        // Add more cases for other brick types if needed
    }

    public String getBrickType() {
        return brickType;
    }

    public void setBrickType(String brickType) {
        this.brickType = brickType;
    }

    public int getHitCount() {
        return hitCount;
    }

    public void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }


    // Getter method for brickBitmap
    public Bitmap getBrickBitmap() {
        return brickBitmap;
    }
    public Brick(RectF rectF) {
        this.rect = rectF;
    }

    public RectF getRect() {
        return rect;
    }

    public void setRect(RectF rect) {
        this.rect = rect;
    }
}