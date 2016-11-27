package han.anthony.anthonydemo.activity_main.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import han.anthony.anthonydemo.beans.DemoBean;
import han.anthony.anthonydemo.R;

/**
 * Created by senior on 2016/11/2.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<DemoBean> mDatas;
    private LayoutInflater mInflater;
    private OnItemClickListener mItemClickListener;
    private OnItemLongClickListener mItemLongClickListener;
    private int[] colors={0xFF4B0082,0xFF6B8E23,0xFFB8860B,0xFFFF7F50};

    public RecyclerAdapter(Context context, List<DemoBean> datas) {
        mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(mInflater.inflate(R.layout.item_demo, parent, false));
    }


    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        DemoBean demoBean = mDatas.get(position);
        holder.demoName.setText(demoBean.getName());
        holder.demoDesc.setText(demoBean.getDesc());
        holder.wrap.setCardBackgroundColor(colors[getItemViewType(position)]);
    }

    @Override
    public int getItemViewType(int position) {
        return position%colors.length;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        @BindView(R.id.tv_demo_name)
        TextView demoName;
        @BindView(R.id.tv_demo_desc)
        TextView demoDesc;
        @BindView(R.id.card_wrap)
        CardView wrap;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            /**
             * 设置点击回调的Listener
             */
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mItemClickListener!=null){
                mItemClickListener.onItemClick(v,getAdapterPosition());
            }
        }

        @Override
        public boolean onLongClick(View v) {

            if(mItemLongClickListener!=null){
                mItemLongClickListener.onItemLongClick(v,getAdapterPosition());
            }
            return false;
        }
    }

    /**
     * 供外部调用设置Listener
     */
    public RecyclerAdapter setOnItemClickListener(OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
        return this;
    }

    public RecyclerAdapter setOnItemLongClickListener(OnItemLongClickListener mItemLongClickListener) {
        this.mItemLongClickListener = mItemLongClickListener;
        return this;
    }

    /**
     * Listener
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}
