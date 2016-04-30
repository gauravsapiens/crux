package com.example.crux.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.crux.ListItem;
import com.crux.fragment.BaseMutableListFragment;
import com.crux.util.CollectionUtils;
import com.crux.view.item.ContainerItem;
import com.example.crux.IntentHelper;
import com.example.crux.api.RetrofitHelper;
import com.example.crux.api.SampleService;
import com.example.crux.api.dto.ProductResponse;
import com.example.crux.bean.Category;
import com.example.crux.bean.Product;
import com.example.crux.view.item.ProductItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
 * @author gauravarora
 * @since 27/04/16.
 */
public class ProductListFragment extends BaseMutableListFragment {

    public interface Callback {
        void onProductSelected(Product product);
    }

    @Override
    public List<ListItem> loadListItems(Mode mode, Object extras) {
        List<Product> products = getProducts((Integer) extras);
        if (CollectionUtils.isEmpty(products)) {
            return CollectionUtils.newArrayList();
        }

        List<ListItem> items = new ArrayList<>();
        for (Product product : products) {
            items.add(new ProductItem(product));
        }
        return items;
    }

    @Override
    public void onItemClicked(ListItem item, long position) {
        Product product = (Product) item.getUserInfo();
        Object callback = getCallbacks(Callback.class);
        if (callback != null) {
            ((Callback) callback).onProductSelected(product);
        }
    }

    @Override
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(getActivity(), 2);
    }

    @Override
    protected void customizeView() {
        GridLayoutManager layoutManager = (GridLayoutManager) mLayoutManager;
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                Class i = mAdapter.getItemClass(position);
                if (i.equals(ContainerItem.class)) {
                    return layoutManager.getSpanCount();
                } else {
                    return 1;
                }
            }
        });
    }

    @Override
    protected boolean isPaginationEnabled() {
        return true;
    }

    private List<Product> getProducts(int pageNumber) {
        SampleService service = RetrofitHelper.getSampleService();
        Call<ProductResponse> productCall = service.listProducts(getCategory().getName(), pageNumber);
        try {
            return productCall.execute().body().getProducts();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Category getCategory() {
        return getArguments().getParcelable(IntentHelper.CATEGORY);
    }
}
