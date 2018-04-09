package layout;

import android.content.Context;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tab.bob.yuantabs.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String Tag = "fang";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView mTextEmail, mTextPassword;
    private EditText mEditEmail, mEditPassword;
    private Button mBLogin;
    private boolean mClickFlage = false;
    private OnFragmentInteractionListener mListener;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        mTextEmail = view.findViewById(R.id.text_email);

        mTextPassword = view.findViewById(R.id.text_password);
        mEditEmail = view.findViewById(R.id.edit_email);

        mEditEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditEmail.setCursorVisible(true);
                mClickFlage = true;
            }
        });
        mEditEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mBLogin.setBackgroundColor(0xff99cc00);
                mBLogin.setTextColor(0xFF000000);
                    mTextEmail.setHint(R.string.email_address);

                    Log.d(Tag, "b=" + b);
                    if (b) {
                        mTextEmail.setHintTextColor(0xff99cc00);
                    } else {
                        mTextEmail.setHintTextColor(0xff808080);
                    }

            }
        });

        mEditPassword = view.findViewById(R.id.edit_password);
        mEditPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                mTextPassword.setHint(R.string.email_address);
                mBLogin.setBackgroundColor(0xff99cc00);
                mBLogin.setTextColor(0xFF000000);
                Log.d(Tag,"b="+ b);
                if( b ){
                    mTextPassword.setHintTextColor(0xff99cc00);
                }else{
                    mTextPassword.setHintTextColor(0xff808080);
                }
            }
        });
        mBLogin = view.findViewById(R.id.loginButton);
        mBLogin.setBackgroundColor(0xFFD9E9B3);
        mBLogin.setTextColor(0xFFBBC5AA);
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
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
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
