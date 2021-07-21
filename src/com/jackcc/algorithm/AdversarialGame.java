//package com.jackcc.algorithm;
//
//import com.jackcc.util.Similarity;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//
//import static com.jackcc.util.HashConvert.byte2str;
//
//public class AdversarialGame {
//
//    // HashMap is [String, ArrayList<byte []>]
//    public ArrayList<HashMap<String, ArrayList<byte []>>> lib;
//    public ArrayList<HashMap<String, ArrayList<byte []>>> script;
//    public ArrayList<ArrayList<String>> functionMatch;
//
//    public Similarity sim = new Similarity(lib);
//
//    public AdversarialGame(ArrayList<HashMap<String, ArrayList<byte []>>> lib, ArrayList<HashMap<String, ArrayList<byte []>>> script){
//        this.lib = lib;
//        this.script = script;
//    }
//
//    public ArrayList<ArrayList<String>> playGame(ArrayList<ArrayList<byte []>> script, ArrayList<ArrayList<byte []>> lib){
//        functionMatch = new ArrayList<>();
//
//
//
//        return functionMatch;
//    }
//
//    public HashMap selectMaxSim(ArrayList<byte []> me, ArrayList<ArrayList<byte []>> other){
//        HashMap maxSimMap = new HashMap();
//
//        for(int i = 0; i < other.size(); i++){
//            ArrayList<String> otherItem = byte2str(other.get(i));
//
//        }
//
//        return maxSimMap;
//    }
//
//}
