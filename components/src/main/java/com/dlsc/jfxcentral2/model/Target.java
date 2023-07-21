package com.dlsc.jfxcentral2.model;

public enum Target {

    DESKTOP,
    BROWSER,
    MOBILE;

    public boolean isMobile() {
        return this == MOBILE;
    }

    public boolean isBrowser() {
        return this == BROWSER;
    }

    public boolean isDesktop() {
        return this == DESKTOP;
    }
}
