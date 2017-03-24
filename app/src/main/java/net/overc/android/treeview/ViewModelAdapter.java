package net.overc.android.treeview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static rx.Observable.from;

/**
 * Created by Turlough on 20/03/2017.
 */

public class ViewModelAdapter extends RecyclerView.Adapter<ViewModelAdapter.PermissionHolder>{

    ViewTreeProvider provider;
    Context context;
    List<ViewModel> models = new ArrayList<>();
    ViewModelAdapter childAdapter;
    TreeViewPopup popup;

    public ViewModelAdapter(Context context, TreeViewPopup popup) {

        provider = ViewTreeProvider.getInstance();
        this.context = context;
        this.popup = popup;
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
        model.setTitleView(holder.header, holder.ivSelect, new TreeViewPopup(holder.header, model, popup));

        holder.tvTitle.setText(model.getName());
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        holder.lvChildren.setLayoutManager(manager);

        if (model.isExpanded()) {
            showChildren(holder, model);
            //holder.tvTitle.setVisibility(View.GONE);
        } else {
            holder.lvChildren.setVisibility(View.GONE);
        }

        if (model.isSelected()) {
            holder.ivSelect.setBackgroundResource(R.drawable.ic_check_blue);
            holder.header.setBackgroundResource(R.drawable.tree_view_item_name_drawable_pressed);
        } else {
            holder.ivSelect.setBackgroundResource(R.drawable.ic_no_permissions);
            holder.header.setBackgroundResource(R.drawable.tree_view_item_name_drawable_not_pressed);
        }

        if(model.isLeaf())
            holder.ivChevron.setVisibility(View.GONE);
        else
            holder.ivChevron.setVisibility(View.VISIBLE);

    }

    @Override
    public int getItemCount() {

        if (childAdapter != null)
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

        if (viewModel.isLeaf()) return;

        childAdapter = new ViewModelAdapter(context, popup);

        holder.lvChildren.setAdapter(childAdapter);
        childAdapter.setData(viewModel.getChildren());

        LayoutInflater
                .from(context)
                .inflate(R.layout.tree_view_item, holder.lvChildren, false);

        holder.lvChildren.setVisibility(View.VISIBLE);
        //holder.tvTitle.setText(viewModel.getName());

        childAdapter.notifyDataSetChanged();
//        notifyDataSetChanged();
    }



    static class PermissionHolder extends RecyclerView.ViewHolder {

        ImageView ivSelect;
        ImageView ivChevron;
        TextView tvTitle;
        View rootView;
        RecyclerView lvChildren;
        RelativeLayout header;

        public PermissionHolder(View itemView) {

            super(itemView);
            rootView = itemView;
            ivSelect = (ImageView) itemView.findViewById(R.id.iv_item_permissions);
            ivChevron = (ImageView) itemView.findViewById(R.id.chevron);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_item_permissions_title);
            lvChildren = (RecyclerView) itemView.findViewById(R.id.lv_permission_children);
            header = (RelativeLayout) itemView.findViewById(R.id.tree_view_header);

            lvChildren.setHasFixedSize(true);
        }
    }

}


