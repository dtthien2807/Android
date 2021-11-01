package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DTS.QuyenDTS;
import com.example.myapplication.DTS.nhanvienDTS;
import com.example.myapplication.R;
import com.example.myapplication.chucnangDB.QuyenDB;
import com.example.myapplication.chucnangDB.nhanvienDB;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    ImageView img_signup_back;
    Button btn_signup_dangky;
    TextView txt_signup_title;
    TextInputLayout txt_signup_hoten, txt_signup_sdt, txt_signup_email, txt_signup_password;

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{6,}" +                // at least 4 characters
                    "$");
    nhanvienDB nv;
    QuyenDB quyenDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        //region lấy đối tượng view
        img_signup_back = (ImageView)findViewById(R.id.img_signup_back);
        txt_signup_title = (TextView)findViewById(R.id.txt_signup_title);
        txt_signup_hoten = (TextInputLayout)findViewById(R.id.txt_signup_hoten);
        txt_signup_email = (TextInputLayout)findViewById(R.id.txt_signup_email);
        txt_signup_sdt = (TextInputLayout)findViewById(R.id.txt_signup_sdt);
        txt_signup_password = (TextInputLayout)findViewById(R.id.txt_signup_password);
        btn_signup_dangky = (Button)findViewById(R.id.btn_signup_dangky);

        //endregion
        nv = new nhanvienDB(this);
        quyenDB = new QuyenDB(this);

        btn_signup_dangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //kiểm tra validate false => phải thỏa đk validate
                if(!validateFullName()  | !validateEmail() | !validatePhone() | !validatePassWord()){
                    return;
                }
                String hoTen = txt_signup_hoten.getEditText().getText().toString();
                String eMail = txt_signup_email.getEditText().getText().toString();
                String sDT = txt_signup_sdt.getEditText().getText().toString();
                String matKhau = txt_signup_password.getEditText().getText().toString();

                // truyền dữ liệu vào nhanvienDTS
                nhanvienDTS nhanvien = new nhanvienDTS();
                nhanvien.setHoten(hoTen);
                nhanvien.setEmail(eMail);
                nhanvien.setSdt(sDT);
                nhanvien.setMatkhau(matKhau);

                if(!nv.KtraTonTaiNV()){
                    quyenDB.ThemQuyen("Quản lý");
                    quyenDB.ThemQuyen("Nhân viên");
                    nhanvien.setMAQUYEN(1);
                }else {
                    nhanvien.setMAQUYEN(2);
                }
                //  thêm nhân viên
                long ktra = nv.ThemNhanVien(nhanvien);
                if(ktra!=0){
                    Toast.makeText(RegisterActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    callLoginFromRegister();
                }else {
                    Toast.makeText(RegisterActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }

    public void callLoginFromRegister() {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    //Hàm quay về màn hình trước
    public void backFromRegister(View view){

        Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
        Pair[] pairs = new Pair[1];
        pairs[0] = new Pair<View, String>(findViewById(R.id.layout_register), "transition_signup");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(RegisterActivity.this,pairs);
            startActivity(intent,options.toBundle());
        }else {
            startActivity(intent);
        }
    }


    //region Validate field
    private boolean validateFullName(){
        String val = txt_signup_hoten.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txt_signup_hoten.setError("Không được để trống");
            return false;
        }else {
            txt_signup_hoten.setError(null);
            txt_signup_hoten.setErrorEnabled(false);
            return true;
        }
    }



    private boolean validateEmail(){
        String val = txt_signup_email.getEditText().getText().toString().trim();
        String checkspaces = "[a-zA-Z0-9._-]+@[a-z]+.+[a-z]+";

        if(val.isEmpty()){
            txt_signup_email.setError("Không được để trống");
            return false;
        }else if(!val.matches(checkspaces)){
            txt_signup_email.setError("Email không hợp lệ!");
            return false;
        }
        else {
            txt_signup_email.setError(null);
            txt_signup_email.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePhone(){
        String val = txt_signup_sdt.getEditText().getText().toString().trim();


        if(val.isEmpty()){
            txt_signup_sdt.setError("Không được để trống");
            return false;
        }else if(val.length() != 10){
            txt_signup_sdt.setError("Số điện thoại không hợp lệ!");
            return false;
        }
        else {
            txt_signup_sdt.setError(null);
            txt_signup_sdt.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePassWord(){
        String val = txt_signup_password.getEditText().getText().toString().trim();

        if(val.isEmpty()){
            txt_signup_password.setError("Không được để trống");
            return false;
        }else if(!PASSWORD_PATTERN.matcher(val).matches()){
            txt_signup_password.setError("Mật khẩu ít nhất 6 ký tự!");
            return false;
        }
        else {
            txt_signup_password.setError(null);
            txt_signup_password.setErrorEnabled(false);
            return true;
        }
    }
    //endregion
}