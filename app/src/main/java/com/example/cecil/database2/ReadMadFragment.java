package com.example.cecil.database2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ReadMadFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Tager indtastet info og viser den
 */
public class ReadMadFragment extends Fragment {
    private TextView TxtInfo;
    private Button BnTilbageRead;

    private OnFragmentInteractionListener mListener;

    public ReadMadFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_read_mad, container, false);
        TxtInfo = view.findViewById(R.id.txt_display_info);
        BnTilbageRead = view.findViewById(R.id.bn_tilbage_read);

        List<Mad> mads = MainActivity.myAppDatabase.myDao().getMad();

        String info = "";
        for(Mad mk : mads)
        {
            String hf12 = mk.getHF12();
            String hf3 = mk.getHF3();
            String hf4 = mk.getHF4();
            String Fedt = mk.getFedt();
            int id = mk.getId();
            String dato = mk.getDato();

            info = info+"\n\n"+"Dato : " +dato+"\n"
                    +"HF1+2 :"+hf12+"\n"
                    +"HF3 : "+hf3 +"\n"
                    +"HF4 : "+hf4 +"\n"
                    +"1-3 spsk. fedt : "+Fedt;
        }
        TxtInfo.setText(info);

        BnTilbageRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.fragmentManager.beginTransaction().replace(R.id.fragment_container, new HomeFragment()).
                        addToBackStack(null).commit();
            }});


        return view;
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
