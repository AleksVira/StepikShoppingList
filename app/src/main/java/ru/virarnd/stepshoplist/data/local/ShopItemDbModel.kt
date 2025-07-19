package ru.virarnd.stepshoplist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("shop_items")
data class ShopItemDbModel(
    @PrimaryKey(true)
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean,
)