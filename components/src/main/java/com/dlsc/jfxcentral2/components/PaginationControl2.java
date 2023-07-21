package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.components.skins.PaginationControl2Skin;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.util.Callback;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

public class PaginationControl2 extends Control {

    private static final int DEFAULT_PAGE_COUNT = 10;
    private static final int DEFAULT_CURRENT_PAGE_INDEX = 0;
    private static final int DEFAULT_MAX_PAGE_INDICATOR_COUNT = 3;

    private static final String DEFAULT_EMPTY_TEXT = "No content to display";

    public PaginationControl2() {
        getStyleClass().add("custom-pagination-control2");
    }

    public PaginationControl2(int pageCount, int pageIndex) {
        this();
        setPageCount(pageCount);
        setCurrentPageIndex(pageIndex);
    }

    public PaginationControl2(int pageCount, int pageIndex, int maxPageIndicatorCount) {
        this(pageCount, pageIndex);
        setMaxPageIndicatorCount(maxPageIndicatorCount);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new PaginationControl2Skin(this);
    }

    private final IntegerProperty pageCount = new SimpleIntegerProperty(this, "pageCount", DEFAULT_PAGE_COUNT);

    public int getPageCount() {
        return pageCount.get();
    }

    public IntegerProperty pageCountProperty() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount.set(pageCount);
    }

    private final IntegerProperty currentPageIndex = new SimpleIntegerProperty(this, "currentPageIndex", DEFAULT_CURRENT_PAGE_INDEX);

    public int getCurrentPageIndex() {
        return currentPageIndex.get();
    }

    public IntegerProperty currentPageIndexProperty() {
        return currentPageIndex;
    }

    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex.set(currentPageIndex);
    }

    private final ObjectProperty<Callback<Integer, Node>> pageFactory = new SimpleObjectProperty<>(this, "pageFactory");

    public Callback<Integer, Node> getPageFactory() {
        return pageFactory.get();
    }

    public ObjectProperty<Callback<Integer, Node>> pageFactoryProperty() {
        return pageFactory;
    }

    public void setPageFactory(Callback<Integer, Node> pageFactory) {
        this.pageFactory.set(pageFactory);
    }

    private final IntegerProperty maxPageIndicatorCount = new SimpleIntegerProperty(this, "maxPageIndicatorCount", DEFAULT_MAX_PAGE_INDICATOR_COUNT);

    public int getMaxPageIndicatorCount() {
        return maxPageIndicatorCount.get();
    }

    public IntegerProperty maxPageIndicatorCountProperty() {
        return maxPageIndicatorCount;
    }

    public void setMaxPageIndicatorCount(int maxPageIndicatorCount) {
        this.maxPageIndicatorCount.set(maxPageIndicatorCount);
    }

    private final ObjectProperty<Node> placeholder = new SimpleObjectProperty<>(this, "placeholder", createDefaultPlaceholder());

    public Node getPlaceholder() {
        return placeholder.get();
    }

    public ObjectProperty<Node> placeholderProperty() {
        return placeholder;
    }

    public void setPlaceholder(Node placeholder) {
        this.placeholder.set(placeholder);
    }

    private Node createDefaultPlaceholder() {
        Label label = new Label(DEFAULT_EMPTY_TEXT, new FontIcon(MaterialDesign.MDI_ALERT));
        label.getStyleClass().add("default-placeholder");
        return label;
    }

}