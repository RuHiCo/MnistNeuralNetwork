import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.Random;

public class Net {

    private int label;
    private double[][] weightInputHidden;
    private double[][] weightHiddenHidden;
    private double[][] weightHiddenOutput;
    private double[][] layerInput;
    private double[][] layerHidden1;
    private double[][] layerHidden2;
    private double[][] layerOutput;


    public Net(){
        //this.importWeight();
    }

    public static void getImageFromArray(int[] pixels, int width, int height) throws InterruptedException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        WritableRaster raster = (WritableRaster) image.getData();
        raster.setPixels(0,0,width,height,pixels);
        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(image)));
        frame.pack();
        frame.setVisible(true);
        java.util.concurrent.TimeUnit.SECONDS.sleep(2);
    }
    public void printweights(){
        Matrix.print(this.weightInputHidden);
        Matrix.print(this.weightHiddenHidden);
        Matrix.print(this.weightHiddenOutput);
    }

    public void newPicture(double[][] picture,int label) {
        this.layerInput = picture;
        this.label = label;
    }

    public void createWeight(int start, int size1, int size2, int end ) {
        Random rd=new Random();
        rd.setSeed(System.currentTimeMillis());
        this.weightInputHidden =new double[size1][start];
        for (int i = 0; i< weightInputHidden.length; i++) {
            for (int j = 0; j < weightInputHidden[0].length; j++) {
                this.weightInputHidden[i][j] = rd.nextDouble() * 0.05+0.001;
            }
        }
        this.weightHiddenHidden =new double[size2][size1];
        for (int i = 0; i< weightHiddenHidden.length; i++) {
            for (int j = 0; j < weightHiddenHidden[0].length; j++) {
                this.weightHiddenHidden[i][j] = rd.nextDouble() * 0.07+0.001;
            }
        }
        this.weightHiddenOutput =new double[end][size2];
        for (int i = 0; i< weightHiddenOutput.length; i++) {
            for (int j = 0; j < weightHiddenOutput[0].length; j++) {
                this.weightHiddenOutput[i][j] = rd.nextDouble()*0.09+0.001;
            }
        }
        //Matrix.print(this.weight3);
    }

    public void exportWeight() throws IOException {
        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("weight1.txt"));
        outputStream.writeObject(this.weightInputHidden);
        outputStream = new ObjectOutputStream(new FileOutputStream("weight2.txt"));
        outputStream.writeObject(this.weightHiddenHidden);
        outputStream = new ObjectOutputStream(new FileOutputStream("weight3.txt"));
        outputStream.writeObject(this.weightHiddenOutput);
        //Matrix.print(this.weight3);
    }

    public void importWeight() throws IOException, ClassNotFoundException {

        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("weight1.txt"));
        this.weightInputHidden = (double[][]) inputStream.readObject();
        inputStream = new ObjectInputStream(new FileInputStream("weight2.txt"));
        this.weightHiddenHidden = (double[][]) inputStream.readObject();
        inputStream = new ObjectInputStream(new FileInputStream("weight3.txt"));
        this.weightHiddenOutput = (double[][]) inputStream.readObject();
        //Matrix.print(this.weight1);
        //Matrix.print(this.weight2);
        //Matrix.print(this.weight3);
    }

    public static double sigmoid(double x) {
        return 1 / (1 + Math.pow(Math.E,-x));
    }

    public static double[][] sigmoidForVector(double[][] layer) {
        for(int i=0;i<layer.length;i++) {
            layer[i][0] = Net.sigmoid(layer[i][0]);
        }
        return layer;
    }

    public void calculate(){
        //double[][] x=Matrix.multiply(this.weight1, this.layerStart);
        //Matrix.print(x);
        this.layerHidden1 = this.sigmoidForVector(Matrix.multiply(this.weightInputHidden, this.layerInput));
        //double[][] y=Matrix.multiply(this.weight2, this.layer1);
        //Matrix.print(y);
        //Matrix.print(this.layer1);
        this.layerHidden2 = this.sigmoidForVector(Matrix.multiply(this.weightHiddenHidden, this.layerHidden1));
        //Matrix.print(this.layer2);
        this.layerOutput = this.sigmoidForVector(Matrix.multiply(this.weightHiddenOutput, this.layerHidden2));
        //Matrix.print(this.layerEnd);

    }

    public int getResults(int count,int[][] haelt1fuer2) throws InterruptedException {
        double[][] error = new double[10][1];
        error[this.label][0] = 1.0;
        double sum2=0;
        for(int i=0;i<10;i++) {
            error[i][0] = (error[i][0] - this.layerOutput[i][0]);
            sum2+=error[i][0]*error[i][0];
        }
        //if(numb%100==0) {
        //Matrix.print(error);
        //}
        //System.out.println(sum2);
        double actual=this.layerOutput[this.label][0];
        for (int i = 0; i < this.layerOutput.length; i++) {
            if (actual < this.layerOutput[i][0]) {
                count += 1;
                haelt1fuer2[this.label][i]+=1;
                //System.out.println(this.label+" but recognised "+i);
                //int[] pixels=new int[784];
                /*for(int k=0;k<784;k++){
                //pixels[k]=(int)this.layerStart[k][0]*255;
                    System.out.printf("%.0f\t",this.layerInput[k][0]*255);
                    if((k+1)%28==0){
                        System.out.println();
                    }
                }*/
                //getImageFromArray(pixels,20,20);
                //System.out.println();
                break;
            }
        }
        haelt1fuer2[this.label][this.label]+=1;

        /*if(numb%7==0) {
            System.out.println(sum2);
        }*/
        return count;
    }
    public int compareResult(int count,double alpha) {
        double[][] error = new double[10][1];
        error[this.label][0] = 1.0;
        double sum2=0;
        for(int i=0;i<10;i++) {
            error[i][0] = (error[i][0] - this.layerOutput[i][0]);
            sum2+=error[i][0]*error[i][0];
        }
        //if(numb%100==0) {
        //Matrix.print(error);
        //}
        //System.out.println(sum2);
        double actual=this.layerOutput[this.label][0];
        for (double[] end : layerOutput) {
            if(actual<end[0]){
                count += 1;
                break;
            }
        }

        /*if(numb%7==0) {
            System.out.println(sum2);
        }*/

        this.adjustWeights(error,alpha);
        return count;
    }
    public void adjustWeights(double[][] error,double alpha) {
        //double alpha = 0.14;

        double[][] sigmoidDervivative3 = new double[10][1];
        for (int i = 0; i < this.weightHiddenOutput.length; i++) {
            sigmoidDervivative3[i][0] = this.layerOutput[i][0] * (1 - this.layerOutput[i][0]) * error[i][0];
        }
        double[][] delta3 = Matrix.multiply(sigmoidDervivative3, Matrix.transpose(this.layerHidden2));
        delta3 = Matrix.multiplyEachBy(delta3, alpha);

        double[][] sigmoidDerivative2 = new double[this.weightHiddenHidden.length][1];
        double[] sums = new double[this.weightHiddenOutput[0].length];
        for (int j = 0; j < this.weightHiddenOutput[0].length; j++) {
            double sum = 0;
            for (int i = 0; i < this.weightHiddenOutput.length; i++) {
                sum += this.weightHiddenOutput[i][j] * sigmoidDervivative3[i][0];
            }
            sums[j] = sum;
            sigmoidDerivative2[j][0] = this.layerHidden2[j][0] * (1 - this.layerHidden2[j][0]) * sum;
        }

        double[][] delta2 = Matrix.multiply(sigmoidDerivative2, Matrix.transpose(this.layerHidden1));
        delta2 = Matrix.multiplyEachBy(delta2, alpha);

        double[][] sigmoidDerivative1 = new double[this.weightInputHidden.length][1];
        double[] sums1 = new double[this.weightHiddenHidden[0].length];
        for (int j = 0; j < this.weightHiddenHidden[0].length; j++) {
            double sum = 0;
            for (int i = 0; i < this.weightHiddenHidden.length; i++) {
                sum += this.weightHiddenHidden[i][j] * sigmoidDerivative2[i][0];
            }
            sums1[j] = sum;
            sigmoidDerivative1[j][0] = this.layerHidden1[j][0] * (1 - this.layerHidden1[j][0]) * sum;
        }
        double[][] delta1 = Matrix.multiply(sigmoidDerivative1, Matrix.transpose(this.layerInput));
        delta1 = Matrix.multiplyEachBy(delta1, alpha);

        this.weightHiddenOutput = Matrix.add(weightHiddenOutput, delta3);
        this.weightHiddenHidden = Matrix.add(weightHiddenHidden, delta2);
        this.weightInputHidden = Matrix.add(weightInputHidden, delta1);
    }
}