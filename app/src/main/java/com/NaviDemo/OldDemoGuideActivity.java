package com.NaviDemo;

/**
 * Created by admin on 2019-02-02.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.OutCar.PayMoney;
import com.baidu.Const;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNRoutePlanNode.CoordinateType;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNRouteGuideManager;
import com.baidu.navisdk.adapter.map.BNItemizedOverlay;
import com.baidu.navisdk.adapter.map.BNOverlayItem;
import com.example.admin.sitp_parkinglot.R;
import com.example.admin.sitp_parkinglot.sysClose;

/**
 * 诱导界面
 *
 * @author sunhao04
 *
 */
public class OldDemoGuideActivity extends Activity {

    private static final String TAG = OldDemoGuideActivity.class.getName();

    private BNRoutePlanNode mBNRoutePlanNode = null;

    private IBNRouteGuideManager mRouteGuideManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createHandler();
        sysClose.getInstance().addActivity(this);
        mRouteGuideManager = BaiduNaviManagerFactory.getRouteGuideManager();
        View view = mRouteGuideManager.onCreate(this, mOnNavigationListener);

        if (view != null) {
            setContentView(view);
        }

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                mBNRoutePlanNode = (BNRoutePlanNode)
                        bundle.getSerializable(NaviActivity.ROUTE_PLAN_NODE);
            }
        }

        routeGuideEvent();
    }

    // 导航过程事件监听
    private void routeGuideEvent() {
        EventHandler.getInstance().getDialog(this);
        EventHandler.getInstance().showDialog();

        BaiduNaviManagerFactory.getRouteGuideManager().setRouteGuideEventListener(
                new IBNRouteGuideManager.IRouteGuideEventListener() {
                    @Override
                    public void onCommonEventCall(int what, int arg1, int arg2, Bundle bundle) {
                        EventHandler.getInstance().handleNaviEvent(what, arg1, arg2, bundle);
                    }
                }
        );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mRouteGuideManager.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRouteGuideManager.onResume();
        // 自定义图层
        showOverlay();
    }

    private void showOverlay() {
        BNOverlayItem item =
                new BNOverlayItem(2563047.686035, 1.2695675172607E7, BNOverlayItem.CoordinateType.BD09_MC);
        BNItemizedOverlay overlay = new BNItemizedOverlay(
                OldDemoGuideActivity.this.getResources().getDrawable(R.drawable
                        .navi_guide_turn));
        overlay.addItem(item);
        overlay.show();
    }

    protected void onPause() {
        super.onPause();
        mRouteGuideManager.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        mRouteGuideManager.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRouteGuideManager.onDestroy(false);
        EventHandler.getInstance().disposeDialog();
    }

    @Override
    public void onBackPressed() {
        mRouteGuideManager.onBackPressed(false, true);
    }

    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mRouteGuideManager.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if ( !mRouteGuideManager.onKeyDown(keyCode, event) ) {
            return super.onKeyDown(keyCode, event);
        }
        return true;

    }

    private static final int MSG_RESET_NODE = 3;

    private Handler hd = null;

    private void createHandler() {
        if (hd == null) {
            hd = new Handler(getMainLooper()) {
                public void handleMessage(android.os.Message msg) {
                    if (msg.what == MSG_RESET_NODE) {
                        mRouteGuideManager.resetEndNodeInNavi(
                                new BNRoutePlanNode(116.21142, 40.85087, "百度大厦11",
                                        null, CoordinateType.GCJ02));
                    }
                }
            };
        }
    }

    private IBNRouteGuideManager.OnNavigationListener mOnNavigationListener =
            new IBNRouteGuideManager.OnNavigationListener() {

                @Override
                public void onNaviGuideEnd() {
                    // 退出导航
                    finish();
                    if(Const.guidetype==0){
                        Intent Parking=new Intent(OldDemoGuideActivity.this, StoreCar.class);
                        startActivity(Parking);
                    }
                    else if(Const.guidetype==1){
                        Intent Parking = new Intent(OldDemoGuideActivity.this, PayMoney.class);
                        startActivity(Parking);
                    }
                }

                @Override
                public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {
                    if (actionType == 0) {
                        // 导航到达目的地 自动退出
                        Log.i(TAG, "notifyOtherAction actionType = " + actionType + ",导航到达目的地！");
                        mRouteGuideManager.forceQuitNaviWithoutDialog();
                        if(Const.guidetype==0) {
                            Intent Parking = new Intent(OldDemoGuideActivity.this, StoreCar.class);
                            startActivity(Parking);
                        }
                        else if(Const.guidetype==1){
                            Intent Parking = new Intent(OldDemoGuideActivity.this, PayMoney.class);
                            startActivity(Parking);
                        }
                    }
                }
            };
}
