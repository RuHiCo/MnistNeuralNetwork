import java.io.IOException;

public class test {
    public static double main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        int size = 10000;
        double[][][] pictures = Reader.readPicture("source\\t10k-images-idx3-ubyte\\t10k-images.idx3-ubyte", size);
        int[] labels = Reader.readLabels("source\\t10k-labels-idx1-ubyte\\t10k-labels.idx1-ubyte", size);
        Net net = new Net();
        net.importWeight();
        /*for (int i=0;i<5;i++){
            for(int k=0;k<784;k++){
                System.out.printf("%.0f\t",pictures[i][k][0]);
                if((k+1)%28==0){
                    System.out.println();
                }
            }
        }*/
        int count = 0;
        int[][] haelt1fuer2=new int[10][10];
        for (int i = 0; i < size; i++) {
            net.newPicture(pictures[i], labels[i]);
            net.calculate();
            count=net.getResults(count,haelt1fuer2);
        }
        /*for(int x=0;x<10;x++){
            for(int y=0;y<10;y++){
                System.out.printf("haelt %d für %d: %4d \n",x,y,haelt1fuer2[x][y]);
            }
        }*/
        for(int x=0;x<10;x++){
            for(int y=0;y<10;y++){
                System.out.printf(" %4d ",haelt1fuer2[x][y]);
            }
            System.out.println();
        }
        double res=(10000-count)/10000.0;
        System.out.printf("%.3f Prozent Trefferquote auf dem Testdatensatz",res);
        //net.exportWeight();
        //System.out.print("finish\n");
        return res;
    }
}