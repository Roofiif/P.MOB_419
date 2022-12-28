package com.example.pmob_419;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nim, nama, password;
    Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nim = findViewById(R.id.nim);
        nama = findViewById(R.id.nama);
        password =findViewById(R.id.pass);
        register =findViewById(R.id.register);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Membuat User Entity
                UserEntity userEntity = new UserEntity();
                userEntity.setNim(nim.getText().toString());
                userEntity.setNama(nama.getText().toString());
                userEntity.setPassword(password.getText().toString());
                if (validasiInput((userEntity))) {
                    //Lakukan operasi input
                    UserDatabase userDatabase = UserDatabase.getUserDatabase(getApplicationContext());
                    UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Registrasi User
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(), "Berhasil Registrasi", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).start();
                }else {
                    Toast.makeText(getApplicationContext(), "Isi semua Inputan", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Boolean validasiInput(UserEntity userEntity){
        if(userEntity.getNim().isEmpty()|| userEntity.getNim().isEmpty()||
            userEntity.getPassword().isEmpty()){
            return false;
        }
        return true;
    }
}