package main.caesar;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import static main.caesar.CaesarApp.mainStage;
import static main.caesar.ReadWrite.readFile;
import static main.caesar.ReadWrite.writeFile;

public class Controller implements Initializable{
    @FXML
    public ChoiceBox<String> generalModeChoiceBox;
    @FXML
    public ChoiceBox<String> decryptionModeChoiceBox;
    @FXML
    public Label pathToOriginalFileLabel;
    @FXML
    public Label pathToEncryptedFileLabel;
    @FXML
    public Label pathToExampleFileLabel;
    @FXML
    public Label offsetLabel;
    @FXML
    public Label encryptionStatus;
    @FXML
    public Label decryptionStatus;


    @FXML
    public TextField pathToOriginalFileTField;
    @FXML
    public TextField pathToEncryptedFileTField;
    @FXML
    public TextField pathToExampleFileTField;
    @FXML
    public TextField offsetTField;

    @FXML
    public TextArea textDisplay;

    @FXML
    private Button browseOriginalFileButton;
    @FXML
    private Button browseEncryptedFileButton;
    @FXML
    private Button browseExampleFileButton;

    @FXML
    private Button encryptButton;
    @FXML
    private Button decryptButton;



    private static final String CHOOSE_MODE = "Choose mode...";
    private static final String ENCRYPTION_MODE = "Encryption mode";
    private static final String DECRYPTION_MODE = "Decryption mode";
    private static final String BRUTE_FORCE = "Brute force";
    private static final String EXAMPLE_FILE = "Example file";


    private String[] generalModes = {CHOOSE_MODE, ENCRYPTION_MODE, DECRYPTION_MODE};
    private String[] decryptionModes = {BRUTE_FORCE, EXAMPLE_FILE};

    public Controller() {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generalModeChoiceBox.getItems().addAll(generalModes);
        generalModeChoiceBox.setValue(generalModes[0]);
        generalModeChoiceBox.setOnAction(this::generalModeSwitch);
        decryptionModeChoiceBox.getItems().addAll(decryptionModes);
        decryptionModeChoiceBox.setValue(decryptionModes[0]);
        decryptionModeChoiceBox.setOnAction(this::decryptionModeSwitch);
        offsetTField.setText("1");
        Alphabet.initialize();
    }

    public void generalModeSwitch(ActionEvent event) {
        switch (generalModeChoiceBox.getValue()) {

            case ENCRYPTION_MODE:
                //enabling encryption functions
                pathToOriginalFileLabel.setDisable(false);
                pathToOriginalFileTField.setDisable(false);
                browseOriginalFileButton.setDisable(false);
                encryptButton.setDisable(false);
                //disabling decryption functions
                pathToEncryptedFileLabel.setDisable(true);
                pathToEncryptedFileTField.setDisable(true);
                browseEncryptedFileButton.setDisable(true);
                decryptButton.setDisable(true);
                pathToExampleFileLabel.setDisable(true);
                pathToExampleFileTField.setDisable(true);
                browseExampleFileButton.setDisable(true);
                decryptionModeChoiceBox.setDisable(true);
                break;

            case DECRYPTION_MODE:
                //disabling encryption functions
                pathToOriginalFileLabel.setDisable(true);
                pathToOriginalFileTField.setDisable(true);
                browseOriginalFileButton.setDisable(true);
                encryptButton.setDisable(true);
                //enabling decryption functions
                pathToEncryptedFileLabel.setDisable(false);
                pathToEncryptedFileTField.setDisable(false);
                browseEncryptedFileButton.setDisable(false);
                decryptButton.setDisable(false);
                pathToExampleFileLabel.setDisable(false);
                pathToExampleFileTField.setDisable(false);
                browseExampleFileButton.setDisable(false);
                decryptionModeChoiceBox.setDisable(false);
                break;

            default:
                //disabling encryption functions
                pathToOriginalFileLabel.setDisable(true);
                pathToOriginalFileTField.setDisable(true);
                browseOriginalFileButton.setDisable(true);
                encryptButton.setDisable(true);
                //disabling decryption functions
                pathToEncryptedFileLabel.setDisable(true);
                pathToEncryptedFileTField.setDisable(true);
                browseEncryptedFileButton.setDisable(true);
                decryptButton.setDisable(true);
                pathToExampleFileLabel.setDisable(true);
                pathToExampleFileTField.setDisable(true);
                browseExampleFileButton.setDisable(true);
                decryptionModeChoiceBox.setDisable(true);
                break;
        }
    }

    public void decryptionModeSwitch(ActionEvent event) {
        System.out.println(decryptionModeChoiceBox.getValue());
    }

    public void encryptionButtonClick(ActionEvent event) throws IOException {
        Path pathToOriginalFile = Path.of(pathToOriginalFileTField.getText());
        Encryption encryption = new Encryption(readFile(pathToOriginalFile));
        int offset = Integer.parseInt(offsetTField.getText());
        String encryptedText = encryption.encryptText(offset);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save encrypted file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));
        File file = fileChooser.showSaveDialog(mainStage);
        textDisplay.setText(encryptedText);
        if (file != null) {
            try {
                writeFile(file, encryptedText);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void decryptionButtonClick(ActionEvent event) throws IOException, InterruptedException {
        Path pathToEncryptedFile = Path.of(pathToEncryptedFileTField.getText());
        Decryption decryption = new Decryption(readFile(pathToEncryptedFile));
        if (decryptionModeChoiceBox.getValue().equals(BRUTE_FORCE)) {
            double d;
            double lowerBound = 0.02;
            double upperBound = 0.1;
            for (int i = 0; i <= Alphabet.size; i++) {
                String possibleOption = decryption.decryptText(i);
                //textDisplay.setText(possibleOption);
                //decryptionStatus.setText(decryption.getSemanticPattern1() + " " + decryption.getSemanticPattern2());
                d = ((float)decryption.getSemanticPattern1() + decryption.getSemanticPattern2()) / possibleOption.length();
                if (d > lowerBound && d < upperBound) {
                    decryptionStatus.setText("Success" + " " + decryption.getSemanticPattern1() + " " + decryption.getSemanticPattern2());
                    decryptionStatus.setText(d+"");
                    textDisplay.setText(possibleOption);
                    break;
                }
            }
        }
        else {
                System.out.println("EXAMPLE");
        }
    }



    public void browseOriginalFileButtonClick(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open the file to be encrypted");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));

        File file = fileChooser.showOpenDialog(mainStage);

        pathToOriginalFileTField.setText(file.getPath());
        textDisplay.setText(readFile(file.toPath()));
    }

    public void browseEncryptedFileButtonClick(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open the file to be decrypted");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));

        File file = fileChooser.showOpenDialog(mainStage);

        pathToEncryptedFileTField.setText(file.getPath());
        textDisplay.setText(readFile(file.toPath()));
    }

    public void browseExampleFileButtonClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open the example file");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        fileChooser.setInitialDirectory(new File(currentPath));

        File file = fileChooser.showOpenDialog(mainStage);

        pathToExampleFileTField.setText(file.getPath());
    }


    public void updateDisplay(String updatedText) {
        textDisplay.setText(updatedText);
    }




}
