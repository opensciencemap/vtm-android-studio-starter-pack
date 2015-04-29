package io.vtm.mapapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import org.oscim.android.cache.TileCache;
import org.oscim.layers.tile.buildings.BuildingLayer;
import org.oscim.layers.tile.vector.VectorTileLayer;
import org.oscim.layers.tile.vector.labeling.LabelLayer;
import org.oscim.map.Layers;
import org.oscim.map.Map;
import org.oscim.android.MapView;
import org.oscim.theme.VtmThemes;
import org.oscim.tiling.TileSource;
import org.oscim.tiling.source.oscimap4.OSciMap4TileSource;


public class MapActivity extends AppCompatActivity {

    private MapView mMapView;
    private Map mMap;
    private TileSource mTileSource;
    private VectorTileLayer mBaseLayer;
    private TileCache mCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mMapView = (MapView) findViewById(R.id.mapView);
        mMap = mMapView.map();

        mTileSource = new OSciMap4TileSource();

        mCache = new TileCache(this, null, "opensciencemap-tiles.db");
        mCache.setCacheSize(512 * (1 << 10));
        mTileSource.setCache(mCache);

        mBaseLayer = mMap.setBaseMap(mTileSource);

        Layers layers = mMap.layers();
        layers.add(new BuildingLayer(mMap, mBaseLayer));
        layers.add(new LabelLayer(mMap, mBaseLayer));

        mMap.setTheme(VtmThemes.DEFAULT);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mCache != null)
            mCache.dispose();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
