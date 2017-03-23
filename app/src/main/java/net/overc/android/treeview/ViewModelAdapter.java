package net.overc.android.treeview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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
    ViewModelAdapter childAdapter;

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

        position = holder.getAdapterPosition();
        ViewModel model = models.get(position);

        holder.tvTitle.setText(model.getName());
//        LinearLayoutManager manager = new LinearLayoutManager(context);
//        manager.setOrientation(LinearLayoutManager.VERTICAL);
//        holder.lvChildren.setLayoutManager(manager);
//
//        if (model.isExpanded()) {
//            showChildren(holder, model);
//        } else {
//            holder.lvChildren.setVisibility(View.GONE);
//        }

    }

    @Override
    public int getItemCount() {
        if(childAdapter != null)
            return models.size() + childAdapter.getItemCount();
        else
            return models.size();
    }

    public void setData(@NonNull Collection<ViewModel> viewModels) {

        models.clear();
        from(viewModels)
                .forEach(models::add);

    }

    private void showChildren(PermissionHolder holder, ViewModel viewModel) {

        if(viewModel.isLeaf()) return;

        childAdapter = new ViewModelAdapter(context);

        holder.lvChildren.setAdapter(childAdapter);
        childAdapter.setData(viewModel.getChildren());

        LayoutInflater
                .from(context)
                .inflate(R.layout.tree_view_item, holder.lvChildren, false);

        holder.lvChildren.setVisibility(View.VISIBLE);
        //holder.tvTitle.setText(viewModel.getName());

        childAdapter.notifyDataSetChanged();
        notifyDataSetChanged();
    }

    static class PermissionHolder extends RecyclerView.ViewHolder {

        ImageView ivSelect;
        Button tvTitle;
        View rootView;
        RecyclerView lvChildren;

        public PermissionHolder(View itemView) {

            super(itemView);
            rootView = itemView;
//            ivSelect = (ImageView) itemView.findViewById(R.id.iv_item_permissions);
            tvTitle = (Button) itemView.findViewById(R.id.name);
            lvChildren = (RecyclerView) itemView.findViewById(R.id.children);


            lvChildren.setHasFixedSize(false);
        }
    }
}


