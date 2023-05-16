package com.dlsc.jfxcentral2.components.skins;

import com.dlsc.jfxcentral2.components.PaginationControl;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;

public class PaginationControlSkin extends ControlBaseSkin<PaginationControl> {
    private PaginationControl control;

    public PaginationControlSkin(PaginationControl control) {
        super(control);
        this.control = control;

        Button btnPrev = new Button();
        btnPrev.getStyleClass().add("prev-button");
        btnPrev.setGraphic(new FontIcon(IkonUtil.arrowLeft));
        btnPrev.setFocusTraversable(false);
        BorderPane.setAlignment(btnPrev, Pos.CENTER);

        Label pageInformationLabel = new Label();
        pageInformationLabel.getStyleClass().add("page-information-label");

        pageInformationLabel.textProperty().bind(
                control.currentPageIndexProperty().add(1)
                        .asString()
                        .concat(control.separatorTextProperty())
                        .concat(control.pageCountProperty())
        );

        BorderPane.setAlignment(pageInformationLabel, Pos.CENTER);

        Button btnNext = new Button();
        btnNext.getStyleClass().add("next-button");
        btnNext.setGraphic(new FontIcon(IkonUtil.arrowRight));
        BorderPane.setAlignment(btnNext, Pos.CENTER);
        btnNext.setFocusTraversable(false);

        BorderPane controlBox = new BorderPane();
        controlBox.setLeft(btnPrev);
        controlBox.setRight(btnNext);
        controlBox.setCenter(pageInformationLabel);

        controlBox.getStyleClass().add("control-box");
        getChildren().setAll(controlBox);

        btnPrev.setOnAction(e -> {
            int currentPageIndex = control.getCurrentPageIndex();
            if (currentPageIndex > 0) {
                control.setCurrentPageIndex(currentPageIndex - 1);
            }
        });

        btnNext.setOnAction(e -> {
            int currentPageIndex = control.getCurrentPageIndex();
            if (currentPageIndex < control.getPageCount() - 1) {
                control.setCurrentPageIndex(currentPageIndex + 1);
            }
        });

        BorderPane contentPane = new BorderPane();
        contentPane.getStyleClass().add("content-pane");

        StackPane contentPaneCenter = new StackPane();
        contentPane.setCenter(contentPaneCenter);
        BorderPane.setAlignment(contentPaneCenter, Pos.TOP_CENTER);
        contentPaneCenter.setMaxHeight(Region.USE_PREF_SIZE);
        contentPaneCenter.getStyleClass().add("content-pane-center");

        control.currentPageIndexProperty().addListener((ob, ov, nv) -> {
            if (nv.intValue() < 0 || nv.intValue() > control.getPageCount() - 1) {
                return;
            }
            btnPrev.setDisable(nv.intValue() == 0);
            btnNext.setDisable(nv.intValue() == control.getPageCount() - 1);
            if (control.getPageFactory() != null) {
                control.requestFocus();
                contentPaneCenter.getChildren().setAll(control.getPageFactory().call(nv.intValue()));
            }
        });

        control.pageCountProperty().addListener((ob, ov, nv) -> {
            if (nv.intValue() < 0) {
                return;
            }
            btnPrev.setDisable(control.getCurrentPageIndex() == 0);
            btnNext.setDisable(control.getCurrentPageIndex() == control.getPageCount() - 1);
        });
        int pageIndex = control.getCurrentPageIndex();
        if (pageIndex >= 0 && control.getPageFactory() != null) {
            contentPaneCenter.getChildren().setAll(control.getPageFactory().call(pageIndex));
        }

        btnPrev.setDisable(pageIndex == 0);

        BorderPane.setAlignment(controlBox, Pos.BOTTOM_CENTER);
        contentPane.setBottom(controlBox);

        getChildren().setAll(contentPane);
    }

}
