package id.com.riezan.crudcontact.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.VisibleForTesting;

import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import id.com.riezan.crudcontact.data.model.ContactData;

@Singleton
public class DatabaseHelper {

    private final BriteDatabase mDb;

    @Inject
    public DatabaseHelper(DbOpenHelper dbOpenHelper) {
        this(dbOpenHelper, Schedulers.io());
    }

    @VisibleForTesting
    public DatabaseHelper(DbOpenHelper dbOpenHelper, Scheduler scheduler) {
        SqlBrite.Builder briteBuilder = new SqlBrite.Builder();
        mDb = briteBuilder.build().wrapDatabaseHelper(dbOpenHelper, scheduler);
    }

    public BriteDatabase getBriteDb() {
        return mDb;
    }

    public Observable<ContactData> setContact(final Collection<ContactData> contactData) {
        return Observable.create(new ObservableOnSubscribe<ContactData>() {
            @Override
            public void subscribe(ObservableEmitter<ContactData> emitter) throws Exception {
                if (emitter.isDisposed()) return;
                BriteDatabase.Transaction transaction = mDb.newTransaction();
                try {
                    mDb.delete(Db.ContactTable.TABLE_NAME, null);
                    for (ContactData contactData1 : contactData) {
                        long result = mDb.insert(Db.ContactTable.TABLE_NAME,
                                Db.ContactTable.toContentValues(contactData1),
                                SQLiteDatabase.CONFLICT_REPLACE);
                        if (result >= 0) emitter.onNext(contactData1);
                    }
                    transaction.markSuccessful();
                    emitter.onComplete();
                } finally {
                    transaction.end();
                }
            }
        });
    }

    public Observable<List<ContactData>> getContact() {
        return mDb.createQuery(Db.ContactTable.TABLE_NAME,
                "SELECT * FROM " + Db.ContactTable.TABLE_NAME)
                .mapToList(new Function<Cursor, ContactData>() {
                    @Override
                    public ContactData apply(@NonNull Cursor cursor) throws Exception {
                        return ContactData.create(Db.ContactTable.parseCursor(cursor));
                    }
                });
    }

}
