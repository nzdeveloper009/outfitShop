package com.nzdeveloper009.affordablefunctionoutfit;

import android.widget.Filter;

import com.nzdeveloper009.affordablefunctionoutfit.AdapterClasses.AdapterOrderSeller;
import com.nzdeveloper009.affordablefunctionoutfit.HelperClasses.ModelOrderBuyer;

import java.util.ArrayList;

public class FilterOrderShop extends Filter {

    private AdapterOrderSeller adapter;
    private ArrayList<ModelOrderBuyer> filterList;

    public FilterOrderShop(AdapterOrderSeller adapter, ArrayList<ModelOrderBuyer> filterList) {
        this.adapter = adapter;
        this.filterList = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();
        //validate data for search query
        if(constraint !=null && constraint.length() > 0)
        {
            //search field not empty, searching something, perform search

            //change to upper case, to make case insensitive
            constraint = constraint.toString().toUpperCase();
            //store our filtered list
            ArrayList<ModelOrderBuyer> filteredModels = new ArrayList<>();
            for(int i=0;i<filterList.size();i++)
            {
                //check search by category
                if(filterList.get(i).getOrderStatus().toUpperCase().contains(constraint))
                {
                    //add filtered data to list
                    filteredModels.add(filterList.get(i));
                }
            }
            results.count = filteredModels.size();
            results.values = filteredModels;

        }
        else{
            //search filed empty, not searching return original/all/complete list
            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.orderBuyerList = (ArrayList<ModelOrderBuyer>) results.values;
        //refresh adapter
        adapter.notifyDataSetChanged();
    }
}
