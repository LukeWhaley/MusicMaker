import javax.sound.sampled.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Instrument {

    public String instrument = "kick";

    public String[] instrumentNames = {"kick","snare","hat","piano","bass", "keyboard", "pluck_synth", "plucked_string"};

    public int[] playtimes = new int[64];
    public int[] pitches = new int[64];

    private int time = 0;
    private Clip clip; 

    private int lastplayed = 0;
    private int lastpitch = 0;

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public void instrumentUp(){
        for (int i = 0; i < instrumentNames.length; i++){
            if (instrument.equals(instrumentNames[i])){
                if (i == instrumentNames.length - 1){
                    instrument = instrumentNames[0];
                } else instrument = instrumentNames[i+1];
                break;
            }
        }    
    }

    public void instrumentDown(){
        for (int i = 0; i < instrumentNames.length; i++){
            if (instrument.equals(instrumentNames[i])){
                if (i == 0){
                    instrument = instrumentNames[instrumentNames.length - 1];
                } else instrument = instrumentNames[i-1];
                break;
            }
        }    
    }


    public void setPlaytime(Integer i, Integer j) {
        playtimes[i] = j;
    }

    public void setPitch(Integer i, Integer j) {
        pitches[i] = j;
    }

    public void settime(int time) {
        this.time = time;
    }

    public void play() {

        if (playtimes[time] == 1) {
            stopSound(); 
            new Thread(() -> playSound("src\\instruments\\" + instrument + "\\"+instrument+" ("+(pitches[time]+1)+").wav")).start();
            lastplayed = 1;
            lastpitch = pitches[time];
        } else if (playtimes[time] == 0) {
            if (lastplayed == 1){
                stopSound();
                lastplayed = 0;
            }
        } else if (playtimes[time] == 2){
            new Thread(() -> playSound("src\\instruments\\" + instrument + "\\"+instrument+" ("+(pitches[time]+1)+").wav")).start();
            lastplayed = 0;
            lastpitch = pitches[time];
        } else {
            if (lastplayed == 0 || pitches[time] != lastpitch) {
                stopSound();
                new Thread(() -> playSound("src\\instruments\\" + instrument + "\\"+instrument+" ("+(pitches[time]+1)+").wav")).start();
                lastplayed = 1;
                lastpitch = pitches[time];
            }
        }
    }

    public void playNote(int pitch){
            new Thread(() -> playSound("src\\instruments\\" + instrument + "\\"+instrument+" ("+(pitch+1)+").wav")).start();
    }

    public void stopSound() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public void songStopped() {
        lastplayed = 0; 
        lastpitch = 0;
    }

    public void loadSound(String sound) {
        new Thread(() -> playSound("src\\instruments\\" + "programSFX" + "\\" + sound + ".wav")).start();
    }

    private void playSound(String filePath) {
        try {
            File soundFile = new File(filePath);
            if (!soundFile.exists()) {
                System.err.println("Sound file not found: " + filePath);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundFile);
            clip = AudioSystem.getClip(); 
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio file: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error reading the audio file: " + e.getMessage());
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable: " + e.getMessage());
        }
    }


    public void readMatrixFromFile(String name) throws IOException {

        File file = new File(name);
        Scanner sc = new Scanner(file);
        String fileContent = "";
        while (sc.hasNextLine()) {
            fileContent += sc.nextLine();
        }
        String[] array = fileContent.split(" ");

        instrument=array[0];

        for (int i = 0; i<64 ;i++){
        playtimes[i] =  Integer.parseInt(array[i+64+1]); 

        pitches[i] =  Integer.parseInt(array[i+1]); 
        }

        sc.close();

    }

    public void writeInstrumentDataToFile(String songName, String name){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\songs\\"+songName+"\\"+name))){
            writer.write(instrument+" ");
            writer.newLine();
            for (int i = 0; i < 64; i++){
                writer.write(pitches[i]+" ");
            }
            writer.newLine();
            for (int i = 0; i < 64; i++){
                writer.write(playtimes[i]+" ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

