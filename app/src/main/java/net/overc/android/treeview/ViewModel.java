package net.overc.android.treeview;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by New User on 20/03/2017.
 */

public class ViewModel {

   private Model model;
   private Set<ViewModel> children = new HashSet<>();
    private ViewModel parent;

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

    public Boolean isNotRoot() {
        return getParentId() != null;
    }
}
