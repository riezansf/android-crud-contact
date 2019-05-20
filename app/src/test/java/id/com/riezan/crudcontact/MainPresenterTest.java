package id.com.riezan.crudcontact;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import id.com.riezan.crudcontact.data.DataManager;
import id.com.riezan.crudcontact.ui.main.MainMvpView;
import id.com.riezan.crudcontact.ui.main.MainPresenter;
import id.com.riezan.crudcontact.util.RxSchedulersOverrideRule;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MainPresenterTest {

    @Mock MainMvpView mMockMainMvpView;
    @Mock DataManager mMockDataManager;
    private MainPresenter mMainPresenter;

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Before
    public void setUp() {
        mMainPresenter = new MainPresenter(mMockDataManager);
        mMainPresenter.attachView(mMockMainMvpView);
    }

    @After
    public void tearDown() {
        mMainPresenter.detachView();
    }

//    @Test
//    public void loadRibotsReturnsRibots() {
//        List<Ribot> ribots = TestDataFactory.makeListRibots(10);
//        when(mMockDataManager.getRibots())
//                .thenReturn(Observable.just(ribots));
//
//        mMainPresenter.loadRibots();
//        verify(mMockMainMvpView).showContactList(ribots);
//        verify(mMockMainMvpView, never()).showContactEmpty();
//        verify(mMockMainMvpView, never()).showError();
//    }
//
//    @Test
//    public void loadRibotsReturnsEmptyList() {
//        when(mMockDataManager.getRibots())
//                .thenReturn(Observable.just(Collections.<Ribot>emptyList()));
//
//        mMainPresenter.loadRibots();
//        verify(mMockMainMvpView).showContactEmpty();
//        verify(mMockMainMvpView, never()).showContactList(ArgumentMatchers.<Ribot>anyList());
//        verify(mMockMainMvpView, never()).showError();
//    }
//
//    @Test
//    public void loadRibotsFails() {
//        when(mMockDataManager.getRibots())
//                .thenReturn(Observable.<List<Ribot>>error(new RuntimeException()));
//
//        mMainPresenter.loadRibots();
//        verify(mMockMainMvpView).showError();
//        verify(mMockMainMvpView, never()).showContactEmpty();
//        verify(mMockMainMvpView, never()).showContactList(ArgumentMatchers.<Ribot>anyList());
//    }

}
