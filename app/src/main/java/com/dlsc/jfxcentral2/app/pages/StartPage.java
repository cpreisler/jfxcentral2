package com.dlsc.jfxcentral2.app.pages;

import com.dlsc.jfxcentral2.components.HomePageTopView;
import com.dlsc.jfxcentral2.components.TopMenuBar;
import com.dlsc.jfxcentral2.components.VideoGalleryView;
import com.dlsc.jfxcentral2.components.WebsiteChangesView;
import com.dlsc.jfxcentral2.components.WeekLinksLiteView;
import com.dlsc.jfxcentral2.model.DateQuickLink;
import com.dlsc.jfxcentral2.model.ImageQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.model.Video;
import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;
import javafx.scene.image.Image;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StartPage extends PageBase {

    public StartPage(ObjectProperty<Size> size) {
        super(size, TopMenuBar.Mode.DARK);
    }

    @Override
    public String title() {
        return "JFXCentral";
    }

    @Override
    public String description() {
        return "A central place for anything related to JavaFX.";
    }

    @Override
    public Node content() {
        // HomePage TopView
        HomePageTopView homePageTopView = new HomePageTopView();
        homePageTopView.sizeProperty().bind(sizeProperty());

        // links of the Week
        WeekLinksLiteView weekLinksLiteView = new WeekLinksLiteView();
        weekLinksLiteView.sizeProperty().bind(sizeProperty());
        weekLinksLiteView.setMdString(createFakeLOTWText());

        // website changes
        WebsiteChangesView websiteChangesView = new WebsiteChangesView();
        websiteChangesView.sizeProperty().bind(sizeProperty());
        websiteChangesView.getQuickLinks().setAll(generateQuickLinks());

        // video gallery
        VideoGalleryView videoGallery = new VideoGalleryView();
        videoGallery.sizeProperty().bind(sizeProperty());
        videoGallery.getVideos().setAll(generateVideos());

        return wrapContent(homePageTopView, weekLinksLiteView, websiteChangesView, videoGallery);
    }

    private String createFakeLOTWText() {
        return "* [**Almas Baim** shared a video](https://twitter.com/AlmasBaim/status/1651351553578500097) showing a game with many subtle visual effects. Go fullscreen to see all details!\n" +
                "  * And he is also trying [to hypnotize you...](https://twitter.com/AlmasBaim/status/1649882265986424832).\n" +
                "* [**Bruno Borges** is looking into his crystal ball for 2024](https://twitter.com/brunoborges/status/1650998712339156992): \"Oracle announces FxWeb, a framework for building WebAssembly UI with JavaFX, so you can code Java in both client and server side.\"\n" +
                "* [**Christopher Schnick** is enhancing the gameplay experience of Paradox Grand Strategy games](https://twitter.com/crschnick/status/1650522935101145088) with JavaFX through the Pdx-Unlimiter and releases it with JReleaser.\n" +
                "* [**OrangoMango** is creating a chess game](https://twitter.com/orango_mango/status/1650868150836113409) and shares the code on GitHub. You can  play chess in LAN (Java sockets) and export a game to FEN and PGN.\n" +
                "* [**Pedro Duque Vieira** shared a testimonial of Fortune 500 company choosing for Java and JavaFX](https://twitter.com/P_Duke/status/1651574017193566208) (place 171) with headquarters in New York, a world leader in its sector.\n" +
                "* On Reddit a [video with 2D and 3D Pacman](https://www.reddit.com/r/Pacman/comments/12v706w/pacman_ms_pacman_2d3d_javafx/) was published.\n" +
                "* [**siedlerchr** shared a link to an excellent article explaining the concepts of Bindings](https://twitter.com/siedlerchr/status/1649518550996987911), it's from 2020 but still very valid.\n" +
                "* [**Jeannot Muller** wrote about \"JavaFX or Swing in 2023\"](https://jeannot-muller.com/javafx-or-swing-in-2023).\n" +
                "* Last week **Cédric Champeau** was included with his collection of libraries and applications for astronomy image processing in Java. This week he published a [blog post with more info and screenshots and video demos is available](https://melix.github.io/blog/2023/04-22-introducing-astro4j.html).\n" +
                "* Also last week, **Tobias Briones** was drawing mathematical flowers. This week it's [not a bird, not a cat: It's the bird cat](https://twitter.com/tobiasbriones_/status/1650900771263717377)!\n" +
                "\n";
    }

    private List<QuickLink> generateQuickLinks() {
        List<QuickLink> quickLinks = new ArrayList<>();
        if (getSize() != Size.SMALL) {
            Random random = new Random();
            int imageQuickLinkCount = random.nextInt(2) + 2;
            int dateQuickLinkCount = random.nextInt(2) + 3;
            int nullCount = 7 - imageQuickLinkCount - dateQuickLinkCount;

            //normal QuickLinks
            for (int i = 0; i < dateQuickLinkCount; i++) {
                quickLinks.add(new DateQuickLink("JDKMon", "Download", null, "xxx url...", ZonedDateTime.now().plusDays(i)));
            }

            //image QuickLinks
            for (int i = 0; i < imageQuickLinkCount; i++) {
                String imgUrl = "/com/dlsc/jfxcentral2/test/images/" + (getSize() == Size.LARGE ? "quick-link-lg" : "website-changes-view-md") + i + ".png";
                quickLinks.add(new ImageQuickLink(ImageQuickLink.class.getResource(imgUrl).toExternalForm()));
            }

            //empty QuickLinks
            for (int i = 0; i < nullCount; i++) {
                quickLinks.add(null);
            }
            Collections.shuffle(quickLinks);
        }else { //small size

            //normal QuickLinks
            for (int i = 0; i < 3; i++) {
                quickLinks.add(new DateQuickLink("JDKMon", "Download", null, "xxx url...", ZonedDateTime.now().plusDays(i)));
            }
        }

        return quickLinks;
    }

    private List<Video> generateVideos() {
        List<Video> list = new ArrayList<>();
        for (int i = 0; i < 18; i++) {
            Video video = new Video(
                    (i+1)+" Having Fun with Java and JavaFX on the Raspberry Pi | Frank Delporte @ JFX Days 2020",
                    "Java, JavaFX and a Raspberry Pi are an ideal combination for any project where you want to connect software and hardware (LEDs, buttons, sensors...) with a beautiful, easy-to-use user interface. In this talk, we will go through the process of building a JavaFX touchscreen user interface to control a relay board and an Arduino with LED strips that were needed to solve the problem of getting my son to the dinner table while he is playing the drums...This includes selecting a Java JDK for Raspberry Pi, setting up a Mosquitto queue, programming the Arduino, understanding the GPIO's on the Raspberry Pi, including a web server, Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut ",
                    new Image(VideoGalleryView.class.getResource("/com/dlsc/jfxcentral2/demoimages/video-thumbnail-01.png").toExternalForm()),
                    "https://www.youtube.com/watch?v=jmKYWdNbty0",
                    false,
                    false,
                    5);
            list.add(video);
        }
        return list;
    }
}
