package br.ufrn.idw;

import java.io.Serializable;

class IDWCalculator implements Serializable {
    static final int POWER = 2;
    double numerator = 0;
    double weights = 0;
    final Point point;

    public IDWCalculator(Point point) {
        super();
        this.point = point;
    }

    public IDWCalculator calculate(Point pointReaded, double valueReaded) {
        double distance = this.point.distanceTo(pointReaded);
        double weight = 1.0 / Math.pow(distance, POWER);

        numerator += valueReaded * weight;
        weights += weight;

        return this;
    }

    public double get() {
        return numerator / weights;
    }
}
