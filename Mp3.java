
import javafx.scene.image.Image;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Mp3 extends Application {

    Slider volslider;
    Slider timeslider;
    private ListView<String> listview;
    private MediaPlayer mediaPlayer;
    private Media media;
    Duration duration;
    private  Label totalDuration = new Label();
    private  Label currentDuration = new Label();
    private static final double MIN_CHANGE = 0.5;
    ArrayList<String> arraylist;
    double currenttime;
    int count = 0;
    long begin;
    long current;
    @Override
    public void start(Stage primaryStage) {
    	  evaluate ev=new evaluate();
        StackPane root = new StackPane();
        root.setMinSize(448, 316);
        root.setStyle("-fx-background-color:#222866");
        //menu to make list of file
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-border-color:#b3b8e6");
        Menu menu = new Menu("open");
        menu.setStyle("-fx-background-color:pink");
        menu.setStyle("-fx-font-style:italic");
        //to choose one file only
        MenuItem openFile = new MenuItem("Open File");
        menu.getItems().add(openFile);
        openFile.setOnAction(e -> ev.openFile());
       
        //to choose multifiles
        
        MenuItem openDurectory = new MenuItem("Open Dir");
        menu.getItems().add(openDurectory);
        openDurectory.setOnAction(e -> ev.openMultiFiles());
        MenuItem addSongs = new MenuItem("add songs to list");
        menu.getItems().add(addSongs);
        addSongs.setOnAction(e -> ev.addSongsTOList());

        menuBar.getMenus().add(menu);

        menuBar.prefWidthProperty().bind(primaryStage.widthProperty());
        menuBar.setMaxSize(400, 20);
        menuBar.setTranslateX(-20);
        menuBar.setTranslateY(-145);

        root.getChildren().add(menuBar);
        //button to play songs
        Button play = new Button();
        play.setOnAction(e ->ev. play());
        play.setTranslateX(-100);
        play.setTranslateY(60);
        ImageView imagePlay = new ImageView(
        new Image("Player_Pause.png"));
        imagePlay.setFitWidth(30);
        imagePlay.setFitHeight(30);
        play.setGraphic(imagePlay);
        root.getChildren().add(play);
        play.setStyle("-fx-border-color:#b3b8e6");
        Button stop = new Button();
        stop.setOnAction(e -> ev.stop());
        stop.setTranslateX(-50);
        stop.setTranslateY(60);
        ImageView imageStop = new ImageView(
        new Image("Player_Play.png"));
        imageStop.setFitWidth(30);
        imageStop.setFitHeight(30);
        stop.setGraphic(imageStop);
        root.getChildren().add(stop);
        stop.setStyle("-fx-border-color:#b3b8e6");
        Button replay = new Button();
        replay.setOnAction(e -> ev.replay());
        replay.setTranslateX(-150);
        replay.setTranslateY(60);
        ImageView imageReplay = new ImageView(
        new Image("400_F_19561120_5cHvztxxWVRAgey489QxExZUJh6xkgxg.PNG"));
        imageReplay.setFitWidth(30);
        imageReplay.setFitHeight(30);
        replay.setGraphic(imageReplay);
        root.getChildren().add(replay);
        replay.setStyle("-fx-border-color:#b3b8e6");
        currentDuration.setStyle("-fx-background-color:white");
        Button backword = new Button();
        backword.setOnAction(e ->ev. back());
        backword.setTranslateX(-200);
        backword.setTranslateY(60);
        ImageView imageBackword = new ImageView(
                new Image("Player_Rew.png"));
        imageBackword.setFitWidth(30);
        imageBackword.setFitHeight(30);
        backword.setGraphic(imageBackword);
        root.getChildren().add(backword);
        backword.setStyle("-fx-border-color:#b3b8e6");
        Button forward = new Button();
        forward.setOnAction(e ->ev. forward());
        forward.setTranslateX(0);
        forward.setTranslateY(60);
        ImageView imageForward = new ImageView(
        new Image("Player_Fwd.png"));
        imageForward.setFitWidth(30);
        imageForward.setFitHeight(30);
        forward.setGraphic(imageForward);
        root.getChildren().add(forward);
       forward.setStyle("-fx-border-color:#b3b8e6");
        volslider = new Slider();
        volslider.setMaxSize(120, 10);
        volslider.setTranslateX(150);
        volslider.setTranslateY(60);
        root.getChildren().add(volslider);
        
         Label soundImage=new Label();
         soundImage.setTranslateX(75);
         soundImage.setTranslateY(60);
        ImageView imageSound = new ImageView(
                new Image("th.PNG"));
        imageSound.setFitWidth(22);
        imageSound.setFitHeight(22);
        soundImage.setGraphic(imageSound);
        root.getChildren().add(soundImage);
        currentDuration.setTranslateX(145);
        currentDuration.setTranslateY(130);
        root.getChildren().add(currentDuration);

        totalDuration.setTranslateX(185);
        totalDuration.setTranslateY(130);
        root.getChildren().add(totalDuration);

        timeslider = new Slider();
        timeslider.setMaxSize(200, 10);
        timeslider.setTranslateX(0);
        timeslider.setTranslateY(130);
        root.getChildren().add(timeslider);
        
        Label clockImage=new Label();
         clockImage.setTranslateX(-145);
         clockImage.setTranslateY(130);
        ImageView imageTime = new ImageView(
                new Image("clock.jpg"));
        imageTime.setFitWidth(28);
        imageTime.setFitHeight(28);
        clockImage.setGraphic(imageTime);
        root.getChildren().add(clockImage);
        
        
        listview = new ListView<String>();
        listview.setMaxSize(400, 150);
        listview.setTranslateX(-20);
        listview.setTranslateY(-50);
        //double click to select song
        listview.setOnMouseClicked(new EventHandler<MouseEvent>() {
    @Override
    public void handle(MouseEvent click) {

        if (click.getClickCount() == 2) {
         try {
            int selectedItem = listview.getSelectionModel().getSelectedIndex();
                    count = selectedItem;
                    String path = arraylist.get(count);
                    ev.stop();
                    media = new Media(new File(path).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();
                    ev.timeSlider();
                  
                
            
            }
            catch(Exception ex){}
        }
    }
});
        root.getChildren().add(listview);
       Scene scene = new Scene(root, 448, 316);

       primaryStage.setMaxHeight(350);
       primaryStage.setMaxWidth(500);
       primaryStage.setMinHeight(350);
       primaryStage.setMinWidth(500);
       primaryStage.setTitle("mp3 player");
       primaryStage.setScene(scene);
       primaryStage.show();
   }

    //inner class contain methods
    public class evaluate {
        //********************************************************************************************

        public void openFile() {
            try {

                FileChooser fc = new FileChooser();
                File chosen = fc.showOpenDialog(null);
                String path = chosen.getAbsolutePath();
                listview.getItems().clear();

                listview.getItems().add(chosen.getName());
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }

                media = new Media(new File(path).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();

                volumeSlider();
                timeSlider();
            } catch (Exception ex) {
            }

        }
//********************************************************************************************

        public void openMultiFiles() {
            try {
                arraylist = new ArrayList<String>();
                FileChooser fc = new FileChooser();
                List<File> chosen = fc.showOpenMultipleDialog(null);
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                }
                listview.getItems().clear();
                for (int i = 0; i < chosen.size(); i++) {
                    arraylist.add(chosen.get(i).getAbsolutePath());

                    listview.getItems().add(chosen.get(i).getName());

                }
                
                String path = chosen.get(0).getAbsolutePath();
                media = new Media(new File(path).toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();

                volumeSlider();
                timeSlider();
            } catch (Exception ex) {
            }

        }
//********************************************************************************************

        public void replay() {
            try {
                mediaPlayer.seek(mediaPlayer.getStartTime());
                mediaPlayer.play();
            } catch (Exception ex) {
            }
        }
//********************************************************************************************

        public void back() {
            try {
                if (count > 0) {
                    --count;
                    mediaPlayer.stop();
                    String path = arraylist.get(count);
                    media = new Media(new File(path).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();

                    timeSlider();
                }
            } catch (Exception ex) {
            }

        }
//********************************************************************************************

        public void forward() {
            try {
                if (count < arraylist.size() - 1) {
                    ++count;
                    mediaPlayer.stop();
                    String path = arraylist.get(count);
                    media = new Media(new File(path).toURI().toString());
                    mediaPlayer = new MediaPlayer(media);
                    mediaPlayer.play();

                    timeSlider();
                }
            } catch (Exception ex) {
            }

        }
//********************************************************************************************

        public void play() {
            try {
                mediaPlayer.play();
            } catch (Exception ex) {
            }
        }
//********************************************************************************************

        public void stop() {
            try {
                mediaPlayer.pause();
            } catch (Exception ex) {
            }
        }

        //******************************************************************************************

        public void timeSlider() {

            try {

                mediaPlayer.setOnReady(() -> {
                    duration = mediaPlayer.getMedia().getDuration();
                    totalDuration.setText("    / " + duration.toSeconds());
                    totalDuration.setStyle("-fx-background-color:white");
                });

                //***
                mediaPlayer.totalDurationProperty().addListener((obs, oldDuration, newDuration)
                   -> timeslider.setMax(newDuration.toSeconds()));

                timeslider.valueChangingProperty().addListener((obs, wasChanging, isChanging) ->{
                    if (!isChanging) {
                        mediaPlayer.seek(Duration.seconds(timeslider.getValue()));
                        currentDuration.setText("" + Duration.seconds(timeslider.getValue()));
                        currenttime = mediaPlayer.getCurrentTime().toSeconds();
                        if (count < arraylist.size() - 1 && currenttime >= duration.toSeconds()) {
                            forward();
                        }
                        if (count == arraylist.size() - 1 && currenttime >= duration.toSeconds()) {
                            replay();
                        }

                    }
                });

                //***********
                timeslider.valueProperty().addListener((obs, oldValue, newValue) -> {
                    if (!timeslider.isValueChanging()) {
                        currenttime = mediaPlayer.getCurrentTime().toSeconds();
                        if (Math.abs(currenttime - newValue.doubleValue()) > 0.5) {
                            mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
                            currentDuration.setText("" + Duration.seconds(newValue.doubleValue()));
                            if (count < arraylist.size() - 1 && currenttime >= duration.toSeconds()) {
                                forward();
                            }
                            if (count == arraylist.size() - 1 && currenttime >= duration.toSeconds()) {
                            	replay();
                              
                            }
                        }

                    }
                });
                //*******************

                mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                    if (!timeslider.isValueChanging()) {
                        timeslider.setValue(newTime.toSeconds());
                        currentDuration.setText("" + newTime.toSeconds());
                        currenttime = mediaPlayer.getCurrentTime().toSeconds();
                        if (count < arraylist.size() - 1 && currenttime >= duration.toSeconds()) {
                            forward();
                        }

                        if (count == arraylist.size() - 1 && currenttime >= duration.toSeconds()) {
                           replay();
                        }
                    }
                });
            } catch (Exception ex) {
            }
        }

        //*********************************************************************************************
        public void volumeSlider() {
            try {
                volslider.setValue(mediaPlayer.getVolume() * 100);
                volslider.valueProperty().addListener(new InvalidationListener() {

                    @Override
                    public void invalidated(javafx.beans.Observable observable) {
                        mediaPlayer.setVolume(volslider.getValue() / 100);
                    }
                });

            } catch (Exception ex) {
            }
        }
    //***************** method for add new songs to current list ***********
        public void addSongsTOList() {
            try {
                FileChooser fc = new FileChooser();
                List<File> chosen = fc.showOpenMultipleDialog(null);

                for (int i = 0; i < chosen.size(); i++) {
                    arraylist.add(chosen.get(i).getAbsolutePath());

                    listview.getItems().add(chosen.get(i).getName());
                }
            } catch (Exception ex) {
            }
        }
    }
    


//************************************************************************************************
    public  static  void main(String[] args) {
        launch(args);
    }

}
