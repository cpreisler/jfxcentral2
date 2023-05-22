package com.dlsc.jfxcentral2.components.overviewbox;

import com.dlsc.jfxcentral.data.DataRepository;
import com.dlsc.jfxcentral.data.model.Tip;
import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;

import java.time.format.DateTimeFormatter;

public class TipOverviewBox extends OverviewBox<Tip> {

    private static final DateTimeFormatter DEFAULT_DATE_FORMATTER = DateTimeFormatter.ofPattern("MMM yyyy");

    private Label locationLabel;
    private Label domainLabel;
    private Label createdByLabel;
    private Label createdOnLabel;

    public TipOverviewBox(Tip tip) {
        this();
        setData(tip);
    }

    public TipOverviewBox() {
        getStyleClass().add("tip-overview-box");
        dataProperty().addListener(it -> {
            Tip tip = getData();
            if (tip != null) {
                fillData();
                markdownProperty().bind(DataRepository.getInstance().tipDescriptionProperty(tip));
            }
        });
        baseURLProperty().bind(Bindings.createStringBinding(() -> getData() == null ? "" : DataRepository.getInstance().getRepositoryDirectoryURL() + "tips/" + getData().getId(), dataProperty()));
    }

    @Override
    protected Node createTopNode() {
        Label locationHeader = new Label("LOCATION");
        locationHeader.getStyleClass().add("field-title");

        locationLabel = new Label();
        locationLabel.getStyleClass().add("field-value");
        locationLabel.setWrapText(true);

        Label domainHeader = new Label("DOMAIN");
        domainHeader.getStyleClass().add("field-title");

        domainLabel = new Label();
        domainLabel.getStyleClass().add("field-value");
        domainLabel.setWrapText(true);

        Label createdByHeader = new Label("CREATED BY");
        createdByHeader.getStyleClass().add("field-title");

        createdByLabel = new Label();
        createdByLabel.getStyleClass().addAll("field-value", "created-by-label");
        createdByLabel.setWrapText(true);

        Label createdOnHeader = new Label("CREATED ON");
        createdOnHeader.getStyleClass().add("field-title");

        createdOnLabel = new Label();
        createdOnLabel.getStyleClass().add("field-value");
        fillData();

        if (!isSmall()) {
            GridPane gridPane = new GridPane();
            gridPane.getStyleClass().add("top-grid");
            for (int i = 0; i < 4; i++) {
                ColumnConstraints columnConstraints = new ColumnConstraints();
                columnConstraints.setHalignment(HPos.LEFT);
                columnConstraints.setHgrow(Priority.ALWAYS);
                gridPane.getColumnConstraints().add(columnConstraints);
            }
            for (int i = 0; i < 2; i++) {
                RowConstraints rowConstraints = new RowConstraints();
                rowConstraints.setValignment(VPos.TOP);
                gridPane.getRowConstraints().add(rowConstraints);
            }
//            gridPane.add(locationHeader, 0, 0);
//            gridPane.add(locationLabel, 0, 1);
//            gridPane.add(domainHeader, 1, 0);
//            gridPane.add(domainLabel, 1, 1);
//            gridPane.add(createdByHeader, 2, 0);
//            gridPane.add(createdByLabel, 2, 1);
            gridPane.add(createdOnHeader, 0, 0);
            gridPane.add(createdOnLabel, 0, 1);
            return gridPane;
        } else {
            VBox topBox = new VBox(
//                    locationHeader,
//                    locationLabel,
//                    domainHeader,
//                    domainLabel,
//                    createdByHeader,
//                    createdByLabel,
                    createdOnHeader,
                    createdOnLabel
            );
            createdOnLabel.getStyleClass().add("last");
            topBox.getStyleClass().add("top-box");
            return topBox;
        }
    }

    private void fillData() {
        Tip tip = getData();
        if (tip != null) {
//            locationLabel.setText(tip.getLocation());
//            domainLabel.setText(tip.getDomain());
//            createdByLabel.setText(tip.getCompany());
            if (tip.getCreatedOn() != null) {
                createdOnLabel.setText(tip.getCreatedOn().format(DEFAULT_DATE_FORMATTER));
            }
            markdownProperty().bind(DataRepository.getInstance().tipDescriptionProperty(tip));
        } else {
            locationLabel.setText("");
            domainLabel.setText("");
            createdByLabel.setText("");
            createdOnLabel.setText("");
            setMarkdown("");
        }
    }
}
