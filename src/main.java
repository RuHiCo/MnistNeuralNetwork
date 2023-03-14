import javax.swing.*;
import java.io.IOException;
import java.util.Random;

public class main {
    public static int[] swap(int[] numbers,int i,int swapInt) {
        int zwerg=numbers[i];
        numbers[i]=numbers[swapInt];
        numbers[swapInt]=zwerg;
        return numbers;
    }
    public static void initializeWeights(int sizeLayer1, int sizeLayer2) throws IOException {
        Net net = new Net();
        net.createWeight(784, sizeLayer1, sizeLayer2, 10);
        net.exportWeight();
    }

    public static void train(double alpha) throws IOException, ClassNotFoundException, InterruptedException {
        int size = 60000;
        int[] numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = i;
        }
        Random randI = new Random();
        randI.setSeed(System.currentTimeMillis());
        for (int i = 0; i < size; i++) {
            int swapInt = randI.nextInt(60000);
            numbers = swap(numbers, i, swapInt);
        }

        double[][][] pictures = Reader.readPicture("source\\train-images-idx3-ubyte\\train-images.idx3-ubyte", size);
        int[] labels = Reader.readLabels("source\\train-labels-idx1-ubyte\\train-labels.idx1-ubyte", size);
        Net net = new Net();
        net.importWeight();
        net.nextGeneration();
        System.out.printf("Generation: %d\n",net.getGeneration());
        int count = 0;
        int count2 = 0;
        for (int i : numbers) {
            net.newPicture(pictures[i], labels[i]);
            net.calculate();
            count = net.compareResult(count, alpha);
            count2++;
            if (count2 % 20000 == 0) {
                System.out.printf("%d zu Ende\n",count2);
            }
        }
        System.out.printf("%.3f Prozent Trefferquote auf dem Trainingsdatensatz",(60000-count)/60000.0);
        double res=test.main(null);
        net.appendProgress(Double.toString(res));
        net.exportWeight();
        //System.out.print("finish\n");

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        boolean initialize=true;
        if(initialize) {
            initializeWeights(123,35);
        }
        double alpha=0;
        for (int j = 0; j < 20; j++) {
            //System.out.println(j+1);
            alpha=0.05-j*0.0025;
            train(alpha);
        }
        Net net=new Net();
        net.importWeight();
        //create an instance of JFrame class
        JFrame frame = new JFrame();
        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new PlotProgress(net.getProgress()));
        frame.setSize(400, 400);
        frame.setLocation(200, 200);
        frame.setVisible(true);
    }
}
