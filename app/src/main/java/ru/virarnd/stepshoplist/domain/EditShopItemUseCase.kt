package ru.virarnd.stepshoplist.domain

import ru.virarnd.stepshoplist.data.ShopItem

class EditShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun editShopItem(item: ShopItem) {
        shopListRepository.editShopItem(item)
    }
}