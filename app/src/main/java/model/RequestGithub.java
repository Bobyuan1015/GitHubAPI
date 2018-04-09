package model;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by bob on 2018-01-13.
 */

public interface RequestGithub {
    @GET("/search/users")
        //The second part of the URL
    Observable<GithubUser> getUsers(@Query("q") String user);
}
