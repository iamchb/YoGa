package com.yitong.yoga.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.yitong.yoga.R;
import com.yitong.yoga.activity.LoginActivity;
import com.yitong.yoga.bean.Curriculum;

import java.util.List;

public class NoLoginAdapter extends BaseRecyclerAdapter<NoLoginAdapter.SimpleAdapterViewHolder> {
    private List<Curriculum> list;
//    private int largeCardHeight, smallCardHeight;
    private Context context;
    public NoLoginAdapter(List<Curriculum> list, Context context) {
        this.list = list;
        this.context=context;
//        largeCardHeight = DensityUtil.dip2px(context, 150);
//        smallCardHeight = DensityUtil.dip2px(context, 100);
    }

    @Override
    public void onBindViewHolder(final SimpleAdapterViewHolder holder, int position, boolean isItem) {
//        final Curriculum person = list.get(position);

//        holder.noLogin.setText(person.getCoach());
        holder.toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, LoginActivity.class);
                context.startActivity(intent);
            }
        });

    }

//    @Override
//    public int getAdapterItemViewType(int position) {
//        return 0;
//    }

    @Override
    public int getAdapterItemCount() {
        return 1;
    }

    @Override
    public SimpleAdapterViewHolder getViewHolder(View view) {
        return new SimpleAdapterViewHolder(view, false);
    }

    public void setData(List<Curriculum> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_no_login, parent, false);
        SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v, true);
        return vh;
    }

    public void insert(Curriculum person, int position) {
        insert(list, person, position);
    }

    public void remove(int position) {
        remove(list, position);
    }

    public void clear() {
        clear(list);
    }

    public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {

//        public TextView noLogin;
        public Button toLogin;

//        CardView cardviewiew;

        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
//                noLogin = (TextView) itemView.findViewById(R.id.no_login);
                toLogin= (Button) itemView.findViewById(R.id.to_login);
            }

        }
    }

    public Curriculum getItem(int position) {
        if (position < list.size())
            return list.get(position);
        else
            return null;
    }

}