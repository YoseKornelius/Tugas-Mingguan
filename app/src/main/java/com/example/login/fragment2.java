package com.example.login;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class fragment2 extends Fragment {
    private RecyclerView mRecyclerView;
//    private  RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList <CardviewItem> cardviewList;

    View view;
    public fragment2() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment2,container,false);
        mRecyclerView = (RecyclerView) view.findViewById( R.id.recyclerView );
        mRecyclerView.setHasFixedSize(  true);
        CardviewAdapter mAdapter = new CardviewAdapter(cardviewList,getContext() );
        mRecyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        //mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter( mAdapter );
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        cardviewList = new ArrayList<>( );
        cardviewList.add( new CardviewItem(R.drawable.imgfilm1,
                "Terrace House: BOYS x Girls Next Door",
                "ini film wibu"));
        cardviewList.add( new CardviewItem(R.drawable.imgfilm2,
                "Terrace House: BOYS & Girls In The City",
                "ini film wibu"));
        cardviewList.add( new CardviewItem(R.drawable.imgfilm3,
                "Terrace House: Aloha State",
                "ini film wibu"));
        cardviewList.add( new CardviewItem(R.drawable.imgfilm4,
                "Terrace House: Opening New Doors",
                "ini film wibu"));
        cardviewList.add( new CardviewItem(R.drawable.imgfilm5,
                "Terrace House: Tokyo 2019-2020",
                "ini film wibu"));
        cardviewList.add( new CardviewItem(R.drawable.imgfilm6,
                "Samurai Gourmet",
                "ini film wibu"));
        cardviewList.add( new CardviewItem(R.drawable.imgfilm7,
                "Midnight Diner: Tokyo Stories",
                "ini film wibu"));
        cardviewList.add( new CardviewItem(R.drawable.imgfilm8,
                "Her Blue Sky",
                "ini film wibu"));
        cardviewList.add( new CardviewItem(R.drawable.imgfilm9,
                "Violet Evergarden Gaiden Movie",
                "ini film wibu"));
    }
}