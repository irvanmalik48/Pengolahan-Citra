import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Main {
    // convolution image filter with kernel using BufferedImage
    public static BufferedImage convolutionFilter(BufferedImage image, double[][] kernel) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        int kernelWidth = kernel.length;
        int kernelHeight = kernel[0].length;
        int kernelRadiusX = kernelWidth / 2;
        int kernelRadiusY = kernelHeight / 2;
        for (int imageY = 0; imageY < height; imageY++) {
            for (int imageX = 0; imageX < width; imageX++) {
                double red = 0.0, green = 0.0, blue = 0.0;
                for (int kernelY = 0; kernelY < kernelHeight; kernelY++) {
                    for (int kernelX = 0; kernelX < kernelWidth; kernelX++) {
                        int imageX1 = imageX + (kernelX - kernelRadiusX);
                        int imageY1 = imageY + (kernelY - kernelRadiusY);
                        if (imageX1 >= 0 && imageX1 < width && imageY1 >= 0 && imageY1 < height) {
                            Color color = new Color(image.getRGB(imageX1, imageY1));
                            red += color.getRed() * kernel[kernelX][kernelY];
                            green += color.getGreen() * kernel[kernelX][kernelY];
                            blue += color.getBlue() * kernel[kernelX][kernelY];
                        }
                    }
                }
                red = Math.min(Math.max(red, 0), 255);
                green = Math.min(Math.max(green, 0), 255);
                blue = Math.min(Math.max(blue, 0), 255);
                result.setRGB(imageX, imageY, new Color((int) red, (int) green, (int) blue).getRGB());
            }
        }
        return result;
    }

    // load test_image.png with ImageIO and process the image
    public static void main(String[] args) throws Exception {
        BufferedImage image = ImageIO.read(new File("test_image.png"));

        // sharpening kernel
        double[][] kernelSharpening = new double[][] {
            { 0, -1, 0 },
            { -1, 5, -1 },
            { 0, -1, 0 }
        };

        // blur kernel
        double[][] kernelBlur = new double[][] {
            { 1.0 / 16.0, 2.0 / 16.0, 1.0 / 16.0 },
            { 2.0 / 16.0, 4.0 / 16.0, 2.0 / 16.0 },
            { 1.0 / 16.0, 2.0 / 16.0, 1.0 / 16.0 }
        };

        // time the convolution filter
        long startTime = System.currentTimeMillis();

        BufferedImage result = convolutionFilter(image, kernelBlur);
        ImageIO.write(result, "png", new File("./out/result.png"));

        long endTime = System.currentTimeMillis();
        System.out.println("Convolution filter took " + (endTime - startTime) + " milliseconds");
    }
}