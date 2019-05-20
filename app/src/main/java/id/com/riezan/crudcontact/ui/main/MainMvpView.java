package id.com.riezan.crudcontact.ui.main;

import java.util.List;

import id.com.riezan.crudcontact.data.model.ContactData;
import id.com.riezan.crudcontact.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showContactList(List<ContactData> ribots);

    void showContactEmpty();

    void showError();

}
