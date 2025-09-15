package game.offline.ponggame;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*
public class Bricks {
    private List<Brick> bricks;
    private SoundPool soundPool;

    private int soundIdBrickHit;

    public Bricks(SoundPool soundPool, int soundIdBrickHit) {
        this.soundPool = soundPool;
        this.soundIdBrickHit = soundIdBrickHit;
        this.bricks = new ArrayList<>();
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void initializeBricks(int screenWidth, int screenHeight) {
        // Clear existing bricks
        bricks.clear();

        // Randomly select a layout type
        String[] layoutTypes = {"GRID", "PYRAMID", "ZIGZAG", "TRIANGLE", "PARALLELOGRAM"};
        Random random = new Random();
        String selectedLayoutType = layoutTypes[random.nextInt(layoutTypes.length)];

        // Example parameters for layouts
        BrickLayoutGenerator.LayoutParams params;
        switch (selectedLayoutType) {
            case "GRID":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "GRID");
                break;
            case "PYRAMID":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 8, 50, 10, "PYRAMID");
                break;
            case "ZIGZAG":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "ZIGZAG");
                break;
            case "TRIANGLE":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "TRIANGLE");
                break;
            case "PARALLELOGRAM":
                params = new BrickLayoutGenerator.LayoutParams(5, 4, screenWidth / 7, 50, 10, "PARALLELOGRAM");
                break;
            default:
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "GRID"); // Fallback to GRID layout
                break;
        }

        // Generate the bricks layout
        BrickLayoutGenerator.generateBricksLayout(params, bricks, screenWidth);
    }

    public void checkBallBrickCollision(Ball ball) {
        RectF ballRect = ball.getRect();
        boolean collisionHandled = false; // Flag to indicate collision handling

        Iterator<Brick> iterator = bricks.iterator();
        while (iterator.hasNext()) {
            Brick brick = iterator.next();
            RectF brickRect = brick.getRect();
            if (RectF.intersects(ballRect, brickRect) && !collisionHandled) {
                System.out.println("Collision detected between ball and brick!");

                // Play the brick hit sound
                if (soundPool != null && soundIdBrickHit != 0) {
                    soundPool.play(soundIdBrickHit, 1.0f, 1.0f, 1, 0, 1.0f);
                }

                // Handle collision with the brick
                iterator.remove(); // Remove the brick from the list or mark it as destroyed

                // Determine the side of the collision and adjust ball velocity accordingly
                float ballCenterX = ballRect.centerX();
                float ballCenterY = ballRect.centerY();
                float brickCenterX = brickRect.centerX();
                float brickCenterY = brickRect.centerY();

                float dx = Math.abs(ballCenterX - brickCenterX);
                float dy = Math.abs(ballCenterY - brickCenterY);

                if (dx > dy) {
                    if (ballCenterX < brickCenterX) {
                        ball.setVelocityX(-Math.abs(ball.getVelocityX()));
                        ball.setRectLeft(brickRect.left - ball.getBitmap().getWidth() / 2); // Adjust position
                    } else {
                        ball.setVelocityX(Math.abs(ball.getVelocityX()));
                        ball.setRectLeft(brickRect.right + ball.getBitmap().getWidth() / 2); // Adjust position
                    }
                } else {
                    if (ballCenterY < brickCenterY) {
                        ball.setVelocityY(-Math.abs(ball.getVelocityY()));
                        ball.setRectTop(brickRect.top - ball.getBitmap().getHeight() / 2); // Adjust position
                    } else {
                        ball.setVelocityY(Math.abs(ball.getVelocityY()));
                        ball.setRectTop(brickRect.bottom + ball.getBitmap().getHeight() / 2); // Adjust position
                    }
                }

                collisionHandled = true; // Set flag to true to indicate that collision has been handled
            }
        }
    }
}

*/

/*
public class Bricks {
    private List<Brick> bricks;
    private SoundManager soundManager;

    public Bricks(SoundManager soundManager) {
        this.soundManager = soundManager;
        this.bricks = new ArrayList<>();
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void initializeBricks(int screenWidth, int screenHeight) {
        // Clear existing bricks
        bricks.clear();

        // Randomly select a layout type
        String[] layoutTypes = {"GRID", "PYRAMID", "ZIGZAG", "TRIANGLE", "PARALLELOGRAM"};
        Random random = new Random();
        String selectedLayoutType = layoutTypes[random.nextInt(layoutTypes.length)];

        // Example parameters for layouts
        BrickLayoutGenerator.LayoutParams params;
        switch (selectedLayoutType) {
            case "GRID":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "GRID");
                break;
            case "PYRAMID":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 8, 50, 10, "PYRAMID");
                break;
            case "ZIGZAG":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "ZIGZAG");
                break;
            case "TRIANGLE":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "TRIANGLE");
                break;
            case "PARALLELOGRAM":
                params = new BrickLayoutGenerator.LayoutParams(5, 4, screenWidth / 7, 50, 10, "PARALLELOGRAM");
                break;
            default:
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "GRID"); // Fallback to GRID layout
                break;
        }

        // Generate the bricks layout
        BrickLayoutGenerator.generateBricksLayout(params, bricks, screenWidth);
    }
}

*/
/*
public class Bricks {
    private List<Brick> bricks;
    private SoundManager soundManager;

    public Bricks(SoundManager soundManager) {
        this.soundManager = soundManager;
        this.bricks = new ArrayList<>();
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void initializeBricks(int screenWidth, int screenHeight) {
        // Clear existing bricks
        bricks.clear();

        // Adjust top padding to create space at the top
        int topPadding = 200; // Adjust this value as needed

        // Randomly select a layout type
        String[] layoutTypes = {"GRID", "PYRAMID", "ZIGZAG", "TRIANGLE", "PARALLELOGRAM"};
        Random random = new Random();
        String selectedLayoutType = layoutTypes[random.nextInt(layoutTypes.length)];

        // Example parameters for layouts
        BrickLayoutGenerator.LayoutParams params;
        switch (selectedLayoutType) {
            case "GRID":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "GRID", topPadding);
                break;
            case "PYRAMID":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 8, 50, 10, "PYRAMID", topPadding);
                break;
            case "ZIGZAG":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "ZIGZAG", topPadding);
                break;
            case "TRIANGLE":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "TRIANGLE", topPadding);
                break;
            case "PARALLELOGRAM":
                params = new BrickLayoutGenerator.LayoutParams(5, 4, screenWidth / 7, 50, 10, "PARALLELOGRAM", topPadding);
                break;
            default:
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "GRID", topPadding); // Fallback to GRID layout
                break;
        }

        // Generate the bricks layout with top padding
        BrickLayoutGenerator.generateBricksLayout(params, bricks, screenWidth, topPadding);
    }
}*/

public class Bricks {
    private List<Brick> bricks;
    private SoundManager soundManager;

    public Bricks(SoundManager soundManager) {
        this.soundManager = soundManager;
        this.bricks = new ArrayList<>();
    }

    public List<Brick> getBricks() {
        return bricks;
    }

    public void initializeBricks(Context context, int screenWidth, int screenHeight, int topPadding) {
        // Clear existing bricks
        bricks.clear();

        // Randomly select a layout type
        String[] layoutTypes = {"GRID", "PYRAMID", "ZIGZAG", "TRIANGLE", "PARALLELOGRAM","TRAPEZOID","CROSS","RANDOM"}; //i delete the STAR shape, becuase it cannot fun well
        Random random = new Random();
        String selectedLayoutType = layoutTypes[random.nextInt(layoutTypes.length)];

        // Example parameters for layouts
        BrickLayoutGenerator.LayoutParams params;
        switch (selectedLayoutType) {
            case "GRID":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "GRID", topPadding);
                break;
            case "PYRAMID":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 8, 50, 10, "PYRAMID", topPadding);
                break;
            case "ZIGZAG":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "ZIGZAG", topPadding);
                break;
            case "TRIANGLE":
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "TRIANGLE", topPadding);
                break;
            case "PARALLELOGRAM":
                params = new BrickLayoutGenerator.LayoutParams(5, 4, screenWidth / 7, 50, 10, "PARALLELOGRAM", topPadding);
                break;
            case "TRAPEZOID":
                params = new BrickLayoutGenerator.LayoutParams(7, 4, screenWidth / 7, 50, 10, "TRAPEZOID", topPadding);
                break;

            case "CROSS":
                params = new BrickLayoutGenerator.LayoutParams(10, 7, screenWidth / 7, 50, 10, "CROSS", topPadding);
                break;
            case "STAR":
                params = new BrickLayoutGenerator.LayoutParams(7, 6, screenWidth / 7, 50, 10, "STAR", topPadding);
                break;
            case "RANDOM":
                params = new BrickLayoutGenerator.LayoutParams(9, 6, screenWidth / 7, 50, 10, "RANDOM", topPadding);
                break;
            default:
                params = new BrickLayoutGenerator.LayoutParams(5, 6, screenWidth / 7, 50, 10, "GRID", topPadding); // Fallback to GRID layout
                break;
        }

        // Generate the bricks layout with top padding
        BrickLayoutGenerator.generateBricksLayout(params, bricks, screenWidth, topPadding);

        for (Brick brick : bricks) {
            String brickType = getRandomBrickType();
            brick.setBrickType(brickType);
            brick.setBitmapForBrickType(context); // Assuming this method sets the bitmap based on the type
        }
    }
    private String getRandomBrickType() {
        String[] brickTypes = {"clay", "steel", "copper"};
        Random random = new Random();
        return brickTypes[random.nextInt(brickTypes.length)];
    }
}

