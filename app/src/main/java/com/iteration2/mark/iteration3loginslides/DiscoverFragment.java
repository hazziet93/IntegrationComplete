package com.iteration2.mark.iteration3loginslides;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


public class DiscoverFragment extends Fragment {

    //Timer autoScrollTimer;
    //TimerTask autoScrollTask;
    HorizontalScrollView scroller;
    LinearLayout imageChain1;
    LinearLayout textChain1;
    ImageView selectedImage;
    ImageView selectedImage2;
    ImageView selectedImage3;
    VideoView selectedVideo;

    TextView selectedName;
    ImageView[] panels  = new ImageView[8];
    TextView[] ticker = new TextView[15];
    //int i = 1, di = 1;

    final Handler autoScrollHandler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //startTimer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discover, container, false);
        scroller = (HorizontalScrollView)v.findViewById(R.id.imageScroll1);
        imageChain1 = (LinearLayout)v.findViewById(R.id.imageChain1);
        textChain1 = (LinearLayout)v.findViewById(R.id.discoTextScroller);
        selectedImage = (ImageView)v.findViewById(R.id.selectedImage);
        selectedImage2 = (ImageView)v.findViewById(R.id.selectedImage2);
        selectedImage3 = (ImageView)v.findViewById(R.id.selectedImage3);
        selectedName = (TextView)v.findViewById(R.id.socName);

        for (int i=0; i < panels.length; i++) {
            panels[i] = new ImageView(getActivity());
            panels[i].setLayoutParams(new LinearLayout.LayoutParams(
                    210, ViewGroup.LayoutParams.MATCH_PARENT));
            panels[i].setScaleType(ImageView.ScaleType.CENTER_CROP);

            panels[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int t = imageChain1.indexOfChild(v);
                    selectedImage.setImageDrawable(panels[t].getDrawable());
                    selectedImage2.setImageDrawable(panels[t+1].getDrawable());
                    selectedImage3.setImageDrawable(panels[t+2].getDrawable());
                    selectedName.setText((String)panels[t].getTag());
                }
            });
        }

       // float sizeInDp = 30;
       // float scale = getResources().getDisplayMetrics().density;
       // int dpAsPixels = (int) (sizeInDp*scale + 0.5f);

        for (int i=0; i < ticker.length; i++) {
            ticker[i] = new TextView(getActivity());
            ticker[i].setText("Society number " + Integer.toString(i));
            ticker[i].setPadding(30, 0, 30, 0);
            ticker[i].setTextSize(20);
            ticker[i].setGravity(Gravity.CENTER_VERTICAL);
            ticker[i].setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
            textChain1.addView(ticker[i]);

            ticker[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView tv = (TextView)v;
                    Toast toast = Toast.makeText(getActivity(), "Go to " + tv.getText(),
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
        }

        panels[0].setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.cloud, 200, 200));
        panels[0].setTag("CloudSoc");
        panels[1].setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.dog, 200, 200));
        panels[1].setTag("DogSoc");
        panels[2].setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.tree, 200, 200));
        panels[2].setTag("TreeSoc");
        panels[3].setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.space, 200, 200));
        panels[3].setTag("SpaceSoc");
        panels[4].setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.hockey, 200, 200));
        panels[4].setTag("HockeySoc");
        panels[5].setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.chess, 200, 200));
        panels[5].setTag("ChessSoc");
        panels[6].setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.mountain, 200, 200));
        panels[6].setTag("MountainSoc");
        panels[7].setImageBitmap(
                decodeSampledBitmapFromResource(getResources(), R.drawable.parachute, 200, 200));
        panels[7].setTag("ParachuteSoc");

        for (int i=0; i < panels.length; i++) {
            imageChain1.addView(panels[i]);
        }

        selectedImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity(), "Go to " + selectedName.getText(),
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        selectedImage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity(), "Go to " + selectedName.getText(),
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        selectedImage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity(), "Go to " + selectedName.getText(),
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        panels[0].performClick();

    // Inflate the layout for this fragment
        return v;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
