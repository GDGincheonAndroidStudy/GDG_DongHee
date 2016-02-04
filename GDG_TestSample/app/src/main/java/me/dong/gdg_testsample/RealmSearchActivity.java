package me.dong.gdg_testsample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import co.moonmonkeylabs.realmsearchview.RealmSearchView;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmSearchActivity extends AppCompatActivity {

    private RealmSearchView mRealmSearchView;
    private RealmSearchRecyclerViewAdapter mRealmSearchRecyclerViewAdapter;
    private Realm mRealm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realm_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //resetRealm();
        loadProductData();

        mRealmSearchView = (RealmSearchView)findViewById(R.id.search_view);

        mRealm = Realm.getInstance(this);
        mRealmSearchRecyclerViewAdapter = new RealmSearchRecyclerViewAdapter(this, mRealm, "name");
        mRealmSearchView.setAdapter(mRealmSearchRecyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mRealm != null){
            mRealm.close();
            mRealm = null;
        }
    }

    //불필요
    private void loadProductData(){
//        SimpleDateFormat formatIn = new SimpleDateFormat("MMMM d, yyyy");
//        SimpleDateFormat formatOut = new SimpleDateFormat("MM/d/yy");
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonFactory jsonFactory = new JsonFactory();
//        Random random = new Random();
//        try {
//            JsonParser jsonParserBlog =
//                    jsonFactory.createParser(getResources().openRawResource(R.raw.blog));
//            List<Blog> entries =
//                    objectMapper.readValue(jsonParserBlog, new TypeReference<List<Blog>>() {
//                    });
//
//            JsonParser jsonParserEmoji =
//                    jsonFactory.createParser(getResources().openRawResource(R.raw.emoji));
//            List<String> emojies =
//                    objectMapper.readValue(jsonParserEmoji, new TypeReference<List<String>>() {});
//
//            int numEmoji = emojies.size();
//            for (Blog blog : entries) {
//                blog.setEmoji(emojies.get(random.nextInt(numEmoji)));
//                try {
//                    blog.setDate(formatOut.format(formatIn.parse(blog.getDate())));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            Realm realm = Realm.getInstance(this);
//            realm.beginTransaction();
//            realm.copyToRealm(entries);
//            realm.commitTransaction();
//            realm.close();
//        } catch (Exception e) {
//            throw new IllegalStateException("Could not load blog data.");
//        }
    }

    //불필요
    private void resetRealm(){
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.deleteRealm(realmConfig);
    }
}
