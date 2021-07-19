package com.jackcc.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import static com.jackcc.util.HashConvert.calcStrandHash;

/**
 * Generate the Strand for simulation
 * */
public class StrandsGenerator {

    /**
     * Random generate strands to test
     * */
    public static ArrayList getStrands(){
        ArrayList<String> strands = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            String str = "add a" + Integer.toString(i) + ", a" + Integer.toString(i + 1);
            strands.add(str);
        }
        return strands;
    }

    public static ArrayList getStrands(int seed1, int seed2){
        ArrayList<String> strands = new ArrayList<>();
        for(int i = seed1; i < seed2; i++){
            String str = "add a" + Integer.toString(i) + ", a" + Integer.toString(i + 1);
            strands.add(str);
        }
        return strands;
    }

    public static ArrayList getSameStrands(){
        ArrayList<String> strands = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            String str = "add a1, a2";
            strands.add(str);
        }
        return strands;
    }

    /**
     * Construct Lib P(Hash): include query procedures
     **/
    public static ArrayList<ArrayList<byte[]>> PGenerator(int seed) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        ArrayList<ArrayList<byte[]>> P = new ArrayList<>();
        for(int i = 0; i < seed; i++){
//            P.add(getStrands());
            P.add(calcStrandHash(getStrands(i, i+11)));
        }
        return P;
    }
}
