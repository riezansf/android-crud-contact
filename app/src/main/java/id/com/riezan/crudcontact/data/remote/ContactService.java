package id.com.riezan.crudcontact.data.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import id.com.riezan.crudcontact.data.model.ContactData;
import id.com.riezan.crudcontact.data.model.ContactRequestBody;
import id.com.riezan.crudcontact.data.model.ResponseMessage;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import id.com.riezan.crudcontact.data.model.ResponseApi;
import id.com.riezan.crudcontact.util.MyGsonTypeAdapterFactory;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ContactService {

    String ENDPOINT = "https://simple-contact-crud.herokuapp.com/";

    @GET("contact")
    Observable<Response<ResponseApi>> getContact();

    @POST("contact")
    Observable<Response<ResponseMessage>> addContact(@Body ContactRequestBody contactData);

    @DELETE("contact/{id}")
    Observable<Response<ResponseMessage>> deleteContact(@Path("id") String id);

    @PUT("contact/{id}")
    Observable<Response<ResponseMessage>> editContact(@Path("id") String id, @Body ContactRequestBody contactData);

    /******** Helper class that sets up a new services *******/
    class Creator {

        public static ContactService newContactService() {

            //Interceptor for logging raw response
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .connectTimeout(5, TimeUnit.SECONDS)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .retryOnConnectionFailure(true)
                    .build();

            Gson gson = new GsonBuilder()
                    .registerTypeAdapterFactory(MyGsonTypeAdapterFactory.create())
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ContactService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
            return retrofit.create(ContactService.class);
        }
    }
}
