package ru.dsid.simulation.mutation;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.rgielen.fxweaver.core.FxWeaver;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import ru.dsid.simulation.mutation.controller.MainController;

public class JavaFxApplication extends Application {
    private ConfigurableApplicationContext applicationContext;

    @Override
    public void init() {
        final String[] args = getParameters().getRaw().toArray(new String[0]);
        this.applicationContext = new SpringApplicationBuilder()
                .sources(SimulationApplication.class)
                .run(args);
    }

    @Override
    public void start(Stage stage) {
        final FxWeaver fxWeaver = applicationContext.getBean(FxWeaver.class);
        final Parent root = fxWeaver.loadView(MainController.class);
        final Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        this.applicationContext.close();
        Platform.exit();
        System.exit(0);
    }
}
