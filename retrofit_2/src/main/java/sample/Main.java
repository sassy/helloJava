package sample;

import java.io.IOException;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Body;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.RequestBody;
import okhttp3.MediaType;


public class Main {
  public interface GitHubService {
    @GET("users/sassy/repos")
    Call<ResponseBody> listRepos();
    @POST("repos/sassy/DLImageFromTwitter/comments/0/reactions")
    Call<ResponseBody> setReaction(@Body RequestBody body);
  }

  public static void main(String args[]) {
    OkHttpClient okhttpclient = new OkHttpClient();
    Retrofit retrofit = new Retrofit.Builder()
                              .client(okhttpclient)
                              .baseUrl("https://api.github.com")
                              .build();

    GitHubService service = retrofit.create(GitHubService.class);

    try {
      ResponseBody response = service.listRepos().execute().body();
      String ret = response.string();
      System.out.println(ret);
      response.close();

      String json = "{ 'content': 'heart' }";
      RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
      int code = service.setReaction(body).execute().code();
      System.out.println(Integer.toString(code));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
