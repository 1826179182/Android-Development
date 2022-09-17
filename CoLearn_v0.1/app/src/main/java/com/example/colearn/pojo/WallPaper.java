package com.example.colearn.pojo;

public class WallPaper {
    private int wallpaper;
    private String wallpaperName;
    private String titleBarColor;

    public WallPaper() {
    }

    ;

    public WallPaper(int wallpaper, String wallpaperName, String titleBarColor) {
        this.wallpaper = wallpaper;
        this.wallpaperName = wallpaperName;
        this.titleBarColor = titleBarColor;
    }

    public int getWallpaper() {
        return wallpaper;
    }

    public void setWallpaper(int wallpaper) {
        this.wallpaper = wallpaper;
    }

    public String getWallpaperName() {
        return wallpaperName;
    }

    public void setWallpaperName(String wallpaperName) {
        this.wallpaperName = wallpaperName;
    }

    public String getTitleBarColor() {
        return titleBarColor;
    }

    public void setTitleBarColor(String titleBarColor) {
        this.titleBarColor = titleBarColor;
    }

    @Override
    public String toString() {
        return "WallPaper{" +
                "wallpaper:" + wallpaper +
                ", wallpaperName:'" + wallpaperName + '\'' +
                ", titleBarColor:'" + titleBarColor + '\'' +
                '}';
    }
}
