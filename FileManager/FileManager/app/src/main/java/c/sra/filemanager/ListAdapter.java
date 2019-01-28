package c.sra.filemanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ListAdapter extends ArrayAdapter<ListItem> {

    private int mResource;
    private List<ListItem> mItems;
    private LayoutInflater mInflater;
    public boolean isPushed = false;

    /**
     * コンストラクタ
     * @param context コンテキスト
     * @param resource リソースID
     * @param items リストビューの要素
     */
    public ListAdapter(Context context, int resource, List<ListItem> items) {
        super(context, resource, items);

        mResource = resource;
        mItems = items;
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView != null) {
            view = convertView;
        } else {
            view = mInflater.inflate(mResource, null);
        }

        // リストビューに表示する要素を取得
        ListItem item = mItems.get(position);

        // サムネイル画像を設定
        ImageView thumbnail = (ImageView)view.findViewById(R.id.thumbnail);
        if (item.getThumbnail() != null) {
            thumbnail.setImageBitmap(item.getThumbnail());
        }

        // タイトルを設定
        TextView title = (TextView)view.findViewById(R.id.title);
        if (item.getTitle() != null) {
            title.setText(item.getTitle());
        }

        // パスを設定
        TextView path = (TextView)view.findViewById(R.id.path);
        if (item.getPath() != null) {
            path.setText(item.getPath());
        }

        // 背景色を変更
        if (item.isHistory()) {
            view.setBackgroundColor(Color.rgb(200, 200, 200));
        } else {
            view.setBackgroundColor(Color.rgb(255,255,255));
        }

        return view;
    }
}