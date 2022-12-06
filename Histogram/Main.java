import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

public class Main {

    public static void main(String[] args) throws IOException {
        String imagepath = "./lena.jpeg";
        BufferedImage image = ImageIO.read(new File(imagepath));
        int width = image.getWidth();
        int height = image.getHeight();
        int count[][] = new int[3][0x100];
        int RED = 0;
        int BLUE = 1;
        int GREEN = 2;
        try {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Color color = new Color(image.getRGB(i, j));
                    int cred = color.getRed();
                    int cgreen = color.getGreen();
                    int cblue = color.getBlue();

                    count[RED][cred]++;
                    count[BLUE][cblue]++;
                    count[GREEN][cgreen]++;
                }
            }

            BufferedImage histo = new BufferedImage(1000, 800, 1);
            int jarak = 3;
            int jelajah = 0;

            for (int i = 0; i < 0x100; i++) {
                System.out.printf(
                    "%03d %04d %04d %04d\n",
                    i,
                    count[RED][i],
                    count[BLUE][i],
                    count[GREEN][i]
                );
                for (int j = 0; j < count[RED][i]; j++) {
                    histo.setRGB(j, jelajah, new Color(255, 0, 0).getRGB());
                }
                jelajah += jarak;
            }

            jelajah = 4;
            for (int i = 0; i < 0x100; i++) {
                for (int j = 0; j < count[GREEN][i]; j++) {
                    histo.setRGB(j, jelajah, new Color(0, 255, 0).getRGB());
                }
                jelajah += jarak;
            }

            jelajah = 8;
            for (int i = 0; i < 0x100; i++) {
                for (int j = 0; j < count[BLUE][i]; j++) {
                    histo.setRGB(j, jelajah, new Color(0, 0, 255).getRGB());
                }
                jelajah += jarak;
            }

            File output = new File(
                "./out/histogram.png"
            );
            ImageIO.write(histo, "PNG", output);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    static BufferedImage LoadImage(String img) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(img));
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "Failed");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Eror");
        }

        return image;
    }

    static void konvolusiColor(String img, int[][] kernel, String name) {
        BufferedImage image = LoadImage(img);
        int width = image.getWidth();
        int height = image.getHeight();
        int sumKernel = 0;
        for (int i = 0; i < kernel.length; i++) {
            for (int j = 0; j < kernel[0].length; j++) {
                sumKernel = sumKernel + kernel[i][j];
            }
        }
        try {
            //konvolusi
            for (int i = 1; i < height - 1; i++) {
                for (int j = 1; j < width - 1; j++) {
                    int x = 0;
                    int y = 0;
                    int r = 0, g = 0, b = 0;

                    // perhitunngan perframe kernel
                    for (int k = i - 1; k <= i + 1; k++) {
                        for (int l = j - 1; l <= j + 1; l++) {
                            Color c = new Color(image.getRGB(l, k));
                            r += c.getRed() * kernel[x][y];
                            g += c.getGreen() * kernel[x][y];
                            b += c.getBlue() * kernel[x][y];

                            x++;
                            if (x > 2) {
                                x = 0;
                                y++;
                            }
                        }
                        if (y > 2) {
                            y = 0;
                        }
                    }

                    if (r > 255) r = 255; else if (r < 0) r = 0;
                    if (g > 255) g = 255; else if (g < 0) g = 0;
                    if (b > 255) b = 255; else if (b < 0) b = 0;

                    image.setRGB(j - 1, i - 1, new Color(r, g, b).getRGB());
                }
            }

            File output = new File("./out/" + name + ".png");
            ImageIO.write(image, "PNG", output);

            System.out.println(name + " Berhasil");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
