package sample;

import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.Call;

public interface GitHubService {
  @GET("users/{user}/repos")
  Call<List<Repo>> listRepos(@Path("user") String user);
}
