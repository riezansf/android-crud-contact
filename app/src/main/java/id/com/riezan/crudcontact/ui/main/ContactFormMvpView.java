package id.com.riezan.crudcontact.ui.main;

import id.com.riezan.crudcontact.ui.base.MvpView;

public interface ContactFormMvpView extends MvpView {

    void onAddContactSuccess();
    void onAddContactFail(String message);

    void onEditContactSuccess();

    void onEditContactFail(String message);
}
