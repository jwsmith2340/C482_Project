module com.c482.c482 {
    requires javafx.controls;
    requires javafx.fxml;

    exports main;
    opens main to javafx.fxml;
    exports Controllers;
    opens Controllers to javafx.fxml;
    exports Model;
    opens Model to javafx.fxml;
}

