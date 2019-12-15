package ru.dimagor555.eyesave.controllers;

import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ButtonGraphicEffects {

    public static final Paint WHITE_COLOR = Paint.valueOf(Color.WHITE.toString());

    public static void addBtnClickEffect(Button btn) {
        btn.setOnMousePressed(mouseEvent ->
                ButtonGraphicEffects.activateBtnClickEffect(true, btn));
        btn.setOnMouseReleased(mouseEvent ->
                ButtonGraphicEffects.activateBtnClickEffect(false, btn));
    }

    private static void activateBtnClickEffect(boolean activate, Button btn) {
        var defaultBorderStroke = new BorderStroke(WHITE_COLOR, BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY, BorderWidths.DEFAULT);
        var activeBorder = new Border(defaultBorderStroke);
        var inactiveBorder = Border.EMPTY;

        if (activate) {
            btn.setBorder(activeBorder);
        } else {
            btn.setBorder(inactiveBorder);
        }
    }
}
