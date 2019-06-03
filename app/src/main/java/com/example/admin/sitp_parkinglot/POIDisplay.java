package com.example.admin.sitp_parkinglot;
/**
 * 此demo演示poi搜索功能
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.Adapter.ParkingAdapter;
import com.baidu.BDLocationUtils;
import com.baidu.Const;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.baidu.mapapi.overlayutil.PoiOverlay;
import com.baidu.mapapi.search.core.CityInfo;
import com.baidu.mapapi.search.core.PoiDetailInfo;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiDetailSearchOption;
import com.baidu.mapapi.search.poi.PoiDetailSearchResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.poi.PoiSortType;

import java.util.ArrayList;
import java.util.List;
/**
 * 演示poi搜索功能
 */
public class POIDisplay extends FragmentActivity implements
        OnGetPoiSearchResultListener {

    private PoiSearch mPoiSearch = null;
    private int loadIndex = 0;
    private LatLng center;// = new LatLng(28.544147,115.934743);//(39.92235, 116.380338);
    private int radius = 1000;
    private LatLng southwest;// = new LatLng(28.544147,115.934743);//( 39.92235, 116.380338 );
    private LatLng northeast;// = new LatLng(28.569043,115.969382);//( 39.947246, 116.414977);
    private LatLngBounds searchBound;// = new LatLngBounds.Builder().include(southwest).include(northeast).build();
    private Button LookMap;
    private ListView display;
    private List<PoiInfo> fruitList = new ArrayList<PoiInfo>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 初始化搜索模块，注册搜索事件监听


        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_poidisplay);
        BDLocationUtils bdLocationUtils = new BDLocationUtils(POIDisplay.this);
        bdLocationUtils.doLocation();//开启定位
        bdLocationUtils.mLocationClient.start();
        center= new LatLng(Const.LATITUDE,Const.LONGITUDE);//(39.92235, 116.380338);
        southwest= new LatLng(Const.LATITUDE,Const.LONGITUDE);//( 39.92235, 116.380338 );
        northeast= new LatLng(Const.LATITUDE+28.569043-28.544147,Const.LONGITUDE+115.969382-115.934743);//( 39.947246, 116.414977);
        searchBound= new LatLngBounds.Builder().include(southwest).include(northeast).build();


        mPoiSearch = PoiSearch.newInstance();
        mPoiSearch.setOnGetPoiSearchResultListener(this);
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                .keyword("停车场")
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(center)
                .radius(radius)
                .pageNum(loadIndex)
                .scope(1);

        mPoiSearch.searchNearby(nearbySearchOption);
        LookMap=(Button)findViewById(R.id.button16);
        LookMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Look=new Intent(POIDisplay.this,PoiSearchDemo.class);
                startActivity(Look);
            }
        });
        display=(ListView)findViewById(R.id.PoiDisplay);
    }
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mPoiSearch.destroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
    /**
     * 响应周边搜索按钮点击事件
     *
     * @param v    检索Button
     */
    public void  searchNearbyProcess(View v) {
        PoiNearbySearchOption nearbySearchOption = new PoiNearbySearchOption()
                .keyword("停车场")
                .sortType(PoiSortType.distance_from_near_to_far)
                .location(center)
                .radius(radius)
                .pageNum(loadIndex)
                .scope(1);

        mPoiSearch.searchNearby(nearbySearchOption);
    }
    /**
     * 获取POI搜索结果，包括searchInCity，searchNearby，searchInBound返回的搜索结果
     *
     * @param result    Poi检索结果，包括城市检索，周边检索，区域检索
     */
    public void onGetPoiResult(PoiResult result) {
        if (result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {
            Toast.makeText(POIDisplay.this, "未找到结果", Toast.LENGTH_LONG).show();
            return;
        }

        if (result.error == SearchResult.ERRORNO.NO_ERROR) {
//            Toast.makeText(POIDisplay.this,result.getAllPoi().get(0).getDistance(),Toast.LENGTH_SHORT).show();
            fruitList=result.getAllPoi();
            ParkingAdapter adapter = new ParkingAdapter(POIDisplay.this, R.layout.item, fruitList);
            ListView listView =(ListView) findViewById(R.id.PoiDisplay);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String name = (String) ((TextView)view.findViewById(R.id.Name)).getText();
                    String address = (String) ((TextView)view.findViewById(R.id.Address)).getText();
                    String distance = (String) ((TextView)view.findViewById(R.id.Distance)).getText();

                    LatLng loc=fruitList.get(i).location;

                    Const.ParkLongtitude=loc.longitude;
                    Const.ParkLatitude=loc.latitude;
                    Const.ParkName=name;
                    Intent intent=new Intent(POIDisplay.this,ParkDetailDisplay.class);
                    Bundle bundle=new Bundle();
                    bundle.putString("Name",name);
                    bundle.putString("Address",address);
                    bundle.putString("Distance",distance);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });

        }

        if (result.error == SearchResult.ERRORNO.AMBIGUOUS_KEYWORD) {
            // 当输入关键字在本市没有找到，但在其他城市找到时，返回包含该关键字信息的城市列表
            String strInfo = "在";

            for (CityInfo cityInfo : result.getSuggestCityList()) {
                strInfo += cityInfo.city;
                strInfo += ",";
            }

            strInfo += "找到结果";
            Toast.makeText(POIDisplay.this, strInfo, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 获取POI详情搜索结果，得到searchPoiDetail返回的搜索结果
     * V5.2.0版本之后，还方法废弃，使用{@link #onGetPoiDetailResult(PoiDetailSearchResult)}代替
     * @param result    POI详情检索结果
     */
    public void onGetPoiDetailResult(PoiDetailResult result) {
        if (result.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(POIDisplay.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(POIDisplay.this,
                    result.getName() + ": " + result.getAddress(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onGetPoiDetailResult(PoiDetailSearchResult poiDetailSearchResult) {
        if (poiDetailSearchResult.error != SearchResult.ERRORNO.NO_ERROR) {
            Toast.makeText(POIDisplay.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
        } else {
            List<PoiDetailInfo> poiDetailInfoList = poiDetailSearchResult.getPoiDetailInfoList();
            if (null == poiDetailInfoList || poiDetailInfoList.isEmpty()) {
                Toast.makeText(POIDisplay.this, "抱歉，检索结果为空", Toast.LENGTH_SHORT).show();
                return;
            }

            for (int i = 0; i < poiDetailInfoList.size(); i++) {
                PoiDetailInfo poiDetailInfo = poiDetailInfoList.get(i);
                if (null != poiDetailInfo) {
                    Toast.makeText(POIDisplay.this,
                            poiDetailInfo.getName() + ": " + poiDetailInfo.getAddress(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {

    }


    private class MyPoiOverlay extends PoiOverlay {
        MyPoiOverlay(BaiduMap baiduMap) {
            super(baiduMap);
        }

        @Override
        public boolean onPoiClick(int index) {
            super.onPoiClick(index);
            PoiInfo poi = getPoiResult().getAllPoi().get(index);
            mPoiSearch.searchPoiDetail((new PoiDetailSearchOption()).poiUid(poi.uid));
            return true;
        }
    }
}
