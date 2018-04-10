package com.beliscosolutions.corneliouzbett.medmanager01.model;

/**
 * Created by CorneliouzBett on 04/04/2018.
 */

public class Medication {
   private int id;
    private String name;
    private String description;
    private String interval;
    private String start_date;
    private String end_date;

    public Medication(int id, String name, String description, String interval, String start_date, String end_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.interval = interval;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public Medication() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "Medication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", interval='" + interval + '\'' +
                ", start_date=" + start_date +
                ", end_date=" + end_date +
                '}';
    }
}
