package com.ujjwal.wallcraft;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.material.navigation.NavigationView;
import com.ujjwal.wallcraft.adapter.WallpaperAdapter;
import com.ujjwal.wallcraft.models.WallpaperModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements WallpaperAdapter.OnItemListener {
    private static final String TAG = "MainActivity";

    private RecyclerView recyclerView;
    private RequestQueue requestQueue;
    private List<WallpaperModel> mList;
    private EditText search;
    private ImageView searchIcon;
    private String searcheditem="wallpapers" ;
    WallpaperAdapter adapter;
    private int pagenumber = 1;
    private AdView mAdView;
    Button next,previous;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawerLayout;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    private TextView set;
    private NestedScrollView nest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        AdView adView = new AdView(this);

        adView.setAdSize(AdSize.BANNER);

        adView.setAdUnitId("ca-app-pub-6391723981977900/8172791187");
// TODO: Add adView to your view hierarchy.
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.

            }

            @Override
            public void onAdFailedToLoad(LoadAdError adError) {
                // Code to be executed when an ad request fails.
                Log.e(TAG, "onAdFailedToLoad: " + adError.getMessage());
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }
        });

        navigationView = (NavigationView) findViewById(R.id.navmenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

//        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
//        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this::onConnectionFailed)
//                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();
        set=findViewById(R.id.topMostTitle);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.nav_abstract:
                        set.setText("Abstract");
                        searcheditem = "abstract";
                        requestQueue = VolleySingleton.getmInstance(MainActivity.this).getRequestQueue();

                        mList = new ArrayList<>();
                        fetchdata();
                        nest.fullScroll(0);


                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.nav_animal:
                        set.setText("Animal");

                        searcheditem = "animal";
                        requestQueue = VolleySingleton.getmInstance(MainActivity.this).getRequestQueue();

                        mList = new ArrayList<>();
                        fetchdata();
                        nest.fullScroll(0);


                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.nav_nature:
                        set.setText("Nature");

                        searcheditem = "nature";
                        requestQueue = VolleySingleton.getmInstance(MainActivity.this).getRequestQueue();

                        mList = new ArrayList<>();
                        fetchdata();
                        nest.fullScroll(0);


                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;
                    case R.id.nav_famous:
                        set.setText("Famous");

                        searcheditem = "famous";
                        requestQueue = VolleySingleton.getmInstance(MainActivity.this).getRequestQueue();

                        mList = new ArrayList<>();
                        fetchdata();
                        nest.fullScroll(0);


                        drawerLayout.closeDrawer(GravityCompat.START);

                        break;

                    case R.id.nav_privacy:
                        Uri uri = Uri.parse("https://www.freeprivacypolicy.com/live/80285e2e-db5a-4a32-9ed4-71958fac9a72");
                        Intent inten = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(inten);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.nav_logout:
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                        alertDialogBuilder.setTitle("Exit Application?");
                        alertDialogBuilder
                                .setMessage("Click yes to exit!")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                moveTaskToBack(true);
                                                android.os.Process.killProcess(android.os.Process.myPid());
                                                System.exit(1);
                                            }
                                        })

                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();


//                        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
//                            @Override
//                            public void onResult(@NonNull Status status) {
//                                if (status.isSuccess())
//                                    gotogoogleactivity();
//                                else
//                                    Toast.makeText(MainActivity.this, "Failed to Logout", Toast.LENGTH_SHORT).show();
//                            }
//
//                            private void gotogoogleactivity() {
//
//                                Intent intent = new Intent(MainActivity.this, GoogleLoginActivity.class);
//                                startActivity(intent);
//                                Toast.makeText(MainActivity.this, "Logout Successfully", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                }
                return true;
            }
        });


        nest=findViewById(R.id.nest);

        search = findViewById(R.id.search);
        searchIcon = findViewById(R.id.search_image);
        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = VolleySingleton.getmInstance(MainActivity.this).getRequestQueue();

                mList = new ArrayList<>();


                pagenumber++;
                fetchdata();
                nest.fullScroll(0);


            }
        });
        previous=findViewById(R.id.previous);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestQueue = VolleySingleton.getmInstance(MainActivity.this).getRequestQueue();

                mList = new ArrayList<>();


                pagenumber--;
                fetchdata();
                nest.fullScroll(0);

            }
        });


        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                searcheditem = search.getText().toString();
                search.setText("");
                set.setText(searcheditem);
                requestQueue = VolleySingleton.getmInstance(MainActivity.this).getRequestQueue();

                mList = new ArrayList<>();
                fetchdata();


            }
        });


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        requestQueue = VolleySingleton.getmInstance(this).getRequestQueue();

        mList = new ArrayList<>();

        fetchdata();


    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    //    public class Fetchdata extends AsyncTask<String,Void,String> {
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            Log.d(TAG, "onPostExecute: "+s);
//            try{
//                JSONObject object;
//                object = new JSONObject(s);
//                JSONArray jsonArray = object.getJSONArray("results");
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                    String tags = jsonObject.getString("id");
//                    String largeimageUrl=jsonObject.getString("color");
//                    JSONObject jsonSrc=jsonObject.getJSONObject("urls");
//                    String imageUrl = jsonSrc.getString("raw");
//
//                    WallpaperModel post = new WallpaperModel(imageUrl, tags,largeimageUrl);
//                    mList.add(post);
//
//                }
//                adapter = new WallpaperAdapter(MainActivity.this, mList,MainActivity.this::onItemClicked);
//                recyclerView.setAdapter(adapter);
//
//
//                adapter.notifyDataSetChanged();
//                pagenumber++;
//
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
//
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//            String result=null;
//
//            String url="https://api.unsplash.com/search/photos?client_id=lvKDaTfZRng1zGfNtn9_h_LNBdGf3yo_Ju8pd5Dg4nA&page="+pagenumber+"&query="+searcheditem+"";
//
//
//
//            try {
//
//                URL url1 = new URL(url);
//
//                HttpURLConnection connection=(HttpURLConnection) url1.openConnection();
//                connection.setRequestMethod("GET");
//                connection.connect();
//                int response=connection.getResponseCode();
//                Log.d(TAG, "doInBackground: "+response);
//
//
//                if(connection.getResponseCode()== HttpURLConnection.HTTP_OK){
//                    InputStreamReader inputStreamReader=new InputStreamReader(connection.getInputStream());
//                    BufferedReader reader=new BufferedReader(inputStreamReader);
//                    StringBuilder stringBuilder=new StringBuilder();
//                    String line;
//                    while (null!=(line=reader.readLine())){
//                        stringBuilder.append(line).append("\n");
//
//                    }
//                   result= stringBuilder.toString();
//                    Log.d(TAG, "doInBackground: ends"+result);
//                }
//
//            } catch (IOException | SecurityException e){
//                e.printStackTrace();
//            }
//
//
//
//
//            return result;
//
//        }
//    }


    private void fetchdata() {
        String url;
              url = "https://pixabay.com/api/?key=22146401-3b60d059e3d4ff52d8209aac1&q=" + searcheditem + "&page=" + pagenumber + "&image_type=photo";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
//                        JSONObject jsonname = jsonObject.getJSONObject("user");
//                        JSONObject jsonlink=jsonObject.getJSONObject("links");

                        String tags = jsonObject.getString("user");
                        String largeimageUrl = jsonObject.getString("largeImageURL");
//                        JSONObject jsonSrc = jsonObject.getJSONObject("urls");
                        String imageUrl = jsonObject.getString("webformatURL");

                        WallpaperModel post = new WallpaperModel(imageUrl, tags, largeimageUrl);
                        mList.add(post);

                    }
                    adapter = new WallpaperAdapter(MainActivity.this, mList, MainActivity.this::onItemClicked);
                    recyclerView.setAdapter(adapter);


                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);


    }


    @Override
    public void onItemClicked(int position) {

        Intent intent = new Intent(this, PhotoDetail.class);
        intent.putExtra(PHOTO_TRANSFER, mList.get(position));
        startActivity(intent);

    }

//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
}