package hw.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("그리드뷰 영화 포스터");

        final GridView gv = (GridView) findViewById(R.id.gridView1);
        MyGridAdapter gAdapter = new MyGridAdapter(this);
        gv.setAdapter(gAdapter);
    }
    public class MyGridAdapter extends BaseAdapter {
        Context context;

        public MyGridAdapter(Context c) {
            context = c;
        }
        public int getCount() {
            return posterID.length;
        }
        public Object getItem(int arg0) {
            return null;
        }
        public long getItemId(int arg0) {
            return 0;
        }
        Integer[] posterID = {R.drawable.m1, R.drawable.m2, R.drawable.m3,
                R.drawable.m4, R.drawable.m5, R.drawable.m6, R.drawable.m7,
                R.drawable.m8, R.drawable.m9, R.drawable.m10};
        String[] postername = {"반지의 제왕", "트랜스포머", "2012", "암살", "인턴",
                "마션", "5백만불의 사나이", "하녀", "타짜", "혈의 누"};


        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.dialog, null);
            convertView.setLayoutParams(new GridView.LayoutParams(300, 400));
            ImageView ivPoster = (ImageView) convertView.findViewById(R.id.ivPoster);
            ivPoster.setImageResource(posterID[position]);
            TextView ivPoster1 = (TextView) convertView.findViewById(R.id.ivPoster1);
            ivPoster1.setText(postername[position]);
            convertView.setPadding(3, 3, 3, 3);
            final int pos = position;
            convertView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    View dialogView = (View) View.inflate(MainActivity.this, R.layout.dialog, null);
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    ImageView ivPoster = (ImageView) dialogView.findViewById(R.id.ivPoster);
                    TextView ivPoster1 = (TextView) dialogView.findViewById(R.id.ivPoster1);
                    ivPoster1.setText(postername[pos]);
                    ivPoster.setImageResource(posterID[pos]);
                    dlg.setTitle(postername[pos]);
                    dlg.setIcon(R.drawable.ic_launcher);
                    dlg.setView(dialogView);
                    dlg.setNegativeButton("닫기", null);
                    dlg.show();
                }
            });
            return convertView;
        }
    }
}




