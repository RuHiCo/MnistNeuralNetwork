public class Stoppuhr {

    private long startzeit;
    private long endzeit;

    public Stoppuhr(){
        this.startzeit = 0;
        this.endzeit = 0;
    }

    public void start(){
        this.startzeit = System.currentTimeMillis();
    }

    public void stopp(){
        this.endzeit = System.currentTimeMillis();
    }

    public void reset(){
        this.startzeit = 0;
        this.endzeit = 0;
    }

    public long getDurationInMs(){
        return this.endzeit - this.startzeit;
    }

    public long getDurationInS(){
        return this.getDurationInMs()/1000;
    }
}