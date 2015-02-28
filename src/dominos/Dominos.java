
package dominos;

import dominos.model.DominoCycleFinder;
import dominos.model.DominoTable;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Dominos extends Application {
    
    private int requiredLength = 4;
    private Thread currentThread;
    
    private static Text cycleFoundText;
    private static Text moreCyclesText;
    private ListView<String> listView;
    
    @Override
    public void start(Stage primaryStage) {
        
        StackPane root = new StackPane();
        DominoTable dominoTable = new DominoTable();
        GridPane dominoPane = dominoTable.getRoot();

        Group glassPane = initGlassPane(dominoPane, dominoTable);
        
        Group textPane = new Group();
        
        cycleFoundText = initText("\n\n\n\n   Cycle     Found", Color.RED);
        cycleFoundText.setVisible(false);
        moreCyclesText = initText("Derivative cycle", Color.GREEN);
        moreCyclesText.setVisible(false);
        textPane.getChildren().addAll(cycleFoundText, moreCyclesText);
        
        root.getChildren().addAll(dominoPane, textPane, glassPane);
        Scene scene = new Scene(root, 520, 520);
        //scene.getStylesheets().add("/dominos/resources/style.css");
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
    Group initGlassPane(GridPane dominoPane, DominoTable dominoTable) {
        BorderPane glassPane = new BorderPane();
        
        Group group = new Group();
        VBox settingsPanel = new VBox();
        HBox textFieldPanel = new HBox(20);
        Label label = new Label("Cycle Length:");
        //label.setTextFill(Color.ALICEBLUE);
        TextField textField = new TextField();
        textFieldPanel.getChildren().addAll(label, textField);
        Button startButton = new Button("Start");
        
        listView = new ListView<>();
        startButton.setOnAction((ActionEvent event) -> {
            if(currentThread != null) {
                currentThread.interrupt();
                dominoPane.getChildren().clear();
                listView.getItems().clear();
            }
            try {
                requiredLength = Integer.parseInt(textField.getText());
            } catch (NumberFormatException e) {
                textField.clear();
                return;
            }
            currentThread = new DominoCycleFinder(dominoTable, listView, cycleFoundText, moreCyclesText, requiredLength);
            currentThread.setDaemon(true);
            currentThread.start();
        });
        listView.getItems().add("Here All Tile Chains will be stored");
        listView.setPrefSize(200, 150);
        settingsPanel.getChildren().addAll(textFieldPanel, startButton, listView);
        group.getChildren().add(settingsPanel);
        glassPane.setCenter(group);
        return group;
    }
    
    public Text initText(String text, Color color) {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(3.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        Text t = new Text();
        t.setEffect(ds);
        t.setCache(true);
        t.setX(10.0f);
        t.setY(270.0f);
        t.setFill(color);
        t.setText(text);
        t.setFont(Font.font(null, FontWeight.BOLD, 50));      
        return t;
    }
    
}