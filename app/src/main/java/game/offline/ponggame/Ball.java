package game.offline.ponggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;
import android.os.Handler;

import java.util.Random;

public class Ball {
    private RectF rect;      // RectF for collision detection and positioning
    private float xVelocity; // Velocity along the X axis
    private float yVelocity; // Velocity along the Y axis
    private Bitmap bitmap;   // Bitmap for the ball

    private boolean isDestroyed;
    private Bitmap largeBitmap;
    private float radius;    // Ball radius

    private float ballX;     // Center X position of the ball
    private float ballY;     // Center Y position of the ball

    private  float initialXVelocity; // Initial X velocity
    private  float initialYVelocity; // Initial Y velocity

    private float reflectionAngle; // Reflection angle

    private Context context;
    // Constructor
    // Constructor
    public Ball(Context context, int screenX, int screenY, float initialXVelocity, float initialYVelocity,float ballCenterX,float ballCenterY,int ballType) {
        this.context = context;
        this.initialXVelocity = initialXVelocity;
        this.initialYVelocity = initialYVelocity;
        this.ballX=ballCenterX;
        this.ballY=ballCenterY;
        if (ballType==0 || ballType==1){
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
        }
        else if(ballType==2){
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball_large);
        }        else if(ballType==3){
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball_blood);
        }

        //largeBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball_large);
        rect = new RectF();

        if (ballType==0 ) {
            reset(screenX / 2, screenY / 2);
           // reset(ballCenterX, ballCenterY);
        }else if(ballType==1 || ballType==2|| ballType==3){
            reset(ballCenterX, ballCenterY);
        }
        reflectionAngle = 20.0f; // Default reflection angle
    }

    public void destroy() {// don't use this method, it cause crash
        // Clear the bitmap and reset the RectF
        bitmap = null;
        rect.setEmpty(); // Clear the rectangle
    }

    private boolean isFireballMode = false;
    // Other existing variables and methods

    public void setFireballMode(boolean isFireballMode) {
        this.isFireballMode = isFireballMode;
    }

    public boolean isFireballMode() {
        return isFireballMode;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public Context getContext() {
        return context;
    }

    public void setBallImage(int imageResource) {
        this.bitmap = BitmapFactory.decodeResource(context.getResources(), imageResource);
    }
    public void setDestroyed(boolean destroyed) {
        this.isDestroyed = destroyed;
    }
    public void reset(float startX, float startY) {
        ballX = startX;  // Set center X position of the ball
        ballY = startY;  // Set center Y position of the ball

        // Calculate the exact position of the ball bitmap within the RectF
        rect.left = ballX - bitmap.getWidth() / 2;
        rect.top = ballY - bitmap.getHeight() / 2;
        rect.right = ballX + bitmap.getWidth() / 2;
        rect.bottom = ballY + bitmap.getHeight() / 2;

        // Reset velocities or set initial velocities
        xVelocity = initialXVelocity;
        yVelocity = initialYVelocity;
    }


    // Update ball position based on velocity
    public void update(int screenWidth, int screenHeight) {
        rect.left += xVelocity;
        rect.top += yVelocity;
        rect.right = rect.left + bitmap.getWidth();
        rect.bottom = rect.top + bitmap.getHeight();

        // Handle ball bouncing off walls
        if (rect.left < 0) {
            rect.left = 0;
            rect.right = rect.left + bitmap.getWidth();
            xVelocity = -xVelocity;  // Reverse X velocity
        } else if (rect.right > screenWidth) {
            rect.right = screenWidth;
            rect.left = rect.right - bitmap.getWidth();
            xVelocity = -xVelocity;  // Reverse X velocity
        }

        if (rect.top < 0) {
            rect.top = 0;
            rect.bottom = rect.top + bitmap.getHeight();
            yVelocity = -yVelocity;  // Reverse Y velocity
        }
    }

    // Setter for ball speed
    public void setSpeed(float speedX, float speedY) {
      //  xVelocity = speedX;
      //  yVelocity = speedY;
       initialXVelocity=speedX;
       initialYVelocity=speedY;
    }

    // Handle ball bouncing off walls
    public void handleWallCollision(int screenWidth, int screenHeight) {
        if (rect.left < 0 || rect.right > screenWidth) {
            reverseXVelocity();  // Reverse X velocity
        }

        if (rect.top < 0) {
            reverseYVelocity();  // Reverse Y velocity
        }
    }

    // Getter for the RectF object representing ball's position
    public RectF getRect() {
        return rect;
    }

    // Getter for the ball's bitmap
    public Bitmap getBitmap() {
        return bitmap;
    }

    // Reverse X velocity
    public void reverseXVelocity() {
        xVelocity = -xVelocity;
    }

    // Reverse Y velocity
    public void reverseYVelocity() {
        yVelocity = -yVelocity;
    }

    // Set random X velocity
    public void setRandomXVelocity() {
        Random random = new Random();
        xVelocity = (random.nextFloat() - 0.5f) * 20;  // Example adjustment
    }

    // Getter and setter for the left position of the ball
    public float getRectLeft() {
        return rect.left;
    }


    // Getter and setter for reflection angle
    public float getReflectionAngle() {
        return reflectionAngle;
    }

    public void setReflectionAngle(float reflectionAngle) {
        this.reflectionAngle = reflectionAngle;
    }


    public void setRectLeft(float left) {
        rect.left = left;
    }

    // Getter and setter for the right position of the ball
    public float getRectRight() {
        return rect.right;
    }

    public void setRectRight(float right) {
        rect.right = right;
    }

    // Getter and setter for the top position of the ball
    public float getRectTop() {
        return rect.top;
    }

    public void setRectTop(float top) {
        rect.top = top;
    }

    // Getter and setter for the bottom position of the ball
    public float getRectBottom() {
        return rect.bottom;
    }

    public void setRectBottom(float bottom) {
        rect.bottom = bottom;
    }

    // Getter for ballX (center X position of the ball)
    public float getBallX() {
        return ballX;
    }

    // Getter for ballY (center Y position of the ball)
    public float getBallY() {
        return ballY;
    }

    // Getter and setter for X velocity
    public float getVelocityX() {
        return xVelocity;
    }

    public void setVelocityX(float velocityX) {
        this.xVelocity = velocityX;
    }

    // Getter and setter for Y velocity
    public float getVelocityY() {
        return yVelocity;
    }

    public void setVelocityY(float velocityY) {
        this.yVelocity = velocityY;
    }


    private boolean collisionCooldown = false;

    public boolean isCollisionCooldown() {
        return collisionCooldown;
    }

    public void setCollisionCooldown(boolean cooldown) {
        this.collisionCooldown = cooldown;
        if (cooldown) {
            new Handler().postDelayed(() -> setCollisionCooldown(false), 100); // Cooldown period in milliseconds
        }   //setup between 50 and 100 millisecodns, GPT to prevent the ball hit the brick twice in a very short time.
    }
}
