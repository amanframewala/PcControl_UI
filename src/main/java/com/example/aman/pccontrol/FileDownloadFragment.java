package com.example.aman.pccontrol;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aman.pccontrol.filetransfer.Client;
import com.example.aman.pccontrol.filetransfer.FileCards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.CopyOnWriteArrayList;

import static com.example.aman.pccontrol.MainActivity.fileCardsList;
import static com.example.aman.pccontrol.MainActivity.objectInputStream;
import static java.lang.Thread.sleep;

/**
 * Created by aman on 10/4/18.
 */

public class FileDownloadFragment extends Fragment implements View.OnClickListener {

    private TextView pathTextView;
    private static RecyclerView recyclerView;
    private Stack<String> pathStack;
    private Button backButton;
    private static FileCardsAdapter mAdapter;
    private List<FileCards> fileCardsL = new ArrayList<>();
    public static Boolean start = false;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_file_download, container, false);
        backButton = (Button) rootView.findViewById(com.example.aman.pccontrol.R.id.backButton);
        pathTextView = (TextView) rootView.findViewById(com.example.aman.pccontrol.R.id.pathTextView);
        recyclerView = (RecyclerView) rootView.findViewById(com.example.aman.pccontrol.R.id.fileTransferListView);
        pathStack = new Stack<String>();
        pathStack.push("/");
        pathTextView.setText(pathStack.peek());
        backButton.setEnabled(false);
        backButton.setOnClickListener(this);
        populateList();
        mAdapter = new FileCardsAdapter(fileCardsL);
        recyclerView.setHasFixedSize(false);


        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);
        final GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }

        });

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mGestureDetector.onTouchEvent(e)) {
                    int a=recyclerView.getChildAdapterPosition(child);

                    Toast.makeText(getContext(), "The Item Clicked is: " + a, Toast.LENGTH_SHORT).show();
                    // below code get the position of data




                    return true;

                }

                return false;

            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });


        return rootView;
    }

    private void populateList() {
        int i = 10;
        while (i>0){
        fileCardsL.add(new FileCards("new.text",String.valueOf(i),"dir/home/","image"));
        //new Client().execute(1);
            i--;
        }

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Download Files");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == com.example.aman.pccontrol.R.id.backButton) {
            pathStack.pop();
            String currentPath = pathStack.peek();
            pathTextView.setText(currentPath);
            if (!currentPath.equals("/")) {
            } else {
                backButton.setEnabled(false);
            }
        }

    }
    public static void setRecyclerView(){
       // populateList();
        mAdapter = new FileCardsAdapter(fileCardsList);
        recyclerView.setHasFixedSize(true);




    }

}