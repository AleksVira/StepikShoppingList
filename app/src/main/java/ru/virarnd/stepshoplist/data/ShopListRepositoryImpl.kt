package ru.virarnd.stepshoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import ru.virarnd.stepshoplist.data.mapper.ShopListMapper
import ru.virarnd.stepshoplist.domain.ShopItem
import ru.virarnd.stepshoplist.domain.ShopListRepository

class ShopListRepositoryImpl(
    application: Application
) : ShopListRepository {

    private val shopListDao = AppDatabase.getInstance(application).shopListDao()

    private val mapper = ShopListMapper()

    override suspend fun addShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun deleteShopItem(item: ShopItem) {
        shopListDao.deleteShopItem(item.id)
    }

    override suspend fun editShopItem(item: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(item))
    }

    override suspend fun getShopItem(itemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(itemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> = shopListDao.getShopList().map {
        mapper.mapListDbModelToListEntity(it)
    }
}