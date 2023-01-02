package com.example.pmob_419;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText nim, pass, nama;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        /* Menginisialisasi variable dengan Form User, Form Password, dan Form Repassword
        dari Layout RegisterActivity */
        nim =findViewById(R.id.nim);
        nama =findViewById(R.id.nama);
        pass =findViewById(R.id.pass);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intents = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intents);
            }
        });

        /* Menjalankan Method razia() jika merasakan tombol SignUp di keyboard disentuh */
        pass.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    razia();
                    return true;
                }
                return false;
            }
        });
        /* Menjalankan Method razia() jika merasakan tombol SignUp disentuh */
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                razia();
            }
        });
    }

    /** Men-check inputan User dan Password dan Memberikan akses ke MainActivity */
    private void razia(){
        /* Mereset semua Error dan fokus menjadi default */
        nim.setError(null);
        nama.setError(null);
        pass.setError(null);
        View fokus = null;
        boolean cancel = false;

        /* Mengambil text dari Form User, Password, Repassword dengan variable baru bertipe String*/
        String Pass = pass.getText().toString();
        String Nim = nim.getText().toString();
        String Nama = nama.getText().toString();

        /* Jika form user kosong atau MEMENUHI kriteria di Method cekUser() maka, Set error di Form-
         * User dengan menset variable fokus dan error di Viewnya juga cancel menjadi true*/
        if (TextUtils.isEmpty(Nim)){
            nim.setError("This field is required");
            fokus = nim;
            cancel = true;
        }else if(cekNim(Nim)){
            nim.setError("Nim ini telah ada");
            fokus = nim;
            cancel = true;
        }
        if (TextUtils.isEmpty(Nama)){
            nama.setError("This field is required");
            fokus = nama;
            cancel = true;
        }
        /* Jika form password kosong dan MEMENUHI kriteria di Method cekPassword maka,
         * Reaksinya sama dengan percabangan User di atas. Bedanya untuk Password dan Repassword*/
        if (TextUtils.isEmpty(Pass)){
            pass.setError("This field is required");
            fokus = pass;
            cancel = true;
        }
        /** Jika cancel true, variable fokus mendapatkan fokus. Jika false, maka
         *  Kembali ke LoginActivity dan Set User dan Password untuk data yang terdaftar */
        if (cancel){
            fokus.requestFocus();
        }else{
            Preferences.setRegisteredUser(getBaseContext(),Nim);
            Preferences.setRegisteredPass(getBaseContext(),Pass);
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    /** True jika parameter user sama dengan data user yang terdaftar dari Preferences */
    private boolean cekNim(String user){
        return user.equals(Preferences.getRegisteredUser(getBaseContext()));
    }
}