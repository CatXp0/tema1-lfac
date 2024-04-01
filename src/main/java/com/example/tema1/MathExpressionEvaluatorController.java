package com.example.tema1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MathExpressionEvaluatorController {
    @FXML
    public Label resultLabel;
    @FXML
    public Button evaluateButton;
    @FXML
    public TextField inputField;
    @FXML
    private TextArea consoleOutputArea;

    @FXML
    protected void onEvaluateButtonClick() {
        String expression = inputField.getText();

        if (MathExpressionEvaluatorService.validateExpression(expression)) {
            double result = MathExpressionEvaluatorService.evaluateExpression(expression);

            resultLabel.setText(STR."Rezultat: \{result}");
        } else {
            resultLabel.setText("Expresia este invalida");
        }
    }

    public void appendTextToConsole(String text) {
        Platform.runLater(() -> consoleOutputArea.appendText(STR."\{text}\n"));
    }
}