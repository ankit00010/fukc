package com.example.fukc;

import static java.sql.Types.NULL;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.TimeZone;


public class CreateYNhabit extends AppCompatActivity {
    ImageView backarrow,savehabit;
    EditText habitname,habitque;
    Button colorbutton;
    TextView reminderbutton,frequencybutton;
    DBHelper db;
    boolean[] selectedDays;
    String hname,hque,catid,habittype;
    ArrayList<Integer> daysList = new ArrayList<>();
    String[] daysArray = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday","Sunday"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ynhabit);
        habitname = findViewById(R.id.name_edittext);
        habitque = findViewById(R.id.question_edittext);
        savehabit= findViewById(R.id.save);
        backarrow =findViewById(R.id.backbutton);
        reminderbutton = findViewById(R.id.Remainderbtn);
        frequencybutton = findViewById(R.id.frequency_edittext);
        ImageView   backarrow =(ImageView) findViewById(R.id.backbutton);
        reminderbutton = (TextView) findViewById(R.id.Remainderbtn);
        colorbutton=(Button)findViewById(R.id.button);
        frequencybutton =(TextView) findViewById(R.id.frequency_edittext);
        selectedDays = new boolean[daysArray.length];
        Intent intent = getIntent();
        catid = intent.getStringExtra("catid");
        habittype =intent.getStringExtra("habittype");
        db = new DBHelper(this);
        backarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HabitOptions.class);
                startActivity(intent);
                finish();

            }
        });
        reminderbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hours = calendar.get(Calendar.HOUR_OF_DAY);
                int mins = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateYNhabit.this, com.google.android.material.R.style.Theme_AppCompat_Dialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, minute);
                        c.setTimeZone(TimeZone.getDefault());
                        SimpleDateFormat format = new SimpleDateFormat("k:mm a");
                        String time = format.format(c.getTime());
                        reminderbutton.setText(time);


                    }
                }, hours, mins, false);
                timePickerDialog.show();

            }
        });

        frequencybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Initialize alert dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(CreateYNhabit.this);
                builder.setTitle("Select Days of the week");
                builder.setCancelable(false);

                builder.setMultiChoiceItems(daysArray, selectedDays, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        if (b) {
                            // when checkbox selected
                            // Add position in lang list
                            daysList.add(i);
                            Collections.sort(daysList);
                        } else {
                            // when checkbox unselected
                            // Remove position from langList
                            daysList.remove(Integer.valueOf(i));
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Initialize string builder
                        StringBuilder stringBuilder = new StringBuilder();

                        for (int j = 0; j < daysList.size(); j++) {
                            // concat array value
                            stringBuilder.append(daysArray[daysList.get(j)]);
                            // check condition
                            if (j != daysList.size() - 1) {
                                // When j value not equal
                                // to lang list size - 1
                                // add comma
                                stringBuilder.append(", ");
                            }
                        }
                        // set text on textView
                        frequencybutton.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // dismiss dialog
                        dialogInterface.dismiss();
                    }
                });
                builder.setNeutralButton("Clear All", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // use for loop
                        for (int j = 0; j < selectedDays.length; j++) {
                            // remove all selection
                            selectedDays[j] = false;
                            // clear language list
                            daysList.clear();
                            // clear text view value
                            reminderbutton.setText("");
                        }
                    }
                });
                // show dialog
                builder.show();
            }
        });
        savehabit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int fcatid = Integer.valueOf(catid);
                int fhabittype = Integer.valueOf(habittype);
                String frequency =frequencybutton.getText().toString();
                String reminder = reminderbutton.getText().toString();
                hname= habitname.getText().toString();
                hque= habitque.getText().toString();
                db.insertDatahabit(hname,"no color",hque,frequency,reminder,fhabittype,NULL,fcatid);
                Intent intent = new Intent(getApplicationContext(), HomeActivity1.class);
                startActivity(intent);
                finish();
            }
        });

        //code for colorpicker
        colorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencolorpicker();
            }
        });

    }
    public void opencolorpicker()
    {





        ArrayList<String> colors =new ArrayList<>();
        colors.add("#258174");
        colors.add("#3C8D2F");
        colors.add("#20724f");
        colors.add("#6a3ab2");
        colors.add("#323299");
        colors.add("#800080");
        colors.add("#b79716");
        colors.add("#966d37");
        colors.add("#b77231");
        colors.add("#808000");
    }

}