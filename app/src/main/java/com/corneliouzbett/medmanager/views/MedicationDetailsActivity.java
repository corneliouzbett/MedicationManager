package com.corneliouzbett.medmanager.views;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.corneliouzbett.medmanager.R;
import com.corneliouzbett.medmanager.utils.ConversionOfDates;
import com.corneliouzbett.medmanager.utils.NotifyService;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.util.Calendar;
import java.util.Date;

public class MedicationDetailsActivity extends AppCompatActivity {

    private TextView desriptionTextView;
    private TextView IntervalTextView;
    private TextView startDateTextView;
    private TextView endDateTextView;
    private CalendarPickerView calendarPickerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_details);

        Bundle bundle = getIntent().getExtras();

        getSupportActionBar().setIcon(R.drawable.ic_today_details);
        getSupportActionBar().setTitle( bundle.get("title").toString());

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        desriptionTextView = findViewById(R.id.detailed_description);
        IntervalTextView = findViewById(R.id.detailed_interval);
        endDateTextView = findViewById(R.id.detailed_endDate);
        startDateTextView = findViewById(R.id.detailed_startDate);
        calendarPickerView = findViewById(R.id.detailed_calendar_view);

        desriptionTextView.setText(bundle.get("description").toString());
        IntervalTextView.setText("You must take your medication after every "+bundle.get("interval").toString() +" Hours" +
                " Daily until you finish your prescribed dose. You will be getting notification when it is time to take the medicine");
        endDateTextView.setText(" The Last Date : "+ConversionOfDates.formatDate(ConversionOfDates
                .getDateFromString( bundle.get("endDate").toString())));
        startDateTextView.setText(" Start Date : "+ConversionOfDates.formatDate(ConversionOfDates
                .getDateFromString(bundle.get("startDate").toString())));

        Calendar maxDate = Calendar.getInstance();
        Calendar minDate = Calendar.getInstance();
        minDate.add(Calendar.MONTH,-2);
        maxDate.add(Calendar.MONTH,3);
        calendarPickerView.init(minDate.getTime(), maxDate.getTime())
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withSelectedDate(new Date());

        Intent myIntent = new Intent(this, NotifyService.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getService(this,0,myIntent,0);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,15);
        calendar.set(Calendar.HOUR,10);
        calendar.set(Calendar.AM_PM,Calendar.AM);
        calendar.add(Calendar.DAY_OF_MONTH,1);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(), 1000 * 60 * 60 * 24,pendingIntent);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()== android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
