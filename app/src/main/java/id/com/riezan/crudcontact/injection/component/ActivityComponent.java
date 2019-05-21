package id.com.riezan.crudcontact.injection.component;

import dagger.Subcomponent;
import id.com.riezan.crudcontact.injection.PerActivity;
import id.com.riezan.crudcontact.injection.module.ActivityModule;
import id.com.riezan.crudcontact.ui.main.ContactFormActivity;
import id.com.riezan.crudcontact.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);
    void inject(ContactFormActivity contactFormActivity);

}
