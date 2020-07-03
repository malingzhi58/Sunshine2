package com.example.sunshine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastAdapterViewHolder> {
    private String[] mWeatherData;

    /*
     * An on-click handler that we've defined to make it easy for an Activity to interface with
     * our RecyclerView
     */
    private final ForecastAdapterOnClickHandler mClickHandler;

    public ForecastAdapter(ForecastAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public ForecastAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        Context context = viewGroup.getContext();
//        int layoutIdForListItem = R.layout.forecast_list_item;
//        LayoutInflater inflater = LayoutInflater.from(context);
//        boolean shouldAttachToParentImmediately = false;
//
//        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
//        return new ForecastAdapterViewHolder(view);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_list_item,parent,false);
        return new ForecastAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastAdapterViewHolder forecastAdapterViewHolder, int position) {
        String weatherForThisDay = mWeatherData[position];
        forecastAdapterViewHolder.mWeatherTextView.setText(weatherForThisDay);
    }


    @Override
    public int getItemCount() {
        if (null == mWeatherData) return 0;
        return mWeatherData.length;
    }

    public interface ForecastAdapterOnClickHandler{
        void onClick(String word);
    }


    class ForecastAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mWeatherTextView;
        public ForecastAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            mWeatherTextView=itemView.findViewById(R.id.tv_weather_data);
            itemView.setOnClickListener(this);//which is important
        }
        // the result of implements OnclickListener
        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String weatherForDay = mWeatherData[adapterPosition];
            // which is the result of the interface
            mClickHandler.onClick(weatherForDay);
            // TODO this toast is not solved
//            Toast.makeText(this,mWeatherData[adapterPosition],Toast.LENGTH_SHORT).show();
//            ToastUtil.showMsg(,mWeatherData[adapterPosition]);

        }
    }
    public void setWeatherData(String[] weatherData) {
        mWeatherData = weatherData;
        notifyDataSetChanged();
    }
}
