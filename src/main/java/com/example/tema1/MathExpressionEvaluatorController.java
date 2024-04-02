package com.example.tema1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MathExpressionEvaluatorController {
    @FXML
    public Label exp4jResultLabel;
    @FXML
    public Label shuntingYardAlgResultLabel;
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
            double resultExp4j = MathExpressionEvaluatorService.evaluateExpressionWithExp4j(expression);
            double resultShuntingYardAlg = MathExpressionEvaluatorService.evaluateExpressionWithShuntingYardAlgorithm(expression);

            exp4jResultLabel.setText(STR."Rezultat Exp4j: \{resultExp4j}");
            shuntingYardAlgResultLabel.setText(STR."Rezultat Shunting-Yard Alg: \{resultShuntingYardAlg}");
        } else {
            exp4jResultLabel.setText("Expresia este invalida");
        }
    }

    public void appendTextToConsole(String text) {
        Platform.runLater(() -> consoleOutputArea.appendText(STR."\{text}\n"));
    }
}