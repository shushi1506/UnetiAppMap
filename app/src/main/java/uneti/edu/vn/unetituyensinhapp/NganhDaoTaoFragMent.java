package uneti.edu.vn.unetituyensinhapp;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Shushi on 5/31/2017.
 */

public class NganhDaoTaoFragMent extends Fragment {



    public NganhDaoTaoFragMent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.nganhdaotao_fragment, container, false);
        final ImageView imageButton=(ImageView)view.findViewById(R.id.image_nganhdaotao);
        imageButton.setImageResource(R.drawable.cachnganhdaotao);
        PhotoViewAttacher photoViewAttacher=new PhotoViewAttacher(imageButton);
        photoViewAttacher.update();
        return view;

    }
}
