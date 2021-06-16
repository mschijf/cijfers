package com.example.cijfers.service.calculation;

import java.util.ArrayList;

public class TreeForm {
    ArrayList<TreePart> treePartList;

    public TreeForm() {
        treePartList = new ArrayList<>();
    }

    public TreeForm add(TreePart treePart) {
        treePartList.add(treePart);
        return this;
    }

    public TreePart get(int index) {
        return treePartList.get(index);
    }

    public int size() {
        return treePartList.size();
    }

}
