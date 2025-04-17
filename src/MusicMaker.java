import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;


public class MusicMaker extends Application {

  private Instrument inst = songPlayer.inst1;

  private int brushType = 1;

  static Song songPlayer = new Song();

  private Cell[][] cell =  new Cell[24][64];

  private Label lblStatus = new Label("Instrument: " + inst.instrument);

  @Override 
  public void start(Stage primaryStage) {
    
    GridPane pane = new GridPane(); 
    for (int i = 0; i < 24; i++)
      for (int j = 0; j < 64; j++)
        pane.add(cell[i][j] = new Cell(j,i), j, i);


    Button tapNoteButton = new Button("Tap Note");
    tapNoteButton.setTranslateX(100);
    tapNoteButton.setStyle("-fx-background-color: red");

    Button holdNoteButton = new Button("Hold Note");
    holdNoteButton.setTranslateX(100);
    holdNoteButton.setStyle("-fx-background-color: lightblue");

    Button sustainNoteButton = new Button("Sustain Note");
    sustainNoteButton.setTranslateX(100);
    sustainNoteButton.setStyle("-fx-background-color: lightgreen");

    tapNoteButton.setOnAction(value ->  {
        brushType = 1;
        tapNoteButton.setStyle("-fx-background-color: red");
        holdNoteButton.setStyle("-fx-background-color: lightblue");
        sustainNoteButton.setStyle("-fx-background-color: lightgreen");
    });


    holdNoteButton.setOnAction(value ->  {
        brushType = 3;
        tapNoteButton.setStyle("-fx-background-color: pink");
        holdNoteButton.setStyle("-fx-background-color: blue");
        sustainNoteButton.setStyle("-fx-background-color: lightgreen");

    });


    sustainNoteButton.setOnAction(value ->  {
        brushType = 2;
        tapNoteButton.setStyle("-fx-background-color: pink");
        holdNoteButton.setStyle("-fx-background-color: lightblue");
        sustainNoteButton.setStyle("-fx-background-color: limegreen");

    });

    TextField tfBPM = new TextField();
    tfBPM.setText("BPM: 120");
    tfBPM.setTranslateX(300);
    tfBPM.setEditable(false);


    Button BPMUpButton = new Button("BPM + 5");
    BPMUpButton.setTranslateX(300);

    BPMUpButton.setOnAction(value ->  {
        songPlayer.increaseSongBPM();
        tfBPM.setText("BPM: "+ songPlayer.BPM);
    });

    Button BPMDownButton = new Button("BPM - 5");
    BPMDownButton.setTranslateX(300);

    BPMDownButton.setOnAction(value ->  {
        songPlayer.decreaseSongBPM();
        tfBPM.setText("BPM: "+ songPlayer.BPM);
    });


    Button playButton = new Button("Play");
    playButton.setTranslateX(50);
    playButton.setTranslateY(0);

    playButton.setOnAction(value ->  {
        songPlayer.playSong();
    });

    Button playButton2 = new Button("Play");

    playButton2.setOnAction(value ->  {
        songPlayer.playSong();
    });


    Button stopButton = new Button("Stop");
    stopButton.setTranslateX(50);
    stopButton.setTranslateY(0);

    stopButton.setOnAction(value ->  {
        songPlayer.stopSong();
    });

    Button stopButton2 = new Button("Stop");

    stopButton2.setOnAction(value ->  {
        songPlayer.stopSong();
    });


    Button saveButton = new Button("Save");
    saveButton.setTranslateX(200);
    saveButton.setTranslateY(0);

    TextField tfSaveSongName = new TextField();
    tfSaveSongName.setTranslateX(200);

    tfSaveSongName.setText("newsong");

    saveButton.setOnAction(value ->  {
        songPlayer.save(tfSaveSongName.getText());
    });


    GridPane pane2 = new GridPane();

    pane2.add(playButton, 1, 0);
    pane2.add(stopButton, 2, 0);

    pane2.add(tapNoteButton, 3, 0);
    pane2.add(holdNoteButton, 4, 0);
    pane2.add(sustainNoteButton, 5, 0);


    GridPane pane3 = new GridPane();

    pane3.add(playButton2, 3, 0);
    pane3.add(stopButton2, 4, 0);
    
    pane3.add(BPMDownButton, 15, 0);
    pane3.add(tfBPM, 16, 0);
    pane3.add(BPMUpButton, 17, 0);

    pane3.setTranslateX(90);


    GridPane pane4 = new GridPane();
    pane4.setAlignment(Pos.CENTER);


    BorderPane borderPane = new BorderPane();
    borderPane.setCenter(pane);
    borderPane.setTop(pane2);
    borderPane.setBottom(lblStatus);

    BorderPane borderPane2 = new BorderPane();
    borderPane2.setTop(pane3);
    borderPane2.setLeft(pane4);

    Scene notePlaceScene = new Scene(borderPane, 1500, 500);

    Scene songViewScene = new Scene(borderPane2, 1500, 500);

    Button backButton = new Button("Back");
    backButton.setTranslateX(0);
    backButton.setTranslateY(0);
    
    backButton.setOnAction(value ->  {
        primaryStage.setScene(songViewScene); 
        });
    
    pane2.add(backButton, 0, 0);

    Button inst1ViewButton = new Button("Instrument 1: " + songPlayer.inst1.instrument);
    inst1ViewButton.setOnAction(value ->  {
        inst = songPlayer.inst1;
        lblStatus.setText("Instrument: " + inst.instrument);
        primaryStage.setScene(notePlaceScene); 
        updateCells();
    });

    Button inst1Left = new Button("<");
    inst1Left.setOnAction(value ->  {
        songPlayer.inst1.instrumentDown();
        inst1ViewButton.setText("Instrument 1: " + songPlayer.inst1.instrument);
        });

        
    Button inst1Right = new Button(">");
    inst1Right.setOnAction(value ->  {
        songPlayer.inst1.instrumentUp();
        inst1ViewButton.setText("Instrument 1: " + songPlayer.inst1.instrument);
        });

    
    Button inst2ViewButton = new Button("Instrument 2: " + songPlayer.inst2.instrument);
    inst2ViewButton.setOnAction(value ->  {
        inst = songPlayer.inst2;
        lblStatus.setText("Instrument: " + inst.instrument);
        primaryStage.setScene(notePlaceScene); 
        updateCells();
        });

    Button inst2Left = new Button("<");
    inst2Left.setOnAction(value ->  {
        songPlayer.inst2.instrumentDown();
        inst2ViewButton.setText("Instrument 2: " + songPlayer.inst2.instrument);
        });

        
    Button inst2Right = new Button(">");
    inst2Right.setOnAction(value ->  {
        songPlayer.inst2.instrumentUp();
        inst2ViewButton.setText("Instrument 2: " + songPlayer.inst2.instrument);
        });



    Button inst3ViewButton = new Button("Instrument 3: " + songPlayer.inst3.instrument);
    inst3ViewButton.setOnAction(value ->  {
        inst = songPlayer.inst3;
        lblStatus.setText("Instrument: " + inst.instrument);
        primaryStage.setScene(notePlaceScene); 
        updateCells();
        });

    Button inst3Left = new Button("<");
    inst3Left.setOnAction(value ->  {
        songPlayer.inst3.instrumentDown();
        inst3ViewButton.setText("Instrument 3: " + songPlayer.inst3.instrument);
        });

        
    Button inst3Right = new Button(">");
    inst3Right.setOnAction(value ->  {
        songPlayer.inst3.instrumentUp();
        inst3ViewButton.setText("Instrument 3: " + songPlayer.inst3.instrument);
        });



    Button inst4ViewButton = new Button("Instrument 4: " + songPlayer.inst4.instrument);
    inst4ViewButton.setOnAction(value ->  {
        inst = songPlayer.inst4;
        lblStatus.setText("Instrument: " + inst.instrument);
        primaryStage.setScene(notePlaceScene); 
        updateCells();

        });

    Button inst4Left = new Button("<");
    inst4Left.setOnAction(value ->  {
        songPlayer.inst4.instrumentDown();
        inst4ViewButton.setText("Instrument 4: " + songPlayer.inst4.instrument);
        });

        
    Button inst4Right = new Button(">");
    inst4Right.setOnAction(value ->  {
        songPlayer.inst4.instrumentUp();
        inst4ViewButton.setText("Instrument 4: " + songPlayer.inst4.instrument);
        });



    Button inst5ViewButton = new Button("Instrument 5: " + songPlayer.inst5.instrument);
    inst5ViewButton.setOnAction(value ->  {
        inst = songPlayer.inst5;
        lblStatus.setText("Instrument: " + inst.instrument);
        primaryStage.setScene(notePlaceScene); 
        updateCells();

        });

    Button inst5Left = new Button("<");
    inst5Left.setOnAction(value ->  {
        songPlayer.inst5.instrumentDown();
        inst5ViewButton.setText("Instrument 5: " + songPlayer.inst5.instrument);
        });

        
    Button inst5Right = new Button(">");
    inst5Right.setOnAction(value ->  {
        songPlayer.inst5.instrumentUp();
        inst5ViewButton.setText("Instrument 5: " + songPlayer.inst5.instrument);
        });



    Button inst6ViewButton = new Button("Instrument 6: " + songPlayer.inst6.instrument);
    inst6ViewButton.setOnAction(value ->  {
        inst = songPlayer.inst6;
        lblStatus.setText("Instrument: " + inst.instrument);
        primaryStage.setScene(notePlaceScene); 
        updateCells();

        });



    Button inst6Left = new Button("<");
    inst6Left.setOnAction(value ->  {
        songPlayer.inst6.instrumentDown();
        inst6ViewButton.setText("Instrument 6: " + songPlayer.inst6.instrument);
        });

        
    Button inst6Right = new Button(">");
    inst6Right.setOnAction(value ->  {
        songPlayer.inst6.instrumentUp();
        inst6ViewButton.setText("Instrument 6: " + songPlayer.inst6.instrument);
        });
    




    Button inst7ViewButton = new Button("Instrument 7: " + songPlayer.inst7.instrument);
    inst7ViewButton.setOnAction(value ->  {
        inst = songPlayer.inst7;
        lblStatus.setText("Instrument: " + inst.instrument);
        primaryStage.setScene(notePlaceScene); 
        updateCells();

        });


        Button inst7Left = new Button("<");
        inst7Left.setOnAction(value ->  {
            songPlayer.inst7.instrumentDown();
            inst7ViewButton.setText("Instrument 7: " + songPlayer.inst7.instrument);
            });
    
            
        Button inst7Right = new Button(">");
        inst7Right.setOnAction(value ->  {
            songPlayer.inst7.instrumentUp();
            inst7ViewButton.setText("Instrument 7: " + songPlayer.inst7.instrument);
            });
    


    Button inst8ViewButton = new Button("Instrument 8: " + songPlayer.inst8.instrument);
    inst8ViewButton.setOnAction(value ->  {
        inst = songPlayer.inst8;
        lblStatus.setText("Instrument: " + inst.instrument);
        primaryStage.setScene(notePlaceScene); 
        updateCells();

        });
    

    Button inst8Left = new Button("<");
    inst8Left.setOnAction(value ->  {
        songPlayer.inst8.instrumentDown();
        inst8ViewButton.setText("Instrument 8: " + songPlayer.inst8.instrument);
        });

        
    Button inst8Right = new Button(">");
    inst8Right.setOnAction(value ->  {
        songPlayer.inst8.instrumentUp();
        inst8ViewButton.setText("Instrument 8: " + songPlayer.inst8.instrument);
        });


    inst1ViewButton.setMinWidth(180);
    inst2ViewButton.setMinWidth(180);
    inst3ViewButton.setMinWidth(180);
    inst4ViewButton.setMinWidth(180);
    inst5ViewButton.setMinWidth(180);
    inst6ViewButton.setMinWidth(180);
    inst7ViewButton.setMinWidth(180);
    inst8ViewButton.setMinWidth(180);

        
    pane4.add(inst1Left, 0, 0);
    pane4.add(inst1ViewButton, 1, 0);
    pane4.add(inst1Right, 2, 0);

    pane4.add(inst2Left, 0, 1);
    pane4.add(inst2ViewButton, 1, 1);
    pane4.add(inst2Right, 2, 1);

    pane4.add(inst3Left, 0, 2);
    pane4.add(inst3ViewButton, 1, 2);
    pane4.add(inst3Right, 2, 2);

    pane4.add(inst4Left, 0, 3);
    pane4.add(inst4ViewButton, 1, 3);
    pane4.add(inst4Right, 2, 3);

    pane4.add(inst5Left, 0, 4);
    pane4.add(inst5ViewButton, 1, 4);
    pane4.add(inst5Right, 2, 4);

    pane4.add(inst6Left, 0, 5);
    pane4.add(inst6ViewButton, 1, 5);
    pane4.add(inst6Right, 2, 5);

    pane4.add(inst7Left, 0, 6);
    pane4.add(inst7ViewButton, 1, 6);
    pane4.add(inst7Right, 2, 6);

    pane4.add(inst8Left, 0, 7);
    pane4.add(inst8ViewButton, 1, 7);
    pane4.add(inst8Right, 2, 7);


    TextField tfLoadSongName = new TextField();
    tfLoadSongName.setTranslateX(100);

    Button loadButton = new Button("Load");
    loadButton.setTranslateX(100);

    loadButton.setOnAction(value ->  {
        songPlayer.load(tfLoadSongName.getText());
        tfBPM.setText("BPM: "+ songPlayer.BPM);
        inst1ViewButton.setText("Instrument 1: " + songPlayer.inst1.instrument);
        inst2ViewButton.setText("Instrument 2: " + songPlayer.inst2.instrument);
        inst3ViewButton.setText("Instrument 3: " + songPlayer.inst3.instrument);
        inst4ViewButton.setText("Instrument 4: " + songPlayer.inst4.instrument);
        inst5ViewButton.setText("Instrument 5: " + songPlayer.inst5.instrument);
        inst6ViewButton.setText("Instrument 6: " + songPlayer.inst6.instrument);
        inst7ViewButton.setText("Instrument 7: " + songPlayer.inst7.instrument);
        inst8ViewButton.setText("Instrument 8: " + songPlayer.inst8.instrument);
        tfSaveSongName.setText(tfLoadSongName.getText());

    });

    pane3.add(tfLoadSongName, 9, 0);
    pane3.add(tfSaveSongName, 11, 0);

    pane3.add(loadButton, 8, 0);
    pane3.add(saveButton, 10, 0);

    primaryStage.setTitle("Music Maker"); 
    primaryStage.setScene(songViewScene); 
    primaryStage.show(); 
    
  }

  public void updateCells(){
    for (int i = 0; i < 24; i++){
        for (int j = 0; j < 64; j++)
        {
            if (inst.pitches[j] == 23-i && inst.playtimes[j] != 0){
                
                if (inst.playtimes[j] == 1){
                cell[i][j].setColor("-fx-background-color: red"); 
                } else if (inst.playtimes[j] == 2){
                    cell[i][j].setColor("-fx-background-color: green"); 
                } else if (inst.playtimes[j] == 3){
                    cell[i][j].setColor("-fx-background-color: blue"); 
                } 
            } else {
                if (j % 4 == 0){
                cell[i][j].setColor("-fx-border-color: black");
                } else {
                cell[i][j].setColor("-fx-border-color: darkgray");
                }
            }
        }
    }
  }
  
  public class Cell extends Pane {

    public Cell(int j, int i) {
        if (inst.pitches[j]==23-i && inst.playtimes[j] != 0){
            if (inst.playtimes[j] == 1){
                setStyle("-fx-background-color: red");
            } else if (inst.playtimes[j] == 2){
                setStyle("-fx-background-color: green");
            } else if (inst.playtimes[j] == 3){
                setStyle("-fx-background-color: blue");
            }
        } else{
            setStyle("-fx-border-color: black"); 
        }
      this.setPrefSize(800, 800);
      this.setOnMouseClicked(e -> handleMouseClick());
    }

    public void setColor(String s){
        this.setStyle(s);
    }

    public void placeNote() {

        int clickedRow = 23-GridPane.getRowIndex(this);
        int clickedColumn = GridPane.getColumnIndex(this);

        for (int i = 0; i < 24; i++){
            if (clickedColumn % 4 == 0){
                cell[i][clickedColumn].setColor("-fx-border-color: black");
                } else {
                cell[i][clickedColumn].setColor("-fx-border-color: darkgray");
                }

        }

        if (inst.playtimes[clickedColumn] == 0 || inst.pitches[clickedColumn] != clickedRow)  {
            inst.setPlaytime(clickedColumn, brushType);
            inst.setPitch(clickedColumn, clickedRow);
            
            if (brushType == 1){
            this.setStyle("-fx-background-color: red");
            } else if (brushType == 2)
            this.setStyle("-fx-background-color: green");
            else {
            this.setStyle("-fx-background-color: blue");
            }

            inst.playNote(clickedRow);

        } 
        else if (inst.playtimes[clickedColumn] != 0) {
            inst.setPlaytime(clickedColumn, 0);
            
            if (clickedColumn % 4 == 0){
                this.setStyle("-fx-border-color: black");
                } else {
                this.setStyle("-fx-border-color: darkgray");
                }

        }
                  
    }

    private void handleMouseClick() {
        placeNote(); 
    }

    }

  public static void main(String[] args) {
    songPlayer.inst1.loadSound("openProgram");
    launch(args);
    songPlayer.stopSong();
  }
}