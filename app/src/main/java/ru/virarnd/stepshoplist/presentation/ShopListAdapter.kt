package ru.virarnd.stepshoplist.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ru.virarnd.stepshoplist.R
import ru.virarnd.stepshoplist.domain.ShopItem

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    companion object {
        const val MAX_POOL_SIZE = 5
    }

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null

    var shopItemClickListener: ((ShopItem) -> Unit)? = null

    override fun getItemCount() = shopList.size

    override fun getItemViewType(position: Int): Int = if (shopList[position].enabled) {
        R.layout.item_shop_enabled
    } else {
        R.layout.item_shop_disabled
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        return when (viewType) {
            R.layout.item_shop_enabled -> {
                ShopItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_shop_enabled, parent, false))
            }

            R.layout.item_shop_disabled -> {
                ShopItemViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_shop_disabled, parent, false))
            }

            else -> {
                throw RuntimeException("Unknown view Type $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            shopItemLongClickListener?.invoke(shopItem)
            true
        }
        holder.itemView.setOnClickListener {
            shopItemClickListener?.invoke(shopItem)
        }

        if (shopItem.enabled) {
            holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.holo_red_light))
        } else {
            holder.tvName.setTextColor(ContextCompat.getColor(holder.itemView.context, android.R.color.white))
        }
    }

    class ShopItemViewHolder(view: View) : ViewHolder(view) {
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
    }


}