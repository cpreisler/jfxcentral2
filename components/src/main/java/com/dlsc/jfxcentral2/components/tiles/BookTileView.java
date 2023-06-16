package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Book;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import one.jpro.routing.LinkUtil;
import org.apache.commons.lang3.StringUtils;
import org.kordamp.ikonli.javafx.FontIcon;

public class BookTileView extends PreviewTileView<Book> {

    public BookTileView(Book book) {
        super(book);

        getStyleClass().add("book-tile-view");

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));

        setButton2Text("amazon");
        Image AMAZON_IMAGE = new Image(BookTileView.class.getResource("/com/dlsc/jfxcentral2/images/amazon.png").toExternalForm());
        setButton2Graphic(new ImageView(AMAZON_IMAGE));
        LinkUtil.setLink(getButton1(), "/books/" + getData().getId());

        if (StringUtils.isNotEmpty(book.getAmazonASIN())) {
            setButton2Visible(true);
            LinkUtil.setExternalLink(getButton2(), "http://www.amazon.com/dp/" + book.getAmazonASIN(), book.getName());
        } else {
            setButton2Visible(false);
        }
    }

    @Override
    protected int getSizeReduction() {
        return 10;
    }
}
