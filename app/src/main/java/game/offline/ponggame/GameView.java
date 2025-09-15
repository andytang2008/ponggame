package game.offline.ponggame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
public class GameView extends View {
    private Paint paint;
    private Handler handler;
    private Runnable runnable;

    private Bitmap ballBitmap;
    private Bitmap brickBitmap;
    private Bitmap paddleBitmap;
    private Bitmap goldCoinBitmap;
    private Bitmap bunBitmap;
    private Bitmap sausageBitmap;
    private Bitmap beerBitmap;
    private Bitmap shortboardBitmap;

    private Bitmap longboardBitmap;

    private Bitmap sizeupBitmap;
    private Bitmap grenadeBitmap;

    private Bitmap ballTripleBitmap;

    private Bitmap ballFireBitmap;

   // private Bitmap explosionBitmap;
    private final int refreshRate = 16; // ~60 FPS
    private GameManager gameManager;
    private SoundManager soundManager;
    private Handler countdownHandler;
    private Runnable countdownRunnable;

    private int topPadding = 200; // Define your top padding here

    private int screenWidth;
    private int screenHeight;

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    private Brick brick;
    private Drawable gifDrawable;
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        soundManager = new SoundManager(context);
    }

    private void init(Context context) {
        paint = new Paint();
        ballBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ball);
       // brickBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.brick);
        brick = new Brick(context, 100, 100, 200, 150,"clay");   //this definition can be whatever any one, it didn't influence the real brick which came from png
        brickBitmap = brick.getBrickBitmap();  //brick definition just for use of getBrickBipmap.
        paddleBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.paddle);

        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        };
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        GlobalState.getInstance().setScreenWidth(w);
        GlobalState.getInstance().setScreenHeight(h);
        screenWidth = w;
        screenHeight = h;

        gameManager = new GameManager(getContext(), w, h, topPadding);
        gameManager.setGameOverListener(new GameManager.GameOverListener() {
            @Override
            public void onGameOver() {
                showGameOverDialog();
            }

            @Override
            public void onGameWon() {
                Log.d("GameView", "Game Won! Showing win dialog...");
                Log.d("GameView", "Game Won called at: " + System.currentTimeMillis());
                showWinDialog();
            }
        });
    }

    private Canvas currentCanvas;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        this.gameManager = gameManager;
        if (!gameManager.isGameOver()) {

            // Ensure all Bitmap objects are initialized before passing them
            if (ballBitmap == null) {
                ballBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
            }
            if (brickBitmap == null) {
                brickBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.brick);
            }
            if (paddleBitmap == null) {
                paddleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.paddle);
            }
            if (goldCoinBitmap == null) {
                goldCoinBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.coin);
            }
            if (bunBitmap == null) {
                bunBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bun);
            }
            if (sausageBitmap == null) {
                sausageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sausage);
            }

            if (beerBitmap == null) {
                beerBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.beer);
            }

            if (shortboardBitmap == null) {
                shortboardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.shortboard);
            }

            if (longboardBitmap == null) {
                longboardBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.longboard);
            }

            if (sizeupBitmap == null) {
                sizeupBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sizeup);
            }
            if (grenadeBitmap == null) {
                grenadeBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.grenade);
            }

            if (ballTripleBitmap == null) {
                ballTripleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball_triple);
            }

            if (ballFireBitmap == null) {
                ballFireBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball_fire_bounce);
            }

           // triggerGamemangerGif();
           gameManager.deliverCanvas(canvas);  //This trigger using canvas without problem.



            gameManager.drawGame(canvas, paint, ballBitmap, brickBitmap, paddleBitmap, goldCoinBitmap, bunBitmap, sausageBitmap, beerBitmap,shortboardBitmap,longboardBitmap,sizeupBitmap,grenadeBitmap, ballTripleBitmap, ballFireBitmap);
            gameManager.updateGame(getContext(), getWidth(), getHeight());
            handler.postDelayed(runnable, refreshRate);
        }
    }

    /*public void triggerGamemangerGif(){
        gameManager.trigger(currentCanvas); // there is time delay to cdreate the currentCanvas, so null error happened.
    }*/


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gameManager.handleTouchEvent(event);
        return true;
    }


    public void showGameOverDialog() {  //change it to public so that collsiion detector class can call it when grenade hit the paddle.
        if (gameManager != null) {
            soundManager.playGameOverSound();
        }

        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Game Over! The app will exit in 15 seconds if no action is taken.")
                    .setPositiveButton("Restart", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            gameManager.resetGame(getWidth(), getHeight());
                            handler.postDelayed(runnable, refreshRate); // Restart the game loop
                            // Cancel the countdown if the user clicks "Restart"
                            countdownHandler.removeCallbacks(countdownRunnable);
                        }
                    })
                    .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // Finish the current activity instead of System.exit(0)
                            ((Activity) getContext()).finish();
                        }
                    });

            final AlertDialog alert = builder.create();
            alert.setCanceledOnTouchOutside(false); // Allow dismiss by clicking outside
            alert.show();

            // Start the countdown timer
            countdownHandler = new Handler(Looper.getMainLooper());
            countdownRunnable = new Runnable() {
                int countdown = 15;

                @Override
                public void run() {
                    if (countdown > 0) {
                        alert.setMessage("Game Over! The app will exit in " + countdown + " seconds if no action is taken.");
                        countdown--;
                        countdownHandler.postDelayed(this, 1000); // Update message every second
                    } else {
                        // Exit the game after 15 seconds
                        ((Activity) getContext()).finish();
                        alert.dismiss(); // Dismiss the dialog if it's still showing
                    }
                }
            };
            countdownHandler.post(countdownRunnable);

            // Set OnDismissListener to clean up countdown handler if dialog is dismissed
            alert.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    countdownHandler.removeCallbacks(countdownRunnable);
                }
            });

        } catch (Exception e) {
            Log.e("GameView", "Error showing game over dialog: " + e.getMessage());
        }
    }

    private void showWinDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("You Win!")
                .setMessage("Congratulations, you destroyed all the bricks!")
                .setPositiveButton("Play New Round", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gameManager.resetGame(getWidth(), getHeight());
                        handler.postDelayed(runnable, refreshRate); // Restart game loop
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }

}
