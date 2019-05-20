package id.com.riezan.crudcontact.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import id.com.riezan.crudcontact.data.DataManager;
import id.com.riezan.crudcontact.data.SyncService;
import id.com.riezan.crudcontact.data.local.DatabaseHelper;
import id.com.riezan.crudcontact.data.local.PreferencesHelper;
import id.com.riezan.crudcontact.data.remote.ContactService;
import id.com.riezan.crudcontact.injection.ApplicationContext;
import id.com.riezan.crudcontact.injection.module.ApplicationModule;
import id.com.riezan.crudcontact.util.RxEventBus;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(SyncService syncService);

    @ApplicationContext Context context();
    Application application();
    ContactService contactService();
    PreferencesHelper preferencesHelper();
    DatabaseHelper databaseHelper();
    DataManager dataManager();
    RxEventBus eventBus();

}
