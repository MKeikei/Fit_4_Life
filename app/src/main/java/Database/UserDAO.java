package Database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;

import com.example.fit_4_life.User;

public class UserDAO extends SQLiteOpenHelper {

    private static final String TAG = "SQLite";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Gym";

    // Table name: Note.
    private static final String TABLE_USER = "User";

    private static final String COLUMN_USER_ID ="User_Id";
    private static final String COLUMN_USER_USERNAME ="User_Username";
    private static final String COLUMN_USER_PASSWORD = "User_Password";
    private static final String COLUMN_USER_TYPE = "User_Type";

    public UserDAO (Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create table
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onCreate ... ");
        // Script.
        String script = "CREATE TABLE " + TABLE_USER + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY," + COLUMN_USER_USERNAME + " TEXT,"
                + COLUMN_USER_PASSWORD + " TEXT," +COLUMN_USER_TYPE + "TEXT" + ")";
        // Execute Script.
        db.execSQL(script);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);

        // Create tables again
        onCreate(db);
    }


    // If Note table has no data
    // default, Insert 2 records.
    public void createDefaultNotesIfNeed()  {
        int count = this.getUserCount();
        if(count ==0 ) {
            User user1 = new User(1,
                    "admin", "admin123", "Admin");
            User user2 = new User(2,
                    "instructor", "instructor123", "Instructor");
            this.addUser(user1);
            this.addUser(user2);
        }
    }


    public void addUser(User user) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + user.getUsername());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_TYPE, user.getType());

        // Inserting Row
        db.insert(TABLE_USER, null, values);

        // Closing database connection
        db.close();
    }


    public User getUser(int id) {
        Log.i(TAG, "MyDatabaseHelper.getNote ... " + id);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER, new String[] { COLUMN_USER_ID,
                        COLUMN_USER_USERNAME, COLUMN_USER_PASSWORD, COLUMN_USER_TYPE }, COLUMN_USER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));
        // return note
        return user;
    }


    public List<User> getAllUsers() {
        Log.i(TAG, "MyDatabaseHelper.getAllUsers ... " );

        List<User> userList = new ArrayList<User>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                user.setType(cursor.getString(3));
                // Adding note to list
                userList.add(user);
            } while (cursor.moveToNext());
        }

        // return note list
        return userList;
    }

    public int getUserCount() {
        Log.i(TAG, "MyDatabaseHelper.getNotesCount ... " );

        String countQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }


    public int updateUser(User user) {


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());


        // updating row
        return db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    public void deleteUser(User user) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[] { String.valueOf(user.getId()) });
        db.close();
    }

}