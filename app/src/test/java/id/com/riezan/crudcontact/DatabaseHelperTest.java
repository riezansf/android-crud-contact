package id.com.riezan.crudcontact;

import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import id.com.riezan.crudcontact.data.local.DatabaseHelper;
import id.com.riezan.crudcontact.data.local.DbOpenHelper;
import id.com.riezan.crudcontact.util.DefaultConfig;
import id.com.riezan.crudcontact.util.RxSchedulersOverrideRule;

import static junit.framework.Assert.assertEquals;

/**
 * Unit tests integration with a SQLite Database using Robolectric
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class DatabaseHelperTest {

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    private DatabaseHelper mDatabaseHelper;

    @Before
    public void setup() {
        if (mDatabaseHelper == null)
            mDatabaseHelper = new DatabaseHelper(new DbOpenHelper(RuntimeEnvironment.application),
                    mOverrideSchedulersRule.getScheduler());
    }

//    @Test
//    public void setRibots() {
//        Ribot ribot1 = TestDataFactory.makeRibot("r1");
//        Ribot ribot2 = TestDataFactory.makeRibot("r2");
//        List<Ribot> ribots = Arrays.asList(ribot1, ribot2);
//
//        TestObserver<Ribot> result = new TestObserver<>();
//        mDatabaseHelper.setRibots(ribots).subscribe(result);
//        result.assertNoErrors();
//        result.assertValueSequence(ribots);
//
//        Cursor cursor = mDatabaseHelper.getBriteDb()
//                .query("SELECT * FROM " + Db.ContactTable.TABLE_NAME);
//        assertEquals(2, cursor.getCount());
//        for (Ribot ribot : ribots) {
//            cursor.moveToNext();
//            assertEquals(ribot.profile(), Db.ContactTable.parseCursor(cursor));
//        }
//    }
//
//    @Test
//    public void getContact() {
//        Ribot ribot1 = TestDataFactory.makeRibot("r1");
//        Ribot ribot2 = TestDataFactory.makeRibot("r2");
//        List<Ribot> ribots = Arrays.asList(ribot1, ribot2);
//
//        mDatabaseHelper.setRibots(ribots).subscribe();
//
//        TestObserver<List<Ribot>> result = new TestObserver<>();
//        mDatabaseHelper.getContact().subscribe(result);
//        result.assertNoErrors();
//        result.assertValue(ribots);
//    }

}
