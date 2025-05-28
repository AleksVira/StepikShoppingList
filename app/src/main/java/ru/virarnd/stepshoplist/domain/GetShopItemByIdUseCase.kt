package ru.virarnd.stepshoplist.domain

class GetShopItemByIdUseCase(private val shopListRepository: ShopListRepository) {

    fun getShopItem(itemId: Int): ShopItem {
        return shopListRepository.getShopItem(itemId)
    }
}