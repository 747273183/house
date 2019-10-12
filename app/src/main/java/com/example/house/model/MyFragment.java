package com.example.house.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.house.R;

public class MyFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        return view;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_password:
                changePassword();
                break;
        }
    }

    private void changePassword() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        builder.setTitle("修改密码");
        builder.setIcon(R.drawable.logo);
        final View v = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_password, null, false);
        builder.setView(v);

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //获得用户id,原密码，新密码，确认密码
                Intent intent = getActivity().getIntent();
                Account account= (Account) intent.getSerializableExtra("account");
                int id = account.getId();
                EditText et_old_pwd= v.findViewById(R.id.et_old_pwd);
                EditText et_new_pwd= v.findViewById(R.id.et_new_pwd);
                EditText et_pas_pwd=v.findViewById(R.id.et_pas_pwd);
                String old_pwd = et_old_pwd.toString();
                String new_pwd = et_new_pwd.toString();
                String pas_pwd = et_pas_pwd.toString();
              //发送修改密码的请求
                postChangePassword(id, old_pwd, new_pwd, pas_pwd);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create();
        builder.show();
    }

    private void postChangePassword(int id, String old_pwd, String new_pwd, String pas_pwd) {

    }
}
