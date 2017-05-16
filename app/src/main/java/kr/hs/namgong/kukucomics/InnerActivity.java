package kr.hs.namgong.kukucomics;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import kr.hs.namgong.kukucomics.pulltorefresh.BottomPullToRefreshView;
import kr.hs.namgong.kukucomics.util.ImageLoader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class InnerActivity extends AppCompatActivity {
    private ListView listView;

    private ArrayList<HashMap<String, String>> mList, mList2;
    private ViewAdapter mAdapter;
    //    private String url = "http://www.cucudas.com//bbs/board.php?bo_table=gangho&page=";//열혈강호 1
//    private String url = "http://www.cucudas.com//bbs/main.php?gid=aniboard";
    private String url;
    private ImageLoader mImageLoader;
    private ProgressDialog mProgressDialog;
    static String TITLE = "title";
    static String NAME = "name";
    static String FLAG = "flag";
    static String HREFSTR = "href_str";
    static String RANK = "rank";
    static String SubTitle = "subTitle";
    private BottomPullToRefreshView pullView2 = null;

    int url_page = 1;
    //    private MainActivity mActivity;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inner);
        Intent intent = getIntent();
        url = "http://www.cucudas.com" + intent.getExtras().getString("hrefStr") + "&page=";
        Toast.makeText(getBaseContext(), url, Toast.LENGTH_LONG).show();
        listView = (ListView) findViewById(R.id.customList);
        mImageLoader = new ImageLoader(getBaseContext());//1
        // Pass the results into ListViewAdapter.java
        mAdapter = new ViewAdapter(getBaseContext(), mImageLoader);//1
        // Set the adapter to the ListView

//        url = getArguments().getString("url");1
        pullView2 = (BottomPullToRefreshView) findViewById(R.id.pull_to_refresh2);//1
        pullView2.setListener(new BottomPullToRefreshView.Listener() {
            @Override
            public void onChangeMode(kr.hs.namgong.kukucomics.pulltorefresh.BottomPullToRefreshView.MODE mode) {
                Log.w("cranix", "pullView2:" + mode);
                switch (mode) {
                    case NORMAL:
                        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_DISABLED);
                        break;
                    case PULL:
                    case READY_TO_REFRESH:
                        if (pullView2.isBottom()) {
                            listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
                        }
                        break;
                    case REFRESH:
                        url_page++;
                        flag = true;
//                        Log.i("aaa", "url" + url + url_page);
                        new JsoupListView().execute();

                        break;
                }
            }

        });

        listView.setAdapter(mAdapter);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                pullView2.touchDelegate(v, event);

                return false;
            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(0);
                if (firstView == null) {
                    return;
                }

                View lastView = view.getChildAt(view.getChildCount() - 1);
                if (lastView == null) {
                    return;
                }
                if (totalItemCount == firstVisibleItem + visibleItemCount && lastView.getBottom() <= view.getHeight()) {
                    pullView2.setBottom(true);
                } else {
                    pullView2.setBottom(false);
                }
            }
        });


        new JsoupListView().execute();

        listView.setOnItemClickListener(new

                                                AdapterView.OnItemClickListener() {
                                                    @Override
                                                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                                                        //오른쪽 단추를 누르면 불러옵니다 Fragment 후
//                                                        FragmentManager manager = getFragmentManager();
//                                                        FragmentTransaction transaction = manager.beginTransaction();
//                                                        DetailFragment detailFragment = new DetailFragment();
//                                                        //기억하다: 이 곳을 반드시 쓰는 영역을 사용할 아니라 쓰는 add
//                                                        transaction.replace(R.id.target_fragment, detailFragment, "detailFragment");
//
//                                                        //는 가운데 item 내용을 다 Bundle 대상 중에서 그리고 갖다 Frament 인자 중에서 가장 오른쪽
//                                                        String item = mList.get(i).get(HREFSTR).toString();
//
//                                                        Bundle args1 = new Bundle();
//                                                        args1.putString("item", item);
//                                                        detailFragment.setArguments(args1);
//                                                        //Toast.makeText(getActivity(), item, 1).show();
//                                                        transaction.addToBackStack(null).commit();//화면에서 뒤로가기 누르면 다시 목록으로
////                                                        transaction.commit();
                                                        String item2 = mList.get(i).get(HREFSTR);
                                                        Toast.makeText(getBaseContext(), item2, Toast.LENGTH_LONG).show();
                                                        Intent intent2 = new Intent(InnerActivity.this, DetailActivity.class);
                                                        intent2.putExtra("hrefStr2", item2);
                                                        startActivity(intent2);
                                                    }
                                                }
        );

    }


    private class JsoupListView extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Create a progressdialog
//            mProgressDialog = new ProgressDialog(getActivity());
//            // Set progressdialog title
//            mProgressDialog.setTitle("Kukudas Comics");
//            // Set progressdialog message
//            mProgressDialog.setMessage("잠시만 기다려주세요...");
//            mProgressDialog.setIndeterminate(false);
//            // Show progressdialog
//            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            // Create an array
            mList = new ArrayList<HashMap<String, String>>();
            mList2 = new ArrayList<HashMap<String, String>>();

            try {
                // Connect to the Website URL
//                Document doc = Jsoup.connect(url + url_page).get();//열혈강호 1
                Document doc = Jsoup.connect(url + url_page).get();

//                Log.i("aaa", doc.toString());

                // Identify Table Class "worldpopulation"
                int i = 0;
                for (Element table : doc.select("section.board-list")) {
                    Log.i("aaa", "table=" + table);
                    // Identify all the table row's(tr)
                    for (Element row : table.select("div.list-item")) {
                        // for (Element row : table.select("h3[class=cds_h3]"))
                        // {
                        //
                        HashMap<String, String> map = new HashMap<String, String>();

                        // Identify all the table cell's(td)
                        // Elements tds = row.select("td");

                        // Identify all img src's
                        // Elements imgSrc = row.select("img[src]");
                        // Get only src from img src
                        // String imgSrcStr = imgSrc.attr("src");


                        Element href = row.select("a[href]").get(0);//index ..etc) 498
                        String date = row.select("span.hidden-xs").text().substring(2).trim();//date
                        Elements imgSrc = row.select("a[href]");
                        String hrefStr = imgSrc.attr("href");//link
                        String[] rank_str = href.text().split(" ");
//                        Log.i("aaa", "hrefStr= " + hrefStr.toString());
//                        Log.i("aaa", "rank_str=" + rank_str.length);
                        try {
//                            Log.i("aaa", "ranks= " + ranks.text().substring(ranks.text().length() - 4, ranks.text().length()));
//                            rank_str = href.text().substring(href.text().length() - 4, href.text().length());
//                            Log.i("aaa", "rank_str=" + href.text().split(" ").toString());
                        } catch (StringIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }

//                        String title_str = ranks.text().substring(3);
//                        Elements href = row.select("a[class=cds_a]");
//                        href_str = href.attr("href");
//
//                        // Retrive Jsoup Elements
//                        // Get the first td
//                        map.put("title", rank_str);//index.. etc)498,497...
//                        // Get the second td
                        map.put("rank", date);
//                        // // Get the third td
                        if (rank_str.length == 4) {
                            map.put("name", rank_str[3]);//index etc)498,497...
                        } else if (rank_str.length == 5) {
                            map.put("name", rank_str[4]);
                        } else {
                            map.put("name", href.text());
                        }

//                        // // Get the image src links
                        map.put("href_str", hrefStr);
//                        map.put("flag", imgSrcStr);
//                        Log.i("aaa", "name" + name_str);
//                        Log.i("aaa", "title" + title_str);
//                        Log.i("aaa", "rank" + rank_str);
//                        Log.i("aaa", "href" + hrefStr);

                        // // Set all extracted Jsoup Elements into the array
//                        if (flag == false) {
                        mList.add(map);
//                        } else {
//                            mList2.add(mList.add(map));
//                        }
                    }
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
            pullView2.completeRefresh();
            // Close the progressdialog
//            mProgressDialog.dismiss();

        }
    }
}
