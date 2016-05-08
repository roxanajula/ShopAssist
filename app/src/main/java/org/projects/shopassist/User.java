package org.projects.shopassist;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by roxanajula on 5/2/16.
 */
public class User implements Parcelable{
    private String name;
    private String password;
    private static String TAG = "org.projects.shopassist";


    public User (String name, String password) {
        this.name = name;
        this.password = password;
        Log.d(TAG, "new user");
    }

    public User (Parcel in)
    {
        Log.d (TAG, "parcel in");
        name = in.readString ();
        password = in.readString ();
    }

    public String getName() {
        Log.d (TAG, "getName()");
        return name;
    }

    public void setName(String name) {
        Log.d (TAG, "setName()");
        this.name = name;
    }

    public String getPassword() {
        Log.d (TAG, "getPassword()");
        return password;
    }

    public void setPassword(String password) {
        Log.d (TAG, "setPassword()");
        this.password = password;
    }

    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>()
    {
        public User createFromParcel(Parcel in)
        {
            Log.d (TAG, "createFromParcel()");
            return new User(in);
        }

        public User[] newArray (int size)
        {
            Log.d (TAG, "createFromParcel() newArray ");
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        Log.d (TAG, "describe()");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d (TAG, "writeToParcel");
        dest.writeString (name);
        dest.writeString (password);
    }
}