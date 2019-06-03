package com.Adapter;

/**
 * Created by admin on 2019-02-01.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.Const;
import com.baidu.mapapi.search.core.PoiInfo;
import com.example.admin.sitp_parkinglot.R;

import java.util.List;
public class ParkingAdapter extends ArrayAdapter{
    private final int resourceId;
    private static final double EARTH_RADIUS = 6378137.0;

    public ParkingAdapter(Context context, int textViewResourceId, List<PoiInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PoiInfo park = (PoiInfo) getItem(position); // 获取当前项的实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView parkImage = (ImageView) view.findViewById(R.id.iv);//获取该布局内的图片视图
        TextView Distance = (TextView) view.findViewById(R.id.Distance);//获取该布局内的文本视图
        TextView Name=(TextView)view.findViewById(R.id.Name);
        TextView Address=(TextView)view.findViewById(R.id.Address);


        parkImage.setImageResource(R.drawable.icon_mark);//为图片视图设置图片资源
        Name.setText(park.getName());//为文本视图设置文本内容
        Address.setText(park.getAddress());
        Double dis=getDistance(park.getLocation().longitude,park.getLocation().latitude, Const.LONGITUDE,Const.LATITUDE);
        Distance.setText("距离为"+dis+"米");
        return view;
    }
    public static double getDistance(double longitude1, double latitude1,
                                     double longitude2, double latitude2) {
        double Lat1 = rad(latitude1);
        double Lat2 = rad(latitude2);
        double a = Lat1 - Lat2;
        double b = rad(longitude1) - rad(longitude2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(Lat1) * Math.cos(Lat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
}