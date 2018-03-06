package com.noonacademy.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.noonacademy.R;
import com.noonacademy.view.CanvasDrawView;
import com.turkialkhateeb.materialcolorpicker.ColorChooserDialog;
import com.turkialkhateeb.materialcolorpicker.ColorListener;

public class DrawFragment extends Fragment {
    ImageView undoImg,redoImg,changeColImg,saveImg;
     CanvasDrawView canvasDrawView;
    public DrawFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DrawFragment newInstance(String param1, String param2) {
        DrawFragment fragment = new DrawFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_draw, container, false);
        canvasDrawView = (CanvasDrawView) view.findViewById(R.id.canvas);
        undoImg = (ImageView) view.findViewById(R.id.undo);
        redoImg = (ImageView) view.findViewById(R.id.redo);
        saveImg = (ImageView) view.findViewById(R.id.save);
        changeColImg = (ImageView) view.findViewById(R.id.change_col);
        undoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 canvasDrawView.undo();
            }
        });
        changeColImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showColorDialog();
            }
        });
        redoImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvasDrawView.redo();
            }
        });
        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canvasDrawView.saveImage();
                Toast.makeText(getContext(),"Image is saved in gallery", Toast.LENGTH_LONG).show();
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void showColorDialog() {
        ColorChooserDialog dialog = new ColorChooserDialog(getContext());
        dialog.setTitle("");
        dialog.setColorListener(new ColorListener() {
            @Override
            public void OnColorClick(View v, int color) {
                //do whatever you want to with the values
                canvasDrawView.changeColor(color);
            }
        });
        //customize the dialog however you want
        dialog.show();
    }


}
