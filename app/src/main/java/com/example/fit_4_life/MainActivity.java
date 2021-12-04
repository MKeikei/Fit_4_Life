package com.example.fit_4_life;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import Database.UserDAO;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;

    private static final int MY_REQUEST_CODE = 1000;

    private final List<User> userList = new ArrayList<User>();
    private ArrayAdapter<User> listViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listView = (ListView) findViewById(R.id.listView);

        UserDAO db = new UserDAO(this);
        db.createDefaultNotesIfNeed();

        List<User> list=  db.getAllUsers();
        this.userList.addAll(list);


        // Define a new Adapter
        // 1 - Context
        // 2 - Layout for the row
        // 3 - ID of the TextView to which the data is written
        // 4 - the List of data

        this.listViewAdapter = new ArrayAdapter<User>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, this.userList);

        // Assign adapter to ListView
        this.listView.setAdapter(this.listViewAdapter);

        // Register the ListView for Context menu
        registerForContextMenu(this.listView);
    }



}