package com.dlsc.jfxcentral2.components;

import com.dlsc.gemsfx.SearchTextField;
import com.dlsc.jfxcentral.data.DataRepository2;
import com.dlsc.jfxcentral.data.model.IkonliPack;
import com.dlsc.jfxcentral2.components.gridview.IkonGridView;
import com.dlsc.jfxcentral2.components.gridview.ModelGridView;
import com.dlsc.jfxcentral2.components.tiles.IkonliPackTileView;
import com.dlsc.jfxcentral2.utils.IkonliPackUtil;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.Ikon;

import java.util.Comparator;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class PacksIconsView extends PaneBase {
    private static final int SEARCH_DELAY = 200;
    private final SearchTextField searchField;
    private final StackPane topWrapper;
    private final HBox sortComboBoxWrapper;
    private final HBox scopeComboBoxWrapper;
    private final StringProperty searchText = new SimpleStringProperty(this, "searchText", "");
    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private ScheduledFuture<?> future;

    private enum Scope {
        PACKS, ICONS
    }

    private enum Sort {
        FROM_A_TO_Z, FROM_Z_TO_A
    }

    public PacksIconsView() {
        getStyleClass().addAll("packs-icons-view");

        // top
        searchField = new SearchTextField();
        searchField.textProperty().addListener((ob, ov, str) -> {
            if (future != null) {
                future.cancel(false);
            }
            future = executorService.schedule(() -> {
                if (StringUtils.equalsIgnoreCase(str, searchField.getText())) {
                    Platform.runLater(() -> searchText.set(str));
                }
            }, SEARCH_DELAY, TimeUnit.MILLISECONDS);
        });

        HBox.setHgrow(searchField, Priority.ALWAYS);

        ComboBox<Scope> scopeComboBox = initScopeComboBox();
        scopeComboBox.getStyleClass().addAll("scope-combo-box");
        scopeComboBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(scopeComboBox, Priority.ALWAYS);
        scopeComboBoxWrapper = new HBox(new Label("SCOPE"), scopeComboBox);
        scopeComboBoxWrapper.getStyleClass().addAll("combo-box-wrapper", "scope-combo-box-wrapper");
        HBox.setHgrow(scopeComboBoxWrapper, Priority.ALWAYS);

        searchField.promptTextProperty().bind(Bindings.createStringBinding(() -> {
            if (scopeComboBox.getSelectionModel().getSelectedItem() == Scope.PACKS) {
                return "Search for icon packs";
            } else {
                return "Search for icons";
            }
        }, scopeComboBox.getSelectionModel().selectedItemProperty()));

        ComboBox<Sort> sortComboBox = initSortComboBox();
        sortComboBox.getStyleClass().addAll("sort-combo-box");
        sortComboBox.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(sortComboBox, Priority.ALWAYS);
        sortComboBoxWrapper = new HBox(new Label("SORT"), sortComboBox);
        sortComboBoxWrapper.getStyleClass().addAll("combo-box-wrapper", "sort-combo-box-wrapper");
        HBox.setHgrow(sortComboBoxWrapper, Priority.ALWAYS);

        topWrapper = new StackPane();
        topWrapper.getStyleClass().add("top-wrapper");

        // center
        IkonGridView ikonGridView = createIkonGridView(searchField, scopeComboBox, sortComboBox);

        ModelGridView<IkonliPack> packGridView = createModelGridView(searchField, scopeComboBox, sortComboBox);
        StackPane gridWrapper = new StackPane(scopeComboBox.getSelectionModel().getSelectedItem() == Scope.PACKS ? packGridView : ikonGridView);
        if (scopeComboBox.getSelectionModel().getSelectedItem() == Scope.PACKS) {
            getStyleClass().add("packs");
        } else {
            getStyleClass().add("icons");
        }

        scopeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == Scope.PACKS) {
                gridWrapper.getChildren().setAll(packGridView);
                getStyleClass().remove("icons");
                if (!getStyleClass().contains("packs")) {
                    getStyleClass().add("packs");
                }
            } else {
                gridWrapper.getChildren().setAll(ikonGridView);
                getStyleClass().remove("packs");
                if (!getStyleClass().contains("icons")) {
                    getStyleClass().add("icons");
                }
            }
        });

        gridWrapper.getStyleClass().add("grid-wrapper");

        Region spacer = new Region();
        spacer.getStyleClass().add("view-spacer");

        VBox contentBox = new VBox(topWrapper, spacer, gridWrapper);
        contentBox.getStyleClass().add("content-box");
        getChildren().setAll(contentBox);
    }

    @Override
    protected void layoutBySize() {
        Pane topBox = isSmall() ? new VBox() : new HBox();
        topBox.getStyleClass().addAll("top-box");
        topBox.getChildren().addAll(searchField, scopeComboBoxWrapper, sortComboBoxWrapper);
        topWrapper.getChildren().setAll(topBox);
    }

    private ModelGridView<IkonliPack> createModelGridView(SearchTextField searchField, ComboBox<Scope> scopeComboBox, ComboBox<Sort> sortComboBox) {
        ModelGridView<IkonliPack> packGridView = new ModelGridView<>();
        packGridView.sizeProperty().bind(sizeProperty());
        packGridView.setTileViewProvider(IkonliPackTileView::new);
        packGridView.setColumns(3);
        packGridView.setRows(3);
        packGridView.managedProperty().bind(visibleProperty());
        packGridView.visibleProperty().bind(scopeComboBox.valueProperty().map(scope -> scope == Scope.PACKS));

        // packs data
        ObservableList<IkonliPack> packs = FXCollections.observableArrayList(DataRepository2.getInstance().getIkonliPacks());
        FilteredList<IkonliPack> filteredPacks = new FilteredList<>(packs);
        filteredPacks.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            String text = searchText.get().trim();
            if (StringUtils.isBlank(text)) {
                return item -> true;
            } else {
                return item -> StringUtils.containsIgnoreCase(item.getName(), text);
            }
        }, searchText));

        //sort
        SortedList<IkonliPack> sortedPacks = new SortedList<>(filteredPacks);
        sortedPacks.comparatorProperty().bind(Bindings.createObjectBinding(() -> {
            Sort sort = sortComboBox.getValue();
            if (sort == Sort.FROM_A_TO_Z) {
                return Comparator.comparing((IkonliPack pack) -> pack.getName().toLowerCase());
            } else {
                return Comparator.comparing((IkonliPack pack) -> pack.getName().toLowerCase()).reversed();
            }
        }, sortComboBox.valueProperty()));
        packGridView.setItems(sortedPacks);
        return packGridView;
    }

    private IkonGridView createIkonGridView(SearchTextField searchField, ComboBox<Scope> scopeComboBox, ComboBox<Sort> sortComboBox) {
        IkonGridView ikonGridView = new IkonGridView();
        ikonGridView.sizeProperty().bind(sizeProperty());
        ikonGridView.managedProperty().bind(visibleProperty());
        ikonGridView.visibleProperty().bind(scopeComboBox.valueProperty().map(scope -> scope == Scope.ICONS));
        ikonGridView.paginationModeProperty().bind(Bindings.createObjectBinding(() -> {
            Scope scope = scopeComboBox.getSelectionModel().getSelectedItem();
            if (scope == Scope.PACKS) {
                return IkonGridView.PaginationMode.ADVANCED;
            }
            if (isLarge()) {
                return IkonGridView.PaginationMode.ADVANCED;
            } else {
                return IkonGridView.PaginationMode.SIMPLE;
            }
        }, sizeProperty(), scopeComboBox.valueProperty()));

        // ikons data
        Set<Ikon> ikons = IkonliPackUtil.getInstance().getDataMap().keySet();
        ObservableList<Ikon> icons = FXCollections.observableArrayList(ikons);

        FilteredList<Ikon> filteredIconsList = new FilteredList<>(icons);
        filteredIconsList.predicateProperty().bind(Bindings.createObjectBinding(() -> {
            String text = searchText.get().trim();
            if (StringUtils.isBlank(text)) {
                return item -> true;
            } else {
                return item -> StringUtils.containsIgnoreCase(item.getDescription(), text);
            }
        }, searchText));

        //sort
        SortedList<Ikon> sortedList = new SortedList<>(filteredIconsList);
        sortedList.comparatorProperty().bind(Bindings.createObjectBinding(() -> {
            Sort sort = sortComboBox.getValue();
            if (sort == Sort.FROM_A_TO_Z) {
                return Comparator.comparing((Ikon ikon) -> ikon.getDescription().toLowerCase());
            } else {
                return Comparator.comparing((Ikon ikon) -> ikon.getDescription().toLowerCase()).reversed();
            }
        }, sortComboBox.valueProperty()));
        ikonGridView.setItems(sortedList);
        return ikonGridView;
    }

    private ComboBox<Sort> initSortComboBox() {
        ComboBox<Sort> comboBox = new ComboBox<>();
        comboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Sort object) {
                return object == Sort.FROM_A_TO_Z ? "From A to Z" : "From Z to A";
            }

            @Override
            public Sort fromString(String string) {
                return null;
            }
        });
        comboBox.getItems().addAll(Sort.values());
        comboBox.getSelectionModel().select(Sort.FROM_A_TO_Z);
        return comboBox;
    }

    private ComboBox<Scope> initScopeComboBox() {
        ComboBox<Scope> scopeBox = new ComboBox<>();
        scopeBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Scope object) {
                return object.name().toUpperCase();
            }

            @Override
            public Scope fromString(String string) {
                return null;
            }
        });
        scopeBox.getItems().addAll(Scope.values());
        scopeBox.getSelectionModel().select(Scope.PACKS);
        return scopeBox;
    }

}
