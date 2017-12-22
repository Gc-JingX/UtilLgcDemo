package com.lgc.garylianglib.util.cusview.flowtagview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.lgc.garylianglib.R;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : feijin_lgc
 *     e-mail : 595184932@qq.com
 *     time   : 2017/11/16 19:33
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TagAdapter<T> extends BaseAdapter implements OnInitSelectedPosition {

    private final Context mContext;
    private final List<T> mDataList;
    private int mViewResId = -1;
    private String mSelectedStr;
    private OnSelectedItemListener mOnSelectedItemListener;
    private List<T> mInterSectionDataList;

    public TagAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<>();
    }

    public TagAdapter(Context context, int viewResId) {
        this.mContext = context;
        mDataList = new ArrayList<>();
        this.mViewResId = viewResId;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(mContext).inflate(mViewResId > 0 ? mViewResId : R.layout.item_flow_tag_layout_default, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_tag);


        T t = mDataList.get(position);

        if (t instanceof String) {
            if (mInterSectionDataList != null) {
                view.setEnabled(mInterSectionDataList.contains(t));
                textView.setEnabled(mInterSectionDataList.contains(t));
            }else {
                view.setEnabled(true);
                textView.setEnabled(true);
            }

            textView.setText((String) t);

            if (t.equals(mSelectedStr)) {
                if (mOnSelectedItemListener != null) {
                    mOnSelectedItemListener.onSelectedItem(position);
                }
            }
        }
        return view;
    }

    public void onlyAddAll(List<T> datas) {
        mDataList.addAll(datas);
        notifyDataSetChanged();
    }

    public void clearAndAddAll(List<T> datas) {
        mDataList.clear();
        onlyAddAll(datas);
    }

    @Override
    public boolean isSelectedPosition(int position) {
        if (position % 2 == 0) {
            return true;
        }
        return false;
    }

    public String getSelectedStr() {
        return mSelectedStr;
    }

    public void setSelectedStr(String selectedStr) {
        this.mSelectedStr = selectedStr;
    }

    public OnSelectedItemListener getOnSelectedItemListener() {
        return mOnSelectedItemListener;
    }

    public void setOnSelectedItemListener(OnSelectedItemListener onSelectedItemListener) {
        this.mOnSelectedItemListener = onSelectedItemListener;
    }

    public List<T> getInterSectionDataList() {
        return mInterSectionDataList;
    }

    public void setInterSectionDataList(List<T> interSectionDataList) {
        this.mInterSectionDataList = interSectionDataList;
    }

    /**
     * OnSelectedItemListener
     */
    public interface OnSelectedItemListener {
        void onSelectedItem(int position);
    }
}
