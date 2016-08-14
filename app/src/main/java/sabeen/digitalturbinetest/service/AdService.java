package sabeen.digitalturbinetest.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;
import sabeen.digitalturbinetest.model.Ads;

/**
 * Interface for using Retrofit for getting Data Resource of Ads
 * Created by sabeen on 8/12/16.
 */

public interface AdService {
    @GET
    Call<Ads> listAds(@Url String url);
}
