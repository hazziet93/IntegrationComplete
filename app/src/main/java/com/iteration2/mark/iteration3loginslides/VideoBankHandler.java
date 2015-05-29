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


public class VideoBankHandler extends Activity {

    private List<Video> myVideo = new ArrayList<Video>();
    public ListView listView;
    public VideoHandler videoHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_bank_handler);
        addVideoInformation();
        addListView();
        clickItem();
    }


    private void addVideoInformation() {
        myVideo.add(new Video(R.drawable.ic_apple, "Title 1", "New Video"));
        myVideo.add(new Video(R.drawable.ic_bike, "Title 2", "New Video"));
        myVideo.add(new Video(R.drawable.ic_cinema, "Title 3", "New Video"));
        myVideo.add(new Video(R.drawable.ic_football, "Title 4", "New Video"));
        myVideo.add(new Video(R.drawable.ic_glasses, "Title 5", "New Video"));
        myVideo.add(new Video(R.drawable.ic_graduate, "Title 6", "New Video"));
        myVideo.add(new Video(R.drawable.ic_monitor, "Title 7", "New Video"));
        myVideo.add(new Video(R.drawable.ic_time, "Title 8", "New Video"));
        myVideo.add(new Video(R.drawable.ic_turkey, "Title 9", "New Video"));


    }

    private void addListView() {
        ArrayAdapter<Video> adapter = new MyListAdapter();
        listView = (ListView)findViewById(R.id.videoListView);
        listView.setAdapter(adapter);

    }


    private class MyListAdapter extends ArrayAdapter<Video> {
        public MyListAdapter() {
            super(VideoBankHandler.this, R.layout.containt_item, myVideo);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.containt_item, parent, false);
            }

            //find the video
            Video currentVideo = myVideo.get(position);

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

                switch (position) {
                    case 0:
                        Intent playVideo0 = new Intent(VideoBankHandler.this, callVideoHandler.class);
                        startActivity(playVideo0);
                        break;
                    case 1:
                        Intent playVideo1 = new Intent(VideoBankHandler.this, callVideoHandler.class);
                        startActivity(playVideo1);
                        break;
                    case 2:
                        Intent playVideo2 = new Intent(VideoBankHandler.this, callVideoHandler.class);
                        startActivity(playVideo2);
                        break;
                    case 3:
                        Intent playVideo3 = new Intent(VideoBankHandler.this, callVideoHandler.class);
                        startActivity(playVideo3);
                        break;
                    case 4:
                        Intent playVideo4 = new Intent(VideoBankHandler.this, callVideoHandler.class);
                        startActivity(playVideo4);
                        break;
                    case 5:
                        Intent playVideo5 = new Intent(VideoBankHandler.this, callVideoHandler.class);
                        startActivity(playVideo5);
                        break;
                    case 6:
                        Intent playVideo6 = new Intent(VideoBankHandler.this, callVideoHandler.class);
                        startActivity(playVideo6);
                        break;
                    case 7:
                        Intent playVideo7 = new Intent(VideoBankHandler.this, callVideoHandler.class);
                        startActivity(playVideo7);
                        break;
                    case 8:
                        Intent playVideo8 = new Intent(VideoBankHandler.this, callVideoHandler.class);
                        startActivity(playVideo8);
                        break;
                    case 9:
                        Intent playVideo9 = new Intent(VideoBankHandler.this, callVideoHandler.class);
                        startActivity(playVideo9);
                        break;
                }

//                Video clickVideo = myVideo.get(position);
//                String message = "You click it " + position + " Which video " + clickVideo.getVideoTitle();
//                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_video_bank_handler, menu);
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
