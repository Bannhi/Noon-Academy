package com.noonacademy.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.noonacademy.R;
import com.noonacademy.utils.Constants;
import com.noonacademy.view.fragment.AddSubjectFragment;
import com.noonacademy.view.fragment.DrawFragment;
import com.noonacademy.view.fragment.SubjectListFragment;

import java.util.prefs.Preferences;

import io.realm.Realm;

public class MainWorkingActivity extends AppCompatActivity {

    TextView tv_user_name;
    ImageView subjectsIcon, addSubjectIcon, drawIcon;
    TextView subjectsTitle, addSubjectsTitle, drawTitle;
    private RelativeLayout rel_loading_frame;
    static Preferences pref;
    private FrameLayout fragmentContainer;
    private LinearLayout toggleLeft, toggleRight, toggleMiddle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_working);
        Realm.init(this);
        System.out.println("LOGGED IN"+getIntent().getExtras().getString(Constants.USER_NAME));
        tv_user_name = (TextView) findViewById(R.id.user_name);
        tv_user_name.setText("Welcome "+ getIntent().getExtras().getString(Constants.USER_NAME)+"!");
        rel_loading_frame = (RelativeLayout) findViewById(R.id.rel_loading_frame);
        fragmentContainer = (FrameLayout) findViewById(R.id.fragment_container);
        toggleLeft = (LinearLayout) findViewById(R.id.leftll);
        toggleRight = (LinearLayout) findViewById(R.id.rightll);
        toggleMiddle = (LinearLayout) findViewById(R.id.midll);
        subjectsIcon = (ImageView) findViewById(R.id.subject_icon);
        addSubjectIcon = (ImageView) findViewById(R.id.add_sub_icon);
        drawIcon = (ImageView) findViewById(R.id.draw_icon);
        subjectsTitle = (TextView) findViewById(R.id.tv_add_sub);
        addSubjectsTitle = (TextView) findViewById(R.id.tv_sub_list);
        drawTitle = (TextView) findViewById(R.id.tv_draw);

        changeFragment(0);

        toggleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_loading_frame.setVisibility(View.VISIBLE);
                changeFragment(0);
            }
        });
        toggleMiddle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_loading_frame.setVisibility(View.VISIBLE);
                changeFragment(1);
            }
        });
        toggleRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_loading_frame.setVisibility(View.VISIBLE);
                changeFragment(2);
            }
        });


    }
    private void changeFragment(int pos) {
        rel_loading_frame.setVisibility(View.GONE);
        Fragment fragment;

        if (pos == 0) {
            subjectsTitle.setTextColor(Color.parseColor("#FF606060"));
            addSubjectsTitle.setTextColor(Color.parseColor("#FF1E3B64"));
            drawTitle.setTextColor(Color.parseColor("#FF606060"));
            subjectsTitle.setTextSize(11);
            addSubjectsTitle.setTextSize(13);
            drawTitle.setTextSize(11);
            subjectsIcon.getLayoutParams().height = 30;
            addSubjectIcon.getLayoutParams().height = 20;
            drawIcon.getLayoutParams().height = 20;
            fragment = new SubjectListFragment();


        } else if (pos == 1) {
            subjectsTitle.setTextColor(Color.parseColor("#FF1E3B64"));
            addSubjectsTitle.setTextColor(Color.parseColor("#FF606060"));
            drawTitle.setTextColor(Color.parseColor("#FF606060"));
            subjectsTitle.setTextSize(13);
            addSubjectsTitle.setTextSize(11);
            drawTitle.setTextSize(11);
            subjectsIcon.getLayoutParams().height = 20;
            addSubjectIcon.getLayoutParams().height = 30;
            drawIcon.getLayoutParams().height = 20;
            fragment = new AddSubjectFragment();


        } else {
            subjectsTitle.setTextColor(Color.parseColor("#FF606060"));
            addSubjectsTitle.setTextColor(Color.parseColor("#FF606060"));
            drawTitle.setTextColor(Color.parseColor("#FF1E3B64"));
            subjectsTitle.setTextSize(11);
            addSubjectsTitle.setTextSize(11);
            drawTitle.setTextSize(13);
            subjectsIcon.getLayoutParams().height = 20;
            addSubjectIcon.getLayoutParams().height = 20;
            drawIcon.getLayoutParams().height = 30;
            fragment = new DrawFragment();
        }

        if(fragment!=null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_container, fragment);
            ft.commit();
        }

    }
}
