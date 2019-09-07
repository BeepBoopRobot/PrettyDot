class Dot {
    double x, y;
    int co_x, co_y;
    double xOffset, yOffset, cOffset, offVal;
    int red, green, blue;
    private int radius = 20;

    Dot(int x, int y,double cOffset) {
        this.co_x = x;
        this.co_y = y;
        this.red = 0;
        this.green = 124;
        this.blue = 0;
        this.cOffset = cOffset;
        calcCords();
    }

    private void calcCords() {
        double xOff = Math.cos(Math.toRadians(120)) * radius;
        double yOff = Math.sin(Math.toRadians(120)) * radius;

        x = co_x * radius + co_y * -xOff;
        y = co_y * yOff;
    }


}
