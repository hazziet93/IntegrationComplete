package com.iteration2.mark.iteration3loginslides;

        import android.os.Bundle;
        import android.support.v7.app.ActionBarActivity;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ListView;


public class ImageBank extends ActionBarActivity {
    ListView list;

    String[] web = {"a","b","c","d"};

    Integer[] imageId = {R.drawable.ic_apple,R.drawable.ic_apple,R.drawable.ic_bike,R.drawable.ic_chemistry};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_bank);

        CustomList adapter = new CustomList(this,web,imageId);

        list = (ListView) findViewById(R.id.list);

        list.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_audio_bank, menu);
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
