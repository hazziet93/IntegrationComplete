package com.iteration2.mark.iteration3loginslides;

        import android.app.Activity;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.nostra13.universalimageloader.core.DisplayImageOptions;
        import com.nostra13.universalimageloader.core.ImageLoader;
        import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by Andy on 23/05/2015.
 */

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] web;
    private final Integer[] imageId;
    public int num;

    public Context blah = getContext();
    public CustomList(Activity context, String[] web, Integer[] imageId){
        super(context, R.layout.list_row, web);

        this.context = context;

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getContext()).build();


        ImageLoader.getInstance().init(config);
        this.web = web;
        this.imageId = imageId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){


        LayoutInflater inflater = context.getLayoutInflater();
        View row = inflater.inflate(R.layout.list_row,null);

        ImageView image = (ImageView) row.findViewById(R.id.icon);
        TextView text = (TextView) row.findViewById(R.id.Itemname);

        int length = imageId.length;

        DisplayImageOptions options = new DisplayImageOptions.Builder().build();

        if(length < 4){
            if(position <= length){
                // ImageLoader.displayImage("https://metrouk2.files.wordpress.com/2014/01/ad125537369refile-updatin.jpg", image);
                text.setText(web[position]);
            }
        }
        else{
            image.setImageResource(this.imageId[position]);
            text.setText(web[position]);
        }

        return row;
    }

}

