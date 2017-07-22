package com.parse.starter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class ReminderActivity extends AppCompatActivity {

    ListView reminderListView;
    ArrayAdapter arrayAdapter;
    ArrayList<String> reminders = new ArrayList<>();
    EditText addReminderTextView;
    String reminderContent =null;





    public void addReminder(View view){

        final String reminderContent = addReminderTextView.getText().toString();

        final ParseObject reminderDb = new ParseObject("RemindMe");
        reminderDb.put("username", ParseUser.getCurrentUser().getUsername());
        reminderDb.put("reminders",reminderContent);

        addReminderTextView.setText("");

        reminderDb.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if(e == null){

                    reminderDb.add("username",ParseUser.getCurrentUser().getUsername());
                    reminderDb.add("reminders",reminderContent);
                    arrayAdapter.notifyDataSetChanged();
                }

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() == R.id.logOut){

            ParseUser.logOut();
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        setTitle("Your Reminders");

        reminderListView = (ListView) findViewById(R.id.reminderListView);
        addReminderTextView = (EditText) findViewById(R.id.addReminderTextView);

        reminders.clear();

        arrayAdapter =  new ArrayAdapter(this,android.R.layout.simple_list_item_1,reminders);

        reminderListView.setAdapter(arrayAdapter);


        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("RemindMe");
        query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.orderByDescending("createdAt");

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                if(e == null){

                    if(objects.size()>0){

                        reminders.clear();

                        for(ParseObject reminderDb : objects){

                            reminderContent = reminderDb.getString("reminders");

                            reminders.add(reminderContent);
                        }

                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


    }
}
