package com.jackcc.util;

import com.jackcc.db.JdbcDao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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

    public static ArrayList<ArrayList<byte []>> calcLibHash(ArrayList<ArrayList<String>> libStrand)
            throws NoSuchAlgorithmException, IOException, SQLException {
        ArrayList<ArrayList<byte []>> libHash= new ArrayList<>();


        for (int i =0; i< libStrand.size(); i++) {
            ArrayList<byte []> hashStrands = new ArrayList<>();
            ArrayList<String> strands = libStrand.get(i);
            for (int j = 0; j < strands.size(); j++) {
                String strand = strands.get(j);
                byte[] bytesOfMessage = strand.getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("MD5");
                byte[] hash = md.digest(bytesOfMessage);
                hashStrands.add(hash);
            }


            libHash.add(hashStrands);
        }

        return libHash;
    }

    public static String calcHash(ArrayList<String> strands)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String data = new String();

        for (int index = 0; index < strands.size(); index++) {
            data  += strands.get(index);
        }
        byte[] bytesOfMessage = data.getBytes("UTF-8");
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] hash = md.digest(bytesOfMessage);

        return byte2str(hash);
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
