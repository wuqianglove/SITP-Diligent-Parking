package com.Adapter;

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
import com.example.admin.sitp_parkinglot.ReserveDisplay;

import java.util.List;

/**
 * Created by admin on 2019-02-02.
 */
public class ReserveDisplayAdapter extends ArrayAdapter {
    private final int resourceId;
    public ReserveDisplayAdapter(Context context, int textViewResourceId, List<ReserveHistory> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ReserveHistory park = (ReserveHistory) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象

        TextView ParkName = (TextView)view.findViewById(R.id.itemName);//获取该布局内的文本视图
        TextView Address=(TextView)view.findViewById(R.id.itemAddress);
        TextView Status=(TextView)view.findViewById(R.id.itemStatus);
        TextView UpTime=(TextView)view.findViewById(R.id.itemTime);
        ParkName.setText(park.getParkingName());
        Address.setText("车位类型:"+park.getParkingType());
        Status.setText("状态:"+park.getStatus());
        UpTime.setText("预约时间:"+park.getUpdatedAt());

        return view;
    }
}