package com.dlsc.jfxcentral2.app.pages.details;

import com.dlsc.jfxcentral.data.model.RealWorldApp;
import com.dlsc.jfxcentral2.app.pages.DetailsPageBase;
import com.dlsc.jfxcentral2.model.Size;
import javafx.beans.property.ObjectProperty;

public class BookDetailsPage extends DetailsPageBase<RealWorldApp> {

    public BookDetailsPage(ObjectProperty<Size> size, String itemId) {
        super(size, RealWorldApp.class, itemId);
    }
}
