package com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    //Deklarasi Variable
    EditText mEmail, mPass;
    Button btnLogin, btnRegis;
    //Deklarasi Variable untuk Firebase Authentication
    FirebaseAuth auth;
    //Deklarasi Variable untuk Database
    DatabaseReference databaseReference;
    //Deklarasi Variable ProgressDialog
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Membuat Progress Dialog Baru
        progressDialog = new ProgressDialog(this);

        mEmail = findViewById(R.id.Eemail);
        mPass = findViewById(R.id.Epass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegis = findViewById(R.id.btnRegis);

        //Inisialisasi Firebase (Auth,Database)
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        //Jika User Telah Login
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(Login.this, MainActivity.class));
        }

        //Button Login ketika di klik
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mengambil isi field form
                String email = mEmail.getText().toString();
                String password = mPass.getText().toString();

                //Jika Data Kosong
                if (email.isEmpty() || password.isEmpty()) {
                    //Muncul Toast untuk isi email dan Password
                    Toast.makeText(Login.this, "Tolong Isi Email dan Password", Toast.LENGTH_SHORT).show();
                } else {
                    //Memunculkan ProgressDialog
                    progressDialog.setMessage("Mohon Tunggu..!!!");
                    progressDialog.show();
                    //eksekusi method Login User
                    LoginUser(email, password);
                }
            }
        });
        //Button Regis ketika di klik
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Pindah halaman ke Registrasi
                startActivity(new Intent(Login.this, Registrasi.class));
            }
        });
    }

    //method Login User dengan membanding kan dengan yang ada di database Auth
    private void LoginUser(String email, String password){
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Jika Berhasil
                        if (task.isSuccessful()){

                            //Berpindah ke halaman MainActiviy (Home)
                            startActivity(new Intent(Login.this, MainActivity.class));
                            progressDialog.dismiss();
                        }else {
                            //Jika gagal Muncul Toast akun belum terdaftar
                            Toast.makeText(Login.this, "Akun Belum Terdaftar", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }
        );
    }
}
