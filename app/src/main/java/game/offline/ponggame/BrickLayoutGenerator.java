package game.offline.ponggame;

// BrickLayoutGenerator.java

import android.graphics.RectF;

import java.util.List;
import java.util.Random;

public class BrickLayoutGenerator {

    public static class LayoutParams {
        public int brickRowCount;
        public int brickColumnCount;
        public float brickWidth;
        public float brickHeight;
        public float padding;
        public String layoutType;
        public int topPadding; // New parameter for top padding

        public LayoutParams(int brickRowCount, int brickColumnCount, float brickWidth, float brickHeight, float padding, String layoutType, int topPadding) {
            this.brickRowCount = brickRowCount;
            this.brickColumnCount = brickColumnCount;
            this.brickWidth = brickWidth;
            this.brickHeight = brickHeight;
            this.padding = padding;
            this.layoutType = layoutType;
            this.topPadding = topPadding;
        }
    }
    public static void generateBricksLayout(LayoutParams params, List<Brick> bricks, int screenWidth, int topPadding) {
        bricks.clear();

        switch (params.layoutType) {
            case "GRID":
                generateGridLayout(params, bricks, screenWidth, topPadding);
                break;
            case "PYRAMID":
                generatePyramidLayout(params, bricks, screenWidth, topPadding);
                break;
            case "ZIGZAG":
                generateZigzagLayout(params, bricks, screenWidth, topPadding);
                break;
            case "TRIANGLE":
                generateTriangleLayout(params, bricks, screenWidth, topPadding);
                break;
            case "PARALLELOGRAM":
                generateParallelogramLayout(params, bricks, screenWidth, topPadding);
                break;
            case "TRAPEZOID": // New trapezoid layout case
                generateTrapezoidLayout(params, bricks, screenWidth, topPadding);
                break;
            case "CROSS":
                generateCrossLayout(params, bricks, screenWidth, topPadding);
                break;
            case "STAR":  // New case for Star Layout
                generateStarLayout(params, bricks, screenWidth, topPadding);
                break;
            case "RANDOM":  // New case for Star Layout
                generateRandomLayout(params, bricks, screenWidth, topPadding);
                break;
            default:
                throw new IllegalArgumentException("Unsupported layout type: " + params.layoutType);
        }
    }

    private static void generateGridLayout(LayoutParams params, List<Brick> bricks, int screenWidth, int topPadding) {
        float totalBrickWidth = params.brickColumnCount * (params.brickWidth + params.padding) - params.padding;
        float startX = (screenWidth - totalBrickWidth) / 2;

        for (int i = 0; i < params.brickRowCount; i++) {
            for (int j = 0; j < params.brickColumnCount; j++) {
                float left = startX + j * (params.brickWidth + params.padding);
                float top = i * (params.brickHeight + params.padding) + params.padding + topPadding;
                float right = left + params.brickWidth;
                float bottom = top + params.brickHeight;
                bricks.add(new Brick(new RectF(left, top, right, bottom)));
            }
        }
    }

    private static void generateRandomLayout(LayoutParams params, List<Brick> bricks, int screenWidth, int topPadding) {
        Random random = new Random();

        int maxAttempts = 1000; // Limit the number of attempts to avoid infinite loop
        int attempts = 0;

        while (bricks.size() < params.brickRowCount * params.brickColumnCount && attempts < maxAttempts) {
            // Generate random x and y coordinates within the screen bounds
            float left = random.nextFloat() * (screenWidth - params.brickWidth);
            // Top is constrained by the brick row count and top padding
            float top = random.nextFloat() * (params.brickRowCount * (params.brickHeight + params.padding)) + topPadding;
            float right = left + params.brickWidth;
            float bottom = top + params.brickHeight;

            // Create a potential brick rectangle
            RectF newBrickRect = new RectF(left, top, right, bottom);

            // Check for overlaps with existing bricks
            boolean overlaps = false;
            for (Brick brick : bricks) {
                if (RectF.intersects(brick.getRect(), newBrickRect)) {
                    overlaps = true;
                    break;
                }
            }

            // If no overlap, add the new brick
            if (!overlaps) {
                bricks.add(new Brick(newBrickRect));
            }

            attempts++;
        }

        if (attempts >= maxAttempts) {
            // Handle the case when max attempts is reached (optional)
            System.out.println("Max attempts reached, couldn't place all bricks.");
        }
    }


    private static void generatePyramidLayout(LayoutParams params, List<Brick> bricks, int screenWidth, int topPadding) {
        for (int i = 0; i < params.brickRowCount; i++) {
            int bricksInRow = params.brickRowCount - i;
            float startX = (screenWidth - (bricksInRow * (params.brickWidth + params.padding))) / 2;

            for (int j = 0; j < bricksInRow; j++) {
                float left = startX + j * (params.brickWidth + params.padding);
                float top = i * (params.brickHeight + params.padding) + params.padding + topPadding;
                float right = left + params.brickWidth;
                float bottom = top + params.brickHeight;
                bricks.add(new Brick(new RectF(left, top, right, bottom)));
            }
        }
    }

    private static void generateZigzagLayout(LayoutParams params, List<Brick> bricks, int screenWidth, int topPadding) {
        for (int i = 0; i < params.brickRowCount; i++) {
            for (int j = 0; j < params.brickColumnCount; j++) {
                float left = j * (params.brickWidth + params.padding) + params.padding;
                float top = i * (params.brickHeight + params.padding) + params.padding + topPadding;

                // Adjust left position based on row number (i)
                if (i % 2 == 1) {
                    left += (params.brickWidth / 2); // Offset every second row
                }

                float right = left + params.brickWidth;
                float bottom = top + params.brickHeight;

                // Ensure bricks stay within the screenWidth
                left = Math.max(left, 0); // Ensure left does not go beyond the screen width
                right = Math.min(right, screenWidth); // Ensure right does not go beyond the screen width

                bricks.add(new Brick(new RectF(left, top, right, bottom)));
            }
        }
    }

    private static void generateTriangleLayout(LayoutParams params, List<Brick> bricks, int screenWidth, int topPadding) {
        for (int i = 0; i < params.brickRowCount; i++) {
            int bricksInRow = i + 1;
            float startX = (screenWidth - (bricksInRow * (params.brickWidth + params.padding))) / 2;

            for (int j = 0; j < bricksInRow; j++) {
                float left = startX + j * (params.brickWidth + params.padding);
                float top = i * (params.brickHeight + params.padding) + params.padding + topPadding;
                float right = left + params.brickWidth;
                float bottom = top + params.brickHeight;
                bricks.add(new Brick(new RectF(left, top, right, bottom)));
            }
        }
    }

    private static void generateParallelogramLayout(LayoutParams params, List<Brick> bricks, int screenWidth, int topPadding) {
        for (int i = 0; i < params.brickRowCount; i++) {
            for (int j = 0; j < params.brickColumnCount; j++) {
                float left = j * (params.brickWidth + params.padding) + params.padding + i * (params.brickWidth / 2);
                float top = i * (params.brickHeight + params.padding) + params.padding + topPadding;
                float right = left + params.brickWidth;
                float bottom = top + params.brickHeight;

                // Adjust left and right based on the screenWidth
                left = Math.max(left, 0); // Ensure left does not go beyond the screen width
                right = Math.min(right, screenWidth); // Ensure right does not go beyond the screen width

                bricks.add(new Brick(new RectF(left, top, right, bottom)));
            }
        }
    }

    private static void generateTrapezoidLayout(LayoutParams params, List<Brick> bricks, int screenWidth, int topPadding) {
        // Start with the maximum number of bricks in the first row
        int maxBricksInRow = params.brickColumnCount;
        int minBricksInRow = Math.max(1, maxBricksInRow / 2); // Set the minimum number of bricks in the top row

        // Calculate how many rows to reduce the brick count over time
        int rowsToTaper = params.brickRowCount;

        // Gradually reduce the number of bricks in each row
        for (int i = 0; i < rowsToTaper; i++) {
            // Calculate the number of bricks in the current row
            int bricksInRow = maxBricksInRow - ((maxBricksInRow - minBricksInRow) * i / rowsToTaper);

            // Ensure there is always at least one brick per row
            bricksInRow = Math.max(bricksInRow, minBricksInRow);

            // Calculate the starting X position to center the row
            float totalBrickWidth = bricksInRow * (params.brickWidth + params.padding) - params.padding;
            float startX = (screenWidth - totalBrickWidth) / 2;

            // Generate bricks for the current row
            for (int j = 0; j < bricksInRow; j++) {
                float left = startX + j * (params.brickWidth + params.padding);
                float top = i * (params.brickHeight + params.padding) + params.padding + topPadding;
                float right = left + params.brickWidth;
                float bottom = top + params.brickHeight;
                bricks.add(new Brick(new RectF(left, top, right, bottom)));
            }
        }
    }


    private static void generateCrossLayout(LayoutParams params, List<Brick> bricks, int screenWidth, int topPadding) {
        float centerX = screenWidth / 2; // The center point for the cross
        float centerY = topPadding + (params.brickRowCount * (params.brickHeight + params.padding)) / 2; // Center vertically based on brick rows

        // Generate vertical part of the cross
        for (int i = 0; i < params.brickRowCount; i++) {
            float top = i * (params.brickHeight + params.padding) + topPadding;
            float bottom = top + params.brickHeight;
            float left = centerX - (params.brickWidth / 2); // Centered horizontally
            float right = left + params.brickWidth;

            bricks.add(new Brick(new RectF(left, top, right, bottom)));
        }

        // Generate horizontal part of the cross
        int horizontalRow = params.brickRowCount / 2; // Middle row for the horizontal part
        float horizontalY = horizontalRow * (params.brickHeight + params.padding) + topPadding;

        for (int j = 0; j < params.brickColumnCount; j++) {
            float left = j * (params.brickWidth + params.padding);
            float right = left + params.brickWidth;

            // Only add horizontal bricks that don't overlap with the vertical part
            if (Math.abs(left - centerX) > params.brickWidth / 2) {
                bricks.add(new Brick(new RectF(left, horizontalY, right, horizontalY + params.brickHeight)));
            }
        }
    }
    private static void generateStarLayout(LayoutParams params, List<Brick> bricks, int screenWidth, int topPadding) {
        int numberOfArms = 5; // Number of arms in the star
        float angleBetweenArms = 360f / numberOfArms;
        float centerX = screenWidth / 2;
        float centerY = topPadding + 200; // Adjust center Y position with some top padding

        // Generate bricks for each arm of the star
        for (int i = 0; i < numberOfArms; i++) {
            float angle = i * angleBetweenArms; // Calculate the angle for each arm
            generateStarArm(params, bricks, centerX, centerY, angle);
        }
    }

    private static void generateStarArm(LayoutParams params, List<Brick> bricks, float centerX, float centerY, float baseAngle) {
        int bricksInArm = params.brickRowCount; // Number of bricks along each arm
        float distanceBetweenBricks = params.brickWidth + params.padding; // Distance between bricks

        for (int j = 0; j < bricksInArm; j++) {
            // Calculate the distance of the brick from the center point
            float distance = j * distanceBetweenBricks;

            // Calculate the angle for this brick in the arm, in radians
            double angleInRadians = Math.toRadians(baseAngle);

            // Calculate the brick's position using polar coordinates and translate to Cartesian coordinates
            float centerXOffset = (float) (distance * Math.cos(angleInRadians));
            float centerYOffset = (float) (distance * Math.sin(angleInRadians));

            // Position the brick centered around the calculated point
            float left = centerX + centerXOffset - (params.brickWidth / 2);
            float top = centerY + centerYOffset - (params.brickHeight / 2);
            float right = left + params.brickWidth;
            float bottom = top + params.brickHeight;

            // Add the brick to the list
            bricks.add(new Brick(new RectF(left, top, right, bottom)));
        }
    }


}

/*
 private static void generateStarLayout(LayoutParams params, List<Brick> bricks, int screenWidth, int topPadding) {
        // Star parameters
        int numberOfArms = 5; // Number of arms in the star
        float angleBetweenArms = 360f / numberOfArms;
        float centerX = screenWidth / 2;
        float centerY = topPadding + 200; // Adjust center Y position with some top padding

        // Generate bricks for each arm of the star
        for (int i = 0; i < numberOfArms; i++) {
            float angle = i * angleBetweenArms; // Calculate the angle for each arm
            generateStarArm(params, bricks, centerX, centerY, angle);
        }
    }

    private static void generateStarArm(LayoutParams params, List<Brick> bricks, float centerX, float centerY, float baseAngle) {
        int bricksInArm = params.brickRowCount; // The number of bricks along each arm
        float distanceBetweenBricks = params.brickWidth + params.padding; // Distance between bricks

        for (int j = 0; j < bricksInArm; j++) {
            // Calculate the position of each brick using polar coordinates
            float distance = j * distanceBetweenBricks; // Distance from the center
            double angleInRadians = Math.toRadians(baseAngle);

            // Calculate the brick position in Cartesian coordinates
            float left = centerX + (float) (distance * Math.cos(angleInRadians));
            float top = centerY + (float) (distance * Math.sin(angleInRadians));
            float right = left + params.brickWidth;
            float bottom = top + params.brickHeight;

            // Add the brick to the list
            bricks.add(new Brick(new RectF(left, top, right, bottom)));
        }
    }
 */