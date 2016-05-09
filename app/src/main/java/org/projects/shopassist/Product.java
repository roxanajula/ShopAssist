package org.projects.shopassist;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by roxanajula on 5/2/16.
 */
public class Product implements Serializable {
    private String item;
    private int quantity;

    public Product(String item, int quantity){
        this.item=item;
        this.quantity=quantity;
    }

    public Product()
    {

    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(byte quantity) {
        this.quantity = quantity;
    }

    public String toString(){
        return this.quantity+" "+this.item;
    }

}
