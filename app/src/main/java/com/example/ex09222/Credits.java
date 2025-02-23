package com.example.ex09222;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Credits extends AppCompatActivity
{

    Intent pi;

    /**
     * Called when the activity is first created.
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
        pi = getIntent();
    }

    /**
     * onCreateOptionsMenu method
     * <p> Creating the options menu
     * </p>
     *
     * @param menu the Menu object to pass to the inflater
     * @return true
     */
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
        if (st.charAt(0) == 'M')
        {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}