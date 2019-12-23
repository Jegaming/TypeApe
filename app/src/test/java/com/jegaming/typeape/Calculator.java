package com.jegaming.typeape;

import org.junit.Test;

/**
 * Created by Jegaming on 12/3/19.
 */
public class Calculator {

    @Test
    public void main() {
        getABWithTwoPoints(
                0.66, 0,
                0.82, -0.04);
//        doOthers();
    }

    private void getABWithTwoPoints(double x1, double y1, double x2, double y2) {
        double a = (y2 - y1) / (x2 - x1);
        double b = y1 - a * x1;

        System.out.println("a = "+a+" b = "+b);
        System.out.printf("y = %+3.2fx %+3.2f \n",a,b);
    }

    private void doOthers(){
        float r = -2f *1 * 0.45f + 0.6f * 1;
        System.out.println(r);
    }
}
