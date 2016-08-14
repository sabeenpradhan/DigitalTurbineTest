package sabeen.digitalturbinetest;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import sabeen.digitalturbinetest.adapter.AdAdapter;
import sabeen.digitalturbinetest.model.Ad;
import sabeen.digitalturbinetest.model.Ads;
import sabeen.digitalturbinetest.service.AdService;

/**
 * Activity for loading and displaying Ad
 * Created by sabeen on 8/12/16.
 */
public class AdActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private Dialog dialog;
    private SwipeRefreshLayout swipeRefreshLayout;

    private AdAdapter adAdapter;
    private List<Ad> adList;

    private Realm realm;
    private static final String ADS_URL = "http://ads.appia.com/getAds?id=236&password=OVUJ1DJN&siteId=4288&deviceId=4230&sessionId=techtestsession&totalCampaignsRequested=10&lname=Pradhan";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Digital Turbine Test");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.about_me);

        adList = new ArrayList<>();
        adAdapter = new AdAdapter(adList);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.adsSwipe);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                adList.clear();
                loadAds();
            }
        });
        //For using Realm Database
        realm = Realm.getInstance(getApplicationContext());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adAdapter);

        loadAds();
    }

    /**
     * Method for loading Ads using Retrofit
     */
    private void loadAds() {
        Call<Ads> ads = listAds(ADS_URL);
        ads.enqueue(new Callback<Ads>() {
            @Override
            public void onResponse(Call<Ads> call, Response<Ads> response) {
                try {
                    for (Ad ad : response.body().getAdList()) {
                        realm.beginTransaction();
                        // Creates new record or updates if it already exits
                        realm.copyToRealmOrUpdate(ad);
                        realm.commitTransaction();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(Call<Ads> call, Throwable t) {
                Log.d("ad_url_error", t.getLocalizedMessage());
            }
        });

        loadAdsFromDB();
    }

    /**
     * Method to load Ad from Realm Database
     */
    private void loadAdsFromDB() {
        adList.clear();
        RealmResults<Ad> results =
                realm.where(Ad.class).findAll();
        for (Ad ad : results) {
            adList.add(ad);
        }
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        swipeRefreshLayout.setRefreshing(false);
        adAdapter.notifyDataSetChanged();
    }

    /**
     * Method to list Ads using Retrofit
     * @param url to be called
     * @return call object for making asynchronous call
     */
    private Call<Ads> listAds(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ads.appia.com/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        AdService contributorService = retrofit.create(AdService.class);
        return contributorService.listAds(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.ad_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                dialog.show();
                ImageView close = (ImageView) dialog.findViewById(R.id.close);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
