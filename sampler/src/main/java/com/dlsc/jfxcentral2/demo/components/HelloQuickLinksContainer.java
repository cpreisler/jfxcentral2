package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.QuickLinksContainer;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.ImageQuickLink;
import com.dlsc.jfxcentral2.model.NormalQuickLink;
import com.dlsc.jfxcentral2.model.QuickLink;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class HelloQuickLinksContainer extends JFXCentralSampleBase {

    private QuickLinksContainer containerLarge;

    @Override
    protected Region createControl() {
        containerLarge = new QuickLinksContainer();
        containerLarge.setQuickLinks(FXCollections.observableList(generateQuickLinkList(Size.LARGE)));
        ScrollPane scrollPane = new ScrollPane(new StackPane(containerLarge));
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        return scrollPane;
    }

    public List<QuickLink> generateQuickLinkList(Size size) {
        if (size != Size.SMALL) {
            return getLargeOrMediumQuickLinks(size);
        } else {
            return getSmallQuickLinks();
        }
    }

    private List<QuickLink> getSmallQuickLinks() {
        List<QuickLink> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            list.add(new NormalQuickLink("Tools", "Lorem ipsum dolor sit amet, consectetur. Lorem ipsum dolor sit amet, consectetur", IkonUtil.tool, "https://www.jfx-central.com"));
        }
        Collections.shuffle(list);
        return list;
    }

    private List<QuickLink> getLargeOrMediumQuickLinks(Size size) {
        ArrayList<QuickLink> list = new ArrayList<>();
        // Randomly generate 4 to 6 QuickLinks
        int count = new Random().nextInt(3) + 4;
        for (int i = 0; i < count; i++) {
            list.add(new NormalQuickLink("Tools", i + " Lorem ipsum dolor sit amet, consectetur. Lorem ipsum dolor sit amet, consectetur", IkonUtil.tool, "https://www.jfx-central.com"));
        }
        //Randomly generate 1~3 QuickLinks for image placeholders
        int count2 = new Random().nextInt(3) + 1;
        List<String> imageUrlList = new ArrayList<>();
        //currently there are only 3 images for testing
        for (int i = 0; i < 3; i++) {
            imageUrlList.add("/com/dlsc/jfxcentral2/demo/components/images/quick-link-" + (size == Size.LARGE ? "lg" : "md") + i + ".png");
        }
        Collections.shuffle(imageUrlList);
        imageUrlList.subList(0, count2).forEach(url -> {
            list.add(new ImageQuickLink(HelloQuickLinksContainer.class.getResource(url).toExternalForm()));
        });

        //If the above QuickLinks are less than 9, fill null
        for (int i = 0; i < 9 - count - count2; i++) {
            list.add(null);
        }
        // shuffle the links
        do {
            Collections.shuffle(list);
        } while (!checkArrayList(list));
        return list;
    }

    public boolean checkArrayList(ArrayList<QuickLink> list) {
        int rows = 3;
        int cols = 3;
        //Cannot have 3 null values or 3 QuickLinks without images in the same row
        for (int i = 0; i < rows; i++) {
            int normalView = 0;
            int imageView = 0;
            int nullCount = 0;
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j;
                if (list.get(index) == null) {
                    nullCount++;
                } else if (list.get(index) instanceof NormalQuickLink) {
                    normalView++;
                } else {
                    imageView++;
                }
            }
            if (normalView == cols || nullCount == cols || imageView == cols) {
                return false;
            }
        }
        //Cannot have 3 null values or 3 QuickLinks without images in the same row
        for (int j = 0; j < cols; j++) {
            int nullCount = 0;
            int imageView = 0;
            int normalView = 0;
            for (int i = 0; i < rows; i++) {
                int index = i * cols + j;
                if (list.get(index) == null) {
                    nullCount++;
                } else if (list.get(index) instanceof NormalQuickLink) {
                    normalView++;
                } else {
                    imageView++;
                }
            }
            if (nullCount == rows || normalView == rows || imageView == rows) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Node getControlPanel() {
        SizeComboBox sizeComboBox = new SizeComboBox();
        sizeComboBox.sizeProperty().addListener((ob, ov, nv) -> {
            containerLarge.setQuickLinks(FXCollections.observableList(generateQuickLinkList(nv)));
            containerLarge.setSize(nv);
        });

        Button button = new Button("Refresh");
        button.setOnAction(event -> {
            containerLarge.setQuickLinks(FXCollections.observableList(generateQuickLinkList(sizeComboBox.getSize())));
        });

        return new VBox(new Label("Change Size"), sizeComboBox, new Label("Refresh Links"), button);
    }

    @Override
    public String getSampleName() {
        return "QuickLinkContainer";
    }
}
