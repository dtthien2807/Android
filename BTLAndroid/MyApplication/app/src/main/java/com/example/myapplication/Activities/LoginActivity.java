package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.chucnangDB.nhanvienDB;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    Button btn_dangnhap, btn_dangky;
    TextInputLayout txt_login_sdt, txt_login_password;
    nhanvienDB nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        //thuộc tính view
        txt_login_sdt = (TextInputLayout)findViewById(R.id.txt_login_sdt);
        txt_login_password = (TextInputLayout)findViewById(R.id.txt_login_password);
        btn_dangnhap = (Button)findViewById(R.id.btn_dangnhap);
        btn_dangky = (Button)findViewById(R.id.btn_dangky);

        nv = new nhanvienDB(this);    //khởi tạo kết nối csdl

        btn_dangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateSDT() | !validatePassWord()){
                    return;
                }

                String sdt = txt_login_sdt.getEditText().getText().toString();
                String matKhau = txt_login_password.getEditText().getText().toString();
                int ktra = nv.KiemTraDN(sdt,matKhau);
                int maquyen = nv.LayQuyenNV(ktra);
                if(ktra != 0){
                    // lưu mã quyền vào shareprefer
                    SharedPreferences sharedPreferences = getSharedPreferences("luuquyen", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor =sharedPreferences.edit();
                    editor.putInt("maquyen",maquyen);
                    editor.commit();

                    //gửi dữ liệu user qua trang chủ
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    intent.putExtra("sdt",txt_login_sdt.getEditText().getText().toString());
                    intent.putExtra("manv",ktra);
                    startActivity(intent);
                }else {
                    Toast.makeText(getApplicationContext(),"Đăng nhập thất bại!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    //Hàm quay lại màn hình chính
    public void backFromLogin(View view)
    {
        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        //tạo animation cho thành phần
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layoutwelcome),"transition_login");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }
        else {
            startActivity(intent);
        }
    }

    //Hàm chuyển qua trang đăng ký
    public void callRegisterFromLogin(View view)
    {
        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //region Validate field
    private boolean validateSDT(){
        String val = txt_login_sdt.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txt_login_sdt.setError("Không được để trống!");
            return false;
        }else {
            txt_login_sdt.setError(null);
            txt_login_sdt.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = txt_login_password.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txt_login_password.setError("Không được để trống");
            return false;
        }else{
            txt_login_password.setError(null);
            txt_login_password.setErrorEnabled(false);
            return true;
        }
    }
    //endregion
}