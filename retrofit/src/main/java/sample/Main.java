package sample;

import java.io.IOException;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {
  public static void main(String[] args) throws IOException {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://api.github.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build();

    GitHubService service = retrofit.create(GitHubService.class);

    Call<List<Repo>> call = service.listRepos("sassy");
    List<Repo> repos = call.execute().body();
    for (Repo repo : repos) {
        System.out.println(repo.name);
    }
  }
}
