package com.example.colearn.utils;

import android.graphics.Rect;

import com.example.colearn.R;

import java.util.ArrayList;
import java.util.List;

import me.samlss.timomenu.TimoItemViewParameter;

/**
 * @author SamLeung
 * @e-mail samlssplus@gmail.com
 * @github https://github.com/samlss
 * @description helper of menu.
 */
public class MenuHelper {
    public static int ROW_TEXT[][] = {
            {R.string.add_event, R.string.check_in, R.string.planting, R.string.camera},
            {},
    };


    private MenuHelper() {

    }

    public static List<TimoItemViewParameter> getTopList(int itemWidth) {
        List<TimoItemViewParameter> listTop = new ArrayList<>();
        TimoItemViewParameter addEvent = getTimoItemViewParameter(itemWidth, R.mipmap.add_event,
                R.mipmap.add_event, R.string.add_event, R.color.black,
                R.color.black);

        TimoItemViewParameter checkIn = getTimoItemViewParameter(itemWidth, R.mipmap.clock_in,
                R.mipmap.clock_in, R.string.check_in, R.color.black,
                R.color.black);
        TimoItemViewParameter planting = getTimoItemViewParameter(itemWidth, R.mipmap.planting,
                R.mipmap.planting, R.string.planting, R.color.black,
                R.color.black);

        TimoItemViewParameter camera = getTimoItemViewParameter(itemWidth, R.mipmap.camera,
                R.mipmap.camera, R.string.camera, R.color.black,
                R.color.black);


        listTop.add(addEvent);
        listTop.add(checkIn);
        listTop.add(planting);
        listTop.add(camera);
        return listTop;
    }

    public static List<TimoItemViewParameter> getBottomList(int itemWidth) {
        List<TimoItemViewParameter> listBottom = new ArrayList<>();
        return listBottom;
    }

    public static TimoItemViewParameter getTimoItemViewParameter(int itemWidth,
                                                                 int normalImageRes,
                                                                 int highlightImageRes,
                                                                 int normalTextRes,
                                                                 int normalTextColorRes,
                                                                 int highlightTextColorRes) {
        return new TimoItemViewParameter.Builder()
                .setWidth(itemWidth)
                .setImageHeight(130)
                .setImageWidth(130)
                .setImagePadding(new Rect(0, 10, 0, 10))
                .setTextPadding(new Rect(0, 0, 0, 0))
                .setNormalImageRes(normalImageRes)
                .setHighlightedImageRes(highlightImageRes)
                .setNormalTextRes(normalTextRes)
                .setNormalTextColorRes(normalTextColorRes)
                .setHighlightedTextColorRes(highlightTextColorRes)
                .build();

    }


}
