package net.overc.android.treeview;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by New User on 20/03/2017.
 */

public class ViewModel {

   private Model model;
   private Collection<ViewModel> children = new HashSet<>();
    private ViewModel parent;
    private boolean expanded =false;
    private boolean selected = false;

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

        return children;
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


}
