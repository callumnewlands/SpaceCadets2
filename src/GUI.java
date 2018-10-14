import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.TextArea;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GUI extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;


    public void show() { launch(); } // launch GUI

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bare Bones IDE");

        TextArea textEditor = new TextArea();
        textEditor.setMaxWidth(WIDTH / 2);

        Button btnHello = new Button();
        btnHello.setText("Run");
        btnHello.setOnAction(new EventHandler<>() {
            @Override public void handle(ActionEvent event) {
                try
                {
                    Interpreter interpreter = new Interpreter(textEditor.getText());
                    interpreter.execute();
                }
                catch (InterpreterException e)
                {
                    System.out.println(e.getMessage());
                }
            }
        });

        Button btnLoad = new Button();
        btnLoad.setText("Load File");
        btnLoad.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser chooser = new FileChooser();
                chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt"));
                chooser.setTitle("Open Text File");
                File file = chooser.showOpenDialog(primaryStage);
                if (file != null)
                {
                    String code = "";
                    try (BufferedReader reader = new BufferedReader(new FileReader(file)))
                    {
                        String line;
                        while ((line = reader.readLine()) != null)
                            code += line.toLowerCase() + "\n";
                        textEditor.setText(code);
                    }
                    catch (IOException e)
                    {
                        textEditor.setText("CANNOT LOAD FILE");
                    }
                }
            }
        });

        VBox rightColumn = new VBox();
        rightColumn.setSpacing(10);
        rightColumn.getChildren().addAll(btnHello, btnLoad);
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(textEditor, rightColumn);
        primaryStage.setScene(new Scene(hbox, WIDTH, HEIGHT));
        primaryStage.show();
    }
}
