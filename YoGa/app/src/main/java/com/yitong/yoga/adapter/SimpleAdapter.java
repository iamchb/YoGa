package com.yitong.yoga.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.yitong.yoga.R;
import com.yitong.yoga.activity.BookingDetailActivity;
import com.yitong.yoga.bean.curriculum;

import java.util.List;

public class SimpleAdapter extends BaseRecyclerAdapter<SimpleAdapter.SimpleAdapterViewHolder> {
    private List<curriculum> list;
//    private int largeCardHeight, smallCardHeight;
    private Context context;
    public SimpleAdapter(List<curriculum> list, Context context) {
        this.list = list;
        this.context=context;
//        largeCardHeight = DensityUtil.dip2px(context, 150);
//        smallCardHeight = DensityUtil.dip2px(context, 100);
    }

    @Override
    public void onBindViewHolder(final SimpleAdapterViewHolder holder, int position, boolean isItem) {
        final curriculum person = list.get(position);

        holder.curriculum.setText(person.getCurriculum());
        holder.time.setText(person.getTime());
        holder.coach.setText(person.getCoach());
        holder.amount_money.setText(person.getMoney());
        holder. name.setText("課程名稱");
        holder. timeTle.setText("時間：");
        holder. coachName.setText("教練：");
        holder.curriculumName.setText("課程金額");
        holder.cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
                builder.setTitle("溫馨提示")
                        .setMessage("您確定取消此預定課程？")
                        .setPositiveButton("是",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(context, "已取消", Toast.LENGTH_SHORT).show();
                                        list.remove(person);
                                        notifyDataSetChanged();

                                    }
                                })
                        .setNegativeButton("否",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dialog.dismiss();
                                    }
                                }).show();


            }
        });

        holder.contentView.setOnClickListener(new View.OnClickListener() {
//        holder.booking_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, BookingDetailActivity.class));
            }
        });
    }

    @Override
    public int getAdapterItemViewType(int position) {
        return 0;
    }

    @Override
    public int getAdapterItemCount() {
        return list.size();
    }

    @Override
    public SimpleAdapterViewHolder getViewHolder(View view) {
        return new SimpleAdapterViewHolder(view, false);
    }

    public void setData(List<curriculum> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.holder_consume, parent, false);
        SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v, true);
        return vh;
    }

    public void insert(curriculum person, int position) {
        insert(list, person, position);
    }

    public void remove(int position) {
        remove(list, position);
    }

    public void clear() {
        clear(list);
    }

    public class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView curriculum;
        public TextView time;
        public TextView coach;

        public TextView name;
        public TextView timeTle;
        public TextView coachName;
        public Button cancel;

        public TextView curriculumName;
        public TextView amount_money;
        public ImageView booking_details;

        public LinearLayout contentView;
        CardView cardviewiew;

        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                contentView= (LinearLayout) itemView.findViewById(R.id.contentview);
                curriculum = (TextView) itemView.findViewById(R.id.type);
                time = (TextView) itemView.findViewById(R.id.time);
                coach = (TextView) itemView.findViewById(R.id.coach);
                cancel = (Button) itemView.findViewById(R.id.cancel_action);

                name=(TextView) itemView.findViewById(R.id.name);
                timeTle=(TextView) itemView.findViewById(R.id.timetitle);
                coachName=(TextView) itemView.findViewById(R.id.coach_name);

                curriculumName=(TextView) itemView.findViewById(R.id.curriculum);
                amount_money=(TextView) itemView.findViewById(R.id.amount_money);
                booking_details=(ImageView) itemView.findViewById(R.id.booking_details);

                cardviewiew= (CardView) itemView.findViewById(R.id.cardviewiew);
            }

        }
    }

    public curriculum getItem(int position) {
        if (position < list.size())
            return list.get(position);
        else
            return null;
    }

}