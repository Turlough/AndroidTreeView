package net.overc.android.treeview;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;


import static rx.Observable.from;

/**
 * Created by New User on 20/03/2017.
 */

class ViewTreeProvider {

    private Map<Long, ViewModel> models = new Hashtable<>();

    private static final ViewTreeProvider ourInstance = new ViewTreeProvider();


    static ViewTreeProvider getInstance() {
        return ourInstance;
    }

    private ViewTreeProvider() {
    }

    public void add(ViewModel viewModel) {
        if(!models.containsKey(viewModel.getId()))
            models.put(viewModel.getId(), viewModel);
    }

    public ViewModel get(long id){
        return models.get(id);
    }

    public void buildTree(Collection<ViewModel> modelCollection){
        from(modelCollection).forEach(this::add);
        from(models.values())
                .filter(ViewModel::isNotRoot)
                .forEach(model->{
                    model.setParent(models.get(model.getParentId()));
                    model.getParent().addChild(model);
                });
    }

    public Collection<ViewModel> getTopLevelItems() {
        return from(models.values())
                .filter(ViewModel::isRoot)
                .toList()
                .toBlocking()
                .first();
    }
}
