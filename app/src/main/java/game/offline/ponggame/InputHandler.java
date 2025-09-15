package game.offline.ponggame;

import android.view.MotionEvent;

public class InputHandler {

    private Paddle paddle;

    public InputHandler(Paddle paddle) {
        this.paddle = paddle;
    }

    public void handleTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float touchX = event.getX();
            paddle.updatePosition(touchX - paddle.getWidth() / 2);
        }
    }
    public void updatePaddlePosition(Paddle paddle) {
        // Handle paddle movement based on input
        this.paddle = paddle;
    }

}