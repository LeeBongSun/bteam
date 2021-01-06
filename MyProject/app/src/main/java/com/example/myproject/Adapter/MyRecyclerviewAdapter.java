package com.example.myproject.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myproject.Dto.MyItem;
import com.example.myproject.R;

import java.util.ArrayList;

import static com.example.myproject.Sub1.selItem;


public class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.ItemViewHolder>{
    private static final String TAG = "MyRecyclerviewAdapter";

    Context mContext;
    ArrayList<MyItem> arrayList;

    public MyRecyclerviewAdapter(Context mContext, ArrayList<MyItem> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.myitem_view, parent, false);

        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        Log.d("main:adater", "" + position);

        MyItem item = arrayList.get(position);
        holder.setItem(item);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + position);

                selItem = arrayList.get(position);

                Toast.makeText(mContext, "Onclick " + arrayList.get(position).getId(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    // 어댑터에 매소드 만들기

    // 리사이클러뷰 내용 모두 지우기
    public void removeAllItem(){
        arrayList.clear();
    }

    // 특정 인덱스 항목 가져오기
    public MyItem getItem(int position) {
        return arrayList.get(position);
    }

    // 특정 인덱스 항목 셋팅하기
    public void setItem(int position, MyItem item){
        arrayList.set(position, item);
    }

    // arrayList 통째로 셋팅하기
    public void setItems(ArrayList<MyItem> arrayList){
        this.arrayList = arrayList;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout parentLayout;
        public TextView id;
        public TextView name;
        public TextView date;
        public ImageView iv_img;
        public ProgressBar progressBar;

        public ItemViewHolder(@NonNull final View itemView) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            id = itemView.findViewById(R.id.tv_id);
            name = itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);
            iv_img = itemView.findViewById(R.id.iv_img);
            progressBar = itemView.findViewById(R.id.progressBar);

        }

        public void setItem(MyItem item){

            id.setText(item.getId());
            name.setText(item.getName());
            date.setText(item.getDate());

            Glide.with(itemView).load(item.getImage_path()).into(iv_img);
        }
    }

}



