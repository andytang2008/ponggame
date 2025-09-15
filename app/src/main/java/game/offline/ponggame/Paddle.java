package game.offline.ponggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Paddle {

    private Bitmap grenadeExplosionBitmap; // Bitmap for grenade explosion
    private boolean showGrenadeExplosion; // Flag to show grenade explosion
    private RectF rect;
    private Bitmap bitmap;
    private Bitmap handleBitmap; // Bitmap for the handle
    private Bitmap grenadeExplodeBitmap; // Bitmap for the grenade
    private int screenWidth;
    private int paddleWidth;
    private int handleHeight; // Height of the handle area
    private float speedMultiplier;
    private float marginBottom; // Margin from the bottom
    private Drawable gifDrawable;    // Constructor with position parameters
    private  Context context;
    private ImageView animationView;
    private boolean isAnimationActive = false;
    private boolean showEffect = false;
    private int gifX, gifY;
    public Paddle(Context context, int screenX, int screenY, int width, int handleHeight, float startX, float startY) {
        this.context=context;
        this.animationView=animationView;

        this.paddleWidth = width;
        this.screenWidth = screenX;
        this.handleHeight = handleHeight;
        this.marginBottom = 200; // Adjust as needed

        // Initialize the RectF object
        rect = new RectF();
        if (startX == 0 && startY == 0) {
            rect.left = screenX / 2 - width / 2;
            rect.top = screenY - marginBottom - handleHeight; // Adjust top to move paddle higher, including handle height
        } else {
            rect.left = startX;
            rect.top = startY;
        }
        rect.right = rect.left + width;
        rect.bottom = screenY - marginBottom; // Bottom of paddle

        // Load paddle bitmap
        Bitmap originalPaddleBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.paddle);
        bitmap = Bitmap.createScaledBitmap(originalPaddleBitmap, width, (int) rect.height(), false);

        // Load handle bitmap (adjust this according to your handle image resource)
        handleBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.handle_shape);
        handleBitmap = Bitmap.createScaledBitmap(handleBitmap, paddleWidth, handleHeight, false);

        // Load grenade bitmap
        grenadeExplodeBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.paddle_explosion);
        //grenadeExplodeBitmap = Bitmap.createScaledBitmap(grenadeExplodeBitmap, width, (int) rect.height(), false);

        speedMultiplier = 10.0f; // Example value, adjust as needed
    }

    public void showGifAnimation(float collisionX, float collisionY) {
        ImageView animationView = AnimationManager.getInstance().getAnimationView();
        if (animationView != null && context != null) {
            Log.d("Paddle", "showGifAnimation method called");
            isAnimationActive = true;
            // Use post to ensure the view has been laid out
            animationView.post(new Runnable() {
                @Override
                public void run() {
                    // Get the width and height of the ImageView
                  //  int width = animationView.getWidth();  // This will be 200px based on your XML
                  //  int height = animationView.getHeight(); // This will be 200px based on your XML

                    int width = 200;  // This will be 200px based on your XML
                    int height = 200;


                    Log.d("Paddle", "AnimationView dimensions - Width: " + width + ", Height: " + height);

                    // Calculate the correct position to center the GIF on the paddle
                    float gifX = collisionX - (width / 2);
                    float gifY = collisionY - height;  // Adjust to align with the top of the paddle

                    Log.d("Paddle", "Calculated Position - X: " + gifX + ", Y: " + gifY);

                    // Set the position of the animationView
                    animationView.setX(gifX);  // Centering the view horizontally
                    animationView.setY(gifY);  // Aligning the GIF to the top of the paddle

                    Log.d("Paddle", "Setting AnimationView Position - X: " + animationView.getX() + ", Y: " + animationView.getY());

                    animationView.setVisibility(View.VISIBLE);

                    Glide.with(context)
                            .asGif()
                            .load(R.drawable.paddle_explosion_animation)
                            .into(animationView);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (animationView != null) {
                                animationView.setVisibility(View.GONE);
                                Log.d("Paddle", "Hiding GIF animation");
                                isAnimationActive = false;
                            }
                        }
                    }, 3000); // Duration of the GIF in milliseconds
                }
            });

        } else {
            Log.e("Paddle", "AnimationView not initialized");
        }
    }


    // Getter for RectF object representing paddle's position
    public RectF getRect() {
        return rect;
    }

    // Getter for paddle's bitmap
    public Bitmap getBitmap() {
        return bitmap;
    }

    // Setter for paddle's bitmap
    public void setBitmap(Bitmap newBitmap) {
        this.bitmap = newBitmap;
    }

    // Getter for paddle's width
    public int getWidth() {
        return paddleWidth;
    }

    public void setWidth(int width) {
        float paddleCenter = rect.centerX();
        rect.left = paddleCenter - (width / 2);
        rect.right = paddleCenter + (width / 2);
        this.paddleWidth = width;
    }

    // Getter for paddle's speed multiplier
    public float getSpeedMultiplier() {
        return speedMultiplier;
    }

    // Setter for paddle's speed multiplier
    public void setSpeedMultiplier(float multiplier) {
        this.speedMultiplier = multiplier;
    }

    // Method to update paddle position based on touch input or other controls
   /* public void updatePosition(float newX) {
        // Ensure paddle stays within screen bounds
        if (newX < 0) {
            newX = 0;
        } else if (newX + paddleWidth > screenWidth) {
            newX = screenWidth - paddleWidth;
        }
        rect.left = newX;
        rect.right = rect.left + paddleWidth;
    }*/

    // Override updatePosition method to consider the animation state
    public void updatePosition(float newX) {
        if (!isAnimationActive) {
            // Ensure paddle stays within screen bounds
            if (newX < 0) {
                newX = 0;
            } else if (newX + paddleWidth > screenWidth) {
                newX = screenWidth - paddleWidth;
            }
            rect.left = newX;
            rect.right = rect.left + paddleWidth;
        }
    }

    // Method to draw the paddle and handle
    public void draw(Canvas canvas, Paint paint) {
        // Draw the rectangular paddle

        Log.d("Paddle in draw", "Drawing paddle with bitmap: " + bitmap.toString());
        Log.d("Paddle in draw", "Paddle Rect: " + rect.toString());


        canvas.drawBitmap(bitmap, null, rect, paint);

        Log.d("PaddleEffect in draw ", "Drawing GIF at X: " + gifX + ", Y: " + gifY);

        if (showEffect && gifDrawable != null) {
            gifDrawable.setBounds(gifX, gifY,
                    gifX + gifDrawable.getIntrinsicWidth(),
                    gifY + gifDrawable.getIntrinsicHeight());
            gifDrawable.draw(canvas);
        }


        // Calculate handle position
        float handleLeft = rect.left;
        float handleTop = rect.bottom; // Position the handle just below the paddle

        // Draw the handle bitmap below the paddle
        canvas.drawBitmap(handleBitmap, handleLeft, handleTop, paint);

        // Draw grenade explosion bitmap if the flag is set
        if (showGrenadeExplosion) {
            float grenadeLeft = rect.centerX() - (grenadeExplosionBitmap.getWidth() / 2);
            float grenadeTop = rect.top - grenadeExplosionBitmap.getHeight();
            canvas.drawBitmap(grenadeExplosionBitmap, grenadeLeft, grenadeTop, paint);
        }
    }
    public void clearEffect() {
        showEffect = false;

    }

    public void setShowEffect() {
        showEffect = true;

    }

   // public void triggerEffect(int x, int y, Canvas canvas) {}
   public void triggerEffect(int x, int y, Canvas canvas) {
        gifX = x;
        gifY = y;
        showEffect = true;
        Log.d("PaddleEffect", "Effect Triggered at X: " + gifX + ", Y: " + gifY);

            if (showEffect && gifDrawable != null) {
                gifDrawable.setBounds(gifX, gifY,
                        gifX + gifDrawable.getIntrinsicWidth(),
                        gifY + gifDrawable.getIntrinsicHeight());
                gifDrawable.draw(canvas);
            } else {
                Log.e("PaddleEffect", "GIF drawable is null.");
            }
        }

        // Method to trigger the GIF effect
        private AnimatedView animatedView;

    public Paddle(AnimatedView animatedView) {
        this.animatedView = animatedView;
    }

    public void triggerAnimation(int x, int y) {
        if (animatedView != null) {
            // Set position or any other parameters if needed
            animatedView.startAnimation();
        }
    }


    // Method to set the position of the paddle
    public void setPosition(float x, float y) {
        rect.left = x;
        rect.top = y;
        rect.right = x + paddleWidth;
        rect.bottom = y + handleHeight;
    }

    // Method to get grenade bitmap
    public Bitmap getGrenadeExplodeBitmapBitmap() {
        return grenadeExplodeBitmap;
    }
}