package com.unioulu.ontime.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.bumptech.glide.Glide;
import com.unioulu.ontime.R;

public class AddPillScreenFragment extends Fragment {

    private static final int GALLERY_REQUEST = 1;

    private ImageView ivPill;
    private EditText etPill;
    private EditText etPillAmount;
    private RadioButton rbMorning;
    private RadioButton rbAfternoon;
    private RadioButton rbEvening;
    private Button btnPillSave;
    private Button btnPillCancel;
    private Button btnPillDelete;

    // The interaction listener is defined.
    private OnFragmentInteractionListener mListener;

    public AddPillScreenFragment() {
        // Empty constructor
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static AddPillScreenFragment newInstance() {
        return new AddPillScreenFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_pill, container, false);

        btnPillSave = (Button) rootView.findViewById(R.id.btnPillSave);
        btnPillSave.setOnClickListener(btnPillSaveClickListener);

        btnPillCancel = (Button) rootView.findViewById(R.id.btnPillCancel);
        btnPillCancel.setOnClickListener(btnPillCancelClickListener);

        btnPillDelete = (Button) rootView.findViewById(R.id.btnPillDelete);
        btnPillDelete.setOnClickListener(btnPillDeleteClickListener);

        ivPill = (ImageView) rootView.findViewById(R.id.iv_pill);
        ivPill.setOnClickListener(pillImageClickListener);

        etPill = (EditText) rootView.findViewById(R.id.et_pill);
        etPillAmount = (EditText) rootView.findViewById(R.id.et_pillAmount);

        rbMorning = (RadioButton) rootView.findViewById(R.id.rb_pillMorning);
        rbAfternoon = (RadioButton) rootView.findViewById(R.id.rb_pillAfternoon);
        rbEvening = (RadioButton) rootView.findViewById(R.id.rb_pillEvening);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setFragmentDetails(String pillName, String pillImage) {
        etPill.setText(pillName);

        // Getting the images
        Glide.with(getContext())
                .asBitmap()
                .load(pillImage)
                .into(ivPill);

        btnPillCancel.setVisibility(View.GONE);
        btnPillDelete.setVisibility(View.VISIBLE);
    }

    private View.OnClickListener btnPillSaveClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: Save the pill into database.
        }
    };

    private View.OnClickListener btnPillCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // Cleaning the data
            ivPill.setImageDrawable(getResources().getDrawable(R.drawable.ic_pill_icon));
            etPill.setText("");
            etPillAmount.setText("");
            rbMorning.setChecked(false);
            rbAfternoon.setChecked(false);
            rbEvening.setChecked(false);

            mListener.pillCancel();
        }
    };

    private View.OnClickListener btnPillDeleteClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO: Remove the pill from database.
        }
    };

    private View.OnClickListener pillImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");

            startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == Activity.RESULT_OK) {
            if(requestCode == 1) {
                final Uri imgSelected = data.getData();
                ivPill.setImageURI(imgSelected);
            }
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void pillCancel();
    }
}