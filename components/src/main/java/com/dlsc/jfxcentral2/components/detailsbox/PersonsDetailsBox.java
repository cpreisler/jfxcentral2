package com.dlsc.jfxcentral2.components.detailsbox;

import com.dlsc.jfxcentral.data.model.Person;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;

import java.util.List;
import java.util.function.Consumer;

public class PersonsDetailsBox extends DetailsBoxBase<Person> {

    public PersonsDetailsBox() {
        getStyleClass().add("persons-details-box");

        setTitle("PEOPLE");
        setIkon(IkonUtil.getModelIkon(Person.class));
        setMaxItemsPerPage(Integer.MAX_VALUE);
        setOnHomepage(Person::getWebsite);
    }

    @Override
    protected List<Node> createActionButtons(Person model) {
        return List.of(createDetailsButton(model), createHomepageButton(model));
    }

    private final ObjectProperty<Consumer<Person>> onHomepage = new SimpleObjectProperty<>(this, "onHomepage");

    public Consumer<Person> getOnHomepage() {
        return onHomepage.get();
    }

    public ObjectProperty<Consumer<Person>> onHomepageProperty() {
        return onHomepage;
    }

    public void setOnHomepage(Consumer<Person> onHomepage) {
        this.onHomepage.set(onHomepage);
    }

}
