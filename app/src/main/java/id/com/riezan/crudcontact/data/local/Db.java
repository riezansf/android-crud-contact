package id.com.riezan.crudcontact.data.local;

import android.content.ContentValues;
import android.database.Cursor;

import id.com.riezan.crudcontact.data.model.ContactData;

public class Db {

    public Db() { }

    public abstract static class ContactTable {
        public static final String TABLE_NAME = "contact";

        public static final String COLUMN_FIRST_NAME = "first_name";
        public static final String COLUMN_LAST_NAME = "last_name";
        public static final String COLUMN_AGE = "age";
        public static final String COLUMN_PHOTO = "photo";

        public static final String CREATE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                        COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                        COLUMN_AGE + " INTEGER NOT NULL, " +
                        COLUMN_PHOTO + " TEXT " +
                        " ); ";

        public static ContentValues toContentValues(ContactData contactData) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_FIRST_NAME, contactData.firstName());
            values.put(COLUMN_LAST_NAME, contactData.lastName());
            values.put(COLUMN_AGE, contactData.age());
            if (contactData.photo() != null) values.put(COLUMN_PHOTO, contactData.photo());
            return values;
        }

        public static ContactData parseCursor(Cursor cursor) {

            return ContactData.builder()
                    .setFirstName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME)))
                    .setLastName(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LAST_NAME)))
                    .setAge(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE)))
                    .setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHOTO)))
                    .build();
        }
    }
}
