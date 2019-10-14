package com.example.house.model;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.house.R;
import com.example.house.controller.MainActivity;

public class IndexFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_index, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btn_show_standard=view.findViewById(R.id.btn_show_standard);
        Button btn_add=view.findViewById(R.id.btn_add);

        //查看标准
        btn_show_standard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                                    MainActivity mainActivity= (MainActivity) getActivity();
               mainActivity.switchFragment(new StandardFragment(),true )        ;
            }
        });

        //添加鉴定文件
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity= (MainActivity) getActivity();
                mainActivity.switchFragment(new AddFragment(),true );
            }
        });
    }
}
