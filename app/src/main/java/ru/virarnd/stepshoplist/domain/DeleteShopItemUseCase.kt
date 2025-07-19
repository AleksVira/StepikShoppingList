package ru.virarnd.stepshoplist.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    suspend fun deleteShopItem(item: ShopItem) {
        return shopListRepository.deleteShopItem(item)
    }
}