package ru.virarnd.stepshoplist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.virarnd.stepshoplist.domain.ShopItem
import ru.virarnd.stepshoplist.data.ShopListRepositoryImpl
import ru.virarnd.stepshoplist.domain.DeleteShopItemUseCase
import ru.virarnd.stepshoplist.domain.EditShopItemUseCase
import ru.virarnd.stepshoplist.domain.GetShopListUseCase

class MainViewModel : ViewModel() {

    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)

    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)

    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(item: ShopItem) {
        val newItem = item.copy(enabled = !item.enabled)
        editShopItemUseCase.editShopItem(newItem)
    }


}