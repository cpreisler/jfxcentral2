package com.dlsc.jfxcentral2.components.gridview;

import com.dlsc.jfxcentral2.utils.FXUtil;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import one.jpro.routing.CopyUtil;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.javafx.FontIcon;

public class IkonDetailView extends DetailView<Ikon> {

    private final HBox detailContent = new HBox();
    private final StackPane previewPane = new StackPane();

    private record IkonInfo(String iconLiteral, String cssCode, String javaCode, String unicode, String mavenInfo,
                            String gradleInfo) {
    }

    private final IkonInfo ikonInfo;

    public IkonDetailView(Ikon item) {
        super(item);

        getStyleClass().add("ikon-detail-view");

        if (item.getClass().getSimpleName().equals("PaymentFont")) {
            getStyleClass().add("payment-font-detail-view");
        }

        FontIcon fontIcon = new FontIcon(item);
        ikonInfo = new IkonInfo(item.getDescription(),
                "-fx-icon-code: \"" + item.getDescription() + "\";",
                item.getClass().getSimpleName() + "." + fontIcon.getIconCode(),
                "\\u" + Integer.toHexString(item.getCode()),
                IkonliPackUtil.getInstance().getMavenDependency(item),
                IkonliPackUtil.getInstance().getGradleDependency(item));

        previewPane.getChildren().setAll(fontIcon);
        previewPane.getStyleClass().add("ikon-preview-wrapper");
        HBox.setHgrow(previewPane, Priority.ALWAYS);

        Node infoNode = createInfoNode();
        StackPane.setAlignment(infoNode, Pos.CENTER);
        detailContent.getChildren().setAll(previewPane, infoNode);
        detailContent.getStyleClass().add("detail-content");
        getChildren().setAll(detailContent);
    }

    private FlowPane createInfoNode() {
        FlowPane flowPane = new FlowPane();
        flowPane.getStyleClass().add("ikon-info-grid-pane");
        HBox.setHgrow(flowPane, Priority.ALWAYS);

        addRow(flowPane, "Icon Literal:", ikonInfo.iconLiteral());
        addRow(flowPane, "CSS Code:", ikonInfo.cssCode());
        addRow(flowPane, "Java Code:", ikonInfo.javaCode());
        addRow(flowPane, "Unicode:", ikonInfo.unicode());
        addRow(flowPane, "Maven:", ikonInfo.mavenInfo());
        addRow(flowPane, "Gradle :", ikonInfo.gradleInfo());
        return flowPane;
    }

    private void addRow(FlowPane flowPane, String title, String contentText) {
        Label titleLabel = new Label(title);
        titleLabel.getStyleClass().addAll("title");
        titleLabel.managedProperty().bind(titleLabel.visibleProperty());

        Button button = new Button();
        button.setGraphic(new FontIcon(IkonUtil.copy));
        button.getStyleClass().addAll("fill-button", "copy-button");
        button.managedProperty().bind(button.visibleProperty());

        TextField textField = new TextField(contentText);
        textField.setFocusTraversable(false);
        textField.setEditable(false);
        textField.setContextMenu(null);
        textField.managedProperty().bind(textField.visibleProperty());
        CopyUtil.setCopyOnClick(button, contentText);

        HBox box = new HBox(titleLabel, textField, button);
        box.getStyleClass().add("row-box");
        flowPane.getChildren().add(box);
    }
}
