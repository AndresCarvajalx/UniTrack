package com.andrescarvajald.unitrack.util;

import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.javafx.FontIcon;

public class SetButtonIcon {
    public static void set(Button button, FontAwesomeSolid icon, String colorHex) {
        FontIcon fontIcon = new FontIcon(icon);
        fontIcon.setIconColor(Paint.valueOf(colorHex));
        button.setGraphic(fontIcon);
    }

    public static void set(Button button, FontAwesomeSolid icon) {
        FontIcon fontIcon = new FontIcon(icon);
        fontIcon.setIconColor(Paint.valueOf("#FFFFFF"));
        button.setGraphic(fontIcon);
    }
}
