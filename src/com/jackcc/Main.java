package com.jackcc;

import com.jackcc.util.Similarity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.jackcc.util.HashConvert.*;
import static com.jackcc.util.StrandsGenerator.*;


public class Main {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {

        // Init strands

        ArrayList<String> strand = convert2Strand("res/puts_strands");

        // Construct the target procedure
        ArrayList<byte[]> strandByteHash = calcStrandHash(strand);
        ArrayList<String> strandHash = byte2str(strandByteHash);

        /**
         * Construct Lib P(Hash)
         **/
//        ArrayList<ArrayList<byte[]>> p = PGenerator(9);
//        ArrayList<ArrayList<String>> pString = byte2str(p);
        ArrayList<ArrayList<String>> p = PGenerator();
        ArrayList<ArrayList<byte[]>> pHash = calcLibHash(p);
        /**
         * q read from P by loop
         * */
        Similarity sim = new Similarity(strandByteHash, pHash);

        for (int i =0; i<sim.lib.size(); i++) {
            sim.query =sim.lib.get(i);
            // Using to test
            sim.sizeOfLib = sim.getLibSize(sim.lib);
            sim.strandNumMap = sim.getStrandNum(sim.target, sim.lib);

            sim.probabilityReverseMap = sim.getStrandProbabilityReverse(sim.strandNumMap, sim.sizeOfLib);

            sim.listIntersection = sim.intersection(byte2str(sim.target), byte2str(sim.query));

            sim.simScore = sim.getSim(sim.listIntersection, sim.probabilityReverseMap);
            sim.relativelySimScore = sim.getRelativelySim(byte2str(sim.target), sim.query, sim.listIntersection, sim.probabilityReverseMap);


            /**
             * Print
             * */
            System.out.println("--------------------------------------------------------------------------------");
            System.out.println(sim.sizeOfLib);

            System.out.println(sim.strandNumMap);
            System.out.println(sim.probabilityReverseMap);

            System.out.println(sim.target);
            System.out.println(sim.query);
            System.out.println(sim.listIntersection);

            System.out.println(sim.simScore);
            System.out.println(sim.relativelySimScore);
        }

    }
}

