package net.overc.android.treeview;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by New User on 20/03/2017.
 */

public class Mock {

    public static ViewModel newViewModel(long id, Long parentId, String name){
        Model model = new Model(id, name, parentId);
        return new ViewModel(model);
    }

    public static Set<ViewModel> createSet(){
        Set<ViewModel> set = new HashSet<>();
        set.add(newViewModel(1, null, "Parent1"));
        set.add(newViewModel(2, null, "Parent2"));
        set.add(newViewModel(3, 1L, "Parent1-child3"));
        set.add(newViewModel(4, 1L, "Parent1-child4"));
        set.add(newViewModel(5, 4L, "child4-child5"));
        set.add(newViewModel(6, 2L, "Parent2-child6"));
        set.add(newViewModel(7, 4L, "child4-child7"));
        set.add(newViewModel(8, 4L, "child4-child8"));
        set.add(newViewModel(9, 4L, "child4-child9"));
        set.add(newViewModel(10, 4L, "child4-child10"));
        set.add(newViewModel(11, 10L, "child10-child11"));


        return set;
    }
}
