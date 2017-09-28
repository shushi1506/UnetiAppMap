package uneti.edu.vn.unetituyensinhapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Shushi on 5/22/2017.
 */

public class InfoFragMent extends Fragment {
    String address="http://anhshushi.pe.hu/source/api/newfeed";
    InputStream is;
    String line=null;
    String reseult=null;
    String[] data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(
                R.layout.info_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new InfoFragMent.SimpleStringRecyclerViewAdapter(getActivity(),
                getData()));
    }
    private ArrayList<ClassInfoTuyenSinh> getData(){


        ArrayList<ClassInfoTuyenSinh>list=new ArrayList<>();
        try {
            URL url=new URL(address);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            is= new BufferedInputStream(httpURLConnection.getInputStream());

        }catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();
            while ((line=br.readLine())!= null){
                sb.append(line+"\n");
            }
            is.close();
            reseult=sb.toString();

        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getContext(),"loi 2"+ex.toString(),Toast.LENGTH_LONG).show();

        }
        try {
            JSONArray ja=new JSONArray(reseult);
            JSONObject jo=null;
            data=new String[ja.length()];
            for(int i=0;i<ja.length();i++){
                ClassInfoTuyenSinh classInfoTuyenSinh=new ClassInfoTuyenSinh();
                jo=ja.getJSONObject(i);
                classInfoTuyenSinh.setHeader(jo.getString("Header"));
                classInfoTuyenSinh.setInfoMain(jo.getString("InfoMain"));
                classInfoTuyenSinh.setText(jo.getString("Text"));
                classInfoTuyenSinh.setTime(jo.getString("Time"));
                classInfoTuyenSinh.setUrl(jo.getString("Url"));
                list.add(classInfoTuyenSinh);
                // data[i]=jo.getString("Header");
            }
            // arrayAdapter.notifyDataSetChanged();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return list;
    }

    private ArrayList<ClassInfoTuyenSinh> getDataNewFeed(){


        ArrayList<ClassInfoTuyenSinh>list=new ArrayList<>();
        try {
            URL url=new URL(address);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            is= new BufferedInputStream(httpURLConnection.getInputStream());

        }catch(Exception ex){
            ex.printStackTrace();
        }
        try{
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuilder sb=new StringBuilder();
            while ((line=br.readLine())!= null){
                sb.append(line+"\n");
            }
            is.close();
            reseult=sb.toString();

        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(getContext(),"loi 2"+ex.toString(),Toast.LENGTH_LONG).show();

        }
        try {
            JSONArray ja=new JSONArray(reseult);
            JSONObject jo=null;
            data=new String[ja.length()];
            for(int i=0;i<ja.length();i++){
                ClassInfoTuyenSinh classInfoTuyenSinh=new ClassInfoTuyenSinh();
                jo=ja.getJSONObject(i);
                classInfoTuyenSinh.setHeader(jo.getString("Header"));
                classInfoTuyenSinh.setInfoMain(jo.getString("InfoMain"));
                classInfoTuyenSinh.setText(jo.getString("Text"));
                classInfoTuyenSinh.setTime(jo.getString("Time"));
                classInfoTuyenSinh.setUrl(jo.getString("Url"));
                list.add(classInfoTuyenSinh);
                // data[i]=jo.getString("Header");
            }
            // arrayAdapter.notifyDataSetChanged();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return list;
    }


    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<InfoFragMent.SimpleStringRecyclerViewAdapter.ViewHolder> {

        private final TypedValue mTypedValue = new TypedValue();
        private int mBackground;
        private ArrayList<ClassInfoTuyenSinh> mValues;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public String mBoundString;

            public final View mView;
            public final ImageView mImageView;
            public final TextView mTextView;
            public final TextView mtextHeader;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.avatar);
                mTextView = (TextView) view.findViewById(android.R.id.text1);
                mTextView.setEllipsize(TextUtils.TruncateAt.END);
                mtextHeader=(TextView)view.findViewById(R.id.textHeader_list_item);
                mtextHeader.setEllipsize(TextUtils.TruncateAt.END);

            }

            @Override
            public String toString() {
                return super.toString() + " '" + mTextView.getText();
            }
        }

        public SimpleStringRecyclerViewAdapter(Context context, ArrayList<ClassInfoTuyenSinh> items) {
            context.getTheme().resolveAttribute(R.attr.selectableItemBackground, mTypedValue, true);
            mBackground = mTypedValue.resourceId;
            mValues = items;
        }

        @Override
        public InfoFragMent.SimpleStringRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_item, parent, false);
            view.setBackgroundResource(mBackground);
            return new InfoFragMent.SimpleStringRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final InfoFragMent.SimpleStringRecyclerViewAdapter.ViewHolder holder, final int position) {

            holder.mTextView.setText(mValues.get(position).getInfoMain());
            holder.mtextHeader.setText(mValues.get(position).getHeader());
            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, TestHTMLActivity.class);
                    intent.putExtra(InfoActivity.EXTRA_NAME, mValues.get(position).getText());

                    context.startActivity(intent);
                }
            });

//            Glide.with(holder.mImageView.getContext())
//                    .load(Info.getRandomCheeseDrawable())
//                    .fitCenter()
//                    .into(holder.mImageView);
            try {
                Picasso.with(holder.mImageView.getContext()).load(mValues.get(position).getUrl()).placeholder(R.drawable.back_ground).error(R.drawable.back_ground).into(holder.mImageView);
//                Picasso.with(holder.mImageView.getContext()).load(Info.getRandomCheeseDrawable()).placeholder(R.drawable.back_ground).error(R.drawable.back_ground).into(holder.mImageView);

            }catch(Exception ex){holder.mImageView.setImageResource(R.drawable.cheese_1);}
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        @Override
        public long getItemId(int position) {
            return position-1;
        }


    }
}
