package id.com.riezan.crudcontact.data;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import retrofit2.Response;
import id.com.riezan.crudcontact.data.local.DatabaseHelper;
import id.com.riezan.crudcontact.data.local.PreferencesHelper;
import id.com.riezan.crudcontact.data.model.ContactData;
import id.com.riezan.crudcontact.data.model.ResponseApi;
import id.com.riezan.crudcontact.data.remote.ContactService;

@Singleton
public class DataManager {

    private final ContactService mContactService;
    private final DatabaseHelper mDatabaseHelper;
    private final PreferencesHelper mPreferencesHelper;

    @Inject
    public DataManager(ContactService contactService, PreferencesHelper preferencesHelper,
                       DatabaseHelper databaseHelper) {
        mContactService = contactService;
        mPreferencesHelper = preferencesHelper;
        mDatabaseHelper = databaseHelper;
    }

    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    public Observable<List<ContactData>> getContactLocal(){
        return mDatabaseHelper.getContact().distinct();
    }

    public Observable<Response<ResponseApi>> getContactData(){
        return mContactService.getRibots();
    }

    public Observable<ContactData> synContactData() {
        return mContactService.getRibots().concatMap(new Function<Response<ResponseApi>, ObservableSource<? extends ContactData>>() {
            @Override
            public ObservableSource<? extends ContactData> apply(Response<ResponseApi> responseApiResponse) throws Exception {
                return mDatabaseHelper.setContact(responseApiResponse.body().data);
            }
        });
    }
}
