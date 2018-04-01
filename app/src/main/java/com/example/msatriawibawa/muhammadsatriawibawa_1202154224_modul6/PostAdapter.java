package com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.msatriawibawa.muhammadsatriawibawa_1202154224_modul6.Model.Post;

import java.util.ArrayList;

/**
 * Created by M. Satria Wibawa on 01/04/2018.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    //Deklarasi Context dan ArrayList pada Post
    Context context;
    ArrayList<Post> postsList;

    public PostAdapter(Context context, ArrayList<Post> postsList) {
        this.context = context;
        this.postsList = postsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Post post = postsList.get(position);

        //Get Username, gambar, Title, dan Deks
        holder.mUsername.setText(post.getUsername());
        Glide.with(context).load(post.getImage()).into(holder.mImagePost);
        holder.mTitlePost.setText(post.getJudulPost());
        holder.mPost.setText(post.getPost());

      holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
     //           Intent i = new Intent(context, CommentPostActivity.class);
     //           i.putExtra("idImage", post.getIdImage());
     //           i.putExtra("username", post.getUsername());
     //           i.putExtra("image", post.getImage());
      //          i.putExtra("title", post.getJudulPost());
     //           i.putExtra("desc", post.getPost());
     //           context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        //Deklarasi Variable
        TextView mUsername;
        TextView mTitlePost;
        TextView mPost;
        ImageView mImagePost;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            mUsername = itemView.findViewById(R.id.tv_username);
            mTitlePost = itemView.findViewById(R.id.tv_title_post);
            mPost = itemView.findViewById(R.id.tv_post);
            mImagePost = itemView.findViewById(R.id.img_post);
            cardView = itemView.findViewById(R.id.cvPost);
        }
    }
}
