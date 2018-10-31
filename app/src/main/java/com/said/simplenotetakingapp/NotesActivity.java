package com.said.simplenotetakingapp;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NotesActivity extends AppCompatActivity {
    private Intent intent;
    private String Title, Body;
    private long id;

    private DbHelper dbHelper = null;
    private SQLiteDatabase db = null;
    private EditText titleEditText = null;
    private EditText bodyEditText = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        intent = getIntent();

        dbHelper = new DbHelper(this);
        db = dbHelper.getWritableDatabase();

        titleEditText = findViewById(R.id.note_title);
        bodyEditText = findViewById(R.id.Note);

        Title = intent.getStringExtra("Title");
        Body = intent.getStringExtra("Body");
        id = intent.getLongExtra("noteid", 1);

        titleEditText.setText(Title);
        bodyEditText.setText(Body);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.note_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.Save){
            if(id == 1) {
                long id1 = dbHelper.insertNote(db, titleEditText.getText().toString(), bodyEditText.getText().toString());

                Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();

                this.finish();
            }
            else
            {
                boolean id2 = dbHelper.updateNote(db, id ,titleEditText.getText().toString(), titleEditText.getText().toString());
                Toast.makeText(this, "Note updated!", Toast.LENGTH_SHORT).show();

                this.finish();
            }
        }

        return true;
    }
}
