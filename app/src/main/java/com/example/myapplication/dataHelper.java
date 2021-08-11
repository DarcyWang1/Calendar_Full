package com.example.myapplication;
import java.io.*;
import java.util.ArrayList;

public class dataHelper {
    public static void writeToFile(String direction, java.io.Serializable input){
        String path = "../data/"+direction+".txt";
        try {
            File fil = new File(path);
            fil.createNewFile();
            FileOutputStream file = new FileOutputStream(fil);
            ObjectOutputStream object = new ObjectOutputStream(file);
            object.writeObject(input);
            object.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static <T> T read(String direction){
        String path = "../data/"+direction+".txt";
        try {
            FileInputStream file = new FileInputStream(new File(path));
            ObjectInputStream object = new ObjectInputStream(file);
            T o = (T)object.readObject();
            object.close();
            file.close();
            return o;
        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static <T> ArrayList<T> readAll(String direction){
        String path = "../data/"+direction;
        File[] files = new File(path).listFiles();
        ArrayList<T> result = new ArrayList<T>();
        if(files!=null) {
            int a = files.length;
            for(File i: files){
                try {
                    FileInputStream file = new FileInputStream(new File(path));
                    ObjectInputStream object = new ObjectInputStream(file);
                    result.add((T)object.readObject());
                    object.close();
                    file.close();
                } catch (IOException | ClassNotFoundException | ClassCastException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }
    public static void saveAll(String direction, ArrayList<String> name, ArrayList<java.io.Serializable> input){
        delateAll(direction);
        int a = Math.min(name.size(),input.size());
        for(int i=0; i<a;i++){
            writeToFile(direction+"/"+name.get(i),input.get(i));
        }
    }
    public static boolean delateData(String direction){
        String path = "../data/"+direction;
        File f = new File(path);
        return f.delete();
    }
    public static void delateAll(String direction){
        String path = "../data/"+direction;
        File[] files = new File(path).listFiles();
        ArrayList<String> result = new ArrayList<String>();
        if(files!=null) {
            int a = files.length;
            for(File i: files){
                i.delete();
            }
        }
    }
}