package com.example.multitouchdraw;

public class Line{
    private float x;
    private float y;
    private float newX;
    private float newY;
    private int color;

    public Line(float x, float y, float newX, float newY, int color) {
        this.x = x;
        this.y = y;
        this.newX = newX;
        this.newY = newY;
        this.color = color;
    }

    public float getPrevX() { return x;}
    public void setPrevX(float x) { this.x = x; }
    public float getPrevY() { return y; }
    public void setPrevY(float y) { this.y = y; }
    public float getNewX() { return newX; }
    public void setNewX(float newX) { this.newX = newX; }
    public float getNewY() { return newY; }
    public void setNewY(float newY) { this.newY = newY; }
    public int getColor() { return color; }
    public void setColor(int color) { this.color = color; }
}
