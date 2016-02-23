package com.woolf.dribbleviewer.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class UserData implements Parcelable {
    public static final Parcelable.Creator<UserData> CREATOR = new Parcelable.Creator<UserData>() {
        public UserData createFromParcel(Parcel source) {
            return new UserData(source);
        }

        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };
    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("username")
    private String mUserName;
    @SerializedName("html_url")
    private String mHtmlAvatar;
    @SerializedName("avatar_url")
    private String mAvatarUrl;
    @SerializedName("bio")
    private String mBio;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("buckets_count")
    private int mBucketsCount;
    @SerializedName("comments_received_count")
    private int mCommentsReceivedCount;
    @SerializedName("followers_count")
    private int mFollowerssCount;
    @SerializedName("followings_count")
    private int mFollowingsCount;
    @SerializedName("likes_count")
    private int mLikesCount;
    @SerializedName("likes_received_count")
    private int mLikesReceivedCountCount;
    @SerializedName("projects_count")
    private int mProjectsCount;
    @SerializedName("rebounds_received_count")
    private int mReboundsReceivedCound;
    @SerializedName("shots_count")
    private int mShotsCount;
    @SerializedName("teams_count")
    private int mTeamsCount;
    @SerializedName("can_upload_shot")
    private boolean mCanUploadCount;
    @SerializedName("type")
    private String mType;
    @SerializedName("pro")
    private boolean mPro;
    @SerializedName("created_at")
    private Date mCreatedAt;
    @SerializedName("updated_at")
    private Date mUpdatedAt;

    public UserData() {
    }

    protected UserData(Parcel in) {
        this.mId = in.readInt();
        this.mName = in.readString();
        this.mUserName = in.readString();
        this.mHtmlAvatar = in.readString();
        this.mAvatarUrl = in.readString();
        this.mBio = in.readString();
        this.mLocation = in.readString();
        this.mBucketsCount = in.readInt();
        this.mCommentsReceivedCount = in.readInt();
        this.mFollowerssCount = in.readInt();
        this.mFollowingsCount = in.readInt();
        this.mLikesCount = in.readInt();
        this.mLikesReceivedCountCount = in.readInt();
        this.mProjectsCount = in.readInt();
        this.mReboundsReceivedCound = in.readInt();
        this.mShotsCount = in.readInt();
        this.mTeamsCount = in.readInt();
        this.mCanUploadCount = in.readByte() != 0;
        this.mType = in.readString();
        this.mPro = in.readByte() != 0;
        long tmpMCreatedAt = in.readLong();
        this.mCreatedAt = tmpMCreatedAt == -1 ? null : new Date(tmpMCreatedAt);
        long tmpMUpdatedAt = in.readLong();
        this.mUpdatedAt = tmpMUpdatedAt == -1 ? null : new Date(tmpMUpdatedAt);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getHtmlAvatar() {
        return mHtmlAvatar;
    }

    public void setHtmlAvatar(String htmlAvatar) {
        mHtmlAvatar = htmlAvatar;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public int getBucketsCount() {
        return mBucketsCount;
    }

    public void setBucketsCount(int bucketsCount) {
        mBucketsCount = bucketsCount;
    }

    public int getCommentsReceivedCount() {
        return mCommentsReceivedCount;
    }

    public void setCommentsReceivedCount(int commentsReceivedCount) {
        mCommentsReceivedCount = commentsReceivedCount;
    }

    public int getFollowerssCount() {
        return mFollowerssCount;
    }

    public void setFollowerssCount(int followerssCount) {
        mFollowerssCount = followerssCount;
    }

    public int getFollowingsCount() {
        return mFollowingsCount;
    }

    public void setFollowingsCount(int followingsCount) {
        mFollowingsCount = followingsCount;
    }

    public int getLikesCount() {
        return mLikesCount;
    }

    public void setLikesCount(int likesCount) {
        mLikesCount = likesCount;
    }

    public int getLikesReceivedCountCount() {
        return mLikesReceivedCountCount;
    }

    public void setLikesReceivedCountCount(int likesReceivedCountCount) {
        mLikesReceivedCountCount = likesReceivedCountCount;
    }

    public int getProjectsCount() {
        return mProjectsCount;
    }

    public void setProjectsCount(int projectsCount) {
        mProjectsCount = projectsCount;
    }

    public int getReboundsReceivedCound() {
        return mReboundsReceivedCound;
    }

    public void setReboundsReceivedCound(int reboundsReceivedCound) {
        mReboundsReceivedCound = reboundsReceivedCound;
    }

    public int getShotsCount() {
        return mShotsCount;
    }

    public void setShotsCount(int shotsCount) {
        mShotsCount = shotsCount;
    }

    public int getTeamsCount() {
        return mTeamsCount;
    }

    public void setTeamsCount(int teamsCount) {
        mTeamsCount = teamsCount;
    }

    public boolean isCanUploadCount() {
        return mCanUploadCount;
    }

    public void setCanUploadCount(boolean canUploadCount) {
        mCanUploadCount = canUploadCount;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public boolean isPro() {
        return mPro;
    }

    public void setPro(boolean pro) {
        mPro = pro;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(Date createdAt) {
        mCreatedAt = createdAt;
    }

    public Date getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        mUpdatedAt = updatedAt;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mName);
        dest.writeString(this.mUserName);
        dest.writeString(this.mHtmlAvatar);
        dest.writeString(this.mAvatarUrl);
        dest.writeString(this.mBio);
        dest.writeString(this.mLocation);
        dest.writeInt(this.mBucketsCount);
        dest.writeInt(this.mCommentsReceivedCount);
        dest.writeInt(this.mFollowerssCount);
        dest.writeInt(this.mFollowingsCount);
        dest.writeInt(this.mLikesCount);
        dest.writeInt(this.mLikesReceivedCountCount);
        dest.writeInt(this.mProjectsCount);
        dest.writeInt(this.mReboundsReceivedCound);
        dest.writeInt(this.mShotsCount);
        dest.writeInt(this.mTeamsCount);
        dest.writeByte(mCanUploadCount ? (byte) 1 : (byte) 0);
        dest.writeString(this.mType);
        dest.writeByte(mPro ? (byte) 1 : (byte) 0);
        dest.writeLong(mCreatedAt != null ? mCreatedAt.getTime() : -1);
        dest.writeLong(mUpdatedAt != null ? mUpdatedAt.getTime() : -1);
    }
}
