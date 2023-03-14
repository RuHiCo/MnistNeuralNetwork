import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Reader {
    public Reader() {

    }

    public static double[][] readPicture(String path, int numPict) throws IOException {
        double[][] pictures = new double[numPict][784];
        File file = new File(path);
        long size = file.length();
        byte[] contents = new byte[(int) size];
        FileInputStream in = new FileInputStream(file);
        in.read(contents);
        in.close();
        for (int i = 0; i < numPict; i++) {
            for (int j = 0; j < 784; j++) {
                int x = (int) contents[i * 784 + j + 16];
                if (x < 0) {
                    x = (x * (0 - 1)) + 128;
                }
                pictures[i][j] = x / 255.0;
                //System.out.print(x + "\t");
                //if ((j+1)%28==0){
                //    System.out.println();
                //}
            }
            //System.out.println();
        }
        return pictures;
    }

    public static int[] readLabels(String path, int numPict) throws IOException {
        int[] labels = new int[numPict];
        File file = new File(path);
        long size = file.length();
        byte[] contents = new byte[(int) size];
        FileInputStream in = new FileInputStream(file);
        in.read(contents);
        in.close();
        for (int i = 0; i < numPict; i++) {
            labels[i] = (int) contents[i + 8];
            //System.out.print(x+" ");
        }
        //System.out.println();
        return labels;
    }
}