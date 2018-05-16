package com.example.cecil.database2;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by cecil on 09-05-2018.
 */

public class DateTypeConverter {
    @TypeConverter
    public Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        } else {
            return date.getTime();
}
    }
        }