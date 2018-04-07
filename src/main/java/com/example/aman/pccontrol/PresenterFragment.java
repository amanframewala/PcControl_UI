package com.example.aman.pccontrol;

import android.annotation.SuppressLint;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.aman.pccontrol.MainActivity;
import com.example.aman.pccontrol.R;
/**
 * Created by aman on 7/4/18.
 */

@SuppressLint("ValidFragment")
public class PresenterFragment extends Fragment implements View.OnClickListener {
    private Button downArrowButton, upArrowButton, f5Button, leftArrowButton, rightArrowButton;

    public PresenterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_presenter, container, false);
        downArrowButton = (Button) rootView.findViewById(R.id.downArrowButton);
        upArrowButton = (Button) rootView.findViewById(R.id.upArrowButton);
        leftArrowButton = (Button) rootView.findViewById(R.id.leftArrowButton);
        rightArrowButton = (Button) rootView.findViewById(R.id.rightArrowButton);
        f5Button = (Button) rootView.findViewById(R.id.f5Button);
        downArrowButton.setOnClickListener(this);
        leftArrowButton.setOnClickListener(this);
        upArrowButton.setOnClickListener(this);
        rightArrowButton.setOnClickListener(this);
        f5Button.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Presenter");
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        String action = "F5_KEY";
        switch (id) {
            case R.id.downArrowButton:
                action = "DOWN_ARROW_KEY";
                break;
            case R.id.leftArrowButton:
                action = "LEFT_ARROW_KEY";
                break;
            case R.id.upArrowButton:
                action = "UP_ARROW_KEY";
                break;
            case R.id.rightArrowButton:
                action = "RIGHT_ARROW_KEY";
                break;
            case R.id.f5Button:
                action = "F5_KEY";
                break;
        }
        sendActionToServer(action);

    }
    private void sendActionToServer(String action) {
        MainActivity.sendMessageToServer(action);
    }

}
