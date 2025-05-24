package ru.virarnd.stepshoplist.domain

class DeleteShopItemUseCase(private val shopListRepository: ShopListRepository) {

    fun deleteShopItem(item: ShopItem) {
        return shopListRepository.deleteShopItem(item)
    }
}