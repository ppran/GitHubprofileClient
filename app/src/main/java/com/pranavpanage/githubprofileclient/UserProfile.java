package com.pranavpanage.githubprofileclient;

import android.os.Parcel;
import android.os.Parcelable;

public class UserProfile implements Parcelable{
    String username;
    String avatar_url;
    String html_url;
    String company;
    String location;
    String email;
    String bio;
    String public_repos;
    String created_on;
    String followers;
    String blog;
    String type;

    public UserProfile() {
    }

    protected UserProfile(Parcel in) {
        username = in.readString();
        avatar_url = in.readString();
        html_url = in.readString();
        company = in.readString();
        location = in.readString();
        email = in.readString();
        bio = in.readString();
        public_repos = in.readString();
        created_on = in.readString();
        followers=in.readString();
        blog=in.readString();
        type=in.readString();
    }

    public static final Creator<UserProfile> CREATOR = new Creator<UserProfile>() {
        @Override
        public UserProfile createFromParcel(Parcel in) {
            return new UserProfile(in);
        }

        @Override
        public UserProfile[] newArray(int size) {
            return new UserProfile[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String compnay) {
        this.company = compnay;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(String public_repos) {
        this.public_repos = public_repos;
    }

    public String getCreated_on() {
        return created_on;
    }

    public void setCreated_on(String created_on) {
        this.created_on = created_on;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getBlog() { return blog; }

    public void setBlog(String blog) { this.blog = blog;  }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFollowers() {
        return followers;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(avatar_url);
        dest.writeString(html_url);
        dest.writeString(company);
        dest.writeString(location);
        dest.writeString(email);
        dest.writeString(bio);
        dest.writeString(public_repos);
        dest.writeString(created_on);
        dest.writeString(followers);
        dest.writeString(blog);
        dest.writeString(type);
    }
}
