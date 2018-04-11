package com.beliscosolutions.corneliouzbett.medmanager01.views;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beliscosolutions.corneliouzbett.medmanager01.R;
import com.beliscosolutions.corneliouzbett.medmanager01.helpers.InputValidation;
import com.beliscosolutions.corneliouzbett.medmanager01.helpers.sql.DatabaseHelper;
import com.beliscosolutions.corneliouzbett.medmanager01.model.Medication;
import com.beliscosolutions.corneliouzbett.medmanager01.utils.AlarmReminder;
import com.beliscosolutions.corneliouzbett.medmanager01.utils.ConversionOfDates;
import com.beliscosolutions.corneliouzbett.medmanager01.utils.MedicationBroadCastReceiver;
import com.beliscosolutions.corneliouzbett.medmanager01.utils.Notifications;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MedicationCreationActivity extends AppCompatActivity {

    private TextInputEditText medicationTitleTextInputEditText;
    private TextInputEditText medicationDescriptionTextInputEditText;
    private TextInputEditText medicationIntervalTextInputEditText;
    private CalendarPickerView calendarView;
    private Button submitMedication;
    private InputValidation validation;

    private TextInputLayout medicationTitleTextInputLayout;
    private TextInputLayout medicationDescriptionTextInputLayout;
    private TextInputLayout medicationIntervalTextInputLayout;

    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_creation);


        medicationTitleTextInputEditText = findViewById(R.id.text_input_editText_medication_name);
        medicationDescriptionTextInputEditText = findViewById(R.id.text_input_editText_medication_description);
        medicationIntervalTextInputEditText = findViewById(R.id.text_input_editText_medication_interval);
        calendarView = findViewById(R.id.calendar_view);
        submitMedication = findViewById(R.id.button_submit_medication);
        validation = new InputValidation(this);

        medicationTitleTextInputLayout = findViewById(R.id.text_input_layout_medication_title);
        medicationDescriptionTextInputLayout = findViewById(R.id.text_input_layout_medication_description);
        medicationIntervalTextInputLayout = findViewById(R.id.text_input_layout_medication_interval);

        Calendar maxDate = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.MONTH,-2);
        maxDate.add(Calendar.MONTH,3);
        calendarView.init(minDate.getTime(), maxDate.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(new Date());

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        submitMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean validateTitle = validation.isInputEditTextFilled(medicationTitleTextInputEditText,medicationTitleTextInputLayout,"Please enter title here");
                boolean validatedescription = validation.isInputEditTextFilled(medicationDescriptionTextInputEditText,medicationDescriptionTextInputLayout,"Please enter description here");
                boolean validateInterval = validation.isInputEditTextFilled(medicationIntervalTextInputEditText,medicationIntervalTextInputLayout,"Please enter Interval here");
                List <Date> dates = calendarView.getSelectedDates();
                Log.e("Display Date :: ", "onClick:  "+dates);
                if (validatedescription && validateInterval && validateTitle && dates.size() > 0){
                    String startDate;
                    String endDate;
                    DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
                    Medication medication = new Medication();
                        startDate = ConversionOfDates.formatDate(dates.get(0));
                        endDate = ConversionOfDates.formatDate(dates.get(dates.size() - 1));

                    medication.setName(medicationTitleTextInputEditText.getText().toString().trim());
                    medication.setDescription(medicationDescriptionTextInputEditText.getText().toString().trim());
                    medication.setInterval(medicationIntervalTextInputEditText.getText().toString().trim());
                    medication.setStart_date(startDate);
                    medication.setEnd_date(endDate);
                    int success = databaseHelper.addMedication(medication);
                    switch (success){
                        case -1:
                            Toast.makeText(getApplicationContext(),"Error Occured",Toast.LENGTH_LONG).show();
                            break;
                        default:
                            setAlarm(
                                    medicationTitleTextInputEditText.getText().toString().trim(),
                                    Integer.parseInt(medicationIntervalTextInputEditText.getText().toString().trim()) *60*60*1000);

                            Toast.makeText(getApplicationContext(),"Success",Toast.LENGTH_LONG).show();
                            Intent mainIntent = new Intent(MedicationCreationActivity.this,MainActivity.class);
                            mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(mainIntent);
                            finish();
                    }
                } else {
                    Snackbar.make(v, "Medication was not added try again", Snackbar.LENGTH_LONG)
                            .setAction("Ok", null).show();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    /**
     *  this is used to set the alarm
     * @param body
     * @param duration_interval
     */

    private void setAlarm(String body,int duration_interval){

        Intent intent = new Intent(getBaseContext(), MedicationBroadCastReceiver.class);
        intent.putExtra("body",body);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Calendar.getInstance().getTimeInMillis(),duration_interval, pendingIntent);

    }
}
