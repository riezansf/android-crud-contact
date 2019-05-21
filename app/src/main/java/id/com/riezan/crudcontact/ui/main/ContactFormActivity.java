package id.com.riezan.crudcontact.ui.main;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.com.riezan.crudcontact.R;
import id.com.riezan.crudcontact.data.model.ContactData;
import id.com.riezan.crudcontact.ui.base.BaseActivity;
import timber.log.Timber;

public class ContactFormActivity extends BaseActivity implements ContactFormMvpView{

    @Inject
    ContactFormPresenter mContactFormPresenter;
    //@Inject ContactAdapter mContactAdapter;

    @BindView(R.id.button_submit) Button mButtonSubmit;
    @BindView(R.id.text_input_first_name) TextInputLayout mTextInputFirstName;
    @BindView(R.id.edit_text_first_name) EditText mEditTextFirstName;
    @BindView(R.id.text_input_last_name) TextInputLayout mTextInputLastName;
    @BindView(R.id.edit_text_last_name) EditText mEditTextLastName;
    @BindView(R.id.text_input_age) TextInputLayout mTextInputAge;
    @BindView(R.id.edit_text_age) EditText mEditTextAge;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_contact_form);
        ButterKnife.bind(this);

        mContactFormPresenter.attachView(this);


        if(getIntent()!=null && getIntent().getExtras()!=null){
            //EDIT

            final ContactData contactData = getIntent().getExtras().getParcelable("contactData");

            mEditTextFirstName.setText(contactData.firstName());
            mEditTextLastName.setText(contactData.lastName());
            mEditTextAge.setText(String.valueOf(contactData.age()));

            mButtonSubmit.setText("UPDATE");
            mButtonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validateForm()){
                        mContactFormPresenter.editContact(contactData.id(),mEditTextFirstName.getText().toString(),mEditTextLastName.getText().toString(),mEditTextAge.getText().toString());
                    }
                }
            });

        }else{
            //ADD

            mButtonSubmit.setText("ADD");
            mButtonSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(validateForm()){
                        mContactFormPresenter.addContact(mEditTextFirstName.getText().toString(),mEditTextLastName.getText().toString(),mEditTextAge.getText().toString());
                    }
                }
            });
        }
    }

    private boolean validateForm(){

        boolean validFirstName=true;
        boolean validLastName=true;
        boolean validAge=true;

        if(mEditTextFirstName.getText().toString().isEmpty() || mEditTextFirstName.getText().toString().length()<3){
            mTextInputFirstName.setError("Required min 3 character");
            validFirstName=false;
        }else{
            mTextInputFirstName.setError("");
        }

        if(mEditTextLastName.getText().toString().isEmpty() || mEditTextLastName.getText().toString().length()<3){
            mTextInputLastName.setError("Required min 3 character");
            validLastName=false;
        }else{
            mTextInputLastName.setError("");
        }

        if(mEditTextAge.getText().toString().isEmpty() || Integer.parseInt(mEditTextAge.getText().toString())>100){
            mTextInputAge.setError("Required min 1 year, max 100 year");
            validAge=false;
        }else{
            mTextInputAge.setError("");
        }

        return validFirstName && validLastName && validAge;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

//        int id = item.getItemId();
//        switch (id) {
//
//            case R.id.add_contact :
//
//                break;
//
//            case android.R.id.home: break;
//
//            default:break;
//        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mContactFormPresenter.detachView();
    }

    @Override
    public void onAddContactSuccess() {
        Toast.makeText(this, "Contact added!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onAddContactFail(String message) {
        Toast.makeText(this, "Fail to add contact "+message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onEditContactSuccess() {
        Toast.makeText(this, "Contact updated!", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void onEditContactFail(String message) {
        Toast.makeText(this, "Fail to update contact "+message, Toast.LENGTH_LONG).show();
    }

    /***** MVP View methods implementation *****/


}
