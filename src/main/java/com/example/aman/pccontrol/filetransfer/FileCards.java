package com.example.aman.pccontrol.filetransfer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class FileCards implements Serializable {
    private final String fileName, size, path, type;

    public FileCards(String fileName, String size, String path, String type) {
        this.fileName = fileName;
        this.size = size;
        this.path = path;
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getSize() {
        return size;
    }

    public String getType() {
        return type;
    }

    public static FileCards readFileHead(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        String fileName = (String) objectInputStream.readObject();
        String size = (String) objectInputStream.readObject();
        String path = (String) objectInputStream.readObject();
        String type = (String) objectInputStream.readObject();

        return(new FileCards(fileName, size, path, type)) ;
    }

    public void sendFileHead(ObjectOutputStream objectOutputStream) throws IOException, ClassNotFoundException {
        objectOutputStream.writeObject(fileName) ;
        objectOutputStream.writeObject(size) ;
        objectOutputStream.writeObject(path) ;
        objectOutputStream.writeObject(type) ;
    }

    public boolean isDir() {
        String[] types = type.split("/") ;
        return(types[0].equals("dir")) ;
    }
}
