package com.example.marketplace.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marketplace.R
import com.example.marketplace.model.Product
import com.example.marketplace.viewmodels.*

class DataAdapter(
    private var list: ArrayList<Product>,
    var filteredList: ArrayList<Product>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener
) :
    RecyclerView.Adapter<DataAdapter.DataViewHolder>(), Filterable {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val name: TextView = itemView.findViewById(R.id.textView_name_item_layout)
        val price: TextView = itemView.findViewById(R.id.textView_price_item_layout)
        val seller: TextView = itemView.findViewById(R.id.textView_seller_item_layout)
        val image: ImageView = itemView.findViewById(R.id.imageView_item_layout)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener.onItemClick(currentPosition)
            return
        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            listener2.onItemLongClick(currentPosition)
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return DataViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = filteredList[position]
        holder.name.text = currentItem.title.replace("\"", "")
        holder.price.text = currentItem.price_per_unit.replace("\"", "")
        holder.seller.text = currentItem.username.replace("\"", "")
        val images = currentItem.images
        if (images.isNotEmpty()) {
            Log.d("xxx", "#num_images: ${images.size}")
            Glide.with(this.context)
                .load(images[2])
                .override(50, 50)
                .into(holder.image);
        }

    }

    override fun getItemCount() = filteredList.size

    fun setData(newlist: ArrayList<Product>) {
        list = newlist
        filteredList = list
        notifyDataSetChanged()
    }

    fun sortAscending(){
        filteredList.sortBy {
            it.creation_time
        }
        notifyDataSetChanged()
    }

    fun sortDescending(){
        filteredList.sortByDescending {
            it.creation_time
        }
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) filteredList = list
                else {
                    val filteredList = ArrayList<Product>()
                    list
                        .filter {
                            (it.title.contains(constraint!!))
                        }
                        .forEach { filteredList.add(it) }
                    this@DataAdapter.filteredList = filteredList

                }
                return FilterResults().apply { values = filteredList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = (if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<Product>).also { filteredList = it }
                notifyDataSetChanged()
            }

        }
    }
}

