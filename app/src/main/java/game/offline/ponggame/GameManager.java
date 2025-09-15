package game.offline.ponggame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GameManager {
    public interface GameOverListener {
        void onGameOver();
        void onGameWon();
    }
    private boolean hasAddedNewBall = false;
    private static final int DEFAULT_PADDLE_WIDTH = 200;
    private static final int DEFAULT_HANDLE_HEIGHT = 50;
    private Context context;
    private GameOverListener gameOverListener;
    private Ball ball;
    private List<Ball> balls;

    private Paddle paddle;
    private Bricks bricks;
    private boolean isGameOver = false;
    private SoundManager soundManager;
    private InputHandler inputHandler;
    private CollisionDetector collisionDetector;
    private RoundScoreManager roundScoreManager;

    private int topPadding;

    private int ballType;

    private List<GoldCoin> goldCoins;
    private List<Bun> buns;
    private List<Sausage> sausages;

    private List<Beer> beers;

    private List<Shortboard> shortboards;
    private List<Longboard> longboards;
    private List<Sizeup> sizeups;
    private List<Grenade> grenades;

    private List<BallTriple> balltriples;

    private List<BallFire> ballfires;

    private ListIterator<Ball> ballIterator;

    private boolean isBallTripleActivated = false;

    private Canvas canvas;
    private int screenHeight;
    private GameScoreManager gameScoreManager;
    public GameManager(Context context, int screenWidth, int screenHeight, int topPadding) {

        gameScoreManager = new GameScoreManager(context);
        this.screenHeight=screenHeight;
        this.context = context;
        this.topPadding = topPadding;
        soundManager = new SoundManager(context);
        float initialBallSpeedX = 10.0f;
        float initialBallSpeedY = 10.0f;
        ballType=0;
        float ballCenterX = 0;
        float ballCenterY = 0;
       // this.ball = new Ball(context, screenWidth, screenHeight, initialBallSpeedX, initialBallSpeedY, ballCenterX,ballCenterY,ballType);
        this.balls = new ArrayList<>();
      //          Ball  ball1 = new Ball(context, screenWidth, screenHeight, initialBallSpeedX, initialBallSpeedY, ballCenterX,ballCenterY,ballType);
     //   this.balls.add(ball1);
      //  Ball  ball2 = new Ball(context, screenWidth, screenHeight, initialBallSpeedX, initialBallSpeedY, ballCenterX,ballCenterY,ballType);
    //    this.balls.add(ball2);
        //deal with triple balls
        //pre add the ball1 and ball 2 into the balls list, and prevent in furture to add again.
    //    Ball  ball1 = new Ball(context, screenWidth, screenHeight, initialBallSpeedX, initialBallSpeedY, ballCenterX,ballCenterY,ballType);
   //        this.balls.add(ball1);
    //    Ball  ball2 = new Ball(context, screenWidth, screenHeight, initialBallSpeedX, initialBallSpeedY, ballCenterX,ballCenterY,ballType);
      //    this.balls.add(ball2);


        this.goldCoins = new ArrayList<>();
        this.buns = new ArrayList<>();
        this.sausages = new ArrayList<>();
        this.beers = new ArrayList<>();
        this.shortboards = new ArrayList<>();
        this.longboards = new ArrayList<>();
        this.sizeups = new ArrayList<>();
        this.grenades = new ArrayList<>();
        this.balltriples = new ArrayList<>();
        this.ballfires=new ArrayList<>();


        float newSpeedX = 15.0f;
        float newSpeedY = 15.0f;

        for(Ball ball:balls) {
            ball.setSpeed(newSpeedX, newSpeedY);
        }

        int paddleWidth = 220;
        int handleHeight = 50;

        float startX=0;
        float startY=0;
        paddle = new Paddle(context, screenWidth, screenHeight, paddleWidth, handleHeight, startX,  startY);

        bricks = new Bricks(soundManager);
        inputHandler = new InputHandler(paddle);
        collisionDetector = new CollisionDetector(context,this, soundManager, topPadding);
        roundScoreManager = new RoundScoreManager();
        resetGame(screenWidth, screenHeight);

    }
    public void activateBallTriple(Ball newBall) {
        //isBallTripleActivated = true;
     addBall(newBall);
    }
    // Method to add a ball
    public void addBall(Ball newBall) {
        this.balls.add(newBall);
    }
    public void setGameOverListener(GameOverListener listener) {
        this.gameOverListener = listener;
    }

    public void resetGame(int screenWidth, int screenHeight) {
        isGameOver = false;
        for(Ball ball:balls) {
            ball.reset(screenWidth / 2, screenHeight / 2);
        }
        bricks.initializeBricks(context,screenWidth, screenHeight, topPadding);
        roundScoreManager.resetScore();
        synchronized (goldCoins) {
            goldCoins.clear();
        }
        synchronized (buns) {
            buns.clear();
        }

        synchronized (sausages) {
            sausages.clear();
        }
        synchronized (buns) {
            buns.clear();
        }

        synchronized (beers) {
            beers.clear();
        }

        synchronized (shortboards) {
            shortboards.clear();
        }

        synchronized (longboards) {
            longboards.clear();
        }

        synchronized (sizeups) {
            sizeups.clear();
        }
        synchronized (grenades) {
            grenades.clear();
        }

        synchronized (balltriples) {
            balltriples.clear();
        }

        synchronized (ballfires) {
            ballfires.clear();
        }
        int paddleWidth = DEFAULT_PADDLE_WIDTH;
        int handleHeight = DEFAULT_HANDLE_HEIGHT;
        float startX=0;
        float startY=0;
        paddle = new Paddle(context, screenWidth, screenHeight, paddleWidth, handleHeight, startX, startY);
       // ball = new Ball(context,screenWidth, screenHeight,10,10,0,0,0);
        this.balls = new ArrayList<>();
        Ball  ball1 = new Ball(context,screenWidth, screenHeight,10,10,0,0,0);
        this.balls.add(ball1);
        Ball  ball2 = new Ball(context,screenWidth, screenHeight,9,8,0,0,0);
        this.balls.add(ball2);
     //   Ball  ball3 = new Ball(context,screenWidth, screenHeight,8,8,0,0,0);
       // this.balls.add(ball3);
    }

    public void updateGame(Context context, int screenWidth, int screenHeight) {
        // Check if the paddle instance needs updating

        // Log the initial state of isBallTripleActivated
        Log.d("GameManager", "isBallTripleActivated: " + isBallTripleActivated);
        if (paddle != null) {
            // Example: Update paddle position based on input
            inputHandler.updatePaddlePosition(paddle);
        }


        if (!isGameOver) {
            for (Ball ball : balls) {
                if (ball != null) {
                    if (!ball.isDestroyed()) {
                        ball.update(screenWidth, screenHeight);
                    }
                } else {
                    Log.e("GameManager", "Ball object is null.");
                }
            }

               // if (ball.isDestroyed() != true) {

            //attention if put synchronized part into for ball loop, the falling speed of spawned items will be faster.
                    synchronized (goldCoins) {
                        for (GoldCoin goldCoin : goldCoins) {
                            goldCoin.update(screenHeight);
                        }
                    }

                    synchronized (buns) {
                        for (Bun bun : buns) {
                            bun.update(screenHeight);
                        }
                    }

                    synchronized (sausages) {
                        for (Sausage sausage : sausages) {
                            sausage.update(screenHeight);
                        }
                    }
                    synchronized (beers) {
                        for (Beer beer : beers) {
                            beer.update(screenHeight);
                        }
                    }
                    synchronized (shortboards) {
                        for (Shortboard shortboard : shortboards) {
                            shortboard.update(screenHeight);
                        }
                    }
                    synchronized (longboards) {
                        for (Longboard longboard : longboards) {
                            longboard.update(screenHeight);
                        }
                    }

                    synchronized (sizeups) {
                        for (Sizeup sizeup : sizeups) {
                            sizeup.update(screenHeight);
                        }
                    }

                    synchronized (grenades) {
                        for (Grenade grenade : grenades) {
                            grenade.update(screenHeight);
                        }
                    }

                    synchronized (balltriples) {
                        for (BallTriple ballTriple : balltriples) {
                            ballTriple.update(screenHeight);
                        }
                    }
                    synchronized (ballfires) {
                        for (BallFire ballFire : ballfires) {
                            ballFire.update(screenHeight);
                        }
                    }


            //for (Iterator<Ball> ballIterator = balls.iterator(); ballIterator.hasNext();) {
           //     Ball ball = ballIterator.next();
           // for (ListIterator<Ball> ballIterator = balls.listIterator();
            for (this.ballIterator = balls.listIterator();
                 ballIterator.hasNext();) {

                Ball ball = ballIterator.next();
                if (!ball.isDestroyed()) {
                    collisionDetector.detectCollisions(context, balls, ballIterator,ball, screenWidth, screenHeight);

                    // Your condition to decide when to add a new ball. Andy it can directly add a new ball.
                    //"AFter comment out this if (){} part, the multiple onwingameover iwndows pop up disappeared, why?"   Andy
                 /*   if (!hasAddedNewBall) { // Check if the new ball has not been added yet
                        Ball ballx3 = new Ball(context, screenWidth, screenHeight, 7, 8, 0, 0, 0);
                        ballIterator.add(ballx3); // Add the new ball to the list
                        hasAddedNewBall = true; // Set the flag to true to prevent further additions
                    }*/


                    if (ball.getRect().top < topPadding) {
                        ball.setRectTop(topPadding + 1);
                    }
                    ball.update(screenWidth, screenHeight);

                    if (bricks.getBricks().isEmpty()) {
                        isGameOver = true;
                        if (gameOverListener != null) {
                            gameOverListener.onGameWon();

                        }
                        break; // Break out of the loop since the win condition is met, to prevent the multiple run if (bricks.getBricks().isEmpty()) {}
                    }

                    // Check if the ball falls out of the screen
                    if (ball.getRect().bottom >= screenHeight) {
                        // If this is the last ball on the screen, trigger game over
                        if (balls.size() == 1) {
                            isGameOver = true;
                            if (gameOverListener != null) {
                                gameOverListener.onGameOver();
                            }
                            ball.reset(screenWidth / 2, screenHeight / 2);
                        }
                        ballIterator.remove();  // Remove the ball from the list
                    }


                    synchronized (goldCoins) {
                        Iterator<GoldCoin> iterator = goldCoins.iterator();
                        while (iterator.hasNext()) {
                            GoldCoin goldCoin = iterator.next();
                            if (!goldCoin.isVisible()) {
                                iterator.remove();
                            }
                        }
                    }

                    synchronized (buns) {
                        Iterator<Bun> iterator = buns.iterator();
                        while (iterator.hasNext()) {
                            Bun bun = iterator.next();
                            if (!bun.isVisible()) {
                                iterator.remove();
                            }
                        }
                    }
                    synchronized (sausages) {
                        Iterator<Sausage> iterator = sausages.iterator();
                        while (iterator.hasNext()) {
                            Sausage sausage = iterator.next();
                            if (!sausage.isVisible()) {
                                iterator.remove();
                            }
                        }
                    }

                    synchronized (beers) {
                        Iterator<Beer> iterator = beers.iterator();
                        while (iterator.hasNext()) {
                            Beer beer = iterator.next();
                            if (!beer.isVisible()) {
                                iterator.remove();
                            }
                        }
                    }

                    synchronized (shortboards) {
                        Iterator<Shortboard> iterator = shortboards.iterator();
                        while (iterator.hasNext()) {
                            Shortboard shortboard = iterator.next();
                            if (!shortboard.isVisible()) {
                                iterator.remove();
                            }
                        }
                    }

                    synchronized (longboards) {
                        Iterator<Longboard> iterator = longboards.iterator();
                        while (iterator.hasNext()) {
                            Longboard longboard = iterator.next();
                            if (!longboard.isVisible()) {
                                iterator.remove();
                            }
                        }
                    }

                    synchronized (sizeups) {
                        Iterator<Sizeup> iterator = sizeups.iterator();
                        while (iterator.hasNext()) {
                            Sizeup sizeup = iterator.next();
                            if (!sizeup.isVisible()) {
                                iterator.remove();
                            }
                        }
                    }

                    synchronized (grenades) {
                        Iterator<Grenade> iterator = grenades.iterator();
                        while (iterator.hasNext()) {
                            Grenade grenade = iterator.next();
                            if (!grenade.isVisible()) {
                                iterator.remove();
                            }
                        }
                    }

                    synchronized (balltriples) {
                        Iterator<BallTriple> iterator = balltriples.iterator();
                        while (iterator.hasNext()) {
                            BallTriple ballTriple = iterator.next();
                            if (!ballTriple.isVisible()) {
                                iterator.remove();
                            }
                        }
                    }

                    synchronized (ballfires) {
                        Iterator<BallFire> iterator = ballfires.iterator();
                        while (iterator.hasNext()) {
                            BallFire ballFire = iterator.next();
                            if (!ballFire.isVisible()) {
                                iterator.remove();
                            }
                        }
                    }
                }
            }
        }
    }


    public void triggerGameOver() {    //used by grenade explosion merely
        isGameOver = true;
        if (gameOverListener != null) {
            gameOverListener.onGameOver();
        }
        //ball.reset(screenWidth / 2, screenHeight / 2);
    }

    public void drawGame(Canvas canvas, Paint paint, Bitmap ballBitmap, Bitmap brickBitmap, Bitmap paddleBitmap, Bitmap goldCoinBitmap, Bitmap bunBitmap, Bitmap sausageBitmap, Bitmap beerBitmap, Bitmap shortboardBitmap, Bitmap longboardBitmap, Bitmap sizeupBitmap, Bitmap grenadeBitmap, Bitmap ballTripleBitmap, Bitmap ballFireBitmap) {
        //The drawGame method is called within the game's rendering loop, which typically refreshes the screen many times per second. Here's a breakdown of how it fits into the overall game loop and what happens with respect to refreshing the screen:
        if (!isGameOver) {
            paint.setColor(Color.BLUE);
            canvas.drawRect(0, 0, canvas.getWidth(), topPadding, paint);

           // canvas.drawBitmap(ball.getBitmap(), ball.getRect().left, ball.getRect().top, paint);// Assuming your Ball class has a draw method
            if (balls != null) {
                for (Ball ball : balls) {
                    if (ball != null) {
                         ballBitmap = ball.getBitmap();
                        if (ballBitmap != null) {
                            canvas.drawBitmap(ball.getBitmap(), ball.getRect().left, ball.getRect().top, paint);
                        } else {
                            Log.e("GameManager", "Ball bitmap is null.");
                        }
                    } else {
                        Log.e("GameManager", "Ball object is null.");
                    }
                }
            } else {
                Log.e("GameManager", "Balls list is null.");
            }

            canvas.drawBitmap(paddle.getBitmap(), paddle.getRect().left, paddle.getRect().top, paint);

           /* for (Brick brick : bricks.getBricks()) {
                canvas.drawBitmap(brickBitmap, null, brick.getRect(), paint);
            }*/
            for (Brick brick : bricks.getBricks()) {
                // Get the bitmap specific to this brick
                 brickBitmap = brick.getBrickBitmap();
                // Draw the brick using its bitmap and rect
                if (brickBitmap != null) {
                    canvas.drawBitmap(brickBitmap, null, brick.getRect(), paint);
                } else {
                    Log.e("GameManager", "Brick bitmap is null for brick type: " + brick.getBrickType());
                }
            }


  /*  If random() method write in gamemanger, it will cause the bricks flashing.
            for (Brick brick : bricks.getBricks()) {
                String brickType = getRandomBrickType(); // Get a random brick type
                 brickBitmap = getBrickBitmap(brickType); // Get the bitmap for the brick type
                canvas.drawBitmap(brickBitmap, null, brick.getRect(), paint);
            }*/


            synchronized (goldCoins) {
                for (GoldCoin goldCoin : goldCoins) {
                    canvas.drawBitmap(goldCoinBitmap, goldCoin.getX(), goldCoin.getY(), paint);
                }
            }

            synchronized (buns) {
                for (Bun bun : buns) {
                    canvas.drawBitmap(bunBitmap, bun.getX(), bun.getY(), paint);
                }
            }

            synchronized (sausages) {
                for (Sausage sausage : sausages) {
                    canvas.drawBitmap(sausageBitmap, sausage.getX(), sausage.getY(), paint);
                }
            }

            synchronized (beers) {
                for (Beer beer : beers) {
                    canvas.drawBitmap(beerBitmap, beer.getX(), beer.getY(), paint);
                }
            }

            synchronized (shortboards) {
                for (Shortboard shortboard : shortboards) {
                    canvas.drawBitmap(shortboardBitmap, shortboard.getX(), shortboard.getY(), paint);
                }
            }

            synchronized (longboards) {
                for (Longboard longboard : longboards) {
                    canvas.drawBitmap(longboardBitmap, longboard.getX(), longboard.getY(), paint);
                }
            }
            synchronized (sizeups) {
                for (Sizeup sizeup : sizeups) {
                    canvas.drawBitmap(sizeupBitmap, sizeup.getX(), sizeup.getY(), paint);
                }
            }

            synchronized (grenades) {
                for (Grenade grenade : grenades) {
                    canvas.drawBitmap(grenadeBitmap, grenade.getX(), grenade.getY(), paint);
                }
            }

            synchronized (balltriples) {
                for (BallTriple ballTriple : balltriples) {
                    canvas.drawBitmap(ballTripleBitmap, ballTriple.getX(), ballTriple.getY(), paint);
                }
            }

            synchronized (ballfires) {
                for (BallFire ballFire : ballfires) {
                    canvas.drawBitmap(ballFireBitmap, ballFire.getX(), ballFire.getY(), paint);
                }
            }
           // paint.setColor(Color.WHITE);
           // canvas.drawText("Score: " + roundScoreManager.getScore(), backgroundLeft + 10, backgroundTop + paint.getTextSize() + 10, paint);
          drawRoundScore(canvas, roundScoreManager, paint, topPadding);
            drawGameScore(canvas, gameScoreManager, paint, topPadding);
        }else{
            //gameover logic here.
        }
    }

    private void drawRoundScore(Canvas canvas, RoundScoreManager roundScoreManager, Paint paint, float topPadding) {
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        float textWidth = paint.measureText("Score: " + roundScoreManager.getScore());
        float backgroundLeft = 50;
        float backgroundTop = (topPadding - paint.getTextSize()) / 2;
        float backgroundRight = backgroundLeft + textWidth + 20;
        float backgroundBottom = backgroundTop + paint.getTextSize() + 20;
        canvas.drawRect(backgroundLeft, backgroundTop, backgroundRight, backgroundBottom, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("Score: " + roundScoreManager.getScore(), backgroundLeft + 10, backgroundTop + paint.getTextSize() + 10, paint);
    }

    private void drawGameScore(Canvas canvas, GameScoreManager gameScoreManager, Paint paint, float topPadding) {
        paint.setColor(Color.RED);
        paint.setTextSize(50);
        float textWidth = paint.measureText("Total Score: " + gameScoreManager.getScore());
        float backgroundLeft = 500;
        float backgroundTop = (topPadding - paint.getTextSize()) / 2;
        float backgroundRight = backgroundLeft + textWidth + 20;
        float backgroundBottom = backgroundTop + paint.getTextSize() + 20;
        canvas.drawRect(backgroundLeft, backgroundTop, backgroundRight, backgroundBottom, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText("Total Score: " + gameScoreManager.getScore(), backgroundLeft + 10, backgroundTop + paint.getTextSize() + 10, paint);
    }

    public void deliverCanvas(Canvas canvas){
        this.canvas=canvas;
       // paddle.triggerEffect((int) paddle.getRect().centerX(), (int) paddle.getRect().centerY(),canvas);
    }

    public void trigger(){
        paddle.triggerEffect((int) paddle.getRect().centerX(), (int) paddle.getRect().centerY(),canvas);
    }

    public void destroyPaddle() {
        // Optionally, you can set the ball as destroyed
        ball.setDestroyed(true);

    }

    public void destroyBall() {
        if (ball != null) {  //this condition is added by GPT
            float offScreenY = screenHeight + ball.getRect().height(); // Position below the screen
            ball.getRect().top = offScreenY;
            ball.getRect().bottom = offScreenY + ball.getRect().height();
        }
     // Optionally, you can set the ball as destroyed
    // ball.setDestroyed(true); //when the ball is falling out of the screen, the ball is null, you cannot setDestoryed() method on null ball.
 }

    public void spawnGoldCoin(Context context, float x, float y) {
        GoldCoin goldCoin = new GoldCoin(context, x, y);
        synchronized (goldCoins) {
            goldCoins.add(goldCoin);
        }
    }

    public void spawnBun(Context context, float x, float y) {
        Bun bun = new Bun(context, x, y);
        synchronized (buns) {
            buns.add(bun);
        }
    }

    public void spawnSausage(Context context, float x, float y) {
        Sausage sausage = new Sausage(context, x, y);
        synchronized (sausages) {
            sausages.add(sausage);
        }
    }

    public void spawnBeer(Context context, float x, float y) {
        Beer beer = new Beer(context, x, y);
        synchronized (beers) {
            beers.add(beer);
        }
    }

    public void spawnShortboard(Context context, float x, float y) {
        Shortboard shortboard = new Shortboard(context, x, y);
        synchronized (shortboards) {
            shortboards.add(shortboard);
        }
    }

    public void spawnLongboard(Context context, float x, float y) {
        Longboard longboard= new Longboard(context, x, y);
        synchronized (longboards) {
            longboards.add(longboard);
        }
    }

    public void spawnSizeup(Context context, float x, float y) {
        Sizeup sizeup= new Sizeup(context, x, y);
        synchronized (sizeups) {
            sizeups.add(sizeup);
        }
    }
    public void spawnGrenade(Context context, float x, float y) {
        Grenade grenade= new Grenade(context, x, y);
        synchronized (grenades) {
            grenades.add(grenade);
        }
    }

    public void spawnBallTriple(Context context, float x, float y) {
        BallTriple ballTriple= new BallTriple(context, x, y);
        synchronized (balltriples) {
            balltriples.add(ballTriple);
        }
    }

    public void spawnBallFire(Context context, float x, float y) {
        BallFire ballFire= new BallFire(context, x, y);
        synchronized (ballfires) {
            ballfires.add(ballFire);
        }
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public Ball getBall() {
        return ball;
    }
    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }
    public void setPaddle(Paddle paddle) {
        this.paddle = paddle;
    }

    public Bricks getBricks() {
        return bricks;
    }

    public List<GoldCoin> getGoldCoins() {
        return goldCoins;
    }
    public List<Bun> getBuns() {
        return buns;
    }
    public List<Sausage> getSausages() {
        return sausages;
    }

    public List<Beer> getBeers() {
        return beers;
    }

    public List<Shortboard> getShortboards() {
        return shortboards;
    }

    public List<Longboard> getLongboards() {
        return longboards;
    }

    public List<Sizeup> getSizeups() {
        return sizeups;
    }

    public List<Grenade> getGrenades() {
        return grenades;
    }

    public List<BallTriple> getBalltriples() {
        return balltriples;
    }

    public List<BallFire> getBallfires() {
        return ballfires;
    }
    public void handleTouchEvent(MotionEvent event) {
        if (!isGameOver) {
            inputHandler.handleTouchEvent(event);
        }
    }

    public void increaseScore(int amount) {
        roundScoreManager.increaseScore(amount);
        gameScoreManager.increaseScore(amount);
    }


    public List<Ball> getBalls() {
        return balls;
    }
    /*public void setBall(Ball ball) {
        this.ball = ball;
    }*/

    public void setBalls(List<Ball> balls) {
        this.balls = balls;
    }
}



