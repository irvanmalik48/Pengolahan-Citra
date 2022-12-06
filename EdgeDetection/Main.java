package EdgeDetection;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;

public class Main {

    //    BufferedImage inputImg,outputImg;
    static String path = "./";

    static int[][] pixelMatrix = new int[3][3];

    public static void main(String[] args) throws IOException {
        final String SOBEL_OPERATOR = "Sobel";
        final String PREWIT_OPERATOR = "Prewit";
        final String ROBERT_OPERATOR = "Roberts";
        final String SELISIH_OPERATOR = "Selisih";
        final String TURUNANPERTAMA_OPERATOR = "TurunanPertama";
        final String TURUNANKEDUA_OPERATOR = "TurunanKedua";
        final String KOMPAS_OPERATOR = "Kompas";

        EdgeDetection(SOBEL_OPERATOR);
        EdgeDetection(PREWIT_OPERATOR);
        EdgeDetection(ROBERT_OPERATOR);
        EdgeDetection(SELISIH_OPERATOR);
        EdgeDetection(TURUNANPERTAMA_OPERATOR);
        EdgeDetection(TURUNANKEDUA_OPERATOR);
        EdgeDetection(KOMPAS_OPERATOR);
    }

    public static void EdgeDetection(String operator) throws IOException {
        BufferedImage inputImg = ImageIO.read(new File(path + "lena.png"));

        BufferedImage outputImg = new BufferedImage(
            inputImg.getWidth(),
            inputImg.getHeight(),
            TYPE_INT_RGB
        );

        for (int i = 1; i < inputImg.getWidth() - 1; i++) {
            for (int j = 1; j < inputImg.getHeight() - 1; j++) {
                pixelMatrix[0][0] =
                    new Color(inputImg.getRGB(i - 1, j - 1)).getRed();
                pixelMatrix[0][1] =
                    new Color(inputImg.getRGB(i - 1, j)).getRed();
                pixelMatrix[0][2] =
                    new Color(inputImg.getRGB(i - 1, j + 1)).getRed();

                pixelMatrix[1][0] =
                    new Color(inputImg.getRGB(i, j - 1)).getRed();
                pixelMatrix[1][1] = new Color(inputImg.getRGB(i, j)).getRed();
                pixelMatrix[1][2] =
                    new Color(inputImg.getRGB(i, j + 1)).getRed();

                pixelMatrix[2][0] =
                    new Color(inputImg.getRGB(i + 1, j - 1)).getRed();
                pixelMatrix[2][1] =
                    new Color(inputImg.getRGB(i + 1, j)).getRed();
                pixelMatrix[2][2] =
                    new Color(inputImg.getRGB(i + 1, j + 1)).getRed();

                int edge;
                switch (operator) {
                    case "Sobel":
                        edge = (int) Sobel(pixelMatrix);
                        outputImg.setRGB(i, j, (edge << 16 | edge << 8 | edge));
                        break;
                    case "Prewit":
                        edge = (int) Prewit(pixelMatrix);
                        outputImg.setRGB(i, j, (edge << 16 | edge << 8 | edge));
                        break;
                    case "Roberts":
                        edge = (int) Roberts(pixelMatrix);
                        outputImg.setRGB(i, j, (edge << 16 | edge << 8 | edge));
                        break;
                    case "Selisih":
                        edge = (int) Selisih(pixelMatrix);
                        outputImg.setRGB(i, j, (edge << 16 | edge << 8 | edge));
                        break;
                    case "TurunanPertama":
                        edge = (int) TurunanPertama(pixelMatrix);
                        outputImg.setRGB(i, j, (edge << 16 | edge << 8 | edge));
                        break;
                    case "TurunanKedua":
                        edge = (int) TurunanKedua(pixelMatrix);
                        outputImg.setRGB(i, j, (edge << 16 | edge << 8 | edge));
                        break;
                    case "Kompas":
                        edge = (int) Kompas(pixelMatrix);
                        outputImg.setRGB(i, j, (edge << 16 | edge << 8 | edge));
                        break;
                }
            }
        }
        File file = new File("./out/" + operator + ".png");
        ImageIO.write(outputImg, "PNG", file);
        System.out.println(file.getAbsolutePath());
    }

    public static double Sobel(int[][] pixelMatrix) {
        int gx =
            (pixelMatrix[0][0] * -1) +
            (pixelMatrix[0][2] * 1) +
            (pixelMatrix[1][0] * -2) +
            (pixelMatrix[1][2] * 2) +
            (pixelMatrix[2][0] * -1) +
            (pixelMatrix[2][2] * 1);
        int gy =
            (pixelMatrix[0][0] * 1) +
            (pixelMatrix[0][1] * 2) +
            (pixelMatrix[0][2] * 1) +
            (pixelMatrix[2][0] * -1) +
            (pixelMatrix[0][1] * -2) +
            (pixelMatrix[2][2] * -1);
        return Math.sqrt(Math.pow(gy, 2) + Math.pow(gx, 2));
    }

    public static double Prewit(int[][] pixelMatrix) {
        int gx =
            (pixelMatrix[0][0] * -1) +
            (pixelMatrix[0][2] * 1) +
            (pixelMatrix[1][0] * -1) +
            (pixelMatrix[1][2] * 1) +
            (pixelMatrix[2][0] * -1) +
            (pixelMatrix[2][2] * 1);
        int gy =
            (pixelMatrix[0][0] * 1) +
            (pixelMatrix[0][1] * 1) +
            (pixelMatrix[0][2] * 1) +
            (pixelMatrix[2][0] * -1) +
            (pixelMatrix[0][1] * -1) +
            (pixelMatrix[2][2] * -1);
        return Math.sqrt(Math.pow(gy, 2) + Math.pow(gx, 2));
    }

    public static double Roberts(int[][] pixelMatrix) {
        int gx =
            (pixelMatrix[0][0] * 1) +
            (pixelMatrix[0][1] * 0) +
            (pixelMatrix[1][0] * 0) +
            (pixelMatrix[1][1] * -1);
        int gy =
            (pixelMatrix[0][0] * 0) +
            (pixelMatrix[0][1] * 1) +
            (pixelMatrix[1][0] * -1) +
            (pixelMatrix[1][1] * 0);
        return Math.sqrt(Math.pow(gy, 2) + Math.pow(gx, 2));
    }

    public static double Selisih(int[][] pixelMatrix) {
        int gx = (pixelMatrix[0][0] * -1) + +(pixelMatrix[0][2] * 1);
        int gy = (pixelMatrix[1][0] * 1) + (pixelMatrix[1][2] * -1);
        return Math.sqrt(Math.pow(gy, 2) + Math.pow(gx, 2));
    }

    public static double TurunanPertama(int[][] pixelMatrix) {
        int gx = (pixelMatrix[0][0] * -1) + (pixelMatrix[0][1] * 1);
        int gy = (pixelMatrix[1][0] * 1) + (pixelMatrix[1][1] * -1);
        return Math.sqrt(Math.pow(gy, 2) + Math.pow(gx, 2));
    }

    public static double TurunanKedua(int[][] pixelMatrix) {
        int gx =
            (pixelMatrix[0][0] * 1) +
            (pixelMatrix[0][1] * 4) +
            (pixelMatrix[0][2] * 1) +
            (pixelMatrix[1][0] * 4) +
            (pixelMatrix[1][2] * -20) +
            (pixelMatrix[1][2] * 4) +
            (pixelMatrix[2][0] * 1) +
            (pixelMatrix[2][1] * 4) +
            (pixelMatrix[2][2] * 1);
        return Math.sqrt(Math.pow(gx, 2));
    }

    public static double Kompas(int[][] pixelMatrix) {
        int Utara =
            (pixelMatrix[0][0] * 1) +
            (pixelMatrix[0][1] * 1) +
            (pixelMatrix[0][2] * 1) +
            (pixelMatrix[1][0] * 1) +
            (pixelMatrix[1][2] * -2) +
            (pixelMatrix[1][2] * 1) +
            (pixelMatrix[2][0] * -1) +
            (pixelMatrix[2][1] * -1) +
            (pixelMatrix[2][2] * -1);
        int TimurLaut =
            (pixelMatrix[0][0] * 1) +
            (pixelMatrix[0][1] * 1) +
            (pixelMatrix[0][2] * 1) +
            (pixelMatrix[1][0] * -1) +
            (pixelMatrix[1][2] * -2) +
            (pixelMatrix[1][2] * 1) +
            (pixelMatrix[2][0] * -1) +
            (pixelMatrix[2][1] * -1) +
            (pixelMatrix[2][2] * 1);
        int Timur =
            (pixelMatrix[0][0] * -1) +
            (pixelMatrix[0][1] * 1) +
            (pixelMatrix[0][2] * 1) +
            (pixelMatrix[1][0] * -1) +
            (pixelMatrix[1][2] * -2) +
            (pixelMatrix[1][2] * 1) +
            (pixelMatrix[2][0] * -1) +
            (pixelMatrix[2][1] * 1) +
            (pixelMatrix[2][2] * 1);
        int Tenggara =
            (pixelMatrix[0][0] * -1) +
            (pixelMatrix[0][1] * -1) +
            (pixelMatrix[0][2] * 1) +
            (pixelMatrix[1][0] * -1) +
            (pixelMatrix[1][2] * -2) +
            (pixelMatrix[1][2] * 1) +
            (pixelMatrix[2][0] * 1) +
            (pixelMatrix[2][1] * 1) +
            (pixelMatrix[2][2] * 1);
        int Selatan =
            (pixelMatrix[0][0] * -1) +
            (pixelMatrix[0][1] * -1) +
            (pixelMatrix[0][2] * -1) +
            (pixelMatrix[1][0] * 1) +
            (pixelMatrix[1][2] * -2) +
            (pixelMatrix[1][2] * 1) +
            (pixelMatrix[2][0] * 1) +
            (pixelMatrix[2][1] * 1) +
            (pixelMatrix[2][2] * 1);
        int BaratDaya =
            (pixelMatrix[0][0] * 1) +
            (pixelMatrix[0][1] * -1) +
            (pixelMatrix[0][2] * -1) +
            (pixelMatrix[1][0] * 1) +
            (pixelMatrix[1][2] * -2) +
            (pixelMatrix[1][2] * -1) +
            (pixelMatrix[2][0] * 1) +
            (pixelMatrix[2][1] * 1) +
            (pixelMatrix[2][2] * 1);
        int Barat =
            (pixelMatrix[0][0] * 1) +
            (pixelMatrix[0][1] * 1) +
            (pixelMatrix[0][2] * -1) +
            (pixelMatrix[1][0] * 1) +
            (pixelMatrix[1][2] * -2) +
            (pixelMatrix[1][2] * -1) +
            (pixelMatrix[2][0] * 1) +
            (pixelMatrix[2][1] * 1) +
            (pixelMatrix[2][2] * -1);
        int BaratLaut =
            (pixelMatrix[0][0] * 1) +
            (pixelMatrix[0][1] * 1) +
            (pixelMatrix[0][2] * 1) +
            (pixelMatrix[1][0] * 1) +
            (pixelMatrix[1][2] * -2) +
            (pixelMatrix[1][2] * -1) +
            (pixelMatrix[2][0] * 1) +
            (pixelMatrix[2][1] * -1) +
            (pixelMatrix[2][2] * -1);

        Double[] kompas = {
            Math.sqrt(Math.pow(Utara, 2) + Math.pow(Utara, 2)),
            Math.sqrt(Math.pow(TimurLaut, 2) + Math.pow(TimurLaut, 2)),
            Math.sqrt(Math.pow(Timur, 2) + Math.pow(Timur, 2)),
            Math.sqrt(Math.pow(Tenggara, 2) + Math.pow(Tenggara, 2)),
            Math.sqrt(Math.pow(Selatan, 2) + Math.pow(Selatan, 2)),
            Math.sqrt(Math.pow(BaratDaya, 2) + Math.pow(BaratDaya, 2)),
            Math.sqrt(Math.pow(Barat, 2) + Math.pow(Barat, 2)),
            Math.sqrt(Math.pow(BaratLaut, 2) + Math.pow(BaratLaut, 2)),
        };

        Arrays.sort(kompas);
        return kompas[kompas.length - 1];
    }
}
