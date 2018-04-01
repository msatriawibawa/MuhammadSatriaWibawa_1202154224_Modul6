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

public class Registrasi extends AppCompatActivity {

    //Deklarasi Variable
    EditText mEmail, mPass;
    Button btnLogin, btnRegis;

    //Deklarasi Variable Firebase Auth, Database, ProgressDialog
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);

        //Inisialisasi Firebase (Auth
        auth = FirebaseAuth.getInstance();
        //Membuat Progress Dialog baru
        progressDialog = new ProgressDialog(this);

        mEmail = findViewById(R.id.Eemail);
        mPass = findViewById(R.id.Epass);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegis = findViewById(R.id.btnRegis);

        //Inisialisasi Firebase (Database)
        databaseReference = FirebaseDatabase.getInstance().getReference("User");

        //Jika Button Regis atau Daftar di klik
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get isi EditText
                String email = mEmail.getText().toString();
                String password = mPass.getText().toString();

                //Jika email dan password kosong muncul toast Tolong Isi Email dan Password"
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Registrasi.this, "Tolong Isi Email dan Password", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Mohon Tunggu..!!!");
                    progressDialog.show();
                    //eksekusi method RegisUser
                    RegisUser(email, password);
                }
            }
        });
    }

    private void RegisUser(final String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //Jika Berhasil
                        if (task.isSuccessful()){
                            //ambil data user yang sedang login
                            String id = auth.getCurrentUser().getUid();
                            //Buat pemisah agar tidak ada @gmail.com
                            String[] username = email.split("@");
                            //Bikin Database dengan isi username dan email
                            User user = new User(username[0], email);
                            //Ambil referensi dari user yang sedang login dan masukan ke dalam database User
                            databaseReference.child(id).setValue(user);
                            startActivity(new Intent(Registrasi.this, Login.class));
                            progressDialog.dismiss();
                        }else {
                            //toast pesan
                            Toast.makeText(Registrasi.this, "Terjadi kesalahan mohon coba lagi", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }
        );
    }
}
