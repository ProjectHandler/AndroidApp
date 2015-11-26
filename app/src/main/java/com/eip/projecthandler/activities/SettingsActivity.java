package com.eip.projecthandler.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.eip.projecthandler.R;
import com.eip.projecthandler.helpers.DBOpenHelper;


public class SettingsActivity extends Activity {

    private DBOpenHelper tdb;
    private SQLiteDatabase sdb;
    private Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        tdb = new DBOpenHelper(this, "server.db", null, 1);
        sdb = tdb.getWritableDatabase();

        displayAddress();

        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAdress = ((EditText) findViewById(R.id.et_address)).getText().toString();

                if (strAdress != null && !strAdress.contentEquals("")) {
                    addNewAddress();
                    Intent result = new Intent(Intent.ACTION_VIEW);
                    setResult(RESULT_OK, result);
                } else {
                    Intent result = new Intent(Intent.ACTION_VIEW);
                    setResult(RESULT_CANCELED, result);
                }
                finish();
            }
        });
    }

    public void displayAddress() {
        DBOpenHelper tdb;
        SQLiteDatabase sdb;
        tdb = new DBOpenHelper(this, "server.db", null, 1);
        sdb = tdb.getReadableDatabase();

        String address;

        String[] columns = {"ADDRESS"};
        Cursor c = sdb.query("server", columns, null, null, null, null, null);
        c.moveToFirst();

        if (c.getCount() == 1)
            address = c.getString(0);
        else
            address = "http://";

        EditText etAddress = (EditText) findViewById(R.id.et_address);
        etAddress.setText(address);
    }

    public void addNewAddress() {
        sdb.delete("server", null, null);
        ContentValues cv = new ContentValues();
        cv.put("ADDRESS", ((EditText) findViewById(R.id.et_address)).getText().toString());
        sdb.insert("server", null, cv);
    }
}
