package id.com.riezan.crudcontact.test.common.injection.component;

import javax.inject.Singleton;

import dagger.Component;
import id.com.riezan.crudcontact.injection.component.ApplicationComponent;
import id.com.riezan.crudcontact.test.common.injection.module.ApplicationTestModule;

@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {

}
