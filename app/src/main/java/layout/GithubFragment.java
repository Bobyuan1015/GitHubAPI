package layout;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
//import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import com.tab.bob.yuantabs.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import model.GithubUser;
import model.Item;
import model.RequestGithub;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.id.list;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GithubFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GithubFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GithubFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String Tag = "GitFang";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SearchView mSearchView;
    private ListView mListView;
    private ArrayList<String> mArraylist;
    private ArrayAdapter<String> mArrayAdapter;
    private OnFragmentInteractionListener mListener;

    public GithubFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GithubFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GithubFragment newInstance(String param1, String param2) {
        GithubFragment fragment = new GithubFragment();
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
       // setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_github, container , false);
        mSearchView = view.findViewById(R.id.searchviewId);
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                Log.d(Tag,"submit="+s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(Tag,"change="+s);
                mArraylist.clear();
                queryGitHubUsers(s);
                return false;
            }
        });
        mListView = view.findViewById(R.id.listviewId);
        mArraylist = new ArrayList<String>();

        mArrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, mArraylist);
        mListView.setAdapter( mArrayAdapter );
        return view;
    }

    public void queryGitHubUsers(String keywords){

        Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.github.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

        RequestGithub service = retrofit.create(RequestGithub.class);
        Observable<GithubUser> call = service.getUsers( keywords );

             call.subscribeOn( Schedulers.io())
                 .observeOn( AndroidSchedulers.mainThread())
                 .subscribe( new Observer<GithubUser>(){
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.i(Tag,"onSubscribe");
                    }

                    @Override
                    public void onNext(GithubUser githubUser) {

                        if( githubUser !=null){
                            List<Item> items = githubUser.getItems();
                            for(Item a: items){
                                mArraylist.add(a.getLogin());
                                Log.i(Tag,"onNext:"+ a.getLogin() );
                            }
                            mArrayAdapter.notifyDataSetChanged();
                        }

                    }

                    @Override
                    public void onError(Throwable t) {
                        mArraylist.clear();
                        mArraylist.add(t.toString());
                        mArrayAdapter.notifyDataSetChanged();
                        Log.i(Tag,"onError: "+t );
                    }

                    @Override
                    public void onComplete() {
                        Log.i(Tag,"onComplete" );
                    }
                });

//        call.enqueue(new Callback<GithubUser>>) {
//            @Override
//            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
//                if( response.body() !=null){
//                    List<Item> items = response.body().getItems();
//
//                    for(Item a: items){
//                        mArraylist.add(a.getLogin());
//                        Log.i(Tag,"login:"+ a.getLogin() );
//                    }
//                    mArrayAdapter.notifyDataSetChanged();
//                }
//            }
//            @Override
//            public void onFailure(Call<GithubUser>>call, Throwable t) {
//                Log.d(Tag,"fail "+t);
//            }
//        });
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.search_menu,menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }

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
