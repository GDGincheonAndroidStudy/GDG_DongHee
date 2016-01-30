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

    private void loadProductData(){

    }

    private void resetRealm(){
        RealmConfiguration realmConfig = new RealmConfiguration
                .Builder(this)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.deleteRealm(realmConfig);
    }






}
