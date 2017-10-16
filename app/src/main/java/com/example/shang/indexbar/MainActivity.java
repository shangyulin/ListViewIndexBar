package com.example.shang.indexbar;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends Activity {

    private ListView listView;
    private IndexBarView bar;
    private List<Person> list = new ArrayList<>();
    private TextView bg;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            bg.setVisibility(View.GONE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        bar = (IndexBarView) findViewById(R.id.bar);
        listView = (ListView) findViewById(R.id.listview);
        bg = (TextView) findViewById(R.id.toast);
        fillAndSort();
        listView.setAdapter(new MyAdapter());
        bar.setOnUpdateLetterListener(new IndexBarView.OnUpdateLetter() {
            @Override
            public void onUpdate(String letter) {
                for (int i = 0; i < list.size(); i++){
                    if (letter.equals(list.get(i).getPinyin().charAt(0)+"")){
                        bg.setVisibility(View.VISIBLE);
                        bg.setText(letter);
                        listView.smoothScrollToPositionFromTop(i, 0);
                        handler.sendEmptyMessageDelayed(0, 2000);
                        break;
                    }
                }
            }
        });
    }

    private void fillAndSort() {
        for (int i = 0; i < Cheeses.NAMES.length; i++) {
            String name = Cheeses.NAMES[i];
            list.add(new Person(name));
        }
        Collections.sort(list);// 排序
    }

    static class ViewHolder {
        TextView logo;
        TextView content;
    }

    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                view = View.inflate(MainActivity.this, R.layout.listview_item, null);
                holder.logo = (TextView) view.findViewById(R.id.item_logo);
                holder.content = (TextView) view.findViewById(R.id.item_content);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            String str = null;
            String currentLetter = list.get(position).getPinyin().charAt(0) + "";
            if (position == 0) {
                str = currentLetter;
            } else {
                String preLetter = list.get(position - 1).getPinyin().charAt(0) + "";
                if (!TextUtils.equals(preLetter, currentLetter)) {
                    str = currentLetter;
                }
            }
            holder.logo.setVisibility(str == null ? View.GONE : View.VISIBLE);
            holder.logo.setText(list.get(position).getPinyin().charAt(0) + "");
            holder.content.setText(list.get(position).getName());
            return view;
        }
    }
}
