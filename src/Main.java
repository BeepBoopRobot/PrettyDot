import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        new JFXPanel();
        Platform.runLater(Main::launch);
        Platform.runLater(Main::control);
    }

    private static int windowWidth = 1000;
    private static int windowHeight = 1000;
    private static int originX = windowWidth / 2;
    private static int originY = windowHeight / 2;
    private static Slider slid;


    private static void close() {
        System.exit(0);
    }

    private static void launch() {
        Stage stage = new Stage();
        stage.setWidth(windowWidth);
        stage.setHeight(windowHeight);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setX(500);
        stage.setY(10);
        stage.show();

        Group group = new Group();
        Scene scene = new Scene(group);
        stage.setScene(scene);
        scene.setOnKeyPressed(ke -> {
            if (ke.getCode().equals(KeyCode.ESCAPE)) {
                close();
            }
        });

        Canvas canvas = new Canvas();
        canvas.setWidth(windowWidth);
        canvas.setHeight(windowHeight);
        group.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        ArrayList<Dot> al = new ArrayList<>();

        int size = 100;
        int half = size / 2;
        int a = 0, b = half;
        for (int i = -half; i <= half; i++) {
            for (int j = a; j <= b; j++) {
                double cOff = Math.sqrt((i * i) + (j * j)); //changing this gives different shapes, idea for control?
                al.add(new Dot(i, j, cOff)); // need to think about the above line to make sure it gives the proper patter.
            }
            if (a == -half) --b;
            if (a != -half) --a;
        }
        AnimationTimer at;
        at = new AnimationTimer() {
            @Override
            public void handle(long now) {
                gc.setFill(Color.web("#191816"));
                gc.fillRect(0, 0, windowWidth, windowHeight);
                update(al, now);
                draw(gc, al);
            }
        };
        at.start();
    }

    private static void update(ArrayList<Dot> test, long now) {
        double c = slid.getValue();
        long clock = now / 1_000_0000;
        double frequency = 0.03;// this can be changed in control
        for (Dot d : test) {
            d.red = (int) (Math.sin(frequency * clock + 0 + d.cOffset) * 127 + 128);
            d.green = (int) (Math.sin(frequency * clock + Math.toRadians(120) + d.cOffset) * 127 + 128);
            d.blue = (int) (Math.sin(frequency * clock + Math.toRadians(240) + d.cOffset) * 127 + 128);
            d.xOffset = -(Math.sin(frequency * clock + d.cOffset) * c * 50); // the offset magnitude can be changed in control
            d.yOffset = (Math.cos(frequency * clock + d.cOffset) * c * 50);
        }

    }

    private static void draw(GraphicsContext gc, ArrayList<Dot> dot) {
        for (Dot d : dot) {
            gc.setFill(Color.rgb(d.red, d.green, d.blue));
            gc.fillOval(d.x + d.xOffset + originX - 8, d.y + d.yOffset + originY - 8, 8, 8);
        }
    }

    private static void control() {
        Stage stage = new Stage();
        stage.setWidth(350);
        stage.setHeight(500);
        stage.setOnCloseRequest(we -> close());
        stage.setTitle("Control");
        stage.setX(1500);
        stage.show();

        Group group = new Group();
        Scene scene = new Scene(group);
        stage.setScene(scene);

        VBox vb = new VBox();
        vb.setPrefWidth(350);
        vb.setPrefHeight(500);
        vb.setSpacing(0);
        vb.setAlignment(Pos.CENTER);
        group.getChildren().add(vb);

        slid = new Slider(0, 1, 0.1);
        slid.setShowTickLabels(true);
        slid.setMaxWidth(300);
        vb.getChildren().add(slid);
    }
}

