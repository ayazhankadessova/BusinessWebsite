package td;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import td.monster.Monster;
import td.tower.ArcheryTower;
import td.tower.CatapultTower;
import td.tower.LaserTower;
import td.tower.Tower;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Optional;

import static javafx.application.Platform.exit;

/**
 * This class is to implement the GUI version of the tower defense.
 * 
 * For students who are curious enough, this GUI is created using JavaFX.
 * There are a few code related to multi-threading which complicates the
 * matter. It is not a typical way that a GUI program should be written.
 * We did it this way because we want to make Display itself to be generic.
 * 
 * We have also used an inner class. The purpose of inner class is for encapsulation.
 */
public class GUIDisplay extends Application implements Displayable {
    private DisplayBlock[][] displayBlocks = new DisplayBlock[Game.HEIGHT][Game.WIDTH];
    private Game game;
    private Object lock;
    private boolean controlTime;
    private boolean gameOver;
    private Button nextButton;
    private Label scoreBox;

    private static final String BUILDING_OPTION[] = {
         "1. ArcheryTower ($5); ",
         "2. LaserTower ($7);", 
         "3. CatapultTower ($7)"
    };


    public static void main(String[] args) {
        System.out.println("Do you want to start the GUI version? (Y/N)");
        if (new Scanner(System.in).next().equalsIgnoreCase("Y"))
            launch(args);
        else
            ConsoleDisplay.main(args);
    }

    private static ImageView getImageView(Block b) {
        try {
            if (b == null)
                return null;
            ImageView result;
            if (b.getSymbol() >= '0' && b.getSymbol() <= '9')
                result = new ImageView(new Image("res/M.png"));
            else
                result = new ImageView(new Image("res/" + b.getSymbol() + ".png"));

        result.setPreserveRatio(true);
        result.setFitHeight(40);
        return result;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    @Override
    public void start(Stage primaryStage) {
        gameOver = false;
        lock = new Object();

        SplitPane root = new SplitPane();
        GridPane gp = new GridPane();
        FlowPane flowPane = new FlowPane();
        root.setOrientation(Orientation.HORIZONTAL);
        root.setDividerPosition(0,0.8);
        root.getItems().add(gp);
        root.getItems().add(flowPane);
        Scene scene = new Scene(root, 1000, 400);

        for (int row = 0; row < Game.HEIGHT; row++)
            for (int col = 0; col < Game.WIDTH; col++) {
                displayBlocks[row][col] = new DisplayBlock(row, col);
                gp.add(displayBlocks[row][col], col, row);
            }

        Label home = new Label("Home");
        home.setAlignment(Pos.BOTTOM_CENTER);
        home.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        home.setPrefSize(120,60*4);
        gp.add(home, 12, 0, 1,4);


        primaryStage.setTitle("Tower Defense Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        game = new Game(this);
        Button b = new Button("Start");
        b.setVisible(true);
        b.setOnAction(event -> {
            b.setVisible(false);
            Thread th = new Thread() {
                @Override
                public void run() {
                    game.run();
                }};
            th.setDaemon(true);
            th.start();
            controlTime = false;
        });

        nextButton = new Button("Next");
        nextButton.setVisible(true);
        nextButton.setOnAction(event -> {
            synchronized (lock) {
                controlTime = false;
                lock.notifyAll();
                refresh();
            }
        });

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setPrefSize(200,200);
        flowPane.getChildren().add(textArea);

        flowPane.getChildren().add(b);
        flowPane.getChildren().add(nextButton);
        PrintStream p = new PrintStream(new CustomOutputStream(textArea));
        System.setOut(p);


        scoreBox = new Label();
        scoreBox.setPrefSize(200,50);
        flowPane.getChildren().add(scoreBox);
        scoreBox.setText(String.format("Score: %d Money: %d", game.getScore(), game.getMoney()));
    }

    @Override
    public void display() {
        for (int r = 0; r < Game.HEIGHT; r++)
            for (int c = 0; c < Game.WIDTH; c++) {
                Block b = game.getBlockByLocation(r, c);
                if (b == null)
                    displayBlocks[r][c].setBlock(null);
                else
                    displayBlocks[r][c].setBlock(b);
            }
        Platform.runLater(() -> refresh());
    }

    private void refresh() {
        if (gameOver) {
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Game Over");
            a.setContentText("Bye");
            a.showAndWait();
            exit();
        }
        for (int r = 0; r < Game.HEIGHT; r++)
            for (int c = 0; c < Game.WIDTH; c++)
                displayBlocks[r][c].refresh();
    }

    @Override
    public void userInput() {
        controlTime = true;
        synchronized (lock) {
            while (controlTime) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    @Override
    public void gameOver() {
        gameOver = true;
    }
    class DisplayBlock extends VBox {
        private Label coordinateLabel;
        private Label imageLabel;
        static final int WIDTH = 60;
        static final int HEIGHT = 60;
        private Block block;
        private int row;
        private int col;


        public DisplayBlock(int row, int col) {
            this.row = row;
            this.col = col;
            imageLabel = new Label();
            imageLabel.setTooltip(new Tooltip(""));
            coordinateLabel = new Label(String.format("[%d,%d]", row, col));
            block = null;
            getChildren().add(coordinateLabel);
            getChildren().add(imageLabel);
            setPrefSize(WIDTH, HEIGHT);
            setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

            setOnMouseClicked(event -> {
                if (block == null) {
                    build();
                } else if (block instanceof Tower) {
                    upgrade();
                }
                refresh();
            });
        }
        private void upgrade() {
            //highlight all cells that are in range
            Tower t = (Tower) block; //safe
            for (int i = 0; i < Game.HEIGHT; i++)
                for (int j = 0; j < Game.WIDTH; j++)
                    if (t.isInRange(new Monster(i, j,0)))
                        displayBlocks[i][j].setBackground(new Background(new BackgroundFill(Color.GRAY,CornerRadii.EMPTY, Insets.EMPTY)));

            Alert d = new Alert(Alert.AlertType.CONFIRMATION);
            d.setTitle("Upgrade Tower");
            d.setContentText("Do you want to upgrade the tower? $" + t.getUpgradeCost());
            Optional<ButtonType> result = d.showAndWait();

            for (int i = 0; i < Game.HEIGHT; i++)
                for (int j = 0; j < Game.WIDTH; j++)
                    displayBlocks[i][j].setBackground(null);

            if (result.get() == ButtonType.OK && !game.upgrade(row, col)) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Upgrade Tower Fail");
                a.setContentText("Sorry, the option is invalid. Please check if you have enough money to upgrade and there is already a tower for you to upgrade.");
                a.show();

            }


        }
        private void build() {
            ChoiceDialog<String> d = new ChoiceDialog<>(BUILDING_OPTION[0], BUILDING_OPTION);
            d.setTitle("Build a Tower");
            d.setContentText("Pick a tower to build");
            Optional<String> result = d.showAndWait();
            if (result.equals(Optional.empty()))
                return;
            int i;
            for (i = 0; i < BUILDING_OPTION.length; i++)
                if (d.getSelectedItem().equals(BUILDING_OPTION[i]))
                    break;
            if (!game.build(i + 1, row, col)) {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Build Tower Fail");
                a.setContentText("Sorry, the option is invalid. Please check if you have enough money and build on a cell without any monster or tower");
                a.show();
            } else
                setBlock(game.getBlockByLocation(row, col));
        }
        public void setBlock(Block b) {

            if (b == null) {
                block = null;
            } else {
                block = b;
            }
        }
        public void refresh() {


            if (block != null) {
                imageLabel.getTooltip().setText(block.toString());
                imageLabel.setGraphic(getImageView(block));
            } else {
                imageLabel.getTooltip().setText("");
                imageLabel.setGraphic(null);
            }
            scoreBox.setText(String.format("Score: %d Money: %d", game.getScore(), game.getMoney()));
        }

    }

}




// ref: https://stackoverflow.com/questions/5107629/how-to-redirect-console-content-to-a-textarea-in-java
class CustomOutputStream extends OutputStream {
    private TextArea textArea;

    public CustomOutputStream(TextArea textArea) {
        this.textArea = textArea;
        textArea.setWrapText(true);

    }
    @Override
    public void write(int b) throws IOException {
        // redirects data to the text area
        Platform.runLater( () -> {
            textArea.setText(textArea.getText() + String.valueOf((char) b));
            textArea.setScrollTop(Double.MAX_VALUE);
        });


    }
}