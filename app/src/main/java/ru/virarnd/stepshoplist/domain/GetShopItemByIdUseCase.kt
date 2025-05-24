package ru.virarnd.stepshoplist.domain

import ru.virarnd.stepshoplist.data.ShopItem

class GetShopItemByIdUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItem(itemId: Int): ShopItem {
        return shopListRepository.getShopItem(itemId)
    }
}