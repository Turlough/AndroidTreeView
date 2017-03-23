package net.overc.android.treeview;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.widget.Button;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static rx.Observable.from;


public class MainActivity extends AppCompatActivity {

    ViewTreeProvider provider;

    @BindView(R.id.name)
    Button title;
    @BindView(R.id.children)
    RecyclerView children;

    ViewModelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        title.setText("Test");

        Set<ViewModel> models = Mock.createSet();

        provider = ViewTreeProvider.getInstance();
        provider.buildTree(models);

        adapter = new ViewModelAdapter(this);
        children.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        children.setLayoutManager(manager);

        from(provider.getTopLevelItems())
                .forEach(model -> model.setExpanded(false));
//        from(provider.getAllItems())
//                .forEach(model -> model.setExpanded(true));

        adapter.setData(provider.getTopLevelItems());
        adapter.notifyDataSetChanged();
    }

    @OnClick(R.id.name)
    public void showChildren(){

        new TreeViewPopup(title, provider.get(1)).show();
    }
}
