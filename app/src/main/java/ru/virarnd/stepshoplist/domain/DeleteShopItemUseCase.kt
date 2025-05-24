package ru.virarnd.stepshoplist.domain

import ru.virarnd.stepshoplist.data.ShopItem

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun deleteShopItem(item: ShopItem) : Boolean {
        return shopListRepository.deleteShopItem(item)
    }
}