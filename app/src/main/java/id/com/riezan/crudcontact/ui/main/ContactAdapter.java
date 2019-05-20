package id.com.riezan.crudcontact.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.com.riezan.crudcontact.R;
import id.com.riezan.crudcontact.data.model.ContactData;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactData> mContactData;

    @Inject
    public ContactAdapter() {
        mContactData = new ArrayList<>();
    }

    public void setContact(List<ContactData> contactData) {
        mContactData = contactData;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, int position) {
        ContactData contactData = mContactData.get(position);
        holder.firstNameTextView.setText(contactData.firstName()+" "+contactData.lastName());
        holder.AgeTextView.setText(contactData.age()+" years");
    }

    @Override
    public int getItemCount() {
        return mContactData.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_name) TextView firstNameTextView;
        @BindView(R.id.text_age) TextView AgeTextView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
