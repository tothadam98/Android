package com.example.marketplace.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.R
import com.example.marketplace.adapters.DataAdapter
import com.example.marketplace.adapters.MyProductsAdapter
import com.example.marketplace.model.Product
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.ListViewModel
import com.example.marketplace.viewmodels.ListViewModelFactory
import com.example.marketplace.viewmodels.ProductViewModel
import com.example.marketplace.viewmodels.ProductViewModelFactory
import kotlinx.android.synthetic.main.fragment_timeline.*
import kotlinx.coroutines.launch

class MyMarketFragment : Fragment() , DataAdapter.OnItemClickListener, DataAdapter.OnItemLongClickListener, SearchView.OnQueryTextListener {
    lateinit var listViewModel: ListViewModel
    private lateinit var productViewModel: ProductViewModel
    private lateinit var recycler_view: RecyclerView
    private lateinit var search: SearchView
    private lateinit var adapter: MyProductsAdapter
    private lateinit var buttonS : Switch
    private lateinit var switch : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ListViewModelFactory(Repository())
        listViewModel = ViewModelProvider(this, factory)[ListViewModel::class.java]
        val factory1 = ProductViewModelFactory(Repository())
        productViewModel = ViewModelProvider(this, factory1).get(ProductViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_my_market, container, false)
        recycler_view = view.findViewById(R.id.recycler_view)
        search = view.findViewById(R.id.search)
        search.setOnQueryTextListener(this)
        setupRecyclerView()
        listViewModel.products.observe(viewLifecycleOwner){
            adapter.setData(listViewModel.products.value as ArrayList<Product>)
            adapter.notifyDataSetChanged()
        }

        switch = view.findViewById(R.id.latest_oldest)
        buttonS = view.findViewById(R.id.switch1)

        buttonS.setOnClickListener {
            if (switch1.isChecked){
                adapter.sortDescending()
                switch.text = getString(R.string.latest)
            }
            else{
                adapter.sortAscending()
                switch.text = getString(R.string.oldest)
            }
        }

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.action_bar, menu)

        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun setupRecyclerView(){
        adapter = MyProductsAdapter(ArrayList<Product>(), ArrayList<Product>(),  this.requireContext(), this, this)
        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this.context)
        recycler_view.setHasFixedSize(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                }
            })
    }

    override fun onItemClick(position: Int) {
//        TODO("Not yet implemented")
    }

    override fun onItemLongClick(position: Int) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Delete")
        builder.setMessage("Are you sure you want to remove this item?")
        builder.setPositiveButton("Yes") { _: DialogInterface, _: Int ->
            lifecycleScope.launch {
                productViewModel.deleteProduct()
            }
            adapter.deleteItem(position)
            Toast.makeText(context, "Successful deletion. ",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("No",{ dialogInterface: DialogInterface, i: Int -> })
        builder.show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

}