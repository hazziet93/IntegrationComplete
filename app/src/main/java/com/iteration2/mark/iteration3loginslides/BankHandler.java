package com.iteration2.mark.iteration3loginslides;

        import android.app.Activity;
        import android.content.Intent;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;


public class BankHandler extends Activity {

    private List<List_Item> myVideo = new ArrayList<List_Item>();
    public ListView listView;
    public boolean blah = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_bank_handler);
        addInformation();
        addListView();
        clickItem();
    }

    private void addInformation() {
        myVideo.add(new List_Item(R.drawable.ic_apple, "Title 1", "New Video"));
        myVideo.add(new List_Item(R.drawable.ic_bike, "Title 2", "New Video"));
        myVideo.add(new List_Item(R.drawable.ic_cinema, "Title 3", "New Video"));
        myVideo.add(new List_Item(R.drawable.ic_football, "Title 4", "New Video"));
        myVideo.add(new List_Item(R.drawable.ic_glasses, "Title 5", "New Video"));
        myVideo.add(new List_Item(R.drawable.ic_graduate, "Title 6", "New Video"));
        myVideo.add(new List_Item(R.drawable.ic_monitor, "Title 7", "New Video"));
        myVideo.add(new List_Item(R.drawable.ic_time, "Title 8", "New Video"));
        myVideo.add(new List_Item(R.drawable.ic_turkey, "Title 9", "New Video"));

    }

    private void addListView() {
        ArrayAdapter<List_Item> adapter = new MyListAdapter();
        listView = (ListView)findViewById(R.id.videoListView);
        listView.setAdapter(adapter);

    }


    private class MyListAdapter extends ArrayAdapter<List_Item> {
        public MyListAdapter() {
            super(BankHandler.this, R.layout.row, myVideo);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.row, parent, false);
            }

            //find the video
            List_Item currentVideo = myVideo.get(position);

            //fill the view
            ImageView imageView = (ImageView)view.findViewById(R.id.exImageView);
            imageView.setImageResource(currentVideo.getVideoId());

            //Title text
            TextView titleText = (TextView)view.findViewById(R.id.textView);
            titleText.setText(currentVideo.getVideoTitle());

            //Description text
            TextView descriptionText = (TextView)view.findViewById(R.id.textView2);
            descriptionText.setText(currentVideo.getDescriptionTitle());

            return view;
        }
    }
    private void clickItem() {
        ListView listView = (ListView)findViewById(R.id.videoListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {

                Intent showFile;
                if(blah)
                    showFile = new Intent(BankHandler.this, callVideoHandler.class);
                else
                    showFile = new Intent(BankHandler.this, callVideoHandler.class);
                startActivity(showFile);


//                Video clickVideo = myVideo.get(position);
//                String message = "You click it " + position + " Which video " + clickVideo.getVideoTitle();
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
