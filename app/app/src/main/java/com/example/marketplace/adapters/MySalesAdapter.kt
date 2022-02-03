package com.example.marketplace.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.marketplace.MyApplication
import com.example.marketplace.R
import com.example.marketplace.model.Order
import java.sql.Timestamp

class MySalesAdapter(
    private var list: ArrayList<Order>,
    private var listOfMyProducts: ArrayList<Order>,
    var listFiltered: ArrayList<Order>,
    private val context: Context,
    private val listener: OnItemClickListener,
    private val listener2: OnItemLongClickListener
) :
    RecyclerView.Adapter<MySalesAdapter.DataViewHolder>(), Filterable {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    interface OnItemLongClickListener {
        fun onItemLongClick(position: Int)
    }

    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        val textView_title: TextView = itemView.findViewById(R.id.textView_name_item_layout)
        val textView_buyer: TextView = itemView.findViewById(R.id.textView_buyer_name)
        val textView_price: TextView = itemView.findViewById(R.id.textView_price_item_layout)
        val textView_amount: TextView = itemView.findViewById(R.id.amount_text)
        val textView_status: TextView = itemView.findViewById(R.id.status)
        val time: TextView = itemView.findViewById(R.id.time_text)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(p0: View?) {
            val currentPosition = this.adapterPosition
            listener2.onItemLongClick(currentPosition)
            return
        }

        override fun onLongClick(p0: View?): Boolean {
            val currentPosition = this.adapterPosition
            listener2.onItemLongClick(currentPosition)

            return true
        }
    }

    // 2. Called only a few times = number of items on screen + a few more ones
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_sell_layout, parent, false)
        return DataViewHolder(itemView)
    }


    // 3. Called many times, when we scroll the list
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val currentItem = listFiltered[position]
        holder.textView_title.text = currentItem.title.replace("\"", "")
        holder.textView_buyer.text = currentItem.username.replace("\"", "")
        holder.textView_price.text = currentItem.price_per_unit.replace("\"", "").replace("\\", "")
        holder.textView_amount.text = currentItem.units.replace("\"", "").replace("\\", "")
        holder.textView_status.text = currentItem.status.replace("\"","")
        val creationTimeLong = currentItem.creation_time
        val creationTime = Timestamp(creationTimeLong)
        holder.time.text = creationTime.toString().subSequence(0,10)
    }

    override fun getItemCount() = listFiltered.size

    // Update the list
    fun setData(newlist: ArrayList<Order>) {
        list = newlist.filter{ s -> s.owner_username == MyApplication.username || s.owner_username == "\"".plus(MyApplication.username).plus("\"")} as ArrayList<Order>
        listFiltered = list
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) listFiltered = list
                else {
                    val filteredList = ArrayList<Order>()
                    list
                        .filter {
                            (it.title.contains(constraint!!))
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
                    results.values as ArrayList<Order>).also { listFiltered = it }
                notifyDataSetChanged()
            }

        }
    }
}

