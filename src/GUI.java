import javafx.application.Application;

import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GUI extends Application {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + Interpreter.KEYWORD_REG_EX + ")"
                    + "|(?<SEMICOLON>" + Interpreter.SEMICOLON_REG_EX + ")"
                    + "|(?<COMMENT>" + Interpreter.COMMENT_REG_EX + ")");



    public void show() { launch(); } // launch GUI

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Bare Bones IDE");

        CodeArea codeEditor = new CodeArea();
        codeEditor.setMaxWidth(WIDTH / 2);
        codeEditor.setMinWidth(WIDTH / 2);
        codeEditor.setParagraphGraphicFactory(LineNumberFactory.get(codeEditor));  // Add line numbers
        loadFileIntoEditor(new File("./resources/multiply.txt"), codeEditor);

        codeEditor.setOnKeyTyped(new EventHandler<>() {
            @Override
            public void handle(KeyEvent event) {
                codeEditor.setStyleSpans(0, computeHighlighting(codeEditor.getText()));
            }
        });

        Button btnRun = new Button();
        btnRun.setText("Run");
        btnRun.setOnAction(new EventHandler<>() {
            @Override public void handle(ActionEvent event) {
                try
                {
                    Interpreter interpreter = new Interpreter(codeEditor.getText());
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
                loadFileIntoEditor(file, codeEditor);  // Load the specified file into the editor and set syntax highlighting
            }
        });

        VBox rightColumn = new VBox();
        rightColumn.setSpacing(10);
        rightColumn.getChildren().addAll(btnRun, btnLoad);  // Add run and load buttons to right hand column

        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.getChildren().addAll(codeEditor, rightColumn);  // Add code editor on left and right column on right

        Scene scene = new Scene(hbox, WIDTH, HEIGHT);
        scene.getStylesheets().add("syntaxHighlighting.css");  // Load the syntax highlighting stylesheet
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void loadFileIntoEditor(File file, CodeArea codeEditor) {
        if (file != null)
        {



            String code = "";
            try (BufferedReader reader = new BufferedReader(new FileReader(file)))
            {
                String line;
                while ((line = reader.readLine()) != null) {
                    Pattern commentPattern = Pattern.compile(Interpreter.COMMENT_REG_EX);
                    Matcher commentMatcher = commentPattern.matcher(line);
                    if (!commentMatcher.matches())
                    {
                        code += line.toLowerCase() + "\n";
                    }
                    else
                    {
                        code += line + "\n";
                    }
                }
                codeEditor.replaceText(code);
                codeEditor.setStyleSpans(0, computeHighlighting(codeEditor.getText()));
            }
            catch (IOException e)
            {
                codeEditor.replaceText("CANNOT LOAD FILE");
            }
        }
    }


    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();

        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("SEMICOLON") != null ? "semicolon" :
                                    matcher.group("COMMENT") != null ? "comment" :
                                            null;
            assert styleClass != null;

            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

}
