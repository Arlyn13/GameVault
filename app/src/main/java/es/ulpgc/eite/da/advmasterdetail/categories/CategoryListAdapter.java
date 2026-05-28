package es.ulpgc.eite.da.advmasterdetail.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.ulpgc.eite.da.advmasterdetail.R;
import es.ulpgc.eite.da.advmasterdetail.data.CategoryItem;


public class CategoryListAdapter
    extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {

  private List<CategoryItem> itemList;
  private final View.OnClickListener clickListener;


  public CategoryListAdapter(View.OnClickListener listener) {

    itemList = new ArrayList();
    clickListener = listener;
  }

  public void addItem(CategoryItem item){
    itemList.add(item);
    notifyDataSetChanged();
  }

  public void addItems(List<CategoryItem> items){
    itemList.addAll(items);
    notifyDataSetChanged();
  }

  public void setItems(List<CategoryItem> items){
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
        .inflate(R.layout.item_category, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.itemView.setTag(itemList.get(position));
    holder.itemView.setOnClickListener(clickListener);

    holder.contentView.setText(itemList.get(position).content);

  }

  class ViewHolder extends RecyclerView.ViewHolder {
    final TextView contentView;

    ViewHolder(View view) {
      super(view);
      contentView = view.findViewById(R.id.category_name);
    }
  }
}
