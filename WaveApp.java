import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.geometry.Pos;

public class WaveApp extends Application {
    Rectangle[] bars;    //The rectangles we draw
    HBox wavePane;       //Holds the rectangles
    int minBarHeight = 7; //so we can still see them and move them

    double pixelsPerUnit = 0.5;  //How tall bars look for height = 1
    double baselineY = 150;      //Vertical position of the "water line"
    double barWidth = 20;        //Width of each bar in pixels

    public static void main(String[] args) { //This is the starting point, it calls to javaFX which calls to sart() and turns the GUI on
        launch(args);
    }

    public void start(Stage primaryStage) {
        WaveSimulation.resetWave();

        wavePane = new HBox();
        wavePane.setAlignment(Pos.BOTTOM_CENTER);
        bars = new Rectangle[WaveSimulation.SIZE];

        //This creates one rectangular per bar
        for (int i = 0; i < WaveSimulation.SIZE; i++) {
            Rectangle r = new Rectangle(barWidth, 0); //Thsi creates the new rectangle shape

            int index = i; 

            //This is what allows up to click and drag on a bar to set its height 
            r.setOnMousePressed(e -> handleBarDrag(e.getY(), index));
            r.setOnMouseDragged(e -> handleBarDrag(e.getY(), index));

            bars[i] = r; 
            wavePane.getChildren().add(r);
            WaveSimulation.setHeight(i, i * 2);
        }
        updateBarsFromModel();

        Button stepButton = new Button("Step");
        stepButton.setOnAction(e -> stepSimultion());

        // Button, reset back to flat
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(e -> {
            WaveSimulation.resetWave();
            updateBarsFromModel();
        });

        //Button, for saving a file
        Button saveButton = new Button("Save");
        saveButton.setOnAction(e -> {
            WaveSimulation.saveToFile("wave.txt");
        });

        //Button for loading a file
        Button loadButton = new Button("Load");
        loadButton.setOnAction(e -> {
            WaveSimulation.loadFromFile("wave.txt");
            updateBarsFromModel();
        });


        //Put buttons in a row
        HBox buttonBox = new HBox(10, stepButton, resetButton, saveButton, loadButton);
        buttonBox.setAlignment(Pos.CENTER);

        //Main layout, bars on top, buttons under them 
        VBox root = new VBox(10, wavePane, buttonBox);
        root.setAlignment(Pos.BOTTOM_CENTER);

        Scene scene = new Scene(root,
            WaveSimulation.SIZE * (int) barWidth + 40,
            380);

        primaryStage.setTitle("Simple 1D Wave");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //When the user clicks/drags on a bar, set the height
    void handleBarDrag(double mouseY, int index) {
        double dyPixels = baselineY - mouseY;   //positive if above baseline
        double value = dyPixels / pixelsPerUnit;

        if (value < 0) {
            value = 0;
        }

        WaveSimulation.setHeight(index, value);
        updateBarsFromModel();
    }

    // Copy heights from WaveSimulation into the rectangles
    void updateBarsFromModel() {
        for (int i = 0; i < bars.length; i++) {
            double value = WaveSimulation.getHeight(i);
            double hPixels = value * pixelsPerUnit;

            if (hPixels < 0) {
                hPixels = 0;
            }

            bars[i].setHeight(hPixels + minBarHeight);

        }
    }

    //Methods
    void stepSimultion() {
        WaveSimulation.stepWave();
        updateBarsFromModel();
    }
}