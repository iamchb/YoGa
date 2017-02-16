package com.yitong.yoga.stickyListHeaders;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yitong.yoga.R;
import com.yitong.yoga.ServiceCode;
import com.yitong.yoga.UserManager;
import com.yitong.yoga.activity.CourseIntroductionActivity;
import com.yitong.yoga.activity.LoginActivity;
import com.yitong.yoga.bean.ClaassDetal;
import com.yitong.yoga.http.APPResponseHandler;
import com.yitong.yoga.http.APPRestClient;
import com.yitong.yoga.http.ServiceUrlManager;
import com.yitong.yoga.http.YTBaseRequestParams;
import com.yitong.yoga.http.YTRequestParams;
import com.yitong.yoga.utils.Logs;
import com.yitong.yoga.utils.NoDoubleClickListener;
import com.yitong.yoga.utils.ToastTools;

import java.util.ArrayList;
import java.util.List;

public class StickylistAdapter extends BaseAdapter implements
        StickyListHeadersAdapter, SectionIndexer {
    private List<StickyListBean> list = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context context;

    private static final String TAG = "StickylistAdapter";

    public StickylistAdapter(Context context, List<StickyListBean> list) {
        this.list = list;
        this.context=context;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public void setData(List<StickyListBean> alarmList) {
        this.list = alarmList;
    }

    @Override
    public StickyListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder  mHolder ;
        if (convertView == null) {
            convertView = mInflater
                    .inflate(R.layout.item_sticky, parent, false);
            mHolder = new ViewHolder();
            mHolder.tvTime = (TextView) convertView.findViewById(R.id.item);
            mHolder.class_time_flag = (TextView) convertView.findViewById(R.id.class_time_flag);
            mHolder.class_time = (TextView) convertView.findViewById(R.id.class_time);
            mHolder.class_location = (TextView) convertView.findViewById(R.id.class_location);
            mHolder.teacher = (TextView) convertView.findViewById(R.id.teacher_name);
            convertView.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) convertView.getTag();
        }
        final StickyListBean info = getItem(position);
        mHolder.tvTime.setText(info.getContent());
        mHolder.teacher.setText(info.getClassTeacher());
        mHolder.class_location.setText(info.getClassLocation());
        mHolder.class_time.setText(info.getClassTime());
        mHolder.class_time_flag.setText(info.getClassTimeFlag());

        convertView.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
//                context.startActivity(new Intent(context, CourseIntroductionActivity.class));

                if(UserManager.getInstance().isLogin()){

                    YTRequestParams params = new YTBaseRequestParams(YTBaseRequestParams.PARAM_TYPE_JSON);
                    params.put("Id", info.getId());
                    if(null!=UserManager.getInstance().getUserInfo().getAgent_id()){
                        params.put("uid", UserManager.getInstance().getUserInfo().getAgent_id());
                    }
                    Logs.e(TAG, ServiceUrlManager.getServiceAbsUrl(ServiceCode.CURRI_DETAIL, context));
                    Logs.e(TAG, params.getParamsString());
                    APPRestClient.post(ServiceUrlManager.getServiceAbsUrl(ServiceCode.CURRI_DETAIL, context), params,
                            new APPResponseHandler<ClaassDetal>(ClaassDetal.class, null) {

                                @Override
                                public void onSuccess(ClaassDetal result) {
//                                waitDialog.dismiss();
                                    Logs.v(TAG, "onSuccess");
                                    Logs.v(TAG, result.toString());

                                    if (result.getSTATUS().equals("1")) {

                                        Intent intent=new Intent(context,CourseIntroductionActivity.class);
                                        intent.putExtra("CLASS_NAME",result.getLIST().get(0).getCLASS_NAME());
                                        intent.putExtra("CLASS_ADDR",result.getLIST().get(0).getCLASS_ADDR());
                                        intent.putExtra("CLASS_DESC",result.getLIST().get(0).getCLASS_DESC());
                                        intent.putExtra("START_DATE",result.getLIST().get(0).getSTART_DATE());
                                        intent.putExtra("START_TIME",result.getLIST().get(0).getSTART_TIME());
                                        intent.putExtra("TAKES_TIME",result.getLIST().get(0).getTAKES_TIME());
                                        intent.putExtra("CLASS_ID",result.getLIST().get(0).getCLASS_ID());
                                        context.startActivity(intent);
                                    }

                                }

                                @Override
                                public void onFailure(String errorCode, String errorMsg) {
//                                waitDialog.dismiss();
                                    Logs.e(TAG, "onFailure");
                                    Logs.e(TAG, errorCode);
                                    switch (errorCode) {
                                        case "YOGA_ISNOT_REGISTERED":
                                            break;
                                        case "YOGA_LOGIN_PASS_FAIL":
                                            break;
//                            case "SMS_EORROR_THREE_TIMES"://该手机号码已被锁定，请一小时后重试！
//                                ToastTools.showShort(getApplicationContext(), errorMsg);
//                                break;
                                        default:
                                            ToastTools.showShort(context, errorMsg);
                                            break;
                                    }
                                }

                                @Override
                                public void onFinish() {
                                    Logs.e(TAG, "onFinish");
//                                if (null != waitDialog) {
//                                    waitDialog.dismiss();
//                                }
                                }

                            }, null);
                    Logs.v(TAG, "发送请求结束");
                }else{

                    Intent intent=new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }

            }
        });
        return convertView;
    }

    private static class ViewHolder {
        private TextView tvTime;
        private TextView class_time_flag;
        private TextView class_time;
        private TextView class_location;
        private TextView teacher;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int position) {
        if (position < getCount()) {
            return getItem(position).getSection();
        }
        return 0;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;

        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = mInflater.inflate(R.layout.item_sticky_header,
                    parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.header);

            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        String headerText = getItem(position).getYM();
        holder.text.setText(headerText);

        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        if (position < getCount()) {
            return getItem(position).getSection();
        }
        return 0;
    }

    private class HeaderViewHolder {
        TextView text;

    }

}
