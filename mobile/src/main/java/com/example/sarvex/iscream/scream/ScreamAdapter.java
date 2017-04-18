package com.example.sarvex.iscream.scream;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sarvex.iscream.R;
import com.example.sarvex.iscream.scream.ScreamAdapter.ViewHolder;

import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by sarvex on 17/04/2017.
 */

public class ScreamAdapter extends Adapter<ViewHolder> {

  private final List<Scream> screams;

  public ScreamAdapter(List<Scream> screams) {
    this.screams = screams;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scream, parent, false));

  }

  @Override
  public void onBindViewHolder(ViewHolder holder, int position) {
    holder.bind(screams.get(position));
  }

  @Override
  public int getItemCount() {
    return screams.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    public void bind(Scream scream) {

    }
  }
}
