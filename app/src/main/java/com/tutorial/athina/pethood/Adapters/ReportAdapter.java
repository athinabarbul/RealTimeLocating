package com.tutorial.athina.pethood.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tutorial.athina.pethood.DogDetailsAndChatActivity;
import com.tutorial.athina.pethood.ItemClickListener;
import com.tutorial.athina.pethood.ListOnline;
import com.tutorial.athina.pethood.MessageActivity;
import com.tutorial.athina.pethood.Models.Dog;
import com.tutorial.athina.pethood.Models.MissingDog;
import com.tutorial.athina.pethood.Models.Owner;
import com.tutorial.athina.pethood.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder> {


    private Context mContext;
    private List<MissingDog> mMissingDog;
    private DatabaseReference ownerRef,dogRef;
    String firebaseUser;




    public ReportAdapter(Context mContext, List<MissingDog> mMissingDog) {
        this.mContext = mContext;
        this.mMissingDog = mMissingDog;

    }

    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.chat_report, parent, false);
        return new ReportAdapter.ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final MissingDog missingDog = mMissingDog.get(position);
        ownerRef = FirebaseDatabase.getInstance().getReference().child("Owners");
        ownerRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Owner owner = data.getValue(Owner.class);
                    if(owner.getEmail().equals(missingDog.getSender())){
                        Glide.with(mContext).load(owner.getProfileImageUrl()).into(holder.user_pic);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        dogRef = FirebaseDatabase.getInstance().getReference().child("dog");
        dogRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data : dataSnapshot.getChildren()){
                    Dog dog = data.getValue(Dog.class);
                    if(dog.getDogOwner().equals(missingDog.getSender())){
                        Glide.with(mContext).load(dog.getDogPhoto()).into(holder.dog_pic);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        holder.show_reporter.setText(missingDog.getSender().substring(0, missingDog.getSender().indexOf("@")));
        holder.show_message_report.setText(missingDog.getMessage());

        holder.itemClickListener = new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if(!mMissingDog.get(position).getSender().equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
                    Intent chatIntent = new Intent(mContext,MessageActivity.class);
                    chatIntent.putExtra("myUser",FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    chatIntent.putExtra("dogOwner",mMissingDog.get(position).getSender());
                    mContext.startActivity(chatIntent);
                }

                }
            };


    }

    @Override
    public int getItemCount() {
        return mMissingDog.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder implements ItemClickListener,View.OnClickListener {

        public TextView show_reporter, show_message_report;
        public ImageView user_pic, dog_pic;
        ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            user_pic = itemView.findViewById(R.id.userPicture);
            show_reporter = itemView.findViewById(R.id.show_reporter);
            show_message_report = itemView.findViewById(R.id.show_message_report);
            dog_pic = itemView.findViewById(R.id.dogPictureLost);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view, int position) {
            itemClickListener.onClick(view, getAdapterPosition());
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition());
        }
    }


}
