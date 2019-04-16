package com.arusoft.mobile.uiapptest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;


public class MainActivity extends Activity implements View.OnClickListener {
    private LinearLayout mQueen;
    private Button mHidden, accept, decline;
    private DraggingPanel mDraggingPanel;
    private LinearLayout mMainLayout;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mDraggingPanel = findViewById(R.id.outer_layout);
//        mMainLayout = findViewById(R.id.main_layout);
//
//        mHidden = findViewById(R.id.hidden_button);
//        accept = findViewById(R.id.button1);
//        accept.setOnClickListener(this);
//        decline = findViewById(R.id.button2);
//        decline.setOnClickListener(this);
//        mHidden.setOnClickListener(this);
//        mQueen = findViewById(R.id.queen_button);
//        mQueen.setOnClickListener(this);
//        mMainLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                if (mDraggingPanel.isMoving()) {
//                    v.setTop(oldTop);
//                    v.setBottom(oldBottom);
//                    v.setLeft(oldLeft);
//                    v.setRight(oldRight);
//                }
//            }
//        });
//    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        Button b = (Button) v;
        Toast t = Toast.makeText(this, b.getText() + " clicked", Toast.LENGTH_SHORT);
        t.show();
    }
}