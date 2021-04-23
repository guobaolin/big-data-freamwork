package com.gbl.bigdata.spark;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        String[] a1 = {"abc"};
        Object[] a2 = a1;
        a2[0] = new Integer(17);
        String s = a1[0];
        System.out.println(s);

    }

    public void p() {
        print();
    }

    public void print() {
        System.out.println("hello word");
    }

}
