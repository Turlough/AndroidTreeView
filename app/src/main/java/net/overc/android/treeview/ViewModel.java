package net.overc.android.treeview;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static rx.Observable.from;

/**
 * Created by New User on 20/03/2017.
 */

public class ViewModel implements Comparable<ViewModel> {

   private Model model;
   private Collection<ViewModel> children = new HashSet<>();
    private ViewModel parent;
    private boolean expanded =false;
    private boolean selected = false;
    private View titleView;
    TreeViewPopup popup;

    public ViewModel(Model model) {
        this.model = model;
    }

    public long getId(){
        return model.getId();
    }

    public String getName(){
        return model.getName();
    }

    public ViewModel getParent(){
        return parent;
    }

    public void setParent(ViewModel parent) {
        this.parent = parent;
    }

    public Long getParentId() {
        return model.getParentId();
    }

    public View getTitleView() {
        return titleView;
    }

    public void setTitleView(View view, ImageView imageView, TreeViewPopup popup) {

        this.titleView = view;
        imageView.setOnClickListener(v -> {

            if(! selected) select();
            else deselect();

            highlightSelection(imageView, v);
        });

        view.setOnClickListener(v->{
            if(isLeaf()){
                if(! selected) select();
                else deselect();
                highlightSelection(imageView, v);
            } else {
                new TreeViewPopup( v, this, popup).show();
            }
        });
    }

    private void deselect() {
        selected = false;
        from(children)
                .forEach(ViewModel::deselect);
    }

    private void highlightSelection(ImageView imageView, View v) {

        if (selected) {
            v.setBackgroundResource(R.drawable.tree_view_item_name_drawable_pressed);
            imageView.setBackgroundResource(R.drawable.ic_check_blue);
        } else {
            v.setBackgroundResource(R.drawable.tree_view_item_name_drawable_not_pressed);
            imageView.setBackgroundResource(R.drawable.ic_no_permissions);
        }
    }

    public void addChild(ViewModel viewModel){
        children.add(viewModel);
    }

    public boolean isNotRoot() {
        return ! isRoot();
    }

    public boolean isRoot(){
        return getParentId() == null;
    }

    public Model getModel() {

        return model;
    }

    public void setModel(Model model) {

        this.model = model;
    }

    public Collection<ViewModel> getChildren() {

        return from(children)
                .toSortedList()
                .toBlocking()
                .first();
    }

    public void setChildren(Set<ViewModel> children) {

        this.children = children;
    }

    public boolean isExpanded() {

        return !isLeaf() && expanded;
    }

    public void setExpanded(boolean expanded) {

        this.expanded = expanded;
    }

    public void select(){
        setSelected(true);
        if(isNotRoot())
            parent.select();
    }

    public void expand(){
        setExpanded(true);
    }

    public boolean isSelected() {

        return selected;
    }

    public void setSelected(boolean selected) {

        this.selected = selected;
    }

    public boolean isLeaf() {
        return children.size() == 0;
    }


    @Override
    public int compareTo(@NonNull ViewModel o) {

        return getName().compareTo(o.getName());
    }
}
