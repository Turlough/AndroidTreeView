package net.overc.android.treeview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    ViewTreeProvider provider;

    @BindView(R.id.name)
    TextView title;
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

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        children.setLayoutManager(layoutManager);

        adapter = new ViewModelAdapter(this);
        children.setAdapter(adapter);
        adapter.setData(provider.getTopLevelItems());
        adapter.notifyDataSetChanged();
    }
}
