package com.NaviDemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.Const;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNRoutePlanManager;
import com.baidu.navisdk.adapter.IBNTTSManager;
import com.baidu.navisdk.adapter.IBaiduNaviManager;
import com.example.admin.sitp_parkinglot.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2019-02-02.
 */

public class NaviActivity extends AppCompatActivity {
    private static final String APP_FOLDER_NAME = "BNSDKSimpleDemo";

    static final String ROUTE_PLAN_NODE = "routePlanNode";

    private static final String[] authBaseArr = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private Button mWgsNaviBtn = null;
    private Button mGcjNaviBtn = null;
    private Button mBdmcNaviBtn = null;
    private Button mDb06ll = null;
    private Button mGotoSettingsBtn = null;
    private String mSDCardPath = null;

    private static final int authBaseRequestCode = 1;

    private boolean hasInitSuccess = false;

    private com.baidu.navisdk.adapter.BNRoutePlanNode mStartNode = null;


    private TextView CarNumber;
    private TextView ParkType;
    private TextView ParkName;
    private TextView status;
    private TextView location;
    private TextView service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_navi);
        CarNumber=(TextView)findViewById(R.id.DetailCarNumber);
        ParkType=(TextView)findViewById(R.id.DetailType);
        ParkName=(TextView)findViewById(R.id.DetailParkName);
        status=(TextView)findViewById(R.id.DetailStatus);
        location=(TextView)findViewById(R.id.DetailLatitudeLongtitude);
        service=(TextView)findViewById(R.id.DetailService);
        CarNumber.setText(Const.NaviDisplay_info.getCarNumber());
        ParkType.setText(Const.NaviDisplay_info.getParkingType());
        ParkName.setText(Const.NaviDisplay_info.getParkingName());
        status.setText(Const.NaviDisplay_info.getStatus());
        location.setText(Const.NaviDisplay_info.getDstLatitude()+" ,"+ Const.NaviDisplay_info.getDstLongtitude());
        String ser=Const.NaviDisplay_info.getService();
        String SerText="";
        if(ser.charAt(0)=='1'){
            SerText+="充电服务";
        }

        if(ser.charAt(1)=='1'){
            SerText+="  洗车服务";
        }

        if(ser.charAt(2)=='1'){
            SerText+="  行车检查";
        }

        if(ser.charAt(3)=='1'){
            SerText+="  车辆保养";
        }
        service.setText(SerText);




        mDb06ll = (Button) findViewById(R.id.StartGuide);

        initListener();
        if (initDirs()) {
            initNavi();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initListener() {
        if (mWgsNaviBtn != null) {
            mWgsNaviBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
                        routeplanToNavi(com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.WGS84);
                    }
                }

            });
        }
        if (mGcjNaviBtn != null) {
            mGcjNaviBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
                        routeplanToNavi(com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.GCJ02);
                    }
                }

            });
        }
        if (mBdmcNaviBtn != null) {
            mBdmcNaviBtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
                        routeplanToNavi(com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.BD09_MC);
                    }
                }
            });
        }

        if (mDb06ll != null) {
            mDb06ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
                        routeplanToNavi(com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.BD09LL);
                    }
                }
            });
        }

        if (mGotoSettingsBtn != null) {
            mGotoSettingsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
                        NormalUtils.gotoSettings(NaviActivity.this);
                    }
                }
            });
        }
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private boolean hasBasePhoneAuth() {
        PackageManager pm = this.getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void initNavi() {
        // 申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }

        BaiduNaviManagerFactory.getBaiduNaviManager().init(this,
                mSDCardPath, APP_FOLDER_NAME, new IBaiduNaviManager.INaviInitListener() {

                    @Override
                    public void onAuthResult(int status, String msg) {
                        String result;
                        if (0 == status) {
                            result = "key校验成功!";
                        } else {
                            result = "key校验失败, " + msg;
                        }
                        Toast.makeText(NaviActivity.this, result, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void initStart() {
                        Toast.makeText(NaviActivity.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void initSuccess() {
                        Toast.makeText(NaviActivity.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                        hasInitSuccess = true;
                        // 初始化tts
                        initTTS();
                    }

                    @Override
                    public void initFailed() {
                        Toast.makeText(NaviActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    // 外置tts时需要实现的接口回调
    private IBNTTSManager.IBNOuterTTSPlayerCallback mTTSCallback = new IBNTTSManager.IBNOuterTTSPlayerCallback() {

        @Override
        public int getTTSState() {
//            /** 播放器空闲 */
//            int PLAYER_STATE_IDLE = 1;
//            /** 播放器正在播报 */
//            int PLAYER_STATE_PLAYING = 2;
            return PLAYER_STATE_IDLE;
        }

        @Override
        public int playTTSText(String text, String s1, int i, String s2) {
            Log.e("BNSDKDemo", "playTTSText:" + text);
            return 0;
        }

        @Override
        public void stopTTS() {
            Log.e("BNSDKDemo", "stopTTS");
        }
    };

    private void initTTS() {
        // 使用内置TTS
        BaiduNaviManagerFactory.getTTSManager().initTTS(getApplicationContext(),
                getSdcardDir(), APP_FOLDER_NAME, NormalUtils.getTTSAppID());

        // 不使用内置TTS
//         BaiduNaviManagerFactory.getTTSManager().initTTS(mTTSCallback);

        // 注册同步内置tts状态回调
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedListener(
                new IBNTTSManager.IOnTTSPlayStateChangedListener() {
                    @Override
                    public void onPlayStart() {
                        Log.e("BNSDKDemo", "ttsCallback.onPlayStart");
                    }

                    @Override
                    public void onPlayEnd(String speechId) {
                        Log.e("BNSDKDemo", "ttsCallback.onPlayEnd");
                    }

                    @Override
                    public void onPlayError(int code, String message) {
                        Log.e("BNSDKDemo", "ttsCallback.onPlayError");
                    }
                }
        );

        // 注册内置tts 异步状态消息
        BaiduNaviManagerFactory.getTTSManager().setOnTTSStateChangedHandler(
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.e("BNSDKDemo", "ttsHandler.msg.what=" + msg.what);
                    }
                }
        );
    }

    private void routeplanToNavi(final int coType) {
        if (!hasInitSuccess) {
            Toast.makeText(NaviActivity.this, "还未初始化!", Toast.LENGTH_SHORT).show();
        }

//        com.baidu.navisdk.adapter.BNRoutePlanNode sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(116.30142, 40.05087, "百度大厦", "百度大厦", coType);
//        com.baidu.navisdk.adapter.BNRoutePlanNode eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(116.39750, 39.90882, "北京天安门", "北京天安门", coType);

        com.baidu.navisdk.adapter.BNRoutePlanNode sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Const.LONGITUDE, Const.LATITUDE, "百度大厦", "百度大厦", coType);
        com.baidu.navisdk.adapter.BNRoutePlanNode eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Double.parseDouble(Const.NaviDisplay_info.getDstLongtitude()), Double.parseDouble(Const.NaviDisplay_info.getDstLatitude()),Const.NaviDisplay_info.getParkingName(), Const.NaviDisplay_info.getParkingName(), coType);
        switch (coType) {
            case com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.GCJ02: {
                sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Const.LONGITUDE, Const.LATITUDE, "百度大厦", "百度大厦", coType);
                eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Double.parseDouble(Const.NaviDisplay_info.getDstLongtitude()), Double.parseDouble(Const.NaviDisplay_info.getDstLatitude()),Const.NaviDisplay_info.getParkingName(), Const.NaviDisplay_info.getParkingName(), coType);
                break;
            }
            case com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.WGS84: {
                sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Const.LONGITUDE, Const.LATITUDE, "百度大厦", "百度大厦", coType);
                eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Double.parseDouble(Const.NaviDisplay_info.getDstLongtitude()), Double.parseDouble(Const.NaviDisplay_info.getDstLatitude()),Const.NaviDisplay_info.getParkingName(), Const.NaviDisplay_info.getParkingName(), coType);
                break;
            }
            case com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.BD09_MC: {
                sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Const.LONGITUDE, Const.LATITUDE, "百度大厦", "百度大厦", coType);
                eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Double.parseDouble(Const.NaviDisplay_info.getDstLongtitude()), Double.parseDouble(Const.NaviDisplay_info.getDstLatitude()),Const.NaviDisplay_info.getParkingName(), Const.NaviDisplay_info.getParkingName(), coType);
                break;
            }
            case com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.BD09LL: {
                sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Const.LONGITUDE, Const.LATITUDE, "百度大厦", "百度大厦", coType);
                eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Double.parseDouble(Const.NaviDisplay_info.getDstLongtitude()), Double.parseDouble(Const.NaviDisplay_info.getDstLatitude()),Const.NaviDisplay_info.getParkingName(), Const.NaviDisplay_info.getParkingName(), coType);
                break;
            }
            default:
                break;
        }

        mStartNode = sNode;

        List<com.baidu.navisdk.adapter.BNRoutePlanNode> list = new ArrayList<com.baidu.navisdk.adapter.BNRoutePlanNode>();
        list.add(sNode);
        list.add(eNode);

        BaiduNaviManagerFactory.getRoutePlanManager().routeplanToNavi(
                list,
                IBNRoutePlanManager.RoutePlanPreference.ROUTE_PLAN_PREFERENCE_DEFAULT,
                null,
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_START:
                                Toast.makeText(NaviActivity.this, "算路开始", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_SUCCESS:
                                Toast.makeText(NaviActivity.this, "算路成功", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_FAILED:
                                Toast.makeText(NaviActivity.this, "算路失败", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_TO_NAVI:
                                Toast.makeText(NaviActivity.this, "算路成功准备进入导航", Toast.LENGTH_SHORT)
                                        .show();
                                Intent intent = new Intent(NaviActivity.this,
                                        OldDemoGuideActivity.class);
                                Bundle bundle = new Bundle();
                                Const.guidetype=0;

                                bundle.putSerializable(ROUTE_PLAN_NODE, mStartNode);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                break;
                            default:
                                // nothing
                                break;
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == authBaseRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                } else {
                    Toast.makeText(NaviActivity.this, "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            initNavi();
        }
    }

}
