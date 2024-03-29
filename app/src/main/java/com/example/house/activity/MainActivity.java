package com.example.house.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.house.R;
import com.example.house.fragment.IndexFragment;
import com.example.house.fragment.ReportListFragment;
import com.example.house.fragment.MyFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();

        //加载首页的Fragment
        switchFragment(new IndexFragment(),true);


    }

    public void switchFragment(Fragment fragment,Boolean isAddToBackStack) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        if (isAddToBackStack)
        {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_index:
                switchFragment(new IndexFragment(),false);
                break;
            case R.id.ll_list:
                switchFragment(new ReportListFragment(),false);
                break;
            case R.id.ll_my:
                switchFragment(new MyFragment(),false);
                break;
        }
    }
}
