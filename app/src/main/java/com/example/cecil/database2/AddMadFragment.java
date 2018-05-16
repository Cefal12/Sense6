package com.example.cecil.database2;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddMadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */

public class AddMadFragment extends Fragment {
    private EditText HF12, HF3, HF4, fedt, Id;
    private Button BnSave, BnDate, BnKamera, BnTilbageMad;
    private TextView Dato;
    private ImageView imageView;


    private static final int CAMERA_PIC_REQUEST = 1337;

    private OnFragmentInteractionListener mListener;


    public AddMadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_mad, container, false);
        //Id = view.findViewById(R.id.txt_mad_id);
        HF12 = (EditText) view.findViewById(R.id.txt_HF12);
        HF3 = (EditText) view.findViewById(R.id.txt_HF3);
        HF4 = (EditText) view.findViewById(R.id.txt_HF4);
        fedt = (EditText) view.findViewById(R.id.txt_fedt);
        BnSave = (Button) view.findViewById(R.id.bn_save_mad);
        BnDate = (Button) view.findViewById(R.id.bn_date);
        Dato = (TextView) view.findViewById(R.id.date);
        Dato.setText("");
        BnKamera = (Button) view.findViewById(R.id.bn_kamera);
        imageView = (ImageView) view.findViewById(R.id.img_imageView);
        BnTilbageMad = view.findViewById(R.id.bn_tilbage_add);

        BnTilbageMad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).
                        addToBackStack(null).commit();
            }});

        BnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //if date not chosen, warn user with textbox
                String datoString = Dato.getText().toString();
                String hf12 = HF12.getText().toString();
                String hf3 = HF3.getText().toString();
                String hf4 = HF4.getText().toString();
                String Fedt = fedt.getText().toString();


                Mad mad = new Mad();
                mad.setHF12(hf12);
                mad.setHF3(hf3);
                mad.setHF4(hf4);
                mad.setFedt(Fedt);
                mad.setDato(datoString);


                /*imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();
                Bitmap bitmap = imageView.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 200, baos);
                byte[] data = baos.toByteArray();
                mad.setFoto(data);*/

                MainActivity.myAppDatabase.myDao().addMad(mad);
                Toast.makeText(getActivity(), "Måltid tilføjet til madkassen", Toast.LENGTH_SHORT).show();

                HF12.setText("");
                HF3.setText("");
                HF4.setText("");
                fedt.setText("");
                //Date.setText("");



            }
        });

        BnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dFragment = new DatePickerFragment();

                dFragment.show(getFragmentManager(), "Date Picker");
            }
        });



        BnKamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                    if (ActivityCompat.shouldShowRequestPermissionRationale((Activity)
                            getContext(), Manifest.permission.CAMERA)) {
                        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, CAMERA_PIC_REQUEST);

                    } else {
                        ActivityCompat.requestPermissions((Activity) getContext(),
                                new String[]{Manifest.permission.CAMERA},
                                1);
                    }

                }else{
                    Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, CAMERA_PIC_REQUEST);
                }
            }


        });





        return view;


}



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_PIC_REQUEST) {
             //super.onActivityResult(requestCode, resultCode, data);
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            ImageView imageView = (ImageView) getActivity().findViewById(R.id.img_imageView);
            imageView.setImageBitmap(bitmap);
        }

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}
