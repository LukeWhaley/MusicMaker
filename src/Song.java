import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Song {
    public int BPM = 120; 
    private int BEAT_INTERVAL = 60000 / BPM; 

    private static boolean isPlaying = false;

    public static float currentBeat = 0;

    public int time = 0;

    private Timer timer; 

    private int measure = 64;

    public void setSongPositon(Integer t) {
        currentBeat = t;
    }
    
    public void increaseSongBPM(){
        if (BPM < 400) {
            BPM = BPM + 5;
            BEAT_INTERVAL = 60000 / BPM;
        }
    }

    public void decreaseSongBPM(){
        if (BPM > 20){
        BPM = BPM - 5;
        BEAT_INTERVAL = 60000 / BPM;
        }
    }

    Instrument inst1 = new Instrument();
    Instrument inst2 = new Instrument();
    Instrument inst3 = new Instrument();
    Instrument inst4 = new Instrument();
    Instrument inst5 = new Instrument();
    Instrument inst6 = new Instrument();
    Instrument inst7 = new Instrument();
    Instrument inst8 = new Instrument();

    public void load(String songName){

        try {
            
            File file = new File("src\\songs\\" + songName + "\\songdata.txt");
            Scanner sc = new Scanner(file);
            String fileContent = "";
            while (sc.hasNextLine()) {
                fileContent += sc.nextLine();
            }
            String[] array = fileContent.split(" ");

            BPM=Integer.parseInt(array[0]);
            BEAT_INTERVAL = 60000 / BPM;

            sc.close();

            inst1.readMatrixFromFile("src\\songs\\" + songName + "\\inst1notes.txt");
            inst2.readMatrixFromFile("src\\songs\\" + songName + "\\inst2notes.txt");
            inst3.readMatrixFromFile("src\\songs\\" + songName + "\\inst3notes.txt");
            inst4.readMatrixFromFile("src\\songs\\" + songName + "\\inst4notes.txt");
            inst5.readMatrixFromFile("src\\songs\\" + songName + "\\inst5notes.txt");
            inst6.readMatrixFromFile("src\\songs\\" + songName + "\\inst6notes.txt");
            inst7.readMatrixFromFile("src\\songs\\" + songName + "\\inst7notes.txt");
            inst8.readMatrixFromFile("src\\songs\\" + songName + "\\inst8notes.txt");
            inst1.loadSound("loadSong");
        } catch (IOException e) {
            System.out.println("Unable to find song: " + songName);
            inst1.loadSound("loadFailed");
        }

    }

    public void save(String songName){

        File dir1 = new File("src\\songs\\"+songName);
        if (dir1.mkdir()) {
            System.out.println("Directory created successfully using mkdir().");
        } else {
            System.out.println("Failed to create directory using mkdir().");
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\songs\\"+songName+"\\"+"songdata.txt"))){
            writer.write(""+BPM);
        } catch (IOException e) {
            e.printStackTrace();
        }


        inst1.writeInstrumentDataToFile(songName,"inst1notes.txt");
        inst2.writeInstrumentDataToFile(songName,"inst2notes.txt");
        inst3.writeInstrumentDataToFile(songName,"inst3notes.txt");
        inst4.writeInstrumentDataToFile(songName,"inst4notes.txt");
        inst5.writeInstrumentDataToFile(songName,"inst5notes.txt");
        inst6.writeInstrumentDataToFile(songName,"inst6notes.txt");
        inst7.writeInstrumentDataToFile(songName,"inst7notes.txt");
        inst8.writeInstrumentDataToFile(songName,"inst8notes.txt");

        inst1.loadSound("saveSong");

    }

    public void playSong() {

        if (isPlaying == false){
            isPlaying = true;
            timer = new Timer(); 
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    inst1.settime(time);
                    inst1.play();

                    inst2.settime(time);
                    inst2.play();

                    inst3.settime(time);
                    inst3.play();

                    inst4.settime(time);
                    inst4.play();

                    inst5.settime(time);
                    inst5.play();

                    inst6.settime(time);
                    inst6.play();

                    inst7.settime(time);
                    inst7.play();

                    inst8.settime(time);
                    inst8.play();

                    time = time + 1;

                    if (time == measure) {
                        time = 0;
                    }

                }
            }, 0, BEAT_INTERVAL/4);

        }
    }

    public void stopSong() {
        if (timer != null) {
            timer.cancel(); 
            timer = null; 
        }
        time = 0;
        
        inst1.songStopped();
        inst2.songStopped();
        inst3.songStopped();
        inst4.songStopped();
        inst5.songStopped();
        inst6.songStopped();
        inst7.songStopped();
        inst8.songStopped();

        isPlaying = false;
    }
}
