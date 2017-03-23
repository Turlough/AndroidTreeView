package net.overc.android.treeview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import java.util.Collection;

import butterknife.BindView;
import butterknife.ButterKnife;

import static rx.Observable.from;

/**
 * Created by Turlough on 23/03/2017.
 */

public class TreeViewPopup extends Dialog {
    ViewTreeProvider provider;

    @BindView(R.id.name)
    Button title;
    @BindView(R.id.children)
    RecyclerView children;

    ViewModelAdapter adapter;
    public TreeViewPopup(@NonNull Context context, @NonNull ViewModel viewModel) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.tree_view_item);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        setCanceledOnTouchOutside(false);

        View view = View.inflate(getContext(), R.layout.tree_view_item, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        provider = ViewTreeProvider.getInstance();
        adapter = new ViewModelAdapter(context);
        children.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        children.setLayoutManager(manager);

        adapter.setData(viewModel.getChildren());
        adapter.notifyDataSetChanged();
    }
}
