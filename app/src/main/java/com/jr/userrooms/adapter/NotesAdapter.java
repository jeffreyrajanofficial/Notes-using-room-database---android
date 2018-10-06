package com.jr.userrooms.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jr.userrooms.R;
import com.jr.userrooms.database.entities.UserNotes;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    private List<UserNotes> userNotes;
    private Context context;
    private LayoutInflater layoutInflater;

    public NotesAdapter(Context context, List<UserNotes> userNotes) {
        this.context = context;
        this.userNotes = userNotes;

        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.notes_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv_title.setText(userNotes.get(i).getTitle());
        viewHolder.tv_description.setText(userNotes.get(i).getDescription());
    }

    @Override
    public int getItemCount() {
        return userNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_description;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            tv_description = itemView.findViewById(R.id.tv_description);
        }
    }
}
