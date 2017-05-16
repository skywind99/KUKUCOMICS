package kr.hs.namgong.kukucomics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import kr.hs.namgong.kukucomics.util.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DetailActivity extends AppCompatActivity {
    private String url;
    static String FLAG = "flag";
    static String COUNT = "count";

    static String subTitle = "subTitle";

    private ArrayList<HashMap<String, String>> mList;
    private ViewAdapter_image mAdapter;
    private ImageLoader mImageLoader;
    private ProgressDialog mProgressDialog;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        url = "http://www.cucudas.com" + intent.getExtras().getString("hrefStr2");
        listView = (ListView) findViewById(R.id.customlist_image);
        mImageLoader = new ImageLoader(getBaseContext());
        mAdapter = new ViewAdapter_image(getBaseContext(), mImageLoader);
        listView.setAdapter(mAdapter);

        new JsoupListView_image().execute();

    }

    private class JsoupListView_image extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(DetailActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("쿠쿠다스 코믹스");
            // Set progressdialog message
            mProgressDialog.setMessage("잠시만 기다려주세요...");
            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            mList = new ArrayList<HashMap<String, String>>();

            try {
                // Connect to the Website URL
                Document doc = Jsoup.connect(url).get();
                // Identify Table Class "worldpopulation"
//                Log.i("aaa", doc.select("section.board-list").toString());
//                int i = 0;
                for (Element table : doc.select("div.view-content")) {
                    // Identify all the table row's(tr)
//                    Log.i("aaa", "table=" + table.toString());
                    int count = 1;

                    for (Element row : table.select("a")) {
                        HashMap<String, String> map = new HashMap<String, String>();
                        // Identify all img src's
                        Elements imgSrc = row.select("img[src]");
                        // Get only src from img src
                        String imgSrcStr = imgSrc.attr("src");
//
//                        // Retrive Jsoup Elements
//                        // // Get the image src links

                        map.put("flag", imgSrcStr);
                        map.put("count", count + "page");
                        count++;


                        // // Set all extracted Jsoup Elements into the array
                        mList.add(map);

                    }
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
//                Toast.makeText(getActivity(), "인터넷에 연결되어있지 않거나, 쿠쿠다스 게시판 경로가 바뀌었습니다.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            // Locate the listview in listview_main.xml

            mAdapter.setData(mList);
            // PagerAdapter로

            // Close the progressdialog
            mProgressDialog.dismiss();

        }
    }
}
