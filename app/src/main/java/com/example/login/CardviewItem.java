package com.example.login;

import android.os.Parcel;
import android.os.Parcelable;

public class CardviewItem implements Parcelable {
    private int mImageResource;
    private String mText1;
    private String mText2;

    public  CardviewItem(int imageResource, String text1, String text2){
        mImageResource = imageResource;
        mText1=text1;
        mText2=text2;
    }

    protected CardviewItem(Parcel in) {
        mImageResource = in.readInt();
        mText1 = in.readString();
        mText2 = in.readString();
    }

    public static final Creator<CardviewItem> CREATOR = new Creator<CardviewItem>() {
        @Override
        public CardviewItem createFromParcel(Parcel in) {
            return new CardviewItem( in );
        }

        @Override
        public CardviewItem[] newArray(int size) {
            return new CardviewItem[size];
        }
    };

    public int getmImageResource(){
        return mImageResource;
    }
    public String getText1(){
        return mText1;
    }
    public String getText2(){
        return mText2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt( mImageResource );
        dest.writeString( mText1 );
        dest.writeString( mText2 );
    }
}
