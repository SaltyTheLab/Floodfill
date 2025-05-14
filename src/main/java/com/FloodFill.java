package com;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.imageio.ImageIO;

public class FloodFill {

    // Load image and convert it to 2D int array (1 for black, 0 for white)
    public static int[][] loadImageToArray(String path) throws IOException {
        BufferedImage image = ImageIO.read(new File(path));
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] array = new int[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = image.getRGB(x, y);
                int gray = (pixel & 0xFF);  // assuming grayscale image
                array[y][x] = gray < 128 ? 1 : 0;  // black = 1, white = 0
            }
        }

        return array;
    }

    // Save 2D int array back to image
    public static void saveArrayToImage(int[][] array, String outputPath) throws IOException {
        int height = array.length;
        int width = array[0].length;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int value = array[y][x] == 1 ? 0 : 255; // black = 0, white = 255
                int rgb = (value << 16) | (value << 8) | value;
                image.setRGB(x, y, rgb);
            }
        }

        ImageIO.write(image, "png", new File(outputPath));
    }

    // Flood fill function
    public static void floodFill(int[][] array, int startX, int startY, int targetValue) {
        int height = array.length;
        int width = array[0].length;
        int originalValue = array[startY][startX];

        if (originalValue == targetValue) return;

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});

        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int x = point[0];
            int y = point[1];

            if (x < 0 || x >= width || y < 0 || y >= height) continue;
            if (array[y][x] != originalValue) continue;

            array[y][x] = targetValue;

            queue.add(new int[]{x + 1, y});
            queue.add(new int[]{x - 1, y});
            queue.add(new int[]{x, y + 1});
            queue.add(new int[]{x, y - 1});
        }
    }

    // Example usage
    public static void main(String[] args) throws IOException {
            String inputPath = "C:\\Users\\micha\\Documents\\Java Data Structures\\Floodfill\\src\\main\\java\\com\\picture.png";
            String outputPath = "C:\\Users\\micha\\Documents\\Java Data Structures\\Floodfill\\src\\main\\java\\com\\output.png";
            int startX = 182;
            int startY = 28;
            int targetValue = 0;  // 0 for white, 1 for black

            int[][] imageArray = loadImageToArray(inputPath);
            floodFill(imageArray, startX, startY, targetValue);
            saveArrayToImage(imageArray, outputPath);

            System.out.println("Fill complete. Image saved to " + outputPath);
    }
}
