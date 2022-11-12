package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * James Smith - 010649376 - C482 Performance Assessment
 *
 * FUTURE ENHANCEMENTS - I would include a decrementing function in the product screens.
 * Currently, when a part is moved from the parts table to the product table, the number
 * of parts remains the same. Decrementing the number of available parts would make sense
 * in a manufacturing business whenever a product is made, and this would be a nice feature.
 */
public class Main extends Application {

    /**
     * The start method creates the FXML loader and loads the first stage.
     * RUNTIME ERROR - I started with a runtime error due to an oversight with the getResource string.
     * I had forgotten to include the full path, specifically the /Views portion of the string, and
     * the program would throw an error. This was solved by inputting the correct path.
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/Views/Main_Page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1064, 450);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main method is the entry point of a Java application and launches the application.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}