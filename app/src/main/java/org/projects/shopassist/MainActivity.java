package org.projects.shopassist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
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

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "org.projects.shopassist";
    private Firebase mRef = new Firebase("https://shopassist.firebaseio.com/items");
    FirebaseListAdapter<Product> adapter;
    private ListView listView;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.i(TAG, "onCreate");

        listView = (ListView) findViewById(R.id.list);

        Spinner spinner = (Spinner) findViewById(R.id.quantitySpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.quantity_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        User user = new User("Roxana","roxana.jula@gmail.com");
    }

    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
        adapter = new FirebaseListAdapter<Product>(
                        this,
                        Product.class,
                        android.R.layout.simple_list_item_multiple_choice,
                        mRef
                ) {
                    @Override
                    protected void populateView(View view, Product product, int i) {
                        TextView textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setText(product.toString());
                    }
                };
        listView.setAdapter(adapter);
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

    public void onClickAddToBag(View view) {
        EditText item= (EditText) findViewById(R.id.itemText);
        Spinner quantity=(Spinner) findViewById(R.id.quantitySpinner);
        Product newProduct= new Product(item.getText().toString(),Integer.parseInt(quantity.getSelectedItem().toString()));
        mRef.push().setValue(newProduct);
        final String productName = newProduct.getItem();
        Snackbar snackbar = Snackbar
                .make(view, productName + " was added to your list.", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Firebase itemRef = adapter.getRef(listView.getCount()-1);
                        itemRef.removeValue();
                        Snackbar snackbar1 = Snackbar.make(view, productName + " was deleted from your list.", Snackbar.LENGTH_SHORT);
                        snackbar1.show();
                    }
                });

        snackbar.show();
        //Reset the Input Text and Spinner to initial values
        item.setText(null);
        quantity.setSelection(0);
    }

    public void onClickDeleteChecked(View view) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        SparseBooleanArray checked = listView.getCheckedItemPositions();
        int itemCount = listView.getCount();
        if (checked.size()!=0) {
            for (int i = itemCount - 1; i >= 0; i--) {
                if (checked.get(i)) {
                    Firebase itemRef = adapter.getRef(i);
                    itemRef.removeValue();
                }
            }
            String nameText = "Checked item(s) deleted.";
            Toast toast = Toast.makeText(context, nameText, duration);
            toast.show();
        } else {
            String nameText = "No items checked.";
            Toast toast = Toast.makeText(context, nameText, duration);
            toast.show();
        }
        checked.clear();
        adapter.notifyDataSetChanged();
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
                    mRef.setValue(null);
                    String nameText = "Your list was cleared.";
                    Context context = getApplicationContext();
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, nameText, duration);
                    toast.show();
                }

            };
            dialog.show(getFragmentManager(), "MyFragment");
        }

        if (id == R.id.actionSettings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivityForResult(intent, 1);
        }

        if (id == R.id.action_share){
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, convertListToString());
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public String convertListToString()
    {
        String result = "Shopping List \n";
        for (int i = 0; i<adapter.getCount();i++)
        {
            Product p = (Product) adapter.getItem(i);
            result = result + "- " + p.toString()+"\n";
        }
        System.out.print(result);
        return result;
    }
}
