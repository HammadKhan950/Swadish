package com.example.swadish;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddReviewDialog extends DialogFragment {
    public interface AddReview {
        void onAddReviewResult(Review review);
    }

    private AddReview addReview;
    private static final String GROCERRY_ITEM_KEY = "incoming_item";
    private TextView txtItemName, txtWarning;
    private EditText editTextUserName;
    private EditText editTextReview;
    private Button btnAddReview;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_review_item, null);
        initViews(view);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()).setView(view);
        Bundle bundle = getArguments();
        if (null != bundle) {
            final GroceryItem item = bundle.getParcelable(GROCERRY_ITEM_KEY);
            if (null != item) {
                txtItemName.setText(item.getName());
                btnAddReview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String userName = editTextUserName.getText().toString();
                        String review = editTextReview.getText().toString();
                        String date = getCurrentDate();
                        if (userName.equals("") || review.equals("")) {
                            txtWarning.setText("FIll all the blanks");
                            txtWarning.setVisibility(View.VISIBLE);
                        } else {
                            txtWarning.setVisibility(View.GONE);
                            try {
                                addReview = (AddReview) getActivity();
                                addReview.onAddReviewResult(new Review(item.getId(), userName, review, date));
                                dismiss();
                            } catch (ClassCastException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
        return builder.create();
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-YYYY");
        return sdf.format(calendar.getTime());
    }

    private void initViews(View view) {
        txtItemName = view.findViewById(R.id.txtItemName);
        txtWarning = view.findViewById(R.id.txtWarning);
        editTextUserName = view.findViewById(R.id.edittextUserName);
        editTextReview = view.findViewById(R.id.edittxtReview);
        btnAddReview = view.findViewById(R.id.btnAddReview);
    }
}
