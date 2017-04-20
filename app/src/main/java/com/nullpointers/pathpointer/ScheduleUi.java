package com.nullpointers.pathpointer;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ScheduleUi.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ScheduleUi#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScheduleUi extends Fragment implements View.OnClickListener{
    private OnFragmentInteractionListener mListener;

    private Schedule sched;

    public ScheduleUi() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment schedule_ui.
     */
    public static ScheduleUi newInstance() {
        ScheduleUi fragment = new ScheduleUi();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_schedule_ui, container, false);
        view.findViewById(R.id.button).setOnClickListener(this);
        view.findViewById(R.id.button2).setOnClickListener(this);
        view.findViewById(R.id.button3).setOnClickListener(this);
        view.findViewById(R.id.button4).setOnClickListener(this);
        sched = Schedule.getInstance();
        return view;
}

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            switch(view.getId()) {
                case R.id.button:
                    mListener.onScheduleFragmentInteraction(1);
                    break;
                case R.id.button2:
                    mListener.onScheduleFragmentInteraction(2);
                    break;
                case R.id.button3:
                    mListener.onScheduleFragmentInteraction(3);
                    break;
                case R.id.button4:
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                    //Setting message manually and performing action on button click
                    builder.setMessage("Do you want to clear the schedule?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    sched.clear();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Clear Schedule");
                    alert.show();
                    break;
            }
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
        void onScheduleFragmentInteraction(int event);
    }
}
