package ru.virarnd.stepshoplist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.virarnd.stepshoplist.data.ShopListRepositoryImpl
import ru.virarnd.stepshoplist.domain.AddShopItemUseCase
import ru.virarnd.stepshoplist.domain.EditShopItemUseCase
import ru.virarnd.stepshoplist.domain.GetShopItemByIdUseCase
import ru.virarnd.stepshoplist.domain.ShopItem

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ShopListRepositoryImpl(application)
    private val getShopItemByIdUseCase = GetShopItemByIdUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName : LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount : LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen


    fun getShopItem(itemId: Int) {
        viewModelScope.launch {
            val item = getShopItemByIdUseCase.getShopItem(itemId)
            _shopItem.value = item
        }
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            viewModelScope.launch {
                addShopItemUseCase.addShopItem(ShopItem(name, count, true))
                finishWork()
            }
        }
    }

    fun editShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            _shopItem.value?.let {
                viewModelScope.launch {
                    editShopItemUseCase.editShopItem(it.copy(name = name, count = count))
                    finishWork()
                }
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputName: String?): Int {
        return try {
            inputName?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int) : Boolean {
        var result = true;
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}