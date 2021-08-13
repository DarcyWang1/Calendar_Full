package com.example.myapplication;
import java.io.*;
import java.util.ArrayList;

public class DataAccess {
    public static void writeToFile(String folder,String direction, java.io.Serializable input){
        String path = folder+direction+".txt";
        //System.out.println(path);
        try {
            //new File(folder+direction).createNewFile();
            File fil = new File(path);
            fil.createNewFile();
            //System.out.println(fil.exists());
            FileOutputStream file = new FileOutputStream(fil);
            ObjectOutputStream object = new ObjectOutputStream(file);
            object.writeObject(input);
            object.close();
            file.close();
            //System.out.println("successed:"+path);
            File f = new File(path);
            //System.out.println(f.length());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static <T> T read(String folder,String direction){
        String path = folder+direction+".txt";
        //System.out.println(path);
        File fil = new File(path);
        //System.out.println(fil.length());
        try {
            FileInputStream file = new FileInputStream(fil);
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
    //the following methods might be useful in later versions
    public static <T> ArrayList<T> readAll(String folder,String direction){
        String path = folder+direction;
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
    public static void saveAll(String folder,String direction, ArrayList<String> name, ArrayList<java.io.Serializable> input){
        delateAll(folder,direction);
        int a = Math.min(name.size(),input.size());
        for(int i=0; i<a;i++){
            writeToFile(folder,direction+"/"+name.get(i),input.get(i));
        }
    }
    public static boolean delateData(String folder,String direction){
        String path = folder+direction;
        File f = new File(path);
        return f.delete();
    }
    public static void delateAll(String folder,String direction){
        String path = folder+direction;
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
