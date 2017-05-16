package kr.hs.namgong.kukucomics;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import kr.hs.namgong.kukucomics.util.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAdapter_kuku extends BaseAdapter {
    private Context mContext;
    private int layout;
    private ArrayList<HashMap<String, String>> mList = new ArrayList<HashMap<String, String>>();
    private LayoutInflater inflater;
    private ImageLoader mImageLoader;

    public ViewAdapter_kuku(Context mContext, ImageLoader mImageLoader) {
        // TODO Auto-generated constructor stub
        this.mContext = mContext;
        this.layout = R.layout.custom_kuku_list;
        this.inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mImageLoader = mImageLoader;

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    @Override
    public HashMap<String, String> getItem(int position) {
        // TODO Auto-generated method stub
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View view = convertView;
        ViewHolder_kuku mHolder;
        //
        // TextView txt1 = (TextView) view.findViewById(R.id.rank_tv);
        // TextView txt2 = (TextView) view.findViewById(R.id.name_tv);
        // TextView txt3 = (TextView) view.findViewById(R.id.title_tv);
        // txt1.setTypeface(myNewFace);
        if (view == null) {
            view = inflater.inflate(layout, null);
            mHolder = new ViewHolder_kuku(view);
            view.setTag(layout, mHolder);
        } else {
            mHolder = (ViewHolder_kuku) view.getTag(layout);
        }

        if (TextUtils.isEmpty(mList.get(position).get(ScrollingActivity.RANK))) {
        } else {
            mHolder.getRank().setText(
                    mList.get(position).get(ScrollingActivity.RANK));
        }
        if (TextUtils.isEmpty(mList.get(position).get(ScrollingActivity.NAME))) {
        } else {
            mHolder.getName().setText(
                    mList.get(position).get(ScrollingActivity.NAME));
        }
//
//		if (TextUtils.isEmpty(mList.get(position).get(HomeFragment.TITLE))) {
//		} else {
//			mHolder.getTitle().setText(
//					mList.get(position).get(HomeFragment.TITLE));
//		}

//		if (TextUtils.isEmpty(mList.get(position).get(HomeFragment.FLAG))) {
//
//		} else {
//			mImageLoader.loadImage(mList.get(position).get(HomeFragment.FLAG),
//					mHolder.getIm());
//		}

        return view;
    }

    public void setData(ArrayList<HashMap<String, String>> mList) {
        this.mList.clear();
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }
}
