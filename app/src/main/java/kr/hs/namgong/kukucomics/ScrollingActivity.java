package kr.hs.namgong.kukucomics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import kr.hs.namgong.kukucomics.util.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ScrollingActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {
    // Some random countries
    private ArrayList<HashMap<String, String>> mList;
    private ViewAdapter_kuku mAdapter;
    //    private String url = "http://www.kukudas.com//bbs/main.php?gid=aniboard";
    private ImageLoader mImageLoader;
    private String url = "http://www.cucudas.com/index.php?device=mobile";
    private ProgressDialog mProgressDialog;
    static String TITLE = "title";
    static String NAME = "name";
    static String FLAG = "flag";
    static String HREFSTR = "href_str";
    static String RANK = "rank";
    static String SubTitle = "subTitle";
    ListView listView;

    private String hrefStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuku_main);
        mAdapter = new ViewAdapter_kuku(ScrollingActivity.this, mImageLoader);
        listView = (ListView) findViewById(R.id.listview);
        listView.setAdapter(mAdapter);
        new JsoupListView().execute();
        listView.setOnItemClickListener(this);
    }


    private class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(ScrollingActivity.this);
            // Set progressdialog title
            mProgressDialog.setTitle("Kukudas Comics");
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

//                Log.i("aaa",doc.toString());
                Element doc2 = doc.select("li.dropdown").get(1);
                for (Element table : doc2.select("li.sub-off")) {

                    HashMap<String, String> map = new HashMap<String, String>();
                    Elements href = table.select("a");
//                    Log.i("aaa", href.toString());
                    hrefStr = href.attr("href");
                    Log.i("aaa", hrefStr);
                    map.put("href_str", hrefStr);

                    String name = href.text();
                    String[] rank = table.select("i[class]").attr("class").split(" ");
                    String rank_str = rank[2];
                    if (rank_str.contains("new")) {
                        map.put("rank", rank_str);
                    }
                    map.put("name", name);
                    mList.add(map);
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            // Locate the listview in listview_main.xml

            mAdapter.setData(mList);
            // mPager.setAdapter(new BkPagerAdapter(getApplicationContext()));//
            // PagerAdapter로
            // Close the progressdialog
            mProgressDialog.dismiss();

        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String item = mList.get(position).get(HREFSTR);
        Toast.makeText(getBaseContext(), item, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ScrollingActivity.this, InnerActivity.class);
        intent.putExtra("hrefStr", item);
        startActivity(intent);
//
    }

}
