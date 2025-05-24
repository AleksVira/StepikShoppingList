package ru.virarnd.stepshoplist.domain

import ru.virarnd.stepshoplist.data.ShopItem

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopList(): List<ShopItem> {
        return shopListRepository.getShopList()
    }
}