package ru.uxapps.counterlessons;

import android.graphics.PorterDuff;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CounterList {

    public interface Listener {

        void onPlus(Counter counter);

        void onMinus(Counter counter);

        void onOpen(Counter counter);

    }

    private final RecyclerView mRv;
    private final CounterAdapter mAdapter;
    private final Listener mListener;

    public CounterList(RecyclerView rv, Listener listener) {
        mRv = rv;
        mListener = listener;
        mAdapter = new CounterAdapter();
        rv.setAdapter(mAdapter);
        setLayout(true);
    }

    public void setCounters(List<Counter> list) {
        mAdapter.setData(list);
    }

    public void setLayout(boolean isList) {
        if (isList) mRv.setLayoutManager(new LinearLayoutManager(mRv.getContext()));
        else mRv.setLayoutManager(new GridLayoutManager(mRv.getContext(), 2));
        mAdapter.setType(isList ? CounterAdapter.TYPE_WIDE : CounterAdapter.TYPE_TALL);
    }

    class CounterAdapter extends RecyclerView.Adapter<CounterAdapter.Vh> {

        private static final int TYPE_WIDE = 0;
        private static final int TYPE_TALL = 1;

        private List<Counter> mData;
        private int mType = TYPE_WIDE;

        CounterAdapter() {
            setHasStableIds(true);
        }

        void setData(List<Counter> data) {
            mData = data;
            notifyDataSetChanged();
        }

        void setType(int type) {
            mType = type;
            notifyDataSetChanged();
        }

        @Override
        public int getItemViewType(int position) {
            return mType;
        }

        @NonNull
        @Override
        public Vh onCreateViewHolder(@NonNull ViewGroup parent, int type) {
            if (type == TYPE_WIDE) return new Vh(parent, R.layout.i_counter_wide);
            else return new Vh(parent, R.layout.i_counter_tall);
        }

        @Override
        public void onBindViewHolder(@NonNull Vh vh, int position) {
            vh.bind(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        @Override
        public long getItemId(int position) {
            return mData.get(position).id;
        }

        class Vh extends RecyclerView.ViewHolder {

            private final TextView mName;
            private final TextView mValue;
            private final View mTint;

            Vh(ViewGroup parent, @LayoutRes int itemLayout) {
                super(LayoutInflater.from(parent.getContext())
                        .inflate(itemLayout, parent, false));
                mName = itemView.findViewById(R.id.i_counter_name);
                mValue = itemView.findViewById(R.id.i_counter_value);
                mTint = itemView.findViewById(R.id.i_counter_tint);
                new FastCountButton(itemView.findViewById(R.id.i_counter_minus), () ->
                        mListener.onMinus(mData.get(getAdapterPosition())));
                new FastCountButton(itemView.findViewById(R.id.i_counter_plus), () ->
                        mListener.onPlus(mData.get(getAdapterPosition())));
                itemView.setOnClickListener(v ->
                        mListener.onOpen(mData.get(getAdapterPosition())));
            }

            void bind(Counter counter) {
                mName.setText(counter.name);
                mValue.setText(String.valueOf(counter.value));
                mTint.getBackground().setColorFilter(counter.color, PorterDuff.Mode.SRC_ATOP);
            }
        }
    }
}
