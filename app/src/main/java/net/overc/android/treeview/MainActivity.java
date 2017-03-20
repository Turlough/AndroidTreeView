package net.overc.android.treeview;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    ViewTreeProvider provider = ViewTreeProvider.getInstance();

    @BindView(R.id.name)
    TextView title;
    @BindView(R.id.children)
    RecyclerView children;

    ViewModelAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title.setText("Test");
        adapter = new ViewModelAdapter();
    }
}
