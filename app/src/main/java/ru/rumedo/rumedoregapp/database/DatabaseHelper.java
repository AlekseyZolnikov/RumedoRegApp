package ru.rumedo.rumedoregapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "users2.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";

    public static final String COLUMN_ID = BaseColumns._ID;
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SURNAME = "surname";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_EVENT = "event";
    public static final String COLUMN_REGDATE = "regdate";
    public static final String COLUMN_IS_SYNC = "is_sync";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
            "CREATE TABLE "
                + TABLE_USERS
                + "( "
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_NAME + " TEXT, "
                    + COLUMN_SURNAME + " TEXT, "
                    + COLUMN_EMAIL + " TEXT, "
                    + COLUMN_PHONE + " TEXT, "
                    + COLUMN_EVENT + " TEXT, "
                    + COLUMN_REGDATE + " TEXT, "
                    + COLUMN_IS_SYNC + " INTEGER" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeQuery = "ALTER TABLE " + TABLE_USERS + " ADD COLUMN "
                + COLUMN_IS_SYNC + " INTEGER";
        db.execSQL(upgradeQuery);
    }
}
