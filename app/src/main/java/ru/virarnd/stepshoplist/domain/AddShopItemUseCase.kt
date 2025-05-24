package ru.virarnd.stepshoplist.domain

import ru.virarnd.stepshoplist.data.ShopItem

class AddShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun addShopItem(item: ShopItem) {
        shopListRepository.addShopItem(item)
    }
}