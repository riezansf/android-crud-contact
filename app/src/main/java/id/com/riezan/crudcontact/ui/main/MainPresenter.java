package id.com.riezan.crudcontact.ui.main;

import java.util.List;

import javax.inject.Inject;

import id.com.riezan.crudcontact.data.model.ResponseMessage;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import timber.log.Timber;
import id.com.riezan.crudcontact.data.DataManager;
import id.com.riezan.crudcontact.data.model.ContactData;
import id.com.riezan.crudcontact.injection.ConfigPersistent;
import id.com.riezan.crudcontact.ui.base.BasePresenter;
import id.com.riezan.crudcontact.util.RxUtil;

@ConfigPersistent
public class MainPresenter extends BasePresenter<MainMvpView> {

    private final DataManager mDataManager;
    private Disposable mDisposable;

    @Inject
    public MainPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void loadContact() {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        mDataManager.getContactDataLocal()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<ContactData>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(@NonNull List<ContactData> contactDataList) {
                        if (contactDataList.isEmpty()) {
                            getMvpView().showContactEmpty();
                        } else {
                            getMvpView().showContactList(contactDataList);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Timber.e(e, "There was an error loading contact.");
                        getMvpView().onErrorLoadContact();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void deleteContact(String id) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        mDataManager.deleteContact(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<ResponseMessage>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Response<ResponseMessage> response) {
                        Timber.d("Clicked response "+response.code());
                        if(response.code()==202){
                            getMvpView().onDeleteContactSuccess();
                            mDataManager.synContactData();
                        }else{
                            getMvpView().onDeleteContactFail(response.message());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().onErrorLoadContact();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
