package com.osdma.milestones.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.osdma.R;
import com.osdma.milestones.ImageItem;

public class GridViewAdapter extends ArrayAdapter<ImageItem> {
        private Context context;
        private int layoutResourceId;
        private ArrayList<ImageItem> data = new ArrayList<ImageItem>();
        private boolean[] thumbnailsselection;

        public GridViewAdapter(Context context, int layoutResourceId,
                        ArrayList<ImageItem> data) {
                super(context, layoutResourceId, data);
                this.layoutResourceId = layoutResourceId;
                this.context = context;
                this.data = data;
                this.thumbnailsselection = new boolean[this.data.size()];
        }
        
        public boolean[] getThumbnailsSelection(){
        	return this.thumbnailsselection;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
                View row = convertView;
                ViewHolder holder = null;

                if (row == null) {
                        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                        row = inflater.inflate(layoutResourceId, parent, false);
                        holder = new ViewHolder();
                        holder.imageSelect = (CheckBox) row.findViewById(R.id.itemCheckBox);
                        holder.image = (ImageView) row.findViewById(R.id.image);
                        row.setTag(holder);
                } else {
                        holder = (ViewHolder) row.getTag();
                }

                ImageItem item = data.get(position);
                holder.image.setImageBitmap(item.getImage());
                holder.imageSelect.setId(position);
                holder.image.setId(position);
                holder.imageSelect.setOnClickListener(new OnClickListener() {
     
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        CheckBox cb = (CheckBox) v;
                        int id = cb.getId();
                        if (thumbnailsselection[id]){
                            cb.setChecked(false);
                            thumbnailsselection[id] = false;
                        } else {
                            cb.setChecked(true);
                            thumbnailsselection[id] = true;
                        }
                    }
                });
                return row;
        }

        static class ViewHolder {
                CheckBox imageSelect;
                ImageView image;
        }
}
