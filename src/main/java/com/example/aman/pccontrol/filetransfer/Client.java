package com.example.aman.pccontrol.filetransfer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aman.pccontrol.FileCardsAdapter;
import com.example.aman.pccontrol.FileDownloadFragment;
import com.example.aman.pccontrol.MainActivity;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Client extends AsyncTask<Integer, Void, Void>{
    private String dstAddress ;         // Connection Address
    private int dstPort ;               // Connection Port
    private final int BUF_SIZE = 1024 ; // Buffer Size
    private String response = "" ;      // Response String
    private String otherText = "" ;     // Just for display
    TextView textResponse, mTextView ;
    Context context ;
    private int choice = 0;
    private FileCards file_get;

    public void setFile_get(FileCards file_get) {
        this.file_get = file_get;
    }

    private final String logTag = "ClientApp" ; // For logging

    /***********************DIRECTORY NAMES IN THE MOBILE FILE SYSTEM***********************/
    private final String docDirName = "PC_Folder", audDirName = "PC_Audio", picDirName = "PC_Album", movDirName = "PC_Movie" ;
    private Directories directories ;       // To create the directories

    public Client() {   }

    public Client(int choice){ this.choice = choice;}
    public Client(String addr, int port, TextView textResponse, TextView textView, Context context) {
        this.dstAddress = addr ;
        this.dstPort = port ;
        this.textResponse = textResponse ;
        this.mTextView = textView ;
        this.context = context ;
        this.directories = new Directories(docDirName, audDirName, picDirName, movDirName) ;
    }

    @Override
    protected Void doInBackground(Integer... params) {
        int type = 3 ;
        choice = params[0];
        try {
            /*socket = new Socket(dstAddress, dstPort) ;
            if(socket == null) {
                otherText = "Connection Failed" ;
            }*/

            /************************* GET ALL MAJOR STREAMS**************************/


            switch (params[0]){
                case 1:

                    getFilesList();
                    break;
                case 2:
                    // To be outputted for downloading a file from PC
                    MainActivity.objectOutputStream.writeObject("FILE_GET") ;

                    /**************************TEMPORARY TEST PARAMETERS*************************/
                    String tempPath = "H:\\Vineet\\Developer\\Temp\\Networking1\\Files\\Pictures" ;
                    FileCards tempHead = new FileCards("Pictures", "10", tempPath, "dir/ord") ;
                    FileCards tempHead1 = new FileCards("universe.jpg", "28172", tempPath + "\\universe.jpg", "image/jpg") ;

                    //recFilesList(objectInputStream, objectOutputStream, tempHead) ;
                    recBufFileExt(MainActivity.objectOutputStream, MainActivity.inputStream, file_get) ;


            }

        } catch (UnknownHostException he) {
            he.printStackTrace() ;
            response = "Unknown Host: " + he.toString() ;
        } catch (IOException e) {
            e.printStackTrace() ;
            response = "IO Exception: " + e.toString() ;
        } catch (Exception e) {
            e.printStackTrace() ;
            response = "Exception: " + e.toString() ;
        }

        return null ;
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState() ;
        return (Environment.MEDIA_MOUNTED.equals(state)) ;
    }

    /***************************** CAN BE USED AS IT IS**************************************/
    /*****************************
     * @param objectInputStream
     * @param objectOutputStream
     * @param parentDir
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * First sends the file's (or folder's) corresponding FileCards
     * Receives the state of whether the server can send the files list
     * Then receives the count of items
     * Receives each item and adds it to its own list
     *
     */
    private void recFilesList(ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, FileCards parentDir) throws IOException, ClassNotFoundException {
        parentDir.sendFileHead(objectOutputStream) ;
        ArrayList<FileCards> fileCardsArrayList = new ArrayList<FileCards>() ;
        String state = (String) objectInputStream.readObject();
        if(state.equals("Null")) {
            Log.d(logTag, "Got a null") ;
            return ;
        }
        Log.d(logTag, "Not Null") ;
        int count = objectInputStream.readInt() ;
        Log.d(logTag, String.valueOf(count)) ;
        for(int i = 0; i < count; i++) {
            Log.d(logTag, String.valueOf(i)) ;
            FileCards tempFileCards = FileCards.readFileHead(objectInputStream) ;
            fileCardsArrayList.add(tempFileCards) ;
        }

        if(fileCardsArrayList != null) {
            for(int i = 0; i < fileCardsArrayList.size(); i++) {
                response += "Filename: " + fileCardsArrayList.get(i).getFileName() + " Path: " + fileCardsArrayList.get(i).getPath() + " Size: " + fileCardsArrayList.get(i).getSize() + " Type: " + fileCardsArrayList.get(i).getType() ;
                Log.d(logTag, "Filename: " + fileCardsArrayList.get(i).getFileName() + " Path: " + fileCardsArrayList.get(i).getPath() + " Size: " + fileCardsArrayList.get(i).getSize() + " Type: " + fileCardsArrayList.get(i).getType()) ;
            }
        }
    }

    /*******************************
     *
     * @param objectOutputStream
     * @param inputStream
     * @param fileCards
     * @throws IOException
     * @throws ClassNotFoundException
     *
     * Receives the file whose header is fileCards
     * First sends the fileCards to server
     * Based on the type of file, selects the parent directory
     * Receives the bytes of the file into a buffer and writes it to disk simultaneously
     *
     */
    public void recBufFileExt(ObjectOutputStream objectOutputStream, InputStream inputStream, FileCards fileCards) throws IOException, ClassNotFoundException {
        File parentDir ;
        DataInputStream dataInputStream = new DataInputStream(inputStream) ;

        fileCards.sendFileHead(objectOutputStream) ;

        String fileType = fileCards.getType().split("/")[0] ;
        if(fileType.equals("audio")) {
            parentDir = directories.audDir ;
        } else if(fileType.equals("image")) {
            parentDir = directories.picDir ;
        } else if(fileType.equals("video")){
            parentDir = directories.movDir ;
        } else {
            parentDir = directories.docDir;
        }

        File file = new File(parentDir, fileCards.getFileName()) ;
        OutputStream outputStream = new FileOutputStream(file) ;
        int remSize = Integer.parseInt(fileCards.getSize());
        byte[] kiloBuffer = new byte[BUF_SIZE] ;
        int numReadBytes ;

        while((remSize > 0) && ((numReadBytes = dataInputStream.read(kiloBuffer, 0, Math.min(BUF_SIZE, remSize))) != -1)) {
            outputStream.write(kiloBuffer, 0, numReadBytes) ;
            remSize -= numReadBytes ;
        }

        dataInputStream.close() ;
        outputStream.close() ;
    }

    @SuppressLint("LongLogTag")
    public void getFilesList() {

        Log.d("Check", "getFilesList: Request Sent");

        try {
            MainActivity.objectOutputStream.writeObject("GET_FILE_LIST");
            MainActivity.objectOutputStream.flush();
            String state = (String) MainActivity.objectInputStream.readObject();
            if(state.equals("Null")) {
                return;

            }
            int count = MainActivity.objectInputStream.readInt() ;
            for(int i = 0; i < count; i++) {
                FileCards tempFileCards = FileCards.readFileHead(MainActivity.objectInputStream) ;
                MainActivity.fileCardsList.add(tempFileCards) ;
            }
           // FileDownloadFragment.mAdapter = new FileCardsAdapter(MainActivity.fileCardsList);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        FileDownloadFragment.setRecyclerView();
        FileDownloadFragment.start = true;
    }
}
