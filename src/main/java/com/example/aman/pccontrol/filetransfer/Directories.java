package com.example.aman.pccontrol.filetransfer;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class Directories {
    private String docDirName, audDirName, picDirName, movDirName ;
    public File docDir, audDir, picDir, movDir ;
    private final String logTag = "ClientApp" ;

    Directories(String docDirName, String audDirName, String picDirName, String movDirName) {
        this.docDirName = docDirName ;
        this.audDirName = audDirName ;
        this.picDirName = picDirName ;
        this.movDirName = movDirName ;

        getFile() ;
    }

    void getFile() {
        docDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), docDirName) ;
        audDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC), audDirName) ;
        picDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), picDirName) ;
        movDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), movDirName) ;
        Log.d(logTag, docDir.getAbsolutePath()) ;
        Log.d(logTag, audDir.getAbsolutePath()) ;
        Log.d(logTag, picDir.getAbsolutePath()) ;
        Log.d(logTag, movDir.getAbsolutePath()) ;
        if(!docDir.isDirectory()) {
            if(!docDir.mkdirs()) {
                Log.e(logTag, "Doc Directory not created") ;
            }
        }
        if(!audDir.isDirectory()) {
            if(!audDir.mkdirs()) {
                Log.e(logTag, "Aud Directory not created") ;
            }
        }
        if(!picDir.isDirectory()) {
            if(!picDir.mkdirs()) {
                Log.e(logTag, "Pic Directory not created") ;
            }
        }
        if(!movDir.isDirectory()) {
            if(!movDir.mkdirs()) {
                Log.e(logTag, "Mov Directory not created") ;
            }
        }
    }
}
