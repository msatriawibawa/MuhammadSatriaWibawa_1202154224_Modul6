package com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6.Model.Post;
import com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddPost extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    //Deklarasi variable
    EditText eTitle, eDesk;
    ImageView imgAdd;
    Button btnChoose;
    ProgressDialog progressDialog;

    //Deklarasi variable Database
    DatabaseReference databasePost;
    //Deklarasi variable Firebase
    FirebaseAuth auth;
    //Deklarasi variable Storage
    private StorageReference storageReference;
    //Deklarasi URI
    private Uri imgUri;
    //Deklarasi Query
    Query databaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);
        imgUri = null;

        //Membuat ProgressDialog
        progressDialog = new ProgressDialog(this);

        //Inisialisasi Firebase (Auth,Storage,Database)
        auth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference().child("Photo");
        databasePost = FirebaseDatabase.getInstance().getReference("Post");
        databaseUser = FirebaseDatabase.getInstance().getReference("User");


        eTitle = findViewById(R.id.eTitle);
        eDesk = findViewById(R.id.eDesk);
        imgAdd = findViewById(R.id.imgAdd);
        btnChoose = findViewById(R.id.btnChoose);

        //Button Pilih Gambar
        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent untuk Set Gambar
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    //Button Ceklish ketika di klik eksekusi TambahPost()
    public void add(View view){
        progressDialog.setMessage("Uploading Image...");
        progressDialog.show();

        TambahPost();

    }

    //Method Tambah Post
    private void TambahPost() {
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.child(auth.getCurrentUser().getUid()).getValue(User.class);

                //Get data
                final String name = user.getUsername();
                final String title = eTitle.getText().toString();
                final String postMsg = eDesk.getText().toString();
                final String idImage = databasePost.push().getKey();
                final String idUser = auth.getCurrentUser().getUid();

                if (imgUri != null && !TextUtils.isEmpty(name)){
                    StorageReference img = storageReference.child(idUser + ".jpg");

                    img.putFile(imgUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            //Jika Sukses
                            if (task.isSuccessful()){
                                String download_url = task.getResult().getDownloadUrl().toString();
                                long timestamp = System.currentTimeMillis();
                                //Data yang di imput ke database
                                Post post = new Post(idImage, idUser, name, title, postMsg, download_url, timestamp*(-1));
                                databasePost.child(idUser).setValue(post);
                                progressDialog.dismiss();
                            }else {
                                Toast.makeText(AddPost.this, "Error : "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                    //Muncul Toast Upload berhasil
                    Toast.makeText(AddPost.this, "Upload Sukses", Toast.LENGTH_SHORT).show();
                    //Intent pindah ke halaman MainActivity
                    Intent i = new Intent(AddPost.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    //Muncul Toast Bila ada kesalahan
                    Toast.makeText(AddPost.this, "Mohon untuk isi data dan pilih gambar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Untuk Set ImageView dengan imagenya
        if (requestCode == PICK_IMAGE){
            imgUri = data.getData();
            imgAdd.setImageURI(imgUri);
        }
    }
}
