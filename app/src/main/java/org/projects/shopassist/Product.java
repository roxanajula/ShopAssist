package org.projects.shopassist;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * Created by roxanajula on 5/2/16.
 */
public class Product implements Parcelable {
    private static String TAG = "org.projects.shopassist" ;
    private String item;
    private int quantity;

    public Product(String item, int quantity){
        this.item=item;
        this.quantity=quantity;
        Log.d(TAG, "new product");
    }

    public Product (Parcel in){
        Log.d (TAG, "parcel in");
        item = in.readString ();
        quantity = in.readInt();
    }

    public String getItem() {
        Log.d (TAG, "getItem()");
        return item;
    }

    public void setItem(String item) {
        Log.d (TAG, "setItem()");
        this.item = item;
    }

    public int getQuantity() {
        Log.d (TAG, "getQuantity()");
        return quantity;
    }

    public void setQuantity(byte quantity) {
        Log.d (TAG, "setQuantity()");
        this.quantity = quantity;
    }

    public static final Parcelable.Creator<Product> CREATOR
            = new Parcelable.Creator<Product>()
    {
        public Product createFromParcel(Parcel in)
        {
            Log.d (TAG, "createFromParcel()");
            return new Product(in);
        }

        public Product[] newArray (int size)
        {
            Log.d (TAG, "createFromParcel() newArray ");
            return new Product[size];
        }
    };

    public String toString(){
        return this.quantity+" "+this.item;
    }

    @Override
    public int describeContents() {
        Log.d (TAG, "describe()");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Log.d (TAG, "writeToParcel");
        dest.writeString (item);
        dest.writeInt (quantity);
    }
}
