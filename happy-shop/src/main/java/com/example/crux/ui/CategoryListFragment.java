package com.example.crux.ui;

import com.crux.ListItem;
import com.crux.fragment.BaseMutableListFragment;
import com.crux.view.item.TextItem;
import com.example.crux.bean.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class CategoryListFragment extends BaseMutableListFragment {

    public interface Callback {
        void onCategorySelected(Category category);
    }

    @Override
    public List<ListItem> loadListItems(Mode mode, Object extras) {
        List<ListItem> items = new ArrayList<>();
        for (Category category : getCategories()) {
            TextItem item = new TextItem(category.getName());
            item.setUserInfo(category);
            items.add(item);
        }
        return items;
    }

    @Override
    public void onItemClicked(ListItem item, long position) {
        Category category = (Category) item.getUserInfo();
        Object callback = getCallbacks(Callback.class);
        if (callback != null) {
            ((Callback) callback).onCategorySelected(category);
        }
    }

    private List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("1", "Skincare", 0));
        categories.add(new Category("2", "Bath & Body", 0));
        categories.add(new Category("3", "Nails", 0));
        categories.add(new Category("4", "Tools", 0));
        categories.add(new Category("5", "Makeup", 0));
        categories.add(new Category("6", "Men", 0));
        return categories;
    }

}
