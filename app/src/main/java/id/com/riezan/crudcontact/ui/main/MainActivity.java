package id.com.riezan.crudcontact.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.com.riezan.crudcontact.R;
import id.com.riezan.crudcontact.data.SyncService;
import id.com.riezan.crudcontact.data.model.ContactData;
import id.com.riezan.crudcontact.ui.base.BaseActivity;
import id.com.riezan.crudcontact.util.DialogFactory;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainMvpView, ContactAdapter.ContactViewHolder.ClickListener {

    private static final String EXTRA_TRIGGER_SYNC_FLAG = "id.com.riezan.crudcontact.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";

    @Inject MainPresenter mMainPresenter;
    @Inject ContactAdapter mContactAdapter;

    @BindView(R.id.recycler_view) RecyclerView mRecyclerView;

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mContactAdapter.setListener(MainActivity.this);

        mRecyclerView.setAdapter(mContactAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMainPresenter.attachView(this);
        mMainPresenter.loadContact();
    }

    public void startSyncService(){
        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        startSyncService();
        mMainPresenter.loadContact();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.add_contact :

                Intent intent = new Intent(this, ContactFormActivity.class);
                startActivity(intent);

                break;

            case android.R.id.home: break;

            default:break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mMainPresenter.detachView();
    }

    /***** MVP View methods implementation *****/

    @Override
    public void showContactList(List<ContactData> contactData) {
        mContactAdapter.setContact(contactData);
        mContactAdapter.notifyDataSetChanged();
    }

    @Override
    public void onErrorLoadContact() {
        DialogFactory.createGenericErrorDialog(this, getString(R.string.error_loading_contact)).show();
    }

    @Override
    public void onDeleteContactSuccess() {
        Toast.makeText(this, "Contact deleted successfully", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteContactFail(String message) {
        Toast.makeText(this, "Unable to delete contact "+message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showContactEmpty() {
        if(!SyncService.isRunning(getApplicationContext())){
            mContactAdapter.setContact(Collections.<ContactData>emptyList());
            mContactAdapter.notifyDataSetChanged();
            Toast.makeText(this, R.string.empty_contact, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDeleteClicked(int position) {
        mMainPresenter.deleteContact(mContactAdapter.getItem(position).id());
    }

    @Override
    public void onEditClicked(int position) {
        Intent intent = new Intent(this, ContactFormActivity.class);
        intent.putExtra("contactData",mContactAdapter.getItem(position));
        startActivity(intent);
    }

    @Override
    public void onStarClicked(View v, int position) {

    }

    @Override
    public boolean onItemLongClicked(int position) {
        return false;
    }
}
