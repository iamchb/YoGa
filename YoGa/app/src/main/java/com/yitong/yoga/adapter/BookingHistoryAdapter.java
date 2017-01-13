package com.yitong.yoga.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.yitong.yoga.R;
import com.yitong.yoga.bean.Curriculum;

import java.util.List;

public class BookingHistoryAdapter extends BaseRecyclerAdapter<BookingHistoryAdapter.SimpleAdapterViewHolder> {
    private List<Curriculum> list;
//    private int largeCardHeight, smallCardHeight;
    private Context context;
    public BookingHistoryAdapter(List<Curriculum> list, Context context) {
        this.list = list;
        this.context=context;
//        largeCardHeight = DensityUtil.dip2px(context, 150);
//        smallCardHeight = DensityUtil.dip2px(context, 100);
    }

    @Override
    public void onBindViewHolder(final SimpleAdapterViewHolder holder, int position, boolean isItem) {
        final Curriculum person = list.get(position);

        holder.curriculum.setText(person.getCurriculum());
        holder.time.setText(person.getTime());
        holder.coach.setText(person.getCoach());
        holder.amount_money.setText(person.getMoney());
        holder. name.setText(context.getResources().getString(R.string.reservation_record_kcmc));
        holder. timeTle.setText(context.getResources().getString(R.string.reservation_time));
        holder. coachName.setText(context.getResources().getString(R.string.reservation_jl));
        holder.curriculumName.setText(context.getResources().getString(R.string.reservation_money));
        holder. reservation_record_kczt.setText(context.getResources().getString(R.string.reservation_record_kczt));

        if(person.getORDER_STATUS().equals("0")){
            holder. reservation_status.setText("已預訂");
        }else if(person.getORDER_STATUS().equals("1")){
            holder. reservation_status.setText("已繳費");
        }else if(person.getORDER_STATUS().equals("2")){
            holder. reservation_status.setText("過期未使用");
        }else if(person.getORDER_STATUS().equals("3")){
            holder. reservation_status.setText("已经取消");
        }

//        holder.cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
//                builder.setTitle(context.getResources().getString(R.string.hint_for_logout))
//                        .setMessage(context.getResources().getString(R.string.reservation_hit))
//                        .setPositiveButton(context.getResources().getString(R.string.yes),
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        Toast.makeText(context, context.getResources().getString(R.string.reservation_cancel), Toast.LENGTH_SHORT).show();
//
//
//
//                                        Log.v(TAG, "发送请求开始");
//                                        YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
//                                        params.put("uid", "22");
//                                        params.put("id", person.getClass_id());
//                                        params.put("code_id", person.getCode_id());
//
//                                        Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.CACEL_ORDER, context));
//                                        Logs.e(TAG, params.getParamsString());
//                                        APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.CACEL_ORDER, context), params,
//                                                new APPResponseHandler<DoLogin>(DoLogin.class, null) {
//
//                                                    @Override
//                                                    public void onSuccess(DoLogin result) {
////                                                        waitDialog.dismiss();
//                                                        Logs.v(TAG, "onSuccess");
//                                                        Logs.v(TAG, result.toString());
//
//                                                        if (result.getSTATUS().equals("1")) {
//
//                                                                list.remove(person);
//                                                                notifyDataSetChanged();
//
//                                                        }
//
//                                                    }
//
//                                                    @Override
//                                                    public void onFailure(String errorCode, String errorMsg) {
////                                                        waitDialog.dismiss();
//                                                        Logs.e(TAG, "onFailure");
//                                                        Logs.e("Login_errorCode", errorCode);
////                        接口返回{"MSG":"登录密码错误！","STATUS":"YOGA_LOGIN_PASS_FAIL"}
////                        "MSG":"该账户还没注册，请先注册！","STATUS":"YOGA_ISNOT_REGISTERED"
//                                                        switch (errorCode) {
//                                                            case "YOGA_ISNOT_REGISTERED":
//                                                                ToastTools.showShort(context, errorMsg);
//                                                                break;
//                                                            case "YOGA_LOGIN_PASS_FAIL":
//                                                                ToastTools.showShort(context, errorMsg);
//                                                                break;
////                            case "SMS_EORROR_THREE_TIMES"://该手机号码已被锁定，请一小时后重试！
////                                ToastTools.showShort(getApplicationContext(), errorMsg);
////                                break;
//                                                            default:
//                                                                ToastTools.showShort(context, errorMsg);
//                                                                break;
//                                                        }
//                                                    }
//
//                                                    @Override
//                                                    public void onFinish() {
//                                                        Logs.e(TAG, "onFinish");
////                                                        if (null != waitDialog) {
////                                                            waitDialog.dismiss();
////                                                        }
//                                                    }
//
//                                                }, null);
//
//                                    }
//                                })
//                        .setNegativeButton(context.getResources().getString(R.string.no),
//                                new DialogInterface.OnClickListener() {
//
//                                    @Override
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        dialog.dismiss();
//                                    }
//                                }).show();
//
//
//            }
//        });

//        holder.contentView.setOnClickListener(new View.OnClickListener() {
////        holder.booking_details.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                // 发送登录请求
//                Log.v(TAG, "发送请求开始");
//                YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
//                params.put("uid", "22");
////                params.put("id", person.getClass_id());
//                params.put("order_id", person.getCode_id());
//
//                Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.BOOKING_DETAIL, context));
//                Logs.e(TAG, params.getParamsString());
//                APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.BOOKING_DETAIL, context), params,
//                        new APPResponseHandler<DoLogin>(DoLogin.class, null) {
//
//                            @Override
//                            public void onSuccess(DoLogin result) {
////                                                        waitDialog.dismiss();
//                                Logs.v(TAG, "onSuccess");
//                                Logs.v(TAG, result.toString());
//
//                                if (result.getSTATUS().equals("1")) {
//
//                                    Intent intent=new Intent(context, BookingDetailActivity.class);
//
//                                    intent.putExtra("code_id", person.getCode_id());
//                                    context.startActivity(intent);
//
//                                }
//
//                            }
//
//                            @Override
//                            public void onFailure(String errorCode, String errorMsg) {
////                                                        waitDialog.dismiss();
//                                Logs.e(TAG, "onFailure");
//                                Logs.e("Login_errorCode", errorCode);
////                        接口返回{"MSG":"登录密码错误！","STATUS":"YOGA_LOGIN_PASS_FAIL"}
////                        "MSG":"该账户还没注册，请先注册！","STATUS":"YOGA_ISNOT_REGISTERED"
//                                switch (errorCode) {
//                                    case "YOGA_ISNOT_REGISTERED":
//                                        ToastTools.showShort(context, errorMsg);
//                                        break;
//                                    case "YOGA_LOGIN_PASS_FAIL":
//                                        ToastTools.showShort(context, errorMsg);
//                                        break;
////                            case "SMS_EORROR_THREE_TIMES"://该手机号码已被锁定，请一小时后重试！
////                                ToastTools.showShort(getApplicationContext(), errorMsg);
////                                break;
//                                    default:
//                                        ToastTools.showShort(context, errorMsg);
//                                        break;
//                                }
//                            }
//
//                            @Override
//                            public void onFinish() {
//                                Logs.e(TAG, "onFinish");
////                                                        if (null != waitDialog) {
////                                                            waitDialog.dismiss();
////                                                        }
//                            }
//
//                        }, null);
//            }
//        });
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

    public void setData(List<Curriculum> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_foe_bookinghistory, parent, false);
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

        public TextView curriculum;
        public TextView time;
        public TextView coach;

        public TextView name;
        public TextView timeTle;
        public TextView coachName;
        public Button cancel;

        public TextView curriculumName;
        public TextView reservation_status;
        public TextView amount_money;
        public ImageView booking_details;
        public TextView reservation_record_kczt;
        public LinearLayout contentView;
        CardView cardviewiew;

        public SimpleAdapterViewHolder(View itemView, boolean isItem) {
            super(itemView);
            if (isItem) {
                contentView= (LinearLayout) itemView.findViewById(R.id.contentview);
                curriculum = (TextView) itemView.findViewById(R.id.type);
                time = (TextView) itemView.findViewById(R.id.time);
                coach = (TextView) itemView.findViewById(R.id.coach);
//                cancel = (Button) itemView.findViewById(R.id.cancel_action);

                name=(TextView) itemView.findViewById(R.id.name);
                timeTle=(TextView) itemView.findViewById(R.id.timetitle);
                coachName=(TextView) itemView.findViewById(R.id.coach_name);

                curriculumName=(TextView) itemView.findViewById(R.id.curriculum);
                amount_money=(TextView) itemView.findViewById(R.id.amount_money);
                booking_details=(ImageView) itemView.findViewById(R.id.booking_details);

                cardviewiew= (CardView) itemView.findViewById(R.id.cardviewiew);

                reservation_record_kczt=(TextView) itemView.findViewById(R.id.reservation_record_kczt);
                reservation_status=(TextView) itemView.findViewById(R.id.reservation_status);
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