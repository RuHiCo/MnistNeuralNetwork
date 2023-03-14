import javax.swing.*;
import java.io.IOException;
import java.sql.Array;
import java.util.*;
import java.util.stream.Collectors;

import com.github.sh0nk.matplotlib4j.NumpyUtils;
import com.github.sh0nk.matplotlib4j.Plot;
import com.github.sh0nk.matplotlib4j.PythonConfig;
import com.github.sh0nk.matplotlib4j.PythonExecutionException;

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

        double[][] pictures = Reader.readPicture("source\\train-images-idx3-ubyte\\train-images.idx3-ubyte", size);
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
        System.out.printf("%.3f Prozent Trefferquote auf dem Trainingsdatensatz\n",(60000-count)/60000.0);
        double res=test.main(null);
        net.appendProgress(Double.toString(res));
        net.exportWeight();
        //System.out.print("finish\n");

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, PythonExecutionException {
        boolean initialize=true;
        Stoppuhr uhr=new Stoppuhr();
        if(initialize) {
            uhr.start();
            initializeWeights(123,35);
            uhr.stopp();
            System.out.printf("Inizialized weights in %d ms",uhr.getDurationInMs());
            uhr.reset();
        }

        double alpha=0;
        for (int j = 0; j < 10; j++) {
            //System.out.println(j+1);
            alpha=0.2-j*0.02;
            uhr.start();
            train(alpha);
            uhr.stopp();
            System.out.printf("Trained and Tested one Generation in %d s\n",uhr.getDurationInS());
        }
        /*Net net=new Net();
        net.importWeight();
        //create an instance of JFrame class
        JFrame frame = new JFrame();
        // set size, layout and location for frame.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new PlotProgress(net.getProgress()));
        frame.setSize(400, 400);
        frame.setLocation(200, 200);
        frame.setVisible(true);*/
        Net net=new Net();
        net.importWeight();
        double[]erg=net.getProgress();
        Plot plt = Plot.create(PythonConfig.pythonBinPathConfig("C:/Users/Reset/PycharmProjects/Umfrage/venv/Scripts/python"));
        List<Double> x = NumpyUtils.linspace(0,erg.length, erg.length);
        ArrayList<Double> c = new ArrayList<>();
        for(double value:erg) {
            c.add(value);
        }
        //List<Double> S = x.stream().map(xi -> Math.sin(xi)).collect(Collectors.toList());
        plt.plot().add(x, c);
        plt.plot().add(x, c,"x").color("black");
        plt.xlabel("Generationen");
        plt.ylabel("Trefferquote Testdatensatz");
        plt.legend();
        //plt.plot().add(x, S);
        plt.show();
    }
}
