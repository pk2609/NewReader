package com.appexample.newsreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.widget.PullRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Main2Activity> newsFeed = new ArrayList<>();
    PullRefreshLayout layout;
    private RecyclerView recyclerView;
    private Adapter mAdapter;
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Always cast your custom Toolbar here, and set it as the ActionBar.
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        tb.setTitle("News Reader");
        setSupportActionBar(tb);
        //tb.setTitle("News Reader");
//        // Get the ActionBar here to configure the way it behaves.
//        final ActionBar ab = getSupportActionBar();
//        //ab.setHomeAsUpIndicator(R.drawable.ic_menu); // set a custom icon for the default home button
//        ab.setDisplayShowCustomEnabled(true); // enable overriding the default toolbar layout
//        ab.setDisplayShowTitleEnabled(false); // disable the default title element here (for centered title)

        layout = (PullRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        newsFeed=new ArrayList<>();
        mAdapter = new Adapter(this, newsFeed);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        mAdapter.notifyDataSetChanged();
        //engine();
        LoadCache();
//        addClickListener();


// listen refresh event
        layout.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // start refresh
                engine();
            }
        });

// refresh complete
    }


    private void engine() {

//        final ArrayAdapter<Main2Activity> adapter = new customAdapter();

//        ListView newsItems = (ListView) (findViewById(R.id.newsList));
//         newsItems.setAdapter(adapter);

        

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest myRequest = new JsonObjectRequest(Request.Method.GET,
                "https://newsapi.org/v1/articles?source=ign&sortBy=top&apiKey=5f8f27265217470e993530b14532d45f", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(MainActivity.this, "Updated", Toast.LENGTH_SHORT).show();
                            JSONArray newsItems = response.getJSONArray("articles");
                            newsFeed = new ArrayList<>();
                            for (int i = 0; i < newsItems.length(); i++) {
                                JSONObject temp = newsItems.getJSONObject(i);

                                String author = temp.getString("author");
                                String title = temp.getString("title");
                                String description = temp.getString("description");
                                String url = temp.getString("url");
                                String imgurl = temp.getString("urlToImage");
                                String published = temp.getString("publishedAt");

                                newsFeed.add(new Main2Activity(author, title, description, url, imgurl, published));
                                //adapter.notifyDataSetChanged();
                            }
                            layout.setRefreshing(false);

                            mAdapter = new Adapter(MainActivity.this, newsFeed);
                            recyclerView.setAdapter(mAdapter);
                            recyclerView.setLayoutManager(linearLayoutManager);
                        } catch (JSONException e) {
                            Log.i("myTag", e.toString());
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("myTag", error.toString());
                        LoadCache();
                    }
                });
        myRequest.setShouldCache(true);
        AppController.getInstance().addToRequestQueue(myRequest);
//        myRequest.setRetryPolicy(new DefaultRetryPolicy(
//                10000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//        queue.add(myRequest);


    }

    private void LoadCache() {
        String url_1 = "https://newsapi.org/v1/articles?source=ign&sortBy=top&apiKey=5f8f27265217470e993530b14532d45f";
        Cache cache = AppController.getInstance().getRequestQueue().getCache();
        Cache.Entry entry = cache.get(url_1);
        if (entry != null) {
            try {
                String data = new String(entry.data, "UTF-8");
                Toast.makeText(MainActivity.this, "Came here \n"+data, Toast.LENGTH_SHORT).show();
                // handle data, like converting it to xml, json, bitmap etc.,
                JSONObject response=new JSONObject(data);
                JSONArray newsItems = response.getJSONArray("articles");
                newsFeed = new ArrayList<>();
                for (int i = 0; i < newsItems.length(); i++) {
                    JSONObject temp = newsItems.getJSONObject(i);

                    String author = temp.getString("author");
                    String title = temp.getString("title");
                    String description = temp.getString("description");
                    String url = temp.getString("url");
                    String imgurl = temp.getString("urlToImage");
                    String published = temp.getString("publishedAt");

                    newsFeed.add(new Main2Activity(author, title, description, url, imgurl, published));
                }
                mAdapter = new Adapter(this, newsFeed);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setLayoutManager(linearLayoutManager);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            // Cached response doesn't exists. Make network call here
            Toast.makeText(MainActivity.this, "No Cache !! god knows", Toast.LENGTH_SHORT).show();

        }

    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }

//    private void addClickListener() {
//        RecyclerView newsItems = (RecyclerView) (findViewById(R.id.list));
//        ListView newsItems = (ListView) (findViewById(R.id.newsList));
//        newsItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(MainActivity.this, "I'm here at least...!", Toast.LENGTH_SHORT).show();
//                    Main2Activity currentItem = newsFeed.get(position);
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(currentItem.getUrl()));
//                startActivity(i);
//            }
//        });
//    }
}

//    private class customAdapter extends ArrayAdapter<Main2Activity> {
//        public customAdapter() {
//            super(MainActivity.this, R.layout.item, newsFeed);
//        }

//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {

//            if(convertView == null) {
//                convertView = getLayoutInflater().inflate(R.layout.item, parent, false);
//            }

//             Main2Activity currentItem = newsFeed.get(position);

//            ImageView newsImage = (ImageView) convertView.findViewById(R.id.img);
//            TextView heading = (TextView) convertView.findViewById(R.id.title);
//            TextView author = (TextView) convertView.findViewById(R.id.author);
//            TextView desc = (TextView) convertView.findViewById(R.id.desc);
//            TextView published = (TextView) convertView.findViewById(R.id.published);



//            heading.setText(currentItem.getTitle());
//            desc.setText(currentItem.getDescription());
//            if(!currentItem.getPublished().contentEquals("null")){
//                published.setText(currentItem.getPublished());
//            }else {
//                published.setText("");
//            }
//            if(!currentItem.getAuthor().contentEquals("null")){
//                author.setText(currentItem.getAuthor());
//            }else {
//                author.setText("");
//            }
//            Picasso.with(MainActivity.this).load(currentItem.getImgurl()).into(newsImage);


//            return convertView;
//        }
//    }
//}
