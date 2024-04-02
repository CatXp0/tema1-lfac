package com.tema1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class MathExpressionEvaluatorFX extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // problema 2
        FXMLLoader fxmlLoader = new FXMLLoader(MathExpressionEvaluatorFX.class.getResource("tema1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Evaluator de expresii matematice");
        stage.setScene(scene);
        stage.show();

        MathExpressionEvaluatorController controller = fxmlLoader.getController();

        // problema 1 - citire din consola - rezultatul va fi afisat si intr-un text field din gui.
        startConsoleInputThread(controller);
    }

    public static void main(String[] args) {
        launch();
    }

    private void startConsoleInputThread(MathExpressionEvaluatorController controller) {
        Thread consoleThread = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Introduceti o expresie matematica: ");
                String input = scanner.nextLine();

                if (MathExpressionEvaluatorService.validateExpression(input)) {
                    double resultExp4j = MathExpressionEvaluatorService.evaluateExpressionWithExp4j(input);
                    double resultShuntingYard = MathExpressionEvaluatorService.evaluateExpressionWithShuntingYardAlgorithm(input);

                    System.out.println(STR."Exp4j: Rezultatul expresiei \{input} este: \{resultExp4j}");
                    System.out.println(STR."Shunting-yard alg: Rezultatul expresiei \{input} este: \{resultShuntingYard}");

                    String output = STR."Introduceti o expresie matematica: \{input}\n";
                    output += STR."Exp4j: Rezultatul expresiei \{input} este: \{resultExp4j}\n";
                    output += STR."Shunting-yard alg: Rezultatul expresiei \{input} este: \{resultShuntingYard}\n";

                    controller.appendTextToConsole(output);
                } else {
                    System.out.println(STR."Expresia \{input} este invalida, incearca din nou");
                }
            }
        });

        consoleThread.setDaemon(true);
        consoleThread.start();
    }
}