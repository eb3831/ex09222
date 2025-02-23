package com.example.ex09222;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnCreateContextMenuListener, AdapterView.OnItemClickListener
{
    Intent si;
    LinearLayout myDialog;
    private AlertDialog.Builder adb;
    TextView tvDorQ, tvA1, tvSn, tvN, tvDifference;
    String firstValueStr = "", diffStr = "";
    EditText dgFirstValueEt, dgDiffEt;
    Switch swAD;
    int seriesType = 0;
    double firstValue, diff, seriesSum;
    Double [] seriesArr;
    String [] disArr;
    ListView lv;
    ArrayAdapter<String> adp;

    DialogInterface.OnClickListener onDialogBtnClick = new DialogInterface.OnClickListener()
    {

        /**
         * This function reacts to the click on one of the dialog buttons - resets the series data,
         * or cancels the action, or calculates the series values.
         * <p>
         *
         * @param dialog The dialog that received the click.
         * @param which The constant of the button that was clicked.
         */
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            if (which == DialogInterface.BUTTON_POSITIVE)
            {
                getSeriesType();
                firstValueStr = dgFirstValueEt.getText().toString();
                diffStr = dgDiffEt.getText().toString();

                if ((isValidNum(firstValueStr)) && (isValidNum(diffStr)))
                {
                    firstValue = Double.parseDouble(firstValueStr);
                    diff = Double.parseDouble(diffStr);

                    createSeriesArr(seriesType, firstValue, diff, seriesArr);
                    lv.setAdapter(adp);

                    tvA1.setText(firstValueStr);
                    tvDifference.setText(diffStr);
                    tvN.setText("");
                    tvSn.setText("");

                    if(seriesType == 0)
                        tvDorQ.setText("q");

                    else
                        tvDorQ.setText("d");
                }

                else
                {
                    Toast.makeText(MainActivity.this, "Invalid data, please try again!",
                        Toast.LENGTH_LONG).show();
                }
            }

            // Cancel button
            else if(which == DialogInterface.BUTTON_NEGATIVE)
                dialog.cancel();

            // Reset button
            else
                resetSeriesData();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seriesType = 0;
        firstValueStr = diffStr = "";
        firstValue = diff = seriesSum = 0;
        seriesArr = new Double[20];

        tvDorQ = findViewById(R.id.tvDorQ);
        tvA1 = findViewById(R.id.tvA1);
        tvDifference = findViewById(R.id.tvDifference);
        tvSn = findViewById(R.id.tvSn);
        tvN = findViewById(R.id.tvN);
        lv = (ListView) findViewById(R.id.lv);

        adp = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, disArr);
        lv.setOnItemClickListener(this);
    }

    /**
     * gets the series data from the dialog and updates accordingly
     *
     * @param view The view that triggered the click event
     */
    public void getSeriesData(View view)
    {
        myDialog = (LinearLayout) getLayoutInflater().inflate(R.layout.my_dialog, null);

        dgFirstValueEt = (EditText) myDialog.findViewById(R.id.dgFirstValueEt);
        dgDiffEt = (EditText) myDialog.findViewById(R.id.dgDiffEt);
        swAD = (Switch) myDialog.findViewById(R.id.swAD);


        adb = new AlertDialog.Builder(this);

        adb.setView(myDialog);
        adb.setTitle("Series data input");
        adb.setCancelable(false);

        adb.setPositiveButton("Apply", onDialogBtnClick);
        adb.setNeutralButton("Reset", onDialogBtnClick);
        adb.setNegativeButton("Cancel", onDialogBtnClick);

        if(seriesType == 0)
            swAD.setChecked(false);
        else
            swAD.setChecked(true);

        dgFirstValueEt.setText(firstValueStr);
        dgDiffEt.setText(diffStr);

        adb.show();
    }


    /**
     *  the function checks the series type and updates seriesType accordingly
     */
    public void getSeriesType()
    {
        if (swAD.isChecked())
            seriesType = 1;

        else
            seriesType = 0;
    }


    /**
     * the function checks if the input from the user is valid
     * @param input the input from the user
     * @return true if valid num else false
     */
    public boolean isValidNum(String input)
    {
        return !((input.equals("")) ||
                (input.equals("-")) ||
                (input.equals(".")) ||
                (input.equals("+")) ||
                (input.equals("+.")) ||
                (input.equals("-.")));
    }


    /**
     * the function creates a series array of the first 20 values in the series.
     *
     * @param type the series type
     * @param first the first value in the series
     * @param d D or Q
     * @param arr the series array
     *
     */
    public void createSeriesArr(int type, double first, double d, Double[] arr)
    {
        if (type == 0)
        {
            tvDorQ.setText("q:");
            for (int i = 1; i < 21; i++)
            {
                arr[i - 1] = first * Math.pow(d, i - 1);
                disArr[i - 1] =  differentView(arr[i - 1]);
            }
        }

        else
        {
            tvDorQ.setText("d:");
            for (int i = 1; i < 21; i++)
            {
                arr[i - 1] = first + ((i - 1) * d);
                disArr[i - 1] =  differentView(arr[i - 1]);
            }
        }
    }


    /**
     * the function handles a click on the list view and updates the series sum and place.
     *
     * @param adapterView The AdapterView where the click happened.
     * @param view The view within the AdapterView that was clicked (this
     *            will be a view provided by the adapter)
     * @param i The position of the view in the adapter.
     * @param l The row id of the item that was clicked.
     *
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
    {
        if (seriesType == 0)
            seriesSum = (firstValue * (Math.pow(diff, i + 1) - 1)) / (diff - 1);

        else
            seriesSum = ((i + 1) * (firstValue + seriesArr[i])) / 2;

        tvSn.setText(differentView(seriesSum));
        tvN.setText(String.valueOf(i + 1));
    }

    /**
     * the function resets all of the series data.
     */
    public void resetSeriesData()
    {
        lv.setAdapter(null);

        dgFirstValueEt.setText("");
        dgDiffEt.setText("");

        tvA1.setText("");
        tvDifference.setText("");
        tvN.setText("");
        tvSn.setText("");

        firstValueStr = diffStr = "";
    }

    /**
     * the function changes the format of the numbers that are either too small or too large
     *
     * @param term The number to be formatted
     * @return A string representation of the number in the appropriate format.
     */
    public static String differentView(double term)
    {
        if (term % 1 == 0 && Math.abs(term) < 10000)
            return String.valueOf((int) term);

        if (Math.abs(term) < 10000 && Math.abs(term) >= 0.001)
            return String.format("%.3f", term);

        int exponent = 0;
        double coefficient = term;

        if (Math.abs(term) >= 1)
        {
            while (Math.abs(coefficient) >= 10)
            {
                coefficient /= 10;
                exponent++;
            }
        }

        else
        {
            while (Math.abs(coefficient) < 1 && coefficient != 0)
            {
                coefficient *= 10;
                exponent--;
            }
        }

        return String.format("%.3f * 10^%d", coefficient, exponent);
    }

    /**
     * Creates the options menu.
     * @param menu The menu to be created.
     * @return true if the menu is created successfully.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Handles menu item selections.
     * @param menuItem The selected menu item.
     * @return true if the menu item is handled successfully.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem)
    {
        String st = menuItem.getTitle().toString();
        if (st.charAt(0) == 'C')
        {
            si = new Intent(this, Credits.class);
            startActivity(si);
        }
        return super.onOptionsItemSelected(menuItem);
    }
}