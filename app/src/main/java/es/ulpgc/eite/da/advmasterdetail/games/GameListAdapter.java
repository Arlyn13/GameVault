package es.ulpgc.eite.da.advmasterdetail.games;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.GameItem;

public class GameListAdapter
        extends RecyclerView.Adapter<GameListAdapter.ViewHolder> {

    private List<GameItem> itemList;
    private final View.OnClickListener clickListener;

    public GameListAdapter(View.OnClickListener listener) {
        itemList = new ArrayList<>();
        clickListener = listener;
    }

    public void setItems(List<GameItem> items) {
        itemList = items;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        GameItem item = itemList.get(position);

        holder.itemView.setTag(item);
        holder.itemView.setOnClickListener(clickListener);

        holder.nameView.setText(item.title);
        holder.genreView.setText(item.genre);
        holder.likesView.setText("♥ " + item.totalFavorites);

        int color = item.favorite
                ? ContextCompat.getColor(holder.itemView.getContext(), R.color.gv_red)
                : ContextCompat.getColor(holder.itemView.getContext(), R.color.gv_text_gray);

        holder.likesView.setTextColor(color);

        Glide.with(holder.itemView.getContext())
                .load(item.image)
                .into(holder.imageView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageView;
        final TextView nameView;
        final TextView genreView;
        final TextView likesView;

        ViewHolder(View view) {
            super(view);

            imageView = view.findViewById(R.id.product_image);
            nameView = view.findViewById(R.id.product_name);
            genreView = view.findViewById(R.id.product_genre);
            likesView = view.findViewById(R.id.product_likes);
        }
    }
}