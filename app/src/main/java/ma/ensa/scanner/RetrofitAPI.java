package ma.ensa.scanner;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @POST("occupation/api")

        //on below line we are creating a method to post our data.
    Call<Occupation> createPost(@Body Occupation occupation);
}
