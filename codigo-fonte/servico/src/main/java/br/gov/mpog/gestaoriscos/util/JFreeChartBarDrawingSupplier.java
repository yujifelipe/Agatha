package br.gov.mpog.gestaoriscos.util;

import com.itextpdf.kernel.color.WebColors;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.jfree.chart.plot.DrawingSupplier;

public class JFreeChartBarDrawingSupplier implements DrawingSupplier {


    private static Stroke stroke = new BasicStroke();
    private static Shape shape = new Rectangle2D.Double();
    private int cursor = 0;
    private List<Color> colorList;

    public JFreeChartBarDrawingSupplier(List colorList) {
        this.colorList = colorList;
    }

    public Paint getNextFillPaint() {
        if (colorList == null || colorList.size() == 0) {
            return Color.RED; //return red on empty or no list
        }

        Color returnColor=colorList.get(cursor);

        cursor++;

        //wrap cursor when all items in the list are traversed
        if (cursor >= colorList.size()) {
            cursor = 0;
        }

        return returnColor;
    }

    public Paint getNextPaint() {
        if (colorList == null || colorList.size() == 0) {
            return Color.RED; //return red on empty or no list
        }

        Color returnColor=colorList.get(cursor);

        cursor++;

        //wrap cursor when all items in the list are traversed
        if (cursor >= colorList.size()) {
            cursor = 0;
        }

        return returnColor;
    }

    public Paint getNextOutlinePaint() { return Color.BLACK; }
    public Stroke getNextStroke() { return stroke; }
    public Stroke getNextOutlineStroke() { return stroke; }
    public Shape getNextShape() { return shape; }


    public static List<Color> getUniqueColorList() {
        Color[] colors = new Color[]{
                getColorFromRGB("DF8244")
        };

        return new ArrayList<>(Arrays.asList(colors));
    }

    public static List<Color> getCustomColorList() {
        Color[] colors = new Color[]{
                getColorFromRGB("00b052"),
                getColorFromRGB("ffff00"),
                getColorFromRGB("e26f01"),
                getColorFromRGB("fe0100")
        };

        return new ArrayList<>(Arrays.asList(colors));
    }

    public static List<com.itextpdf.kernel.color.Color> getCustomiTextColorList() {
        com.itextpdf.kernel.color.Color[] colors = new com.itextpdf.kernel.color.Color[]{
                WebColors.getRGBColor("#00b052"),
                WebColors.getRGBColor("#ffff00"),
                WebColors.getRGBColor("#e26f01"),
                WebColors.getRGBColor("#fe0100")
        };

        return new ArrayList<>(Arrays.asList(colors));
    }

    public static Color getColorFromRGB(final String rgb) {
        final int[] ret = new int[3];
        for (int i = 0; i < 3; i++) {
            ret[i] = Integer.parseInt(rgb.substring(i * 2, i * 2 + 2), 16);
        }

        return new Color(ret[0], ret[1], ret[2]);
    }
}