package ru.dsid.simulation.mutation.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.stereotype.Component;
import ru.dsid.simulation.mutation.MutationConstants;
import ru.dsid.simulation.mutation.pojo.MutationCreature;
import ru.dsid.simulation.mutation.service.MutationSimulationService;
import ru.dsid.simulation.pojo.PointObject;

@Component
@FxmlView("/main.fxml")
public class MainController {
    private final MutationSimulationService simulationService;

    @FXML
    public Canvas canvas;

    public MainController(MutationSimulationService simulationService) {
        this.simulationService = simulationService;
    }

    @FXML
    public void initialize() {
        new Thread(simulationService::start).start();
        new Thread(() -> {
            while (true) {
                Platform.runLater(this::draw);
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    private void draw() {
        final GraphicsContext context = canvas.getGraphicsContext2D();
        drawArea(context);
        drawFood(context);
        drawCreatures(context);
    }

    private void drawFood(GraphicsContext context) {
        context.setFill(Color.GREEN);
        simulationService.getFood().forEach(food -> drawCircle(context, food));
    }

    private void drawArea(GraphicsContext context) {
        context.setFill(Color.WHITE);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        context.setFill(Color.BLACK);
        context.setLineWidth(5.0);
        context.strokeRect(5, 5, canvas.getWidth() - 7.5, canvas.getHeight() - 7.5);
    }

    private void drawCreatures(GraphicsContext context) {
        simulationService.getCreatures().forEach(creature -> {
            context.setFill(getColor(creature));
            drawCircle(context, creature);
            context.setLineDashes(5, 15);
            context.setLineWidth(1.0);
            context.strokeOval(translateX(creature.getPoint().getX() - creature.getRange()),
                    translateY(creature.getPoint().getY() - creature.getRange()),
                    translateX(creature.getRange() * 2), translateY(creature.getRange() * 2));
            context.setLineDashes();
        });
    }

    private Paint getColor(MutationCreature creature) {
        return Color.color(Math.min(creature.getSize() / (MutationConstants.DEFAULT_SIZE * 2), 1.0),
                Math.min(creature.getSpeed() / (MutationConstants.DEFAULT_SPEED * 2), 1.0),
                Math.min(creature.getRange() / (MutationConstants.DEFAULT_RANGE * 2), 1.0));
    }

    private void drawCircle(GraphicsContext context, PointObject pointObject) {
        drawCircle(context, pointObject, 10);
    }

    private void drawCircle(GraphicsContext context, PointObject pointObject, double rate) {
        final double size = pointObject.getSize() * rate;
        context.fillOval(translateX(pointObject.getPoint().getX() - size / 2),
                translateY(pointObject.getPoint().getY() - size / 2),
                translateX(size), translateY(size));
    }

    private double translateX(double x) {
        return x * canvas.getWidth() / simulationService.getArea().getLength();
    }

    private double translateY(double y) {
        return y * canvas.getHeight() / simulationService.getArea().getWidth();
    }
}
