package ru.virarnd.stepshoplist.data

import ru.virarnd.stepshoplist.domain.ShopListRepository

object ShopListRepositoryImpl: ShopListRepository {

    private val shopList = mutableListOf<ShopItem>()

    private var autoIncrementId = 0

    override fun addShopItem(item: ShopItem) {
        if (item.id == ShopItem.UNDEFINED_ID) {
            item.id = autoIncrementId++
        }
        shopList.add(item)
    }

    override fun deleteShopItem(item: ShopItem) {
        shopList.remove(item)
    }

    override fun editShopItem(item: ShopItem) {
        val oldElement = getShopItem(item.id)
        shopList.remove(oldElement)
        addShopItem(item)
    }

    override fun getShopItem(itemId: Int): ShopItem {
        return shopList.find { it.id == itemId } ?: throw RuntimeException("Element with id $itemId not found")
    }

    override fun getShopList(): List<ShopItem> {
        return shopList.toList()
    }
}