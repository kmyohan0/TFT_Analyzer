package com.example.tfthelper.Model.Dto;

public class obj {

    private String style;
    private int min;
    private int max;

    public obj() {
        style = "";
        min = 0;
        max = 0;
    }

    public obj(String style, int min) {
        this.style = style;
        this.min = min;
        max = min;
    }

    public obj(String style, int min, int max) {
        this.style = style;
        this.min = min;
        this.max = max;
        if (max == 0) {
            max = min;
        }
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
