package id.com.riezan.crudcontact.data.model;

import android.os.Parcelable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

@AutoValue
public abstract class ContactData implements Parcelable {

    public abstract String firstName();
    public abstract String lastName();
    public abstract int age();
    public abstract String photo();

    public static Builder builder() {
        return new AutoValue_ContactData.Builder();
    }

    public static ContactData create(ContactData contactData) {
        return new AutoValue_ContactData(contactData.firstName(),contactData.lastName(),contactData.age(),contactData.photo());
    }

    public static TypeAdapter<ContactData> typeAdapter(Gson gson) {
        return new AutoValue_ContactData.GsonTypeAdapter(gson);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder setFirstName(String firstName);
        public abstract Builder setLastName(String lastName);
        public abstract Builder setAge(int age);
        public abstract Builder setPhoto(String photo);

        public abstract ContactData build();
    }
}
