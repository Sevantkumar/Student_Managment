package com.p2.project;

import java.security.PublicKey;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class AndroidStudentMgt extends AppCompatActivity {
    EditText ename,eroll_no,emarks,emarks2,emarks3,emarks4,emarks5;
    Button add,view,viewall,Show1,delete,modify;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_student_mgt);
        ename=(EditText)findViewById(R.id.name);
        eroll_no=(EditText)findViewById(R.id.roll_no);
        emarks=(EditText)findViewById(R.id.emarks);

        emarks2=(EditText)findViewById(R.id.emarks2);
        emarks3=(EditText)findViewById(R.id.emarks3);
        emarks4=(EditText)findViewById(R.id.emarks4);
        emarks5=(EditText)findViewById(R.id.emarks5);

        add=(Button)findViewById(R.id.addbtn);
        view=(Button)findViewById(R.id.viewbtn);
        viewall=(Button)findViewById(R.id.viewallbtn);
        delete=(Button)findViewById(R.id.deletebtn);
        Show1=(Button)findViewById(R.id.showbtn);
        modify=(Button)findViewById(R.id.modifybtn);

        db=openOrCreateDatabase("Student_manager", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno INTEGER,name VARCHAR,marks INTEGER,marks2 INTEGER,marks3 INTEGER,marks4 INTEGER,marks5 INTEGER);");


        add.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(eroll_no.getText().toString().trim().length()==0||
                        ename.getText().toString().trim().length()==0||
                        emarks.getText().toString().trim().length()==0||
                        emarks2.getText().toString().trim().length()==0||
                        emarks3.getText().toString().trim().length()==0||
                        emarks4.getText().toString().trim().length()==0||
                        emarks5.getText().toString().trim().length()==0
                )
                {
                    showMessage("Error", "Please enter all values");
                    return;
                }
                db.execSQL("INSERT INTO student VALUES('"+eroll_no.getText()+"','"+ename.getText()+
                        "','"+emarks.getText()+"','"+emarks2.getText()+"','"+emarks3.getText()+"','"+emarks4.getText()+"','"+emarks5.getText()+"');");
                showMessage("Success", "Record added successfully");
                clearText();
            }
        });
        delete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(eroll_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+eroll_no.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("DELETE FROM student WHERE rollno='"+eroll_no.getText()+"'");
                    showMessage("Success", "Record Deleted");
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
            }
        });
        modify.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(eroll_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+eroll_no.getText()+"'", null);
                if(c.moveToFirst())
                {
                    db.execSQL("UPDATE student SET name='"+ename.getText()+"',marks='"+emarks.getText()+"',marks2='"+emarks2.getText()+"',marks3='"+emarks3.getText()+"',marks4='"+emarks4.getText()+"',marks5='"+emarks5.getText()+"' WHERE rollno='"+eroll_no.getText()+"'");
                    showMessage("Success", "Record Modified");
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                }
                clearText();
            }
        });
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(eroll_no.getText().toString().trim().length()==0)
                {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c=db.rawQuery("SELECT * FROM student WHERE rollno='"+eroll_no.getText()+"'", null);
                if(c.moveToFirst())
                {
                    ename.setText(c.getString(1));
                    emarks.setText(c.getString(2));
                    emarks2.setText(c.getString(3));
                    emarks3.setText(c.getString(4));
                    emarks4.setText(c.getString(5));
                    emarks5.setText(c.getString(6));
                }
                else
                {
                    showMessage("Error", "Invalid Rollno");
                    clearText();
                }
            }
        });
        viewall.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Cursor c=db.rawQuery("SELECT * FROM student", null);
                if(c.getCount()==0)
                {
                    showMessage("Error", "No records found");
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(c.moveToNext())
                {
                    buffer.append("Rollno: "+c.getString(0)+"\n");
                    buffer.append("Name: "+c.getString(1)+"\n");
                    buffer.append("Marks: "+c.getString(2)+"\n");
                    buffer.append("Marks2: "+c.getString(3)+"\n");
                    buffer.append("Marks3: "+c.getString(4)+"\n");
                    buffer.append("Marks4: "+c.getString(5)+"\n");
                    buffer.append("Marks5: "+c.getString(6)+"\n\n");
                }
                showMessage("Student Details", buffer.toString());
            }
        });
        Show1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                showMessage("Student Management Application", "updated ");
            }
        });

    }
    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        eroll_no.setText("");
        ename.setText("");
        emarks.setText("");
        emarks2.setText("");
        emarks3.setText("");
        emarks4.setText("");
        emarks5.setText("");
        eroll_no.requestFocus();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_main, menu);
        return true;
    }

}