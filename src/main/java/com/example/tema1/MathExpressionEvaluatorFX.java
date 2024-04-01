package com.example.tema1;

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

        // problema 1
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
                    double result = MathExpressionEvaluatorService.evaluateExpression(input);

                    System.out.println(STR."Rezultatul expresiei \{input} este: \{result}");

                    String output = STR."Introduceti o expresie matematica: \{input}\nRezultatul expresiei \{input} este: \{result}";
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