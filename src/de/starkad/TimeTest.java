package de.starkad;

import de.starkad.timeTest.AbstractTimeTest;
import de.starkad.timeTest.ArrayListTime;
import de.starkad.timeTest.ArrayTime;
import de.starkad.timeTest.HashMapTime;
import de.starkad.timeTest.LinkedListTime;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Manuel Müller
 */
public class TimeTest extends Application {

    ObservableList<AbstractTimeTest> testList;

    @Override
    public void start(Stage primaryStage) {
        initfields();
        TextField iterationTextField = new TextField();
        iterationTextField.setId("Wiederholungen: ");
        TextField sizeTextField = new TextField();
        sizeTextField.setId("Größe: ");

        Button btn = new Button();
        btn.setText("Teste Zeiten");
        Rectangle rect = new Rectangle(20, 20);
        HBox inputBox = new HBox(10, new Label(iterationTextField.getId()), iterationTextField, new Label(sizeTextField.getId()), sizeTextField, btn);
        inputBox.getChildren().add(rect);
        inputBox.setAlignment(Pos.CENTER);

        TableView<AbstractTimeTest> timeTable = new TableView(testList);
        timeTable.setPlaceholder(new Label("empty"));

        btn.setOnMouseClicked((e) -> {
            rect.setFill(Paint.valueOf(Color.RED.toString()));
        });
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                rect.setFill(Paint.valueOf(Color.RED.toString()));

                testList.forEach((test) -> {

                    int iterations = 100;
                    int size = 1000;
                    try {
                        iterations = Integer.parseInt(iterationTextField.getText().trim());
                        size = Integer.parseInt(sizeTextField.getText().trim());
                    } catch (NumberFormatException ex) {
                        //simply ingnore stupid User
                    }
                    test.reset();
                    test.setTestSize(size);
                    test.setIterations(iterations);
                    test.run();
                    test.doPrint();
                });

                rect.setFill(Paint.valueOf(Color.GREEN.toString()));
                timeTable.refresh();
            }
        });

        TableColumn<AbstractTimeTest, String> testNameCol = new TableColumn<AbstractTimeTest, String>("Testname");
        testNameCol.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getClassName()));
        timeTable.getColumns().add(testNameCol);

        TableColumn<AbstractTimeTest, Long> indexCol = new TableColumn<AbstractTimeTest, Long>("Index(ns)");
        indexCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Long>(Long.valueOf(cellData.getValue().getMedianIndexedTime())));
        timeTable.getColumns().add(indexCol);

        TableColumn<AbstractTimeTest, Long> iteratorCol = new TableColumn<AbstractTimeTest, Long>("Iterator(ns)");
        iteratorCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Long>(Long.valueOf(cellData.getValue().getMedianIteratorTime())));
        timeTable.getColumns().add(iteratorCol);

        TableColumn<AbstractTimeTest, Long> lambdaCol = new TableColumn<AbstractTimeTest, Long>("Lambda(ns)");
        lambdaCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Long>(Long.valueOf(cellData.getValue().getMedianLambdaTime())));
        timeTable.getColumns().add(lambdaCol);

        TableColumn<AbstractTimeTest, Long> runCol = new TableColumn<AbstractTimeTest, Long>("Durchläufe");
        runCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Long>(Long.valueOf(cellData.getValue().getIterations())));
        timeTable.getColumns().add(runCol);

        TableColumn<AbstractTimeTest, Long> sizeCol = new TableColumn<AbstractTimeTest, Long>("Größe");
        sizeCol.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<Long>(Long.valueOf(cellData.getValue().getTestSize())));
        timeTable.getColumns().add(sizeCol);

        StackPane root = new StackPane();
        root.getChildren().add(new VBox(10, inputBox, timeTable));

        Scene scene = new Scene(root, 700, 600);

        primaryStage.setTitle("Loop Zeittest");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void initfields() {
        ArrayList<AbstractTimeTest> tmpList = new ArrayList();
        tmpList.add(new ArrayTime(1, 1));
        tmpList.add(new ArrayListTime(1, 1));
        tmpList.add(new LinkedListTime(1, 1));
        tmpList.add(new HashMapTime(1, 1));

        testList = FXCollections.observableArrayList(tmpList);

    }

}
