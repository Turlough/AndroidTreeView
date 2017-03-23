package net.overc.android.treeview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
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
    public TreeViewPopup(@NonNull View launcher, @NonNull ViewModel viewModel) {
        super(launcher.getContext());

        Context context = launcher.getContext();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        View view = View.inflate(getContext(), R.layout.tree_view_item, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        title.setText(viewModel.getName());
        title.setVisibility(View.GONE);

        provider = ViewTreeProvider.getInstance();
        adapter = new ViewModelAdapter(context);
        children.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        children.setLayoutManager(manager);

        adapter.setData(viewModel.getChildren());
        adapter.notifyDataSetChanged();

        moveDialog(launcher);
    }

    void moveDialog(View launcher){
        int[] location = new int[2];
        launcher.getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];

        getWindow().setGravity(Gravity.TOP|Gravity.LEFT);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = location[0];
        params.y = location[1];
        getWindow().setAttributes(params);
    }
}
