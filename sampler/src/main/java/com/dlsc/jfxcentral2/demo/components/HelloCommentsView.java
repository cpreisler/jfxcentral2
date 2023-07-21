package com.dlsc.jfxcentral2.demo.components;

import com.dlsc.jfxcentral2.components.CommentsView;
import com.dlsc.jfxcentral2.components.SizeComboBox;
import com.dlsc.jfxcentral2.demo.JFXCentralSampleBase;
import com.dlsc.jfxcentral2.model.Badge;
import com.dlsc.jfxcentral2.model.Size;
import com.dlsc.jfxcentral2.model.User;
import com.dlsc.jfxcentral2.utils.IkonUtil;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class HelloCommentsView extends JFXCentralSampleBase {

    private CommentsView commentsView;

    @Override
    protected Region createControl() {

        commentsView = new CommentsView();
        commentsView.setTitle("WHAT PEOPLE THINK");
        User currentUser = new User(
                "0",
                "Dirk Lemmermann",
                new Image(getClass().getResource("/com/dlsc/jfxcentral2/demo/components/images/person-avatar.png").toExternalForm()),
                List.of(new Badge("Champion", IkonUtil.champion),
                        new Badge("Rockstar", IkonUtil.rockstar))
        );
        commentsView.setUser(currentUser);

        StackPane.setAlignment(commentsView, Pos.TOP_CENTER);
        ScrollPane scrollPane = new ScrollPane(new StackPane(commentsView));
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        return scrollPane;
    }

    @Override
    public Node getControlPanel() {

        SizeComboBox sizeComboBox = new SizeComboBox(Size.SMALL);
        commentsView.sizeProperty().bind(sizeComboBox.valueProperty());

        return new VBox(10, sizeComboBox);
    }

    @Override
    public String getSampleName() {
        return "CommentsView";
    }
}
