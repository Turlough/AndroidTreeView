package net.overc.android.treeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

import static rx.Observable.from;


public class MainActivity extends AppCompatActivity {

    ViewTreeProvider provider;

    @BindView(R.id.tv_item_permissions_title)
    Button title;
    @BindView(R.id.lv_permission_children)
    RecyclerView children;

    ViewModelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        title.setText("Test");
        title.setVisibility(View.INVISIBLE);

        Set<ViewModel> models = Mock.createSet();

        provider = ViewTreeProvider.getInstance();
        provider.buildTree(models);

        adapter = new ViewModelAdapter(this, null);
        children.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        children.setLayoutManager(manager);

        from(provider.getTopLevelItems())
                .forEach(model -> model.setExpanded(false));

        adapter.setData(provider.getTopLevelItems());
        adapter.notifyDataSetChanged();
    }

}
