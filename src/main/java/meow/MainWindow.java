package meow;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import meow.parser.ParsedInput;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private MeowBot mb;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaCat.png"));
    private Image meowImage = new Image(this.getClass().getResourceAsStream("/images/DaMeow.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        String greeting = "Hello! I'm Meow\n" + "What can I do for you?\n";
        dialogContainer.getChildren().add(
                DialogBox.getMeowDialog(greeting, meowImage)
        );
    }

    /** Injects the MeowBot instance */
    public void setMeowBot(MeowBot meow) {
        mb = meow;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = mb.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMeowDialog(response, meowImage)
        );
        userInput.clear();
    }
}
