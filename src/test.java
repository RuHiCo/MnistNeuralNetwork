import java.io.IOException;
import java.util.ArrayList;

public class test {
    public static double main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        int size = 10000;
        double[][] pictures = Reader.readPicture("source\\t10k-images-idx3-ubyte\\t10k-images.idx3-ubyte", size);
        int[] labels = Reader.readLabels("source\\t10k-labels-idx1-ubyte\\t10k-labels.idx1-ubyte", size);
        Net net = new Net();
        net.importWeight();
        double[] results={0,0};
        int[][] haelt1fuer2=new int[10][10];
        for (int i = 0; i < size; i++) {
            net.newPicture(pictures[i], labels[i]);
            net.calculate();
            net.getResults(results,haelt1fuer2);
        }
        results[1]/=size;
        /*for(int x=0;x<10;x++){
            for(int y=0;y<10;y++){
                System.out.printf(" %4d ",haelt1fuer2[x][y]);
            }
            System.out.println();
        }*/
        results[0]=(10000-results[0])/10000.0;
        System.out.printf("%.3f Prozent Trefferquote auf dem Testdatensatz\n",results[0]);
        System.out.printf("%.3f Quadrierter Fehler im Durchschnitt",results[1]);
        return results[0];
    }
}