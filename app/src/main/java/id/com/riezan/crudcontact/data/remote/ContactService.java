package id.com.riezan.crudcontact.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import id.com.riezan.crudcontact.data.model.ResponseApi;
import id.com.riezan.crudcontact.util.MyGsonTypeAdapterFactory;

public interface ContactService {

    String ENDPOINT = "https://simple-contact-crud.herokuapp.com/";

    @GET("contact")
    Observable<Response<ResponseApi>> getRibots();

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static ContactService newRibotsService() {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ContactService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            return retrofit.create(ContactService.class);
        }
    }
}
