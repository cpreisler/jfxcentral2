package com.dlsc.jfxcentral2.utils;

import com.jpro.webapi.WebAPI;
import javafx.geometry.Bounds;
import javafx.scene.Node;

/**
 * Only used in web pages;
 */
public class WebAPIUtil {

    private WebAPIUtil() {
    }

    public static void executeScript(Node node, String script) {
        getWebAPI(node).executeScript(script);
    }

    public static void executeScript(WebAPI webAPI, String script) {
        webAPI.executeScript(script);
    }

    /**
     * Scrolls to the given node.
     */
    public static void scrollToNode(Node node, int offset) {
        Bounds boundsInScene = node.localToScene(node.getBoundsInLocal());
        int minY = (int) (boundsInScene.getMinY() + offset);
        executeScript(node, "window.scrollTo({ top: " + minY + ", behavior: 'smooth' });");
    }

    /**
     * Scrolls to the given node.
     */
    public static void scrollToNode(Node node) {
        scrollToNode(node, 0);
    }

    /**
     * Scrolls to the top of the page.
     * If smooth is true, it will scroll smoothly.
     *
     * @param node node is used to get the webAPI;
     */
    public static void scrollToTop(Node node, boolean smooth, int offset) {
        if (smooth) {
            executeScript(node, "window.scrollTo({ top: " + offset + ", behavior: 'smooth' });");
        } else {
            executeScript(node, "window.scrollTo(0, " + offset + ")");
        }
    }

    /**
     * Scrolls to the top of the page.
     * If smooth is true, it will scroll smoothly.
     *
     * @param node node is used to get the webAPI;
     */
    public static void scrollToTop(Node node, boolean smooth) {
        scrollToTop(node, smooth, 0);
    }

    /**
     * Scrolls to the position of the page;
     */
    public static void scrollToPosition(WebAPI webAPI, int x, int y) {
        executeScript(webAPI, "window.scrollTo(" + x + " ," + y + " )");
    }

    /**
     * Scrolls to the position of the page;
     *
     * @param node node is used to get the webAPI;
     */
    public static void scrollToPosition(Node node, int x, int y) {
        scrollToPosition(getWebAPI(node), x, y);
    }

    public static WebAPI getWebAPI(Node node) {
        return WebAPI.getWebAPI(node.getScene());
    }
    
}
