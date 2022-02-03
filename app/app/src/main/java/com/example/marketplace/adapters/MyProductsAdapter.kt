package com.example.marketplace.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.MyApplication
import com.example.marketplace.R
import com.example.marketplace.fragments.MyMarketFragment
import com.example.marketplace.model.Product

class MyProductsAdapter(
    private var list: ArrayList<Product>,
    var listFiltered: ArrayList<Product>,
    private val context: Context,
    private val listener: MyMarketFragment,
    private val listener2: MyMarketFragment,
) :
    RecyclerView.Adapter<MyProductsAdapter.DataViewHolder>(), Filterable {

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val textView_name: TextView = itemView.findViewById(R.id.textView_name_item_layout)
        val textView_seller: TextView = itemView.findViewById(R.id.textView_seller_item_layout)
        val imageView: ImageView = itemView.findViewById(R.id.imageView_item_layout)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)
        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            MyApplication.product_id = listFiltered[currentPosition].product_id
            listener2.onItemLongClick(currentPosition)
            return true
        }
    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.my_item_layout, parent, false)
        return DataViewHolder(itemView)
    }


    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = listFiltered[position]
        holder.textView_name.text = currentItem.title.replace("\"", "")
        holder.textView_seller.text = currentItem.username.replace("\"", "")
        val images = currentItem.images
        if (images != null && images.isNotEmpty()) {
            Log.d("xxx", "#num_images: ${images.size}")
            Glide.with(this.context)
                .load(images[0])
                .override(50, 50)
                .into(holder.imageView);
        }
    }

    override fun getItemCount() = listFiltered.size

    // Update the list
    fun setData(newlist: ArrayList<Product>) {
        for(i in newlist){
            if(i.username == MyApplication.username){
                list += i
            }
        }
        listFiltered = list
        notifyDataSetChanged()
    }

    fun sortAscending(){
        listFiltered.sortBy {
            it.creation_time
        }
        notifyDataSetChanged()
    }

    fun sortDescending(){
        listFiltered.sortByDescending {
            it.creation_time
        }
        notifyDataSetChanged()
    }

    fun deleteItem(index: Int){
        listFiltered.removeAt(index)
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) listFiltered = list
                else {
                    val filteredList = ArrayList<Product>()
                    list
                        .filter {
                            (it.title.contains(constraint!!)) or (it.description.contains(constraint))
                        }
                        .forEach { filteredList.add(it) }
                    listFiltered = filteredList

                }
                return FilterResults().apply { values = listFiltered }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                listFiltered = (if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Product>).also { listFiltered = it }
                notifyDataSetChanged()
            }

        }
    }
}

