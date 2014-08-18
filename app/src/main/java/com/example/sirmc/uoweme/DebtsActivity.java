package com.example.sirmc.uoweme;

import android.app.Activity;
import android.app.ActionBar;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sirmc.uoweme.R;

import java.util.List;

import de.timroes.android.listview.EnhancedListView;

public class DebtsActivity extends Activity implements AddDebtDialog.AddDebtDialogListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_debts);

        final EnhancedListView listView = (EnhancedListView) findViewById(R.id.listView);

        List<Debt> debts = Debt.listAll(Debt.class);
        listView.setAdapter(new MyAdapter(this, debts));



        listView.setDismissCallback(new EnhancedListView.OnDismissCallback() {
            @Override
            public EnhancedListView.Undoable onDismiss(EnhancedListView enhancedListView, final int position) {

                final MyAdapter adapter = (MyAdapter) listView.getAdapter();
                final Debt debt = (Debt) adapter.getItem(position);

                adapter.remove(debt);


                //listView.delete(position);

                return new EnhancedListView.Undoable() {
                    @Override
                    public void undo() {
                        adapter.insert(debt, position);
                    }

                    @Override public String getTitle() {
                        return "Deleted '" + debt.name + "'";
                    }

                    @Override public void discard() {

                        debt.delete();
                    }
                };


                //return null;
            }


        });

        listView.enableSwipeToDismiss();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /*
                Debt debt = (Debt) listView.getAdapter().getItem(position);

                Toast.makeText(getApplicationContext(), debt.name, Toast.LENGTH_SHORT).show();
                debt.delete();

                ItemFragment fr = (ItemFragment) getFragmentManager().findFragmentByTag("list");
                MyAdapter adapter = (MyAdapter) listView.getAdapter();

                adapter.remove(debt);
                adapter.notifyDataSetChanged();
                */

            }
        });
        /*
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    //.add(R.id.container, new PlaceholderFragment())
                    .add(R.id.container, new ItemFragment(), "list")
                    .commit();
        }
        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.debts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_add) {

            /*
            Debt debt = new Debt("Name", 350);
            debt.save();
            ItemFragment fr = (ItemFragment) getFragmentManager().findFragmentByTag("list");
            MyAdapter adapter = (MyAdapter) fr.getListView().getAdapter();

            adapter.add(debt);
            adapter.notifyDataSetChanged();
            */

            AddDebtDialog dialog = new AddDebtDialog();
            dialog.show(getFragmentManager(), "AddDialog");

            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //View view =  (View) ((AddDebtDialog) dialog).getDialog();

        EditText nameField = (EditText) ((AddDebtDialog) dialog).getDialog().findViewById(R.id.dialog_debt_name);
        EditText amountField = (EditText) ((AddDebtDialog) dialog).getDialog().findViewById(R.id.dialog_debt_amount);

        String name = String.valueOf(nameField.getText());
        double amount = Double.parseDouble(amountField.getText().toString());

        Debt debt = new Debt(name, amount);
        debt.save();

        //ItemFragment fr = (ItemFragment) getFragmentManager().findFragmentByTag("list");
        EnhancedListView listView = (EnhancedListView) findViewById(R.id.listView);
        MyAdapter adapter = (MyAdapter) listView.getAdapter();

        adapter.add(debt);
        adapter.notifyDataSetChanged();
        Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }


}
