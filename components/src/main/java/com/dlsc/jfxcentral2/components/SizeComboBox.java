package com.dlsc.jfxcentral2.components;

import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

public class SizeComboBox extends ComboBox<Size> {
    public SizeComboBox() {
       this(Size.LARGE);
    }

    public SizeComboBox(Size size) {
        getItems().addAll(Size.values());
        getSelectionModel().select(size);
        setConverter(new StringConverter<>() {
            @Override
            public String toString(Size object) {
                return object.toString();
            }

            @Override
            public Size fromString(String string) {
                return null;
            }
        });
        this.size.bind(getSelectionModel().selectedItemProperty());
    }

    private final ReadOnlyObjectWrapper<Size> size = new ReadOnlyObjectWrapper<>(this, "size");

    public final ReadOnlyObjectProperty<Size> sizeProperty() {
        return size.getReadOnlyProperty();
    }

    public final Size getSize() {
        return sizeProperty().get();
    }

}
