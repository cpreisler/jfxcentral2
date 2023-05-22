package com.dlsc.jfxcentral2.components.tiles;

import com.dlsc.jfxcentral.data.model.Video;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import com.jpro.webapi.WebAPI;
import one.jpro.routing.LinkUtil;
import org.kordamp.ikonli.javafx.FontIcon;
import org.kordamp.ikonli.materialdesign.MaterialDesign;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class VideoTileView extends TileView<Video> {

    public VideoTileView() {
        getStyleClass().add("video-tile-view");

        setButton1Text("PLAY");
        setButton1Graphic(new FontIcon(IkonUtil.play));

        setButton2Text("YouTube");
        setButton2Graphic(new FontIcon(MaterialDesign.MDI_YOUTUBE_PLAY));

        dataProperty().addListener(it -> {
            Video video = getData();
            setRemark(video.getMinutes() + " mins");

            if (WebAPI.isBrowser()) {
                LinkUtil.setExternalLink(getButton2(), "https://www.youtube.com/watch?v=" + video.getId());
            } else {
                getButton2().setOnAction(evt -> {
                    try {
                        Desktop.getDesktop().browse(URI.create("https://www.youtube.com/watch?v=" + video.getId()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });
    }
}
