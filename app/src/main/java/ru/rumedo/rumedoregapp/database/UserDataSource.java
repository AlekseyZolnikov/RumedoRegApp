package ru.rumedo.rumedoregapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.Closeable;
import java.io.IOException;

import ru.rumedo.rumedoregapp.pojo.User;

public class UserDataSource implements Closeable {

    private final DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private UserDataReader userDataReader;

    public UserDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
        userDataReader = new UserDataReader(database);
        userDataReader.open();
    }

    @Override
    public void close() throws IOException {
        userDataReader.close();
        dbHelper.close();
    }

    public User addUser(User user) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, user.getName());
        cv.put(DatabaseHelper.COLUMN_SURNAME, user.getSurname());
        cv.put(DatabaseHelper.COLUMN_EMAIL, user.getEmail());
        cv.put(DatabaseHelper.COLUMN_PHONE, user.getPhone());
        cv.put(DatabaseHelper.COLUMN_EVENT, user.getEvent());
        cv.put(DatabaseHelper.COLUMN_IS_SYNC, user.getIsSync());

        long insertId = database.insert(DatabaseHelper.TABLE_USERS, null, cv);

        User newUser = new User();
        newUser.setId(insertId);
        newUser.setName(user.getName());
        newUser.setSurname(user.getSurname());
        newUser.setEmail(user.getEmail());
        newUser.setPhone(user.getPhone());
        newUser.setEvent(user.getEvent());
        newUser.setIsSync(user.getIsSync());

        return newUser;
    }

    public void deleteAll() {
        database.delete(DatabaseHelper.TABLE_USERS, null, null);
    }

    public long updateUser(User user) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_IS_SYNC, 1);
        long insertId = database.update(DatabaseHelper.TABLE_USERS, cv,DatabaseHelper.COLUMN_EMAIL + " = ?", new String[]{user.getEmail()});
        return insertId;
    }

    public UserDataReader getUserDataReader() {
        return userDataReader;
    }
}
