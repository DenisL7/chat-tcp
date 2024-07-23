module com.example.chattcpfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chattcpfx to javafx.fxml;
    exports com.example.chattcpfx;
}