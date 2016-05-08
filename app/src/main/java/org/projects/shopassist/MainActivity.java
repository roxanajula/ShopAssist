package org.projects.shopassist;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayAdapter<Product> adapter;
    ListView listView;
    ArrayList<Product> bag = new ArrayList<Product>();
    User user;
    private static final String TAG = "org.projects.shopassist";

    public ArrayAdapter getMyAdapter()
    {
        return adapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //List View Setup
        listView = (ListView) findViewById(R.id.list);
        adapter =  new ArrayAdapter<Product>(this,
                android.R.layout.simple_list_item_checked,bag );
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        //Quantity Spinner Setup
        Spinner spinner = (Spinner) findViewById(R.id.quantitySpinner);
        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        //Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        if (savedInstanceState!=null)
        {
            bag = savedInstanceState.getParcelableArrayList("bag");
            user = savedInstanceState.getParcelable("user");

        } else {
            //Create an initial shopping list
            Product p1 = new Product("Bananas", 4);
            Product p2 = new Product("Milk", 2);
            Product p3 = new Product("Cream cheese", 1);
            bag.add(p1);
            bag.add(p2);
            bag.add(p3);
            //Create an initial user
            user = new User("Roxana", "password");
        }

    }

    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    //This method is called before our activity is destroyed
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        //ALWAYS CALL THE SUPER METHOD
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState()");
		/* Here we put code now to save the state */
        savedInstanceState.putParcelableArrayList("bag", bag);
        savedInstanceState.putParcelable("user", user);
        savedInstanceState.putParcelable("checkedItems", new SparseBooleanArrayParcelable(listView.getCheckedItemPositions()));
        }

    //this is called when our activity is recreated, but
    //AFTER our onCreate method has been called.
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState()");
        listView = (ListView) findViewById(R.id.list);
        adapter =  new ArrayAdapter<Product>(this,
                android.R.layout.simple_list_item_checked, bag);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        SparseBooleanArray checkedItemsToRestore = (SparseBooleanArray) savedInstanceState.getParcelable("checkedItems");
        for (int i=0;i<checkedItemsToRestore.size();i++){
            listView.setItemChecked(i,checkedItemsToRestore.valueAt(i));
        }
        adapter.notifyDataSetChanged();
    }

    public void onClickAddToBag(View view) {
        EditText item= (EditText) findViewById(R.id.itemText);
        Spinner quantity=(Spinner) findViewById(R.id.quantitySpinner);
        //EditText quantity= (EditText) findViewById(R.id.quantityText);
        Product newProduct= new Product(item.getText().toString(),Integer.parseInt(quantity.getSelectedItem().toString()));
        bag.add(newProduct);
        //quantity.setText(null);
        String nameText = "You added " + newProduct.getItem() + " to your shopping list.";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, nameText, duration);
        toast.show();
        //Reset the Input Text and Spinner to initial values
        item.setText(null);
        quantity.setSelection(0);
        getMyAdapter().notifyDataSetChanged();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.clearList) {
            ConfirmationDialog dialog = new ConfirmationDialog() {
                @Override
                protected void positiveClick() {
                    bag.clear();
                    String nameText = "Your list was cleared.";
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, nameText, duration);
                    toast.show();
                    getMyAdapter().notifyDataSetChanged();
                }

            };
            dialog.show(getFragmentManager(), "MyFragment");
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.actionSettings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
