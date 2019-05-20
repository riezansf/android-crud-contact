package id.com.riezan.crudcontact;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import id.com.riezan.crudcontact.data.DataManager;
import id.com.riezan.crudcontact.data.local.DatabaseHelper;
import id.com.riezan.crudcontact.data.local.PreferencesHelper;
import id.com.riezan.crudcontact.data.remote.ContactService;

import static org.mockito.Mockito.verify;

/**
 * This test class performs local unit tests without dependencies on the Android framework
 * For testing methods in the DataManager follow this approach:
 * 1. Stub mock helper classes that your method relies on. e.g. RetrofitServices or DatabaseHelper
 * 2. Test the Observable using TestSubscriber
 * 3. Optionally write a SEPARATE test that verifies that your method is calling the right helper
 * using Mockito.verify()
 */
@RunWith(MockitoJUnitRunner.class)
public class DataManagerTest {

    @Mock DatabaseHelper mMockDatabaseHelper;
    @Mock PreferencesHelper mMockPreferencesHelper;
    @Mock
    ContactService mMockContactService;
    private DataManager mDataManager;

    @Before
    public void setUp() {
        mDataManager = new DataManager(mMockContactService, mMockPreferencesHelper,
                mMockDatabaseHelper);
    }

//    @Test
//    public void syncRibotsEmitsValues() {
//        List<Ribot> ribots = Arrays.asList(TestDataFactory.makeRibot("r1"),
//                TestDataFactory.makeRibot("r2"));
//        stubSyncRibotsHelperCalls(ribots);
//
//        TestObserver<Ribot> result = new TestObserver<>();
//        mDataManager.syncRibots().subscribe(result);
//        result.assertNoErrors();
//        result.assertValueSequence(ribots);
//    }
//
//    @Test
//    public void syncRibotsCallsApiAndDatabase() {
//        List<Ribot> ribots = Arrays.asList(TestDataFactory.makeRibot("r1"),
//                TestDataFactory.makeRibot("r2"));
//        stubSyncRibotsHelperCalls(ribots);
//
//        mDataManager.syncRibots().subscribe();
//        // Verify right calls to helper methods
//        verify(mMockContactService).getContact();
//        verify(mMockDatabaseHelper).setRibots(ribots);
//    }
//
//    @Test
//    public void syncRibotsDoesNotCallDatabaseWhenApiFails() {
//        when(mMockContactService.getContact())
//                .thenReturn(Observable.<List<Ribot>>error(new RuntimeException()));
//
//        mDataManager.syncRibots().subscribe(new TestObserver<Ribot>());
//        // Verify right calls to helper methods
//        verify(mMockContactService).getContact();
//        verify(mMockDatabaseHelper, never()).setRibots(ArgumentMatchers.<Ribot>anyList());
//    }
//
//    private void stubSyncRibotsHelperCalls(List<Ribot> ribots) {
//        // Stub calls to the ribot service and database helper.
//        when(mMockContactService.getContact())
//                .thenReturn(Observable.just(ribots));
//        when(mMockDatabaseHelper.setRibots(ribots))
//                .thenReturn(Observable.fromIterable(ribots));
//    }

}
