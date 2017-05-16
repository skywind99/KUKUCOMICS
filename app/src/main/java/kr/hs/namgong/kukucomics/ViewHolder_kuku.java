package kr.hs.namgong.kukucomics;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewHolder_kuku {
	private View v;
	private TextView rank_str;
	private TextView name_str;
	private TextView title_str;

	private ImageView im;

	public ViewHolder_kuku(View v) {
		// TODO Auto-generated constructor stub
		this.v = v;
	}

	public TextView getRank() {
		if (rank_str == null) {
			rank_str = (TextView) v.findViewById(R.id.rank_tv);
		}
		return rank_str;

	}

	public TextView getName() {
		if (name_str == null) {
			name_str = (TextView) v.findViewById(R.id.name_tv);
		}
		return name_str;

	}
//
//	public TextView getTitle() {
//		if (title_str == null) {
//			title_str = (TextView) v.findViewById(R.id.title_tv);
//		}
//		return title_str;
//
//	}
//
//	public ImageView getIm() {
//		if (im == null) {
//			im = (ImageView) v.findViewById(R.id.icon_iv);
//		}
//		return im;
//	}
}
