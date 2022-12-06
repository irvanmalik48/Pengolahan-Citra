import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Main
 */
public class Main {
    // turn image into tresholded image
    public static BufferedImage tresholdImage(BufferedImage img, int treshold) {
        BufferedImage tresholded = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);
                int gray = (r + g + b) / 3;
                if (gray > treshold) {
                    tresholded.setRGB(x, y, 0xFFFFFF);
                } else {
                    tresholded.setRGB(x, y, 0x000000);
                }
            }
        }
        return tresholded;
    }

    // manipulate image contrast
    public static BufferedImage contrastImage(BufferedImage img, double contrast) {
        BufferedImage contrasted = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);
                int gray = (r + g + b) / 3;
                int newGray = (int) (gray * contrast);
                if (newGray > 255) {
                    newGray = 255;
                }
                contrasted.setRGB(x, y, (newGray << 16) + (newGray << 8) + newGray);
            }
        }
        return contrasted;
    }

    // manipulate image hue
    public static BufferedImage hueImage(BufferedImage img, double hue) {
        BufferedImage hued = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);
                int gray = (r + g + b) / 3;
                int newGray = (int) (gray * hue);
                if (newGray > 255) {
                    newGray = 255;
                }
                hued.setRGB(x, y, (newGray << 16) + (newGray << 8) + newGray);
            }
        }
        return hued;
    }

    // manipulate image saturation
    public static BufferedImage saturationImage(BufferedImage img, double saturation) {
        BufferedImage saturated = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);
                int gray = (r + g + b) / 3;
                int newGray = (int) (gray * saturation);
                if (newGray > 255) {
                    newGray = 255;
                }
                saturated.setRGB(x, y, (newGray << 16) + (newGray << 8) + newGray);
            }
        }
        return saturated;
    }

    // separate image into 3 channels
    public static BufferedImage[] separateImage(BufferedImage img) {
        BufferedImage[] separated = new BufferedImage[3];
        separated[0] = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        separated[1] = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        separated[2] = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                int rgb = img.getRGB(x, y);
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = (rgb & 0xFF);
                separated[0].setRGB(x, y, (r << 16) + (r << 8) + r);
                separated[1].setRGB(x, y, (g << 16) + (g << 8) + g);
                separated[2].setRGB(x, y, (b << 16) + (b << 8) + b);
            }
        }
        return separated;
    }

    // invert image function using BufferedImage
    public static BufferedImage invertImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage invertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                red = 255 - red;
                green = 255 - green;
                blue = 255 - blue;
                pixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                invertedImage.setRGB(j, i, pixel);
            }
        }
        return invertedImage;
    }

    // turn image grayscale using BufferedImage
    public static BufferedImage grayscaleImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage grayscaleImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                int average = (red + green + blue) / 3;
                pixel = (alpha << 24) | (average << 16) | (average << 8) | average;
                grayscaleImage.setRGB(j, i, pixel);
            }
        }
        return grayscaleImage;
    }

    // image brightening using BufferedImage that takes integer as parameter
    public static BufferedImage brightenImage(BufferedImage image, int brightness) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage brightenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                red = red + brightness;
                green = green + brightness;
                blue = blue + brightness;
                if (red > 255) {
                    red = 255;
                }
                if (green > 255) {
                    green = 255;
                }
                if (blue > 255) {
                    blue = 255;
                }
                pixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                brightenImage.setRGB(j, i, pixel);
            }
        }
        return brightenImage;
    }

    // arithmetics operation on 2 images using BufferedImage
    public static BufferedImage arithmeticOperation(BufferedImage image1, BufferedImage image2) {
        int width = image1.getWidth();
        int height = image1.getHeight();
        BufferedImage arithmeticImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel1 = image1.getRGB(j, i);
                int pixel2 = image2.getRGB(j, i);
                int alpha1 = (pixel1 >> 24) & 0xff;
                int red1 = (pixel1 >> 16) & 0xff;
                int green1 = (pixel1 >> 8) & 0xff;
                int blue1 = pixel1 & 0xff;
                int red2 = (pixel2 >> 16) & 0xff;
                int green2 = (pixel2 >> 8) & 0xff;
                int blue2 = pixel2 & 0xff;
                int red = (red1 + red2) / 2;
                int green = (green1 + green2) / 2;
                int blue = (blue1 + blue2) / 2;
                pixel1 = (alpha1 << 24) | (red << 16) | (green << 8) | blue;
                arithmeticImage.setRGB(j, i, pixel1);
            }
        }
        return arithmeticImage;
    }

    // boolean operation on 1 image using BufferedImage
    public static BufferedImage booleanOperation(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage booleanImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                int alpha = (pixel >> 24) & 0xff;
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = pixel & 0xff;
                if (red > 127 && green > 127 && blue > 127) {
                    red = 255;
                    green = 255;
                    blue = 255;
                } else {
                    red = 0;
                    green = 0;
                    blue = 0;
                }
                pixel = (alpha << 24) | (red << 16) | (green << 8) | blue;
                booleanImage.setRGB(j, i, pixel);
            }
        }
        return booleanImage;
    }

    // image rotation using BufferedImage and take integer as parameter
    public static BufferedImage rotateImage(BufferedImage image, int degree) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage rotateImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                if (degree == 90) {
                    rotateImage.setRGB(height - i - 1, j, pixel);
                } else if (degree == 180) {
                    rotateImage.setRGB(width - j - 1, height - i - 1, pixel);
                } else if (degree == 270) {
                    rotateImage.setRGB(i, width - j - 1, pixel);
                } else if (degree == 360 || degree == 0) {
                    rotateImage.setRGB(j, i, pixel);
                }
            }
        }
        return rotateImage;
    }

    // image scaling using BufferedImage and take integer as input
    public static BufferedImage scaleImage(BufferedImage image, int scale) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage scaleImage = new BufferedImage(width * scale, height * scale, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                for (int k = 0; k < scale; k++) {
                    for (int l = 0; l < scale; l++) {
                        scaleImage.setRGB(j * scale + l, i * scale + k, pixel);
                    }
                }
            }
        }
        return scaleImage;
    }

    // image translation using BufferedImage leaving the white space and take integer as input
    public static BufferedImage translateImage(BufferedImage image, int x, int y) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage translateImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                if (j + x < width && i + y < height && j + x >= 0 && i + y >= 0) {
                    translateImage.setRGB(j + x, i + y, pixel);
                }
            }
        }
        return translateImage;
    }

    // image reflection using BufferedImage and take vertical or horizontal as input
    public static BufferedImage reflectImage(BufferedImage image, String direction) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage reflectImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = image.getRGB(j, i);
                if (direction.equals("vertical")) {
                    reflectImage.setRGB(width - j - 1, i, pixel);
                } else if (direction.equals("horizontal")) {
                    reflectImage.setRGB(j, height - i - 1, pixel);
                }
            }
        }
        return reflectImage;
    }

    // load file and return it
    public static BufferedImage loadImage(String path) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(path));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
        return image;
    }
    
    // grid 4 images in 4x4
    public static BufferedImage gridImage(BufferedImage image1, BufferedImage image2, BufferedImage image3, BufferedImage image4) {
        int width = image1.getWidth();
        int height = image1.getHeight();
        BufferedImage gridImage = new BufferedImage(width * 2, height * 2, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel1 = image1.getRGB(j, i);
                int pixel2 = image2.getRGB(j, i);
                int pixel3 = image3.getRGB(j, i);
                int pixel4 = image4.getRGB(j, i);
                gridImage.setRGB(j, i, pixel1);
                gridImage.setRGB(j + width, i, pixel2);
                gridImage.setRGB(j, i + height, pixel3);
                gridImage.setRGB(j + width, i + height, pixel4);
            }
        }
        return gridImage;
    }

    // save file as png
    public static void saveImage(BufferedImage image, String path) {
        try {
            ImageIO.write(image, "png", new File(path));
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    // main method that takes test_image.png
    public static void main(String[] args) {
        BufferedImage image = loadImage("test_image.png");
        BufferedImage tresholdImage = tresholdImage(image, 128);
        BufferedImage grayImage = grayscaleImage(image);
        BufferedImage invertImage = invertImage(image);
        BufferedImage brightImage = brightenImage(image, 50);
        BufferedImage rotateImage = rotateImage(image, 180);
        BufferedImage arithmeticImage = arithmeticOperation(image, rotateImage);
        BufferedImage booleanImage = booleanOperation(image);
        BufferedImage scaleImage = scaleImage(image, 2);
        BufferedImage translateImage = translateImage(image, 100, 100);
        BufferedImage reflectImage = reflectImage(image, "horizontal");

        BufferedImage gridImage = gridImage(rotateImage, scaleImage, translateImage, reflectImage);

        saveImage(tresholdImage, "out/treshold.png");
        saveImage(grayImage, "out/gray_image.png");
        saveImage(invertImage, "out/invert_image.png");
        saveImage(brightImage, "out/bright_image.png");
        saveImage(arithmeticImage, "out/arithmetic_image.png");
        saveImage(booleanImage, "out/boolean_image.png");
        saveImage(rotateImage, "out/rotate_image.png");
        saveImage(scaleImage, "out/scale_image.png");
        saveImage(translateImage, "out/translate_image.png");
        saveImage(reflectImage, "out/reflect_image.png");
        saveImage(gridImage, "out/grid_image.png");
    }
}