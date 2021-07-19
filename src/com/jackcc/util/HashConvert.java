package com.jackcc.util;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class HashConvert {

    /**
     * Strands to Hash
     * */
    public static ArrayList<byte []> calcStrandHash(ArrayList<String> strands)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        ArrayList<byte []> hashStrands = new ArrayList<>();
        for (int index = 0; index < strands.size(); index++) {
            String strand = strands.get(index);
            byte[] bytesOfMessage = strand.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(bytesOfMessage);
            hashStrands.add(hash);
        }
        return hashStrands;
    }

    /**
     * Hash to String: Using to test!!!
     * */

//    public static ArrayList<ArrayList<String>> byte2str(ArrayList<ArrayList< byte[]>> hashStrands) {
//        ArrayList<ArrayList<String>> hashStrStrands = new ArrayList<>();
//
//        for (int index = 0; index < hashStrands.size(); index++) {
//            ArrayList<byte[]> hash = hashStrands.get(index);
//            ArrayList<String> hashStr = new ArrayList<>();
//            for(int j = 0; j < hash.size(); j++){
//                byte[] hashStrand = hash.get(j);
//                BigInteger bigInt = new BigInteger(1, hashStrand);
//                String hashText = bigInt.toString(16);
//                hashStr.add(hashText);
//            }
//            hashStrStrands.add(hashStr);
//        }
//        return hashStrStrands;
//    }

    public static ArrayList<String> byte2str(ArrayList<byte[]> hashStrands) {
        ArrayList<String> hashStrStrands = new ArrayList<>();

        for (int index = 0; index < hashStrands.size(); index++) {

            byte[] hashStrand = hashStrands.get(index);
            BigInteger bigInt = new BigInteger(1, hashStrand);

            String hashText = bigInt.toString(16);
            hashStrStrands.add(hashText);
        }
        return hashStrStrands;
    }

    public static String byte2str(byte[] hashStrand){
        BigInteger bigInt = new BigInteger(1, hashStrand);
        return bigInt.toString(16);
    }



}
