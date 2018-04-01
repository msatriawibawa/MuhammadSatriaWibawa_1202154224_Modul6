package com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6.Model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TerbaruFragment extends Fragment {

    //Deklarasi Variable
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ArrayList<Post> listPosts;
    DatabaseReference database;

    public TerbaruFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate untuk layout tersebut
        View view = inflater.inflate(R.layout.fragment_terbaru, container, false);
        //Inisialisasi Firebase (Database)
        database = FirebaseDatabase.getInstance().getReference("Post");

        recyclerView = view.findViewById(R.id.rv_terbaru);
        //buat Array Baru
        listPosts = new ArrayList<>();

        //Menampilkan View
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        database.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listPosts.clear();
                //ambil post dari database child
                for (DataSnapshot posts : dataSnapshot.getChildren()){
                    Post post = posts.getValue(Post.class);
                    listPosts.add(post);
                }
                //Ditampilkan ke recyclerView
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                PostAdapter postAdapter = new PostAdapter(getContext(), listPosts);
                recyclerView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
