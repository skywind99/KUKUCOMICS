package kr.hs.namgong.kukucomics;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder_image {
    private View v;
    private TextView sub_title;
    private TextView name_str;
    private TextView title_str;

    private ImageView im;

    public ViewHolder_image(View v) {
        // TODO Auto-generated constructor stub
        this.v = v;
    }

    //	public TextView getRank() {
//		if (rank_str == null) {
//			rank_str = (TextView) v.findViewById(R.id.rank_tv);
//
//		}
//		return rank_str;
//
//	}
//
    public TextView getSubTitle() {
        if (sub_title == null) {
            sub_title = (TextView) v.findViewById(R.id.sub_title);
        }
        return sub_title;

    }

    public TextView getCount() {
        if (title_str == null) {
            title_str = (TextView) v.findViewById(R.id.text_count);
        }
        return title_str;

    }


    public ImageView getIm() {
        if (im == null) {
            im = (ImageView) v.findViewById(R.id.icon_iv);

        }
        return im;
    }
}
