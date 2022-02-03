package com.example.marketplace.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.adapters.MySalesAdapter
import com.example.marketplace.model.Order
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.OrderViewModel
import com.example.marketplace.viewmodels.OrderViewModelFactory

class OngoingSalesFragment : Fragment(), MySalesAdapter.OnItemClickListener, MySalesAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener {

    private lateinit var salesAdapter: MySalesAdapter
    private lateinit var recycler_view: RecyclerView
    lateinit var orderViewModel: OrderViewModel

    private lateinit var search_layout: SearchView
    private lateinit var ongoing_orders: TextView
    private lateinit var ongoing_sales: TextView
    private lateinit var profile: ActionMenuItemView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = OrderViewModelFactory(Repository())
        orderViewModel = ViewModelProvider(this, factory).get(OrderViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_fares, container, false)

        recycler_view = view.findViewById(R.id.recycler_view)
        search_layout = view.findViewById(R.id.search)
        search_layout.setOnQueryTextListener(this)
        setupRecyclerView_forSales()
        orderViewModel.orders.observe(viewLifecycleOwner){
            salesAdapter.setData(orderViewModel.orders.value as ArrayList<Order>)
            salesAdapter.notifyDataSetChanged()
        }
        ongoing_orders = view.findViewById(R.id.textview_ongoing_orders)

        ongoing_orders.setOnClickListener{
            findNavController().navigate(R.id.myFaresOrdersFragment)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d("xxx", "MyFaresFragment - BackPressed")
                }
            })
    }

    private fun setupRecyclerView_forSales(){
        salesAdapter = MySalesAdapter(ArrayList<Order>(), ArrayList<Order>(),ArrayList<Order>(), this.requireContext(),this,this)
        recycler_view.adapter = salesAdapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)
    }

    override fun onItemClick(position: Int) {
//        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int) {
//        TODO("Not yet implemented")
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        salesAdapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        salesAdapter.filter.filter(newText)
        return false
    }
}