package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.ImageManager;
import com.dlsc.jfxcentral.data.model.Download;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;

public class DownloadTileView extends TileView<Download> {

    public DownloadTileView(Download download) {
        super(download);

        getStyleClass().add("download-tile-view");

        imageProperty().bind(ImageManager.getInstance().downloadBannerImageProperty(download));

        setButton1Text("DISCOVER");
        setButton1Graphic(new FontIcon(IkonUtil.link));

        setButton2Text("DOWNLOAD");
        setButton2Graphic(new FontIcon(IkonUtil.download));

        LinkUtil.setLink(getButton1(), "/downloads/" + download.getId());
    }
}
