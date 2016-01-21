package Utility;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.bubblepedia.R;

/**
 * Created by Lenovo on 1/17/2016.
 */
public class BubbleFeedAdapter extends ArrayAdapter {

    private Context context;
    private String[] values;

    public BubbleFeedAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
     /*
     * The convertView argument is essentially a "ScrapView" as described is Lucas post
     * http://lucasr.org/2012/04/05/performance-tips-for-androids-listview/
     * It will have a non-null value when ListView is asking you recycle the row layout.
     * So, when convertView is not null, you should simply update its contents instead of inflating a new row layout.
     */
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.home_listview_row,parent,false);

            TextView textView = (TextView) convertView.findViewById(R.id.txtHomeListViewRow_BubbleShort);
            textView.setText(values[position]);

            ViewHolder holder = new ViewHolder();
            holder.text = textView;
            holder.userImage = (ImageView) convertView.findViewById(R.id.img_btn_arrow_bubbleDetail);

            convertView.setTag(holder);
        }
        else {
            convertView.getTag();
        }
        return convertView;
    }

    private static class ViewHolder {
        TextView text;
        ImageView userImage;
    }
}