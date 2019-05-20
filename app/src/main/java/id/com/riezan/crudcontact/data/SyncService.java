package id.com.riezan.crudcontact.data;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.IBinder;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;
import id.com.riezan.crudcontact.CrudContactApplication;
import id.com.riezan.crudcontact.data.model.ContactData;
import id.com.riezan.crudcontact.util.AndroidComponentUtil;
import id.com.riezan.crudcontact.util.NetworkUtil;
import id.com.riezan.crudcontact.util.RxUtil;

public class SyncService extends Service {

    @Inject DataManager mDataManager;
    private Disposable mDisposable;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SyncService.class);
    }

    public static boolean isRunning(Context context) {
        return AndroidComponentUtil.isServiceRunning(context, SyncService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CrudContactApplication.get(this).getComponent().inject(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        Timber.i("Starting sync...");

        if (!NetworkUtil.isNetworkConnected(this)) {
            Timber.i("Sync canceled, connection not available");
            AndroidComponentUtil.toggleComponent(this, SyncOnConnectionAvailable.class, true);
            stopSelf(startId);
            return START_NOT_STICKY;
        }

        RxUtil.dispose(mDisposable);
        mDataManager.synContactData()
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ContactData>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposable = d;
                    }

                    @Override
                    public void onNext(ContactData contactData) {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.w(e, "Error syncing.");
                        stopSelf(startId);
                    }

                    @Override
                    public void onComplete() {
                        Timber.i("Synced successfully!");
                        stopSelf(startId);
//                    }
                    }
                });

//        mDataManager.synContactData()
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<ContactData>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        mDisposable = d;
//                    }
//
//                    @Override
//                    public void onNext(@NonNull ContactData contactData) {
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        Timber.w(e, "Error syncing.");
//                        stopSelf(startId);
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Timber.i("Synced successfully!");
//                        stopSelf(startId);
//                    }
//                });

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (mDisposable != null) mDisposable.dispose();
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static class SyncOnConnectionAvailable extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)
                    && NetworkUtil.isNetworkConnected(context)) {
                Timber.i("Connection is now available, triggering sync...");
                AndroidComponentUtil.toggleComponent(context, this.getClass(), false);
                context.startService(getStartIntent(context));
            }
        }
    }

}
