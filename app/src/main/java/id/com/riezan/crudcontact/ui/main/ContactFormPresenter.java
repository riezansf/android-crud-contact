package id.com.riezan.crudcontact.ui.main;

import javax.inject.Inject;

import id.com.riezan.crudcontact.data.DataManager;
import id.com.riezan.crudcontact.data.model.ResponseMessage;
import id.com.riezan.crudcontact.injection.ConfigPersistent;
import id.com.riezan.crudcontact.ui.base.BasePresenter;
import id.com.riezan.crudcontact.util.RxUtil;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import timber.log.Timber;

@ConfigPersistent
public class ContactFormPresenter extends BasePresenter<ContactFormMvpView> {

    private final DataManager mDataManager;
    private Disposable mDisposable;

    @Inject
    public ContactFormPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void attachView(ContactFormMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mDisposable != null) mDisposable.dispose();
    }

    public void addContact(String firstName, String lastName, String age) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        mDataManager.addContact(firstName,lastName,age)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<ResponseMessage>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Response<ResponseMessage> response) {
                        if(response.code()==201){
                            getMvpView().onAddContactSuccess();
                            mDataManager.synContactData();
                        }else{
                            getMvpView().onAddContactFail(response.message());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().onAddContactFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void editContact(String id, String firstName, String lastName, String age) {
        checkViewAttached();
        RxUtil.dispose(mDisposable);
        mDataManager.editContact(id, firstName,lastName,age)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<Response<ResponseMessage>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(Response<ResponseMessage> response) {
                        if(response.code()==201){
                            getMvpView().onEditContactSuccess();
                            mDataManager.synContactData();
                        }else{
                            getMvpView().onEditContactFail(response.message());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().onEditContactFail(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
