package id.com.riezan.crudcontact.ui.main;

import android.support.v7.widget.CardView;
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
import timber.log.Timber;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {

    private List<ContactData> mContactData;

    private ContactViewHolder.ClickListener mClickListener;

    @Inject
    public ContactAdapter() {
        mContactData = new ArrayList<>();
    }

    public void setListener(ContactViewHolder.ClickListener clickListener){
        mClickListener=clickListener;
    }

    public void setContact(List<ContactData> contactData) {
        mContactData = contactData;
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(itemView, mClickListener);
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder holder, int position) {
        ContactData contactData = mContactData.get(position);
        holder.bind(contactData);
    }

    @Override
    public int getItemCount() {
        return mContactData.size();
    }

    public ContactData getItem(int position) {
        return mContactData.get(position);
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        @BindView(R.id.card_view) CardView contactCardView;
        @BindView(R.id.text_name) TextView firstNameTextView;
        @BindView(R.id.text_age) TextView ageTextView;
        @BindView(R.id.text_delete) TextView deleteTextView;

        public ClickListener mListener;

        public interface ClickListener {
            void onItemClicked(int position);
            void onStarClicked(View v,int position);
            boolean onItemLongClicked(int position);
        }

        public ContactViewHolder(View itemView, ClickListener clickListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            this.mListener = clickListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void bind(ContactData contactData){
            firstNameTextView.setText(contactData.firstName()+" "+contactData.lastName());
            ageTextView.setText(contactData.age()+" years");

            setupViewClickListener();
        }

        private void setupViewClickListener(){
            deleteTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClicked(getAdapterPosition());
                    }
                }
            });

            contactCardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return mListener != null && mListener.onItemLongClicked(getAdapterPosition());
                }
            });
        }

        @Override
        public void onClick(View view) { }

        @Override
        public boolean onLongClick(View view) {
            return false;
        }
    }
}
