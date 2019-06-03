package com.OutCar;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.icu.text.SimpleDateFormat;
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

import com.NaviDemo.NormalUtils;
import com.NaviDemo.OldDemoGuideActivity;
import com.baidu.Const;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNRoutePlanManager;
import com.baidu.navisdk.adapter.IBNTTSManager;
import com.baidu.navisdk.adapter.IBaiduNaviManager;
import com.example.admin.sitp_parkinglot.R;
import com.example.admin.sitp_parkinglot.sysClose;

import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OutCarDetail extends AppCompatActivity {
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
    private TextView Money;
    private TextView ParkType;
    private TextView ParkName;
    private TextView status;
    private TextView location;
    private TextView service;
    private int money=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sysClose.getInstance().addActivity(this);
        setContentView(R.layout.activity_out_car_detail);
        CarNumber=(TextView)findViewById(R.id.DetailCarNumber);
        Money=(TextView)findViewById(R.id.DetailMoney);
        ParkType=(TextView)findViewById(R.id.DetailType);
        ParkName=(TextView)findViewById(R.id.DetailParkName);
        status=(TextView)findViewById(R.id.DetailStatus);
        location=(TextView)findViewById(R.id.DetailLatitudeLongtitude);
        service=(TextView)findViewById(R.id.DetailService);
        CarNumber.setText(Const.OutCar_info.getCarNumber());
        ParkType.setText(Const.OutCar_info.getParkingType());
        ParkName.setText(Const.OutCar_info.getParkingName());
        status.setText(Const.OutCar_info.getStatus());
        location.setText(Const.OutCar_info.getDstLatitude()+" ,"+ Const.OutCar_info.getDstLongtitude());
        String ser=Const.OutCar_info.getService();
        String SerText="";
        int num=Integer.parseInt(ser);
        if(num/1000==1){
            SerText+="充电服务";
            money=money+30;
        }

        if((num/100%10)==1){
            SerText+="  洗车服务";
            money=money+30;
        }

        if((num/10)%10==1){
            SerText+="  行车检查";
            money=money+30;
        }

        if(num%10==1){
            SerText+="  车辆保养";
            money=money+30;
        }
        service.setText(SerText);
        String date=Const.OutCar_info.getUpdatedAt();
        if(Const.OutCar_info.getStatus().equals("已生效")){
            int type=2;
            if(Const.OutCar_info.getParkingType().equals("大型车位")){
                type=0;
            }
            else if(Const.OutCar_info.getParkingType().equals("中型车位")){
                type=1;
            }
            else if(Const.OutCar_info.getParkingType().equals("小型车位")){
                type=2;
            }


            SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date curDate =  new Date(System.currentTimeMillis());
            String str=formatter.format(curDate);
            money=money+getTimeDifference(type,date,str);
            Money.setText(Integer.toString(money));
        }else{
            Money.setText("--");
        }


        mDb06ll = (Button) findViewById(R.id.StartOut);

        initListener();
        if (initDirs()) {
            initNavi();
        }

    }


    public int getTimeDifference(int type,String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int money=0;
        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime()+8*60*60*1000;

            long hour1 = diff / (60 * 60 * 1000);
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            if(type==0){
                money+=hour1*15;
                if(min1>0)
                    money+=15;
            }
            else if(type==1){
                money+=hour1*10;
                if(min1>0)
                    money+=10;
            }
            else if(type==2){
                money+=hour1*5;
                if(min1>0)
                    money+=5;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return money;

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
                        NormalUtils.gotoSettings(OutCarDetail.this);
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
                        Toast.makeText(OutCarDetail.this, result, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void initStart() {
                        Toast.makeText(OutCarDetail.this, "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void initSuccess() {
                        Toast.makeText(OutCarDetail.this, "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                        hasInitSuccess = true;
                        // 初始化tts
                        initTTS();
                    }

                    @Override
                    public void initFailed() {
                        Toast.makeText(OutCarDetail.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(OutCarDetail.this, "还未初始化!", Toast.LENGTH_SHORT).show();
        }

//        com.baidu.navisdk.adapter.BNRoutePlanNode sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(116.30142, 40.05087, "百度大厦", "百度大厦", coType);
//        com.baidu.navisdk.adapter.BNRoutePlanNode eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(116.39750, 39.90882, "北京天安门", "北京天安门", coType);

        com.baidu.navisdk.adapter.BNRoutePlanNode sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Const.LONGITUDE, Const.LATITUDE, "百度大厦", "百度大厦", coType);
        com.baidu.navisdk.adapter.BNRoutePlanNode eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Double.parseDouble(Const.OutCar_info.getDstLongtitude()), Double.parseDouble(Const.OutCar_info.getDstLatitude()),Const.OutCar_info.getParkingName(), Const.OutCar_info.getParkingName(), coType);
        switch (coType) {
            case com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.GCJ02: {
                sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Const.LONGITUDE, Const.LATITUDE, "百度大厦", "百度大厦", coType);
                eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Double.parseDouble(Const.OutCar_info.getDstLongtitude()), Double.parseDouble(Const.OutCar_info.getDstLatitude()),Const.OutCar_info.getParkingName(), Const.OutCar_info.getParkingName(), coType);
                break;
            }
            case com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.WGS84: {
                sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Const.LONGITUDE, Const.LATITUDE, "百度大厦", "百度大厦", coType);
                eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Double.parseDouble(Const.OutCar_info.getDstLongtitude()), Double.parseDouble(Const.OutCar_info.getDstLatitude()),Const.OutCar_info.getParkingName(), Const.OutCar_info.getParkingName(), coType);
                break;
            }
            case com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.BD09_MC: {
                sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Const.LONGITUDE, Const.LATITUDE, "百度大厦", "百度大厦", coType);
                eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Double.parseDouble(Const.OutCar_info.getDstLongtitude()), Double.parseDouble(Const.OutCar_info.getDstLatitude()),Const.OutCar_info.getParkingName(), Const.OutCar_info.getParkingName(), coType);
                break;
            }
            case com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType.BD09LL: {
                sNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Const.LONGITUDE, Const.LATITUDE, "百度大厦", "百度大厦", coType);
                eNode = new com.baidu.navisdk.adapter.BNRoutePlanNode(Double.parseDouble(Const.OutCar_info.getDstLongtitude()), Double.parseDouble(Const.OutCar_info.getDstLatitude()),Const.OutCar_info.getParkingName(), Const.OutCar_info.getParkingName(), coType);
                break;
            }
            default:
                break;
        }

        mStartNode = sNode;

        List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
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
                                Toast.makeText(OutCarDetail.this, "算路开始", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_SUCCESS:
                                Toast.makeText(OutCarDetail.this, "算路成功", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_FAILED:
                                Toast.makeText(OutCarDetail.this, "算路失败", Toast.LENGTH_SHORT)
                                        .show();
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_TO_NAVI:
                                Toast.makeText(OutCarDetail.this, "算路成功准备进入导航", Toast.LENGTH_SHORT)
                                        .show();
                                Intent intent = new Intent(OutCarDetail.this,
                                        OldDemoGuideActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(ROUTE_PLAN_NODE, mStartNode);
                                intent.putExtras(bundle);
                                Const.guidetype=1;
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
                    Toast.makeText(OutCarDetail.this, "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            initNavi();
        }
    }




}
