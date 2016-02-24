package com.woolf.dribbleviewer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class ShotImagesData implements Parcelable {
    public static final Parcelable.Creator<ShotImagesData> CREATOR = new Parcelable.Creator<ShotImagesData>() {
        public ShotImagesData createFromParcel(Parcel source) {
            return new ShotImagesData(source);
        }

        public ShotImagesData[] newArray(int size) {
            return new ShotImagesData[size];
        }
    };
    @SerializedName("hidpi")
    private String mHiDpi;
    @SerializedName("normal")
    private String mNormal;
    @SerializedName("teaser")
    private String mTeaser;

    public ShotImagesData() {
    }

    public ShotImagesData(String hiDpi, String normal, String teaser) {
        mHiDpi = hiDpi;
        mNormal = normal;
        mTeaser = teaser;
    }

    protected ShotImagesData(Parcel in) {
        this.mHiDpi = in.readString();
        this.mNormal = in.readString();
        this.mTeaser = in.readString();
    }

    public String getHiDpi() {
        return mHiDpi;
    }

    public void setHiDpi(String hiDpi) {
        mHiDpi = hiDpi;
    }

    public String getNormal() {
        return mNormal;
    }

    public void setNormal(String normal) {
        mNormal = normal;
    }

    public String getTeaser() {
        return mTeaser;
    }

    public void setTeaser(String teaser) {
        mTeaser = teaser;
    }

    public String getImageUrl() {
        if (mHiDpi != null) {
            return mHiDpi;
        } else {
            if (mNormal != null) {
                return mNormal;
            } else {
                return mTeaser;
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mHiDpi);
        dest.writeString(this.mNormal);
        dest.writeString(this.mTeaser);
    }
}
