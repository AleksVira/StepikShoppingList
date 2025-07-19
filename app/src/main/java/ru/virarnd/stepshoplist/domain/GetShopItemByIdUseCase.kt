package ru.virarnd.stepshoplist.domain

class GetShopItemByIdUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun getShopItem(itemId: Int): ShopItem {
        return shopListRepository.getShopItem(itemId)
    }
}