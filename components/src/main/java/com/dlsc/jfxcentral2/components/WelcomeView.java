package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.utils.JFXCentralUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class WelcomeView extends PaneBase {

    private final VBox labelBox;
    private final FlowPane flowPane;

    public WelcomeView() {
        getStyleClass().add("welcome-view");

        labelBox = new VBox();
        labelBox.getStyleClass().add("label-box");
        Label label1 = new Label("Home to anything");
        label1.getStyleClass().add("home-label");
        CustomImageView fxLogo = new CustomImageView();
        fxLogo.getStyleClass().add("fx-logo");
        Label label2 = new Label("related", fxLogo);
        label2.getStyleClass().add("related-label");
        Label label3 = new Label("JFX Central is an opensource project you can find on GitHub, with the content in a separate project. Feel free to add your project, documentation, book, etc. via a pull-request!");
        label3.getStyleClass().add("description-label");
        label3.setWrapText(true);
        labelBox.getChildren().addAll(label1, label2, label3);

        flowPane = new FlowPane();
        flowPane.getStyleClass().add("flow-pane");
        Button jfxCentralButton = new Button("jfxcentral");
        jfxCentralButton.setGraphic(new FontIcon(MaterialDesign.MDI_GITHUB_CIRCLE));
        jfxCentralButton.getStyleClass().addAll("transparent-button", "jfxcentral-button");
        jfxCentralButton.setOnAction(event -> JFXCentralUtil.run(onJFXCentral));

        Button jfxcentralDataButton = new Button("jfxcentral-data", new FontIcon(MaterialDesign.MDI_GITHUB_CIRCLE));
        jfxcentralDataButton.getStyleClass().addAll("transparent-button", "jfxcentral-data-button");
        jfxcentralDataButton.setOnAction(event -> JFXCentralUtil.run(onJFXCentralData));

        Button installLocallyButton = new Button("Install Locally",new FontIcon(MaterialDesign.MDI_ARROW_DOWN));
        installLocallyButton.getStyleClass().addAll("fill-button", "install-button");
        installLocallyButton.setOnAction(event -> JFXCentralUtil.run(onInstallLocally));

        Button openJFXProjectButton = new Button("OpenJFX project", new FontIcon(MaterialDesign.MDI_ARROW_TOP_RIGHT));
        openJFXProjectButton.getStyleClass().addAll("fill-button", "openjfx-button");
        openJFXProjectButton.setOnAction(event -> JFXCentralUtil.run(onOpenJFXProject));

        flowPane.getChildren().addAll(jfxCentralButton, jfxcentralDataButton, installLocallyButton, openJFXProjectButton);

        layoutBySize();
        sizeProperty().addListener((ob, ov, nv) -> layoutBySize());
    }

    private void layoutBySize() {
        if (isLarge()) {
            HBox box = new HBox();
            box.getStyleClass().add("content");
            box.getChildren().setAll(labelBox, flowPane);
            getChildren().add(box);
        }else {
            VBox box = new VBox();
            box.getStyleClass().add("content");
            box.getChildren().setAll(labelBox, flowPane);
            getChildren().add(box);
        }
    }

    private final ObjectProperty<Runnable> onInstallLocally = new SimpleObjectProperty<>(this, "onInstallLocally");

    public Runnable getOnInstallLocally() {
        return onInstallLocally.get();
    }

    public ObjectProperty<Runnable> onInstallLocallyProperty() {
        return onInstallLocally;
    }

    public void setOnInstallLocally(Runnable onInstallLocally) {
        this.onInstallLocally.set(onInstallLocally);
    }

    private final ObjectProperty<Runnable> onOpenJFXProject = new SimpleObjectProperty<>(this, "onOpenJFXProject");

    public Runnable getOnOpenJFXProject() {
        return onOpenJFXProject.get();
    }

    public ObjectProperty<Runnable> onOpenJFXProjectProperty() {
        return onOpenJFXProject;
    }

    public void setOnOpenJFXProject(Runnable onOpenJFXProject) {
        this.onOpenJFXProject.set(onOpenJFXProject);
    }

    private final ObjectProperty<Runnable> onJFXCentral = new SimpleObjectProperty<>(this, "onJFXCentral");

    public Runnable getOnJFXCentral() {
        return onJFXCentral.get();
    }

    public ObjectProperty<Runnable> onJFXCentralProperty() {
        return onJFXCentral;
    }

    public void setOnJFXCentral(Runnable onJFXCentral) {
        this.onJFXCentral.set(onJFXCentral);
    }

    private final ObjectProperty<Runnable> onJFXCentralData = new SimpleObjectProperty<>(this, "onJFXCentralData");

    public Runnable getOnJFXCentralData() {
        return onJFXCentralData.get();
    }

    public ObjectProperty<Runnable> onJFXCentralDataProperty() {
        return onJFXCentralData;
    }

    public void setOnJFXCentralData(Runnable onJFXCentralData) {
        this.onJFXCentralData.set(onJFXCentralData);
    }
}
