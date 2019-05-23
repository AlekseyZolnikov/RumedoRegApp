package ru.rumedo.rumedoregapp.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import ru.rumedo.rumedoregapp.User;

public class UserDataReader {

    private Cursor cursor;
    private final SQLiteDatabase database;

    private final String[] usersAllColumn = {
            DatabaseHelper.COLUMN_ID,
            DatabaseHelper.COLUMN_NAME,
            DatabaseHelper.COLUMN_SURNAME,
            DatabaseHelper.COLUMN_EMAIL,
            DatabaseHelper.COLUMN_PHONE,
            DatabaseHelper.COLUMN_EVENT,
            DatabaseHelper.COLUMN_REGDATE
    };

    public UserDataReader(SQLiteDatabase database) {
        this.database = database;
    }

    public void open() {
        query();
        cursor.moveToFirst();
    }

    public void close() {
        cursor.close();
    }

    public void refresh() {
        int position = cursor.getPosition();
        query();
        cursor.moveToPosition(position);
    }

    private void query() {
        cursor = database.query(DatabaseHelper.TABLE_USERS, usersAllColumn,
                null, null, null, null, null);
    }

    public User getPosition(int position) {
        cursor.moveToPosition(position);
        return cursorToUser();
    }

    public int getCount() {
        return cursor.getCount();
    }

    private User cursorToUser() {
        User user = new User();
        user.setId(cursor.getLong(0));
        user.setName(cursor.getString(1));
        user.setSurname(cursor.getString(2));
        user.setEmail(cursor.getString(3));
        user.setPhone(cursor.getString(4));
        user.setEvent(cursor.getString(5));
        return user;
    }
}
