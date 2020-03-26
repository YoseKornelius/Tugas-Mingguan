package com.example.login;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardviewAdapter extends RecyclerView.Adapter<CardviewAdapter.CardViewHolder> {
    Context context;
    ArrayList<CardviewItem> mCardviewList;
    private onItemClickListener mlistener;

    public void setOnItemClickListener(onItemClickListener listener){
        mlistener = listener;
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    public CardviewAdapter(ArrayList<CardviewItem> cardViewList,Context context){
        mCardviewList=cardViewList;
        this.context = context;

    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(  parent.getContext()).inflate( R.layout.cardview_item, parent, false );
        CardViewHolder cvh = new CardViewHolder( view, mlistener );
        return cvh;
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        CardviewItem currentItem =mCardviewList.get( position );

        holder.mImageView.setImageResource( currentItem.getmImageResource() );
        holder.mTextView1.setText( currentItem. getText1());
        holder.mTextView2.setText( currentItem. getText2());
    }

    @Override
    public int getItemCount() {
        return mCardviewList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextView1;
        public TextView mTextView2;

        public CardViewHolder(@NonNull View itemView, final onItemClickListener listener) {
            super( itemView );

            mImageView = itemView.findViewById( R.id.imageView );
            mTextView1 = itemView.findViewById( R.id.textView );
            mTextView2 = itemView.findViewById( R.id.textView2 );
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((listener != null)) {
                        int postion = getAdapterPosition();
                        if (postion != RecyclerView.NO_POSITION){
                            listener.onItemClick( postion );
                        }
                    }
                }
            } );
        }
    }
}
