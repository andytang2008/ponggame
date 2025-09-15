package game.offline.ponggame;

import android.content.Context;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class CollisionDetector {
    private Context context;
    private GameManager gameManager;
    private SoundManager soundManager;

    private int topPadding;

    private GameView gameView;

    private int screenWidth;
    private int screenHeight;

    private Ball ball;

    private List<Ball> balls;

    public CollisionDetector(Context context, GameManager gameManager, SoundManager soundManager, int topPadding) {
        this.gameManager = gameManager;
        this.soundManager = soundManager;
        this.topPadding = topPadding;
        this.context = context;
        this.gameView = gameView;

        this.balls = gameManager.getBalls();
    }

   // public void detectCollisions(Context context, int screenWidth, int screenHeight) {  //old one
    public void detectCollisions(Context context, List<Ball> balls, ListIterator<Ball> ballIterator, Ball ball, int screenWidth, int screenHeight) {
       // Ball ball = gameManager.getBall();
        Paddle paddle = gameManager.getPaddle();
        Bricks bricks = gameManager.getBricks();
        this.ball=ball;
        this.balls=balls;
        this.screenWidth=screenWidth;
        this.screenHeight=screenHeight;
        checkWallCollisions(ball, screenWidth, screenHeight);
        checkPaddleCollision(ball, paddle);
        checkBrickCollisions(context,ball, bricks);
        checkCoinCollisions(paddle);
        checkBunCollisions(paddle);
        checkSausageCollisions(paddle);
        checkBeerCollisions(paddle);
        checkShortboardCollisions(paddle);
        checkLongboardCollisions(paddle);
       // checkSizeupCollisions(ball,paddle);
        checkSizeupCollisions(balls, paddle);
        checkGrenadeCollisions(paddle);
        checkBallTripleCollisions(paddle, context, balls, ballIterator,ball, screenWidth, screenHeight);
        checkBallFireCollisions(balls,ballIterator,paddle);
    }

    private void checkBallTripleCollisions(Paddle paddle, Context context, List<Ball> balls,ListIterator<Ball> ballIterator,Ball ball,int screenWidth, int screenHeight)  {
        // Check for collisions between the paddle and ball_triple
        Iterator<BallTriple> iterator_TripleBall = gameManager.getBalltriples().iterator();
        List<Ball> newBalls = new ArrayList<>();

        while (iterator_TripleBall.hasNext()) {
            BallTriple ballTriple = iterator_TripleBall.next();
            if (RectF.intersects(ballTriple.getBounds(), paddle.getRect())) {
                ballTriple.setVisible(false);
                soundManager.playBallTripleCollectSound(); // Use appropriate sound for ball triple collection
                gameManager.increaseScore(24); // Increase score when a ball triple is collected

               if (!balls.isEmpty()) {
                    Ball firstBall = balls.get(0); // Assuming the first ball is the reference
                    float currentX = firstBall.getBallX();
                    float currentY = firstBall.getBallY();
                    float currentSpeedX = firstBall.getVelocityX();
                    float currentSpeedY = firstBall.getVelocityY();

                    // Create a new ball with a slightly different velocity or position
                    Ball newBall = new Ball(
                            context,  // Use the context passed into this method
                            screenWidth, // Pass the screen width
                            screenHeight, // Pass the screen height
                            -currentSpeedX, // Reverse the X velocity for variation
                            currentSpeedY,  // Keep the Y velocity the same
                            currentX + 10, // Slightly offset the new ball's position
                            currentY + 10, // Slightly offset the new ball's position
                            1 // Assuming this is the ball type (adjust as necessary)  ballType=1, normal size ball generator
                    );

                    ballIterator.add(newBall);  // Collect new balls in a temporary list
                }
            }
        }
    }

      private void checkGrenadeCollisions(Paddle paddle) {
        // Check for collisions between the paddle and grenades
        Iterator<Grenade> iterator = gameManager.getGrenades().iterator();
        while (iterator.hasNext()) {
            Grenade grenade = iterator.next();
            if (RectF.intersects(grenade.getBounds(), paddle.getRect())) {
                grenade.setVisible(false); // Make the grenade disappear
                soundManager.playGrenadeCollectSound(); // Play grenade explosion sound
                //triggerExplosionEffect(paddle); // Trigger explosion effect

                Log.d("PaddleEffect", "Paddle Center X: " + (int) paddle.getRect().centerX());
                Log.d("PaddleEffect", "Paddle Center Y: " + (int) paddle.getRect().centerY());
                //paddle.triggerEffect((int) paddle.getRect().centerX(), (int) paddle.getRect().centerY());
                // Change the paddle's image to the grenade
              // paddle.setBitmap(paddle.getGrenadeExplodeBitmapBitmap());
              // paddle.showGifAnimation();
                paddle.showGifAnimation((float) paddle.getRect().centerX(), (float) paddle.getRect().centerY());
                //gameView.triggerGamemangerGif();
                //gameManager.destroyPaddle();
                gameManager.trigger();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gameManager.destroyBall(); // Destroy the ball after 3 seconds  //?we use fall out of bottom way to destroy ball, it will trigger gamevoer() automatically.
                        gameManager.isGameOver();
                        gameManager.triggerGameOver();
                    }
                }, 1000); // 3000 milliseconds = 3 seconds

                // Optionally: break out of the loop if only one grenade should be processed at a time
                break;
            }
        }
    }

    //private void checkBallFireCollisions(ListIterator<Ball> ballIterator, Paddle paddle) {
    private void checkBallFireCollisions(List<Ball> balls, ListIterator<Ball> ballIterator,Paddle paddle) {

        int screenWidth = GlobalState.getInstance().getScreenWidth();
        int screenHeight = GlobalState.getInstance().getScreenHeight();

        // Check for collisions between the paddle and buns
        Iterator<BallFire> iterator = gameManager.getBallfires().iterator();
        while (iterator.hasNext()) {
            BallFire ballFire = iterator.next();
            if (RectF.intersects(ballFire.getBounds(), paddle.getRect())) {
                ballFire.setVisible(false);
                soundManager.playBallFireCollectSound();; // Use appropriate sound for bun collection

                if (context == null) {
                    throw new IllegalStateException("Context cannot be null");
                }

                // Iterate over all balls in the list
                // for (Ball ball : balls) {

                for (int i = 0; i < balls.size(); i++) {
                    Ball ball = balls.get(i);
                    // if (ball.isDeadBall() == false) {
                    float ballCenterX = ball.getRect().centerX();
                    float ballCenterY = ball.getRect().centerY();

                    // Preserve existing velocity
                    float currentXVelocity = ball.getVelocityX();
                    float currentYVelocity = ball.getVelocityY();

                   // ballType 3 is fireball with blook image;
                    // Create a new ball with updated size
                    Ball newBall = new Ball(context, screenWidth, screenHeight, currentXVelocity, currentYVelocity, ballCenterX, ballCenterY, 3);
                    newBall.setFireballMode(true);//Issue the capablity of the ball to penetrate the bricks
                    // Replace the old ball with the new one
                    // balls.set(balls.indexOf(ball), newBall);  //This line determined which ball will be set as large ball.
                    //if you want to set 2 balls large, change this line.
                    balls.set(i, newBall);
                }
                // Increase score when a bun is collected
                gameManager.increaseScore(11);

                // Schedule a task to reset the ball size after 10 seconds
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Reset each ball to normal size after 10 seconds
                        for (Ball ball : balls) {
                            float ballCenterX = ball.getRect().centerX();
                            float ballCenterY = ball.getRect().centerY();
                            float ballXVelocity = ball.getVelocityX();
                            float ballYVelocity = ball.getVelocityY();
                            // Create a new ball with normal size
                            Ball normalBall = new Ball(context, screenWidth, screenHeight, ballXVelocity, ballYVelocity, ballCenterX, ballCenterY, 1);
                            normalBall.setFireballMode(false);  //call the capablity of the ball to penetrate the bricks
                            // Replace the old ball with the new one
                            int index = balls.indexOf(ball);
                            if (index != -1) {
                                balls.set(index, normalBall);
                            }
                        }
                    }
                }, 6000); // 10000 milliseconds = 10 seconds

                // Exit loop after processing the first ball since we're updating all balls
                break;
                // }
            }
        }
        //}
    }




    private void checkSizeupCollisions(List<Ball> balls, Paddle paddle) {

        int screenWidth = GlobalState.getInstance().getScreenWidth();
        int screenHeight = GlobalState.getInstance().getScreenHeight();

        // Check for collisions between the paddle and buns
        Iterator<Sizeup> iterator = gameManager.getSizeups().iterator();
        while (iterator.hasNext()) {
            Sizeup sizeup = iterator.next();
            if (RectF.intersects(sizeup.getBounds(), paddle.getRect())) {
                sizeup.setVisible(false);
                soundManager.playSizeupCollectSound(); // Use appropriate sound for bun collection

                if (context == null) {
                    throw new IllegalStateException("Context cannot be null");
                }

                // Iterate over all balls in the list
               // for (Ball ball : balls) {

                    for (int i = 0; i < balls.size(); i++) {
                        Ball ball = balls.get(i);
                        // if (ball.isDeadBall() == false) {
                        float ballCenterX = ball.getRect().centerX();
                        float ballCenterY = ball.getRect().centerY();

                        // Preserve existing velocity
                        float currentXVelocity = ball.getVelocityX();
                        float currentYVelocity = ball.getVelocityY();

                        // Create a new ball with updated size, ballType=2
                        Ball newBall = new Ball(context, screenWidth, screenHeight, currentXVelocity, currentYVelocity, ballCenterX, ballCenterY, 2);

                        // Replace the old ball with the new one
                        // balls.set(balls.indexOf(ball), newBall);  //This line determined which ball will be set as large ball.
                        //if you want to set 2 balls large, change this line.
                        balls.set(i, newBall);
                    }
                    // Increase score when a bun is collected
                        gameManager.increaseScore(10);

                        // Schedule a task to reset the ball size after 10 seconds
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Reset each ball to normal size after 10 seconds
                                for (Ball ball : balls) {
                                    float ballCenterX = ball.getRect().centerX();
                                    float ballCenterY = ball.getRect().centerY();
                                    float ballXVelocity = ball.getVelocityX();
                                    float ballYVelocity = ball.getVelocityY();
                                    // Create a new ball with normal size  ballType=1
                                    Ball normalBall = new Ball(context, screenWidth, screenHeight, ballXVelocity, ballYVelocity, ballCenterX, ballCenterY, 1);
                                    // Replace the old ball with the new one
                                    int index = balls.indexOf(ball);
                                    if (index != -1) {
                                        balls.set(index, normalBall);
                                    }
                                }
                            }
                        }, 10000); // 10000 milliseconds = 10 seconds

                        // Exit loop after processing the first ball since we're updating all balls
                        break;
                   // }
                }
            }
        //}
    }


    private void checkLongboardCollisions(Paddle paddle) {

        // Check for collisions between paddlethe paddle and buns
        Iterator<Longboard> iterator = gameManager.getLongboards().iterator();
        while (iterator.hasNext()) {
            Longboard longboard = iterator.next();
            if (RectF.intersects(longboard.getBounds(), paddle.getRect())) {
                longboard.setVisible(false);
                soundManager.playLongboardCollectSound(); // Use appropriate sound for bun collection
                gameManager.increaseScore(2); // Increase score by 5 when a bun is collected
                // paddle.setWidth(110);
                int paddleWidth = 260;
                int handleHeight = 50;
                int screenWidth=GlobalState.getInstance().getScreenWidth();
                int  screenHeight= GlobalState.getInstance().getScreenHeight();
                if (context == null) {
                    throw new IllegalStateException("Context cannot be null");
                }

                // Get current paddle position
                float startX = paddle.getRect().left;
                float startY = paddle.getRect().top;
                Paddle newPaddle = new Paddle(context, screenWidth, screenHeight, paddleWidth, handleHeight, startX, startY);
                gameManager.setPaddle(newPaddle);
            }
        }
    }

    private void checkShortboardCollisions(Paddle paddle) {

        // Check for collisions between paddlethe paddle and buns
        Iterator<Shortboard> iterator = gameManager.getShortboards().iterator();
        while (iterator.hasNext()) {
            Shortboard shortboard = iterator.next();
            if (RectF.intersects(shortboard.getBounds(), paddle.getRect())) {
                shortboard.setVisible(false);
                soundManager.playShortboardCollectSound(); // Use appropriate sound for bun collection
                gameManager.increaseScore(1); // Increase score by 5 when a bun is collected
               // paddle.setWidth(110);
                int paddleWidth = 140;
                int handleHeight = 50;
                int screenWidth=GlobalState.getInstance().getScreenWidth();
                int  screenHeight= GlobalState.getInstance().getScreenHeight();
                if (context == null) {
                    throw new IllegalStateException("Context cannot be null");
                }

                // Get current paddle position
                float startX = paddle.getRect().left;
                float startY = paddle.getRect().top;
               Paddle newPaddle = new Paddle(context, screenWidth, screenHeight, paddleWidth, handleHeight, startX, startY);
                gameManager.setPaddle(newPaddle);
            }
        }
    }


    private void checkBeerCollisions(Paddle paddle) {
        // Check for collisions between the paddle and buns
        Iterator<Beer> iterator = gameManager.getBeers().iterator();
        while (iterator.hasNext()) {
            Beer beer = iterator.next();
            if (RectF.intersects(beer.getBounds(), paddle.getRect())) {
                beer.setVisible(false);
                soundManager.playBeerCollectSound(); // Use appropriate sound for bun collection
                gameManager.increaseScore(10); // Increase score by 5 when a bun is collected
            }
        }
    }
    private void checkSausageCollisions(Paddle paddle) {
        // Check for collisions between the paddle and buns
        Iterator<Sausage> iterator = gameManager.getSausages().iterator();
        while (iterator.hasNext()) {
            Sausage sausage = iterator.next();
            if (RectF.intersects(sausage.getBounds(), paddle.getRect())) {
                sausage.setVisible(false);
                soundManager.playSausageCollectSound(); // Use appropriate sound for bun collection
                gameManager.increaseScore(7); // Increase score by 5 when a bun is collected
            }
        }
    }
    private void checkBunCollisions(Paddle paddle) {
        // Check for collisions between the paddle and buns
        Iterator<Bun> iterator = gameManager.getBuns().iterator();
        while (iterator.hasNext()) {
            Bun bun = iterator.next();
            if (RectF.intersects(bun.getBounds(), paddle.getRect())) {
                bun.setVisible(false);

                soundManager.playBunCollectSound(); // Use appropriate sound for bun collection
                gameManager.increaseScore(5); // Increase score by 5 when a bun is collected
            }
        }
    }

    private void checkCoinCollisions(Paddle paddle) {
        // Check for collisions between the paddle and gold coins
        Iterator<GoldCoin> iterator = gameManager.getGoldCoins().iterator();
        while (iterator.hasNext()) {
            GoldCoin goldCoin = iterator.next();
            if (RectF.intersects(goldCoin.getBounds(), paddle.getRect())) {
                goldCoin.setVisible(false);

                soundManager.playCoinCollectSound();
                gameManager.increaseScore(25);
                                //soundManager.playCoinCollectSound();
             //   gameManager.getScoreManager().increaseScore(50); // Increase score by 50 when a coin is collected
            }
        }
    }


    private void checkWallCollisions(Ball ball, int screenWidth, int screenHeight) {
        if (ball.getRect().left <= 0 || ball.getRect().right >= screenWidth) {
            ball.reverseXVelocity();
            soundManager.playWallHitSound();
            Log.d("Collision", "Horizontal wall hit detected and sound played");
        }

        if (ball.getRect().top <= topPadding) { // Adjusted to check against topPadding
            ball.reverseYVelocity();
            soundManager.playWallHitSound();
            Log.d("Collision", "Top wall hit detected and sound played");

            // Ensure ball stays below topPadding after collision
            ball.setRectTop(topPadding + 1); // Adjust as needed
        }
    }


    private void checkPaddleCollision(Ball ball, Paddle paddle) {
        if (RectF.intersects(ball.getRect(), paddle.getRect())) {
            soundManager.playPaddleHitSound();

            // Calculate hit point relative to paddle width
            float hitPoint = (ball.getRect().left + ball.getRect().width() / 2 - paddle.getRect().left) / paddle.getRect().width() - 0.5f;

            // Determine reflection angle based on hit point
            float maxReflectionAngle = 115.0f; // Maximum reflection angle (in degrees)
            float reflectionAngle = hitPoint * maxReflectionAngle;

            // Calculate new velocities based on reflection angle
            float velMagnitude = (float) Math.sqrt(ball.getVelocityX() * ball.getVelocityX() + ball.getVelocityY() * ball.getVelocityY());
            float speedReductionFactor = 0.8f; // Reduce speed to 80% of the current speed
            velMagnitude *= speedReductionFactor;
            // Log the current velocity magnitude
            Log.d("Velocity", "Current velMagnitude: " + velMagnitude);


            float minSpeed = 12.0f; // Minimum speed limit
            // Apply speed reduction factor only if above the minimum speed
            if (velMagnitude < minSpeed) {
                velMagnitude /= speedReductionFactor;
            }

            float newVelDirection = (float) Math.toRadians(reflectionAngle); // Convert angle to radians

            // Calculate new velocity components
            float velX = velMagnitude * (float) Math.sin(newVelDirection);
            float velY = -velMagnitude * (float) Math.cos(newVelDirection);

            // Apply velocities to the ball
            ball.setVelocityX(velX);
            ball.setVelocityY(velY);

            // Move the ball out of the paddle to avoid sticking
            adjustBallPosition(ball, paddle);
        }
    }

    // Method to adjust ball position after collision with paddle
    private void adjustBallPosition(Ball ball, Paddle paddle) {
        // Set ball position above the paddle
        ball.setRectBottom(paddle.getRect().top);
        ball.setRectTop(paddle.getRect().top - ball.getBitmap().getHeight());
    }

    private void checkBrickCollisions(Context context, Ball ball, Bricks bricks) {
        RectF ballRect = ball.getRect();
        boolean collisionHandled = false;

        Random random = new Random();
        Iterator<Brick> iterator = bricks.getBricks().iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            RectF brickRect = brick.getRect();
            if (RectF.intersects(ballRect, brickRect) && !collisionHandled) {
                if (ball.isFireballMode()) {
                    // Destroy the brick and remove it from the list
                    iterator.remove();
                    gameManager.increaseScore(10);
                    collisionHandled = true;
                    System.out.println("Fireball Collision detected between ball and brick!");
                } else {

                    System.out.println("Collision detected between ball and brick!");

                    // Play the brick hit sound using SoundManager
                    soundManager.playBrickHitSound();

                    // Decrease the hit count of the brick
                    brick.decreaseHitCount();
                    if (brick.getHitCount() <= 0) {
                        // Brick is destroyed, remove it and spawn power-ups
                        iterator.remove();
                        gameManager.increaseScore(10);

                        // Spawn a power-up only if the brick is destroyed
                        float spawnX = brickRect.centerX();
                        float spawnY = brickRect.centerY();

                        int randomValue = random.nextInt(10); // Generates 0-9
                        switch (randomValue) {
                            case 0:
                                gameManager.spawnGoldCoin(context, spawnX, spawnY);
                                break;
                            case 1:
                                gameManager.spawnBun(context, spawnX, spawnY);
                                break;
                            case 2:
                                gameManager.spawnSausage(context, spawnX, spawnY);
                                break;
                            case 3:
                                gameManager.spawnBeer(context, spawnX, spawnY);
                                break;
                            case 4:
                                gameManager.spawnShortboard(context, spawnX, spawnY);
                                break;
                            case 5:
                                gameManager.spawnLongboard(context, spawnX, spawnY);
                                break;
                            case 6:
                                gameManager.spawnSizeup(context, spawnX, spawnY);
                                break;
                            case 7:
                                gameManager.spawnGrenade(context, spawnX, spawnY);
                                break;
                            case 8:
                                gameManager.spawnBallTriple(context, spawnX, spawnY);
                                break;
                            case 9:
                                gameManager.spawnBallFire(context, spawnX, spawnY);
                                break;
                        }
                    } else {
                        // Optionally update the brick's appearance if it's not yet destroyed
                        brick.updateBitmapForHit(context);
                    }

                    // Determine the side of the collision and adjust ball velocity accordingly
                   /* float ballCenterX = ballRect.centerX();
                    float ballCenterY = ballRect.centerY();
                    float brickCenterX = brickRect.centerX();
                    float brickCenterY = brickRect.centerY();

                    float dx = Math.abs(ballCenterX - brickCenterX);
                    float dy = Math.abs(ballCenterY - brickCenterY);

                    if (dx > dy) {
                        if (ballCenterX < brickCenterX) {
                            ball.setVelocityX(-Math.abs(ball.getVelocityX()));
                            ball.setRectLeft(brickRect.left - ball.getBitmap().getWidth() / 2);
                        } else {
                            ball.setVelocityX(Math.abs(ball.getVelocityX()));
                            ball.setRectLeft(brickRect.right + ball.getBitmap().getWidth() / 2);
                        }
                    } else {
                        if (ballCenterY < brickCenterY) {
                            ball.setVelocityY(-Math.abs(ball.getVelocityY()));
                            ball.setRectTop(brickRect.top - ball.getBitmap().getHeight() / 2);
                        } else {
                            ball.setVelocityY(Math.abs(ball.getVelocityY()));
                            ball.setRectTop(brickRect.bottom + ball.getBitmap().getHeight() / 2);
                        }
                    }*/


                    /*  This code is good, but still had the problem when hitting the cornner, it will  cause multiple time hitting in milliseconds.
                    float ballCenterX = ballRect.centerX();
                    float ballCenterY = ballRect.centerY();
                    float brickCenterX = brickRect.centerX();
                    float brickCenterY = brickRect.centerY();

                    float dx = Math.abs(ballCenterX - brickCenterX);
                    float dy = Math.abs(ballCenterY - brickCenterY);
                    // Define a small buffer to handle corner cases
                    float buffer = 5.0f;

// Handle collision by comparing the distances with a buffer
                    if (Math.abs(dx - dy) < buffer) {
                        // Corner case: prioritize one axis over the other to avoid multiple hits
                        if (ballCenterX < brickCenterX) {
                            ball.setVelocityX(-Math.abs(ball.getVelocityX()));
                            ball.setRectLeft(brickRect.left - ball.getBitmap().getWidth() / 2);
                        } else {
                            ball.setVelocityX(Math.abs(ball.getVelocityX()));
                            ball.setRectLeft(brickRect.right + ball.getBitmap().getWidth() / 2);
                        }
                    } else {
                        if (dx > dy) {
                            // Side collision
                            if (ballCenterX < brickCenterX) {
                                ball.setVelocityX(-Math.abs(ball.getVelocityX()));
                                ball.setRectLeft(brickRect.left - ball.getBitmap().getWidth() / 2);
                            } else {
                                ball.setVelocityX(Math.abs(ball.getVelocityX()));
                                ball.setRectLeft(brickRect.right + ball.getBitmap().getWidth() / 2);
                            }
                        } else {
                            // Top or bottom collision
                            if (ballCenterY < brickCenterY) {
                                ball.setVelocityY(-Math.abs(ball.getVelocityY()));
                                ball.setRectTop(brickRect.top - ball.getBitmap().getHeight() / 2);
                            } else {
                                ball.setVelocityY(Math.abs(ball.getVelocityY()));
                                ball.setRectTop(brickRect.bottom + ball.getBitmap().getHeight() / 2);
                            }
                        }
                    }

// Adjust the ball's position further to ensure it doesn't overlap with multiple bricks
                    ball.setRectLeft(Math.max(ball.getRect().left, 0));
                    ball.setRectTop(Math.max(ball.getRect().top, 0));
                    ball.setRectRight(Math.min(ball.getRect().right, screenWidth));
                    ball.setRectBottom(Math.min(ball.getRect().bottom, screenHeight));*/


                    // Calculate collision side and adjust ball velocity
                    //adjustBallVelocity(ball, brickRect);
                    // Resolve the collision based on edges
                    resolveCollision2(ball, brickRect);

                    collisionHandled = true;
                    ball.setCollisionCooldown(true);
                }
            }
        }
    }

    private void resolveCollision2(Ball ball, RectF brickRect) {
        RectF ballRect = ball.getRect();

        // Convenience variables for edges
        float ballLeft = ballRect.left;
        float ballRight = ballRect.right;
        float ballTop = ballRect.top;
        float ballBottom = ballRect.bottom;

        float brickLeft = brickRect.left;
        float brickRight = brickRect.right;
        float brickTop = brickRect.top;
        float brickBottom = brickRect.bottom;

        float ballCenterX = (ballLeft + ballRight) / 2;
        float ballCenterY = (ballTop + ballBottom) / 2;

        float overlapLeft = ballRight - brickLeft;
        float overlapRight = brickRight - ballLeft;
        float overlapTop = ballBottom - brickTop;
        float overlapBottom = brickBottom - ballTop;

        // Determine the smallest overlap
        float minOverlap = Math.min(Math.min(overlapLeft, overlapRight), Math.min(overlapTop, overlapBottom));

        // Resolve collision based on smallest overlap
        if (minOverlap == overlapLeft) {
            // Collision on the left
            ball.setVelocityX(-Math.abs(ball.getVelocityX()));
            ball.setRectRight(brickRect.left);
        } else if (minOverlap == overlapRight) {
            // Collision on the right
            ball.setVelocityX(Math.abs(ball.getVelocityX()));
            ball.setRectLeft(brickRect.right);
        } else if (minOverlap == overlapTop) {
            // Collision on the top
            ball.setVelocityY(-Math.abs(ball.getVelocityY()));
            ball.setRectBottom(brickRect.top);
        } else if (minOverlap == overlapBottom) {
            // Collision on the bottom
            ball.setVelocityY(Math.abs(ball.getVelocityY()));
            ball.setRectTop(brickRect.bottom);
        }

        // Ensure the ball is within screen bounds
        ball.setRectLeft(Math.max(ball.getRect().left, 0));
        ball.setRectTop(Math.max(ball.getRect().top, 0));
        ball.setRectRight(Math.min(ball.getRect().right, screenWidth));
        ball.setRectBottom(Math.min(ball.getRect().bottom, screenHeight));
    }


    private void resolveCollision(Ball ball, RectF brickRect) {
        RectF ballRect = ball.getRect();

        // Convenience variables
        float ballLeft = ballRect.left;
        float ballRight = ballRect.right;
        float ballTop = ballRect.top;
        float ballBottom = ballRect.bottom;

        float brickLeft = brickRect.left;
        float brickRight = brickRect.right;
        float brickTop = brickRect.top;
        float brickBottom = brickRect.bottom;

        boolean flipX = (ballRight >= brickLeft && ballLeft <= brickRight);
        boolean flipY = (ballBottom >= brickTop && ballTop <= brickBottom);

        // Resolve collision along X axis
        if (flipX) {
            ball.setVelocityX(-ball.getVelocityX());
            if (ball.getVelocityX() > 0) {
                ball.setRectLeft(brickRect.right);
            } else {
                ball.setRectRight(brickRect.left);
            }
        }

        // Resolve collision along Y axis
        if (flipY) {
            ball.setVelocityY(-ball.getVelocityY());
            if (ball.getVelocityY() > 0) {
                ball.setRectTop(brickRect.bottom);
            } else {
                ball.setRectBottom(brickRect.top);
            }
        }

        // Prevent the ball from overlapping with the brick
        ball.setRectLeft(Math.max(ball.getRect().left, 0));
        ball.setRectTop(Math.max(ball.getRect().top, 0));
        ball.setRectRight(Math.min(ball.getRect().right, screenWidth));
        ball.setRectBottom(Math.min(ball.getRect().bottom, screenHeight));
    }
    private void adjustBallVelocity(Ball ball, RectF brickRect) {
        RectF ballRect = ball.getRect();

        // Calculate the distance from the ball's center to the brick's center
        float ballCenterX = ballRect.centerX();
        float ballCenterY = ballRect.centerY();
        float brickCenterX = brickRect.centerX();
        float brickCenterY = brickRect.centerY();

        float dx = ballCenterX - brickCenterX;
        float dy = ballCenterY - brickCenterY;

        // Use the angle to determine the collision type
        double angle = Math.toDegrees(Math.atan2(dy, dx));
        double thresholdAngle = 45.0;

        if (Math.abs(angle) > thresholdAngle) {
            // Side collision
            if (ballCenterX < brickCenterX) {
                ball.setVelocityX(-Math.abs(ball.getVelocityX()));
                ball.setRectLeft(brickRect.left - ball.getBitmap().getWidth() / 2);
            } else {
                ball.setVelocityX(Math.abs(ball.getVelocityX()));
                ball.setRectLeft(brickRect.right + ball.getBitmap().getWidth() / 2);
            }
        } else {
            // Top or bottom collision
            if (ballCenterY < brickCenterY) {
                ball.setVelocityY(-Math.abs(ball.getVelocityY()));
                ball.setRectTop(brickRect.top - ball.getBitmap().getHeight() / 2);
            } else {
                ball.setVelocityY(Math.abs(ball.getVelocityY()));
                ball.setRectTop(brickRect.bottom + ball.getBitmap().getHeight() / 2);
            }
        }

        // Adjust the ball's position further to ensure it doesn't overlap with multiple bricks
        ball.setRectLeft(Math.max(ball.getRect().left, 0));
        ball.setRectTop(Math.max(ball.getRect().top, 0));
        ball.setRectRight(Math.min(ball.getRect().right, screenWidth));
        ball.setRectBottom(Math.min(ball.getRect().bottom, screenHeight));
    }

}

