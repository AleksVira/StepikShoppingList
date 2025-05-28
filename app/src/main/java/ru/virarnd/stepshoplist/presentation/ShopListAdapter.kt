package ru.virarnd.stepshoplist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import ru.virarnd.stepshoplist.R
import ru.virarnd.stepshoplist.domain.ShopItem

class ShopListAdapter : ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    companion object {
        const val MAX_POOL_SIZE = 5
    }

    var shopItemLongClickListener: ((ShopItem) -> Unit)? = null
    var shopItemClickListener: ((ShopItem) -> Unit)? = null
    var shopListItemRemoveListener: ((ShopItem) -> Unit)? = null

    override fun getItemViewType(position: Int): Int = if (getItem(position).enabled) {
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
        val shopItem = getItem(position)
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

    fun removeItem(position: Int) {
        shopListItemRemoveListener?.invoke(getItem(position))
    }

}