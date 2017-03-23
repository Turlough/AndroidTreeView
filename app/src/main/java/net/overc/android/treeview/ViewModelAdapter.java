package net.overc.android.treeview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static rx.Observable.from;

/**
 * Created by New User on 20/03/2017.
 */

public class ViewModelAdapter extends RecyclerView.Adapter<ViewModelAdapter.PermissionHolder> {

    ViewTreeProvider provider;
    Context context;
    List<ViewModel> models = new ArrayList<>();

    public ViewModelAdapter(Context context){
        provider = ViewTreeProvider.getInstance();
        this.context = context;
    }

    @Override
    public PermissionHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new PermissionHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.tree_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PermissionHolder holder, int position) {
        ViewModel model = models.get(position);
        fillItem(holder, model);
    }

    @Override
    public int getItemCount() {

        return models.size();
    }

    private void fillItem(PermissionHolder holder, ViewModel viewModel) {

        holder.tvTitle.setText(viewModel.getName());

        showChildren(holder, viewModel);

//        if (viewModel.isExpanded()) {
//            showChildren(holder, viewModel);
//        } else {
//            holder.lvChildren.setVisibility(View.GONE);
//        }
//
//        if (viewModel.isSelected()) {
//            holder.ivSelect.setImageResource(R.drawable.ic_check_blue);
//        } else {
//            holder.ivSelect.setImageResource(R.drawable.ic_no_permissions);
//        }

    }

    public void setData(@NonNull Collection<ViewModel> viewModels) {

        models.clear();
        from(viewModels)
                .forEach(item -> {
                    item.setExpanded(true);
                    models.add(item);
                });

        notifyDataSetChanged();
    }

    private void showChildren(PermissionHolder holder, ViewModel viewModel) {

        ViewModelAdapter adapter = new ViewModelAdapter(context);
        holder.lvChildren.setAdapter(adapter);
        adapter.setData(
                new ArrayList<>(viewModel.getChildren())
        );
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.lvChildren.setLayoutManager(manager);

        LayoutInflater
                .from(context)
                .inflate(R.layout.tree_view_item, holder.lvChildren, false);

        holder.lvChildren.setVisibility(View.VISIBLE);
        holder.tvTitle.setText(viewModel.getName());

        adapter.notifyDataSetChanged();
    }


    static class PermissionHolder extends RecyclerView.ViewHolder {

        ImageView ivSelect;
        TextView tvTitle;
        View rootView;
        RecyclerView lvChildren;

        public PermissionHolder(View itemView) {

            super(itemView);
            rootView = itemView;
//            ivSelect = (ImageView) itemView.findViewById(R.id.iv_item_permissions);
            tvTitle = (TextView) itemView.findViewById(R.id.name);
            lvChildren = (RecyclerView) itemView.findViewById(R.id.children);


            lvChildren.setHasFixedSize(false);
        }
    }
}


