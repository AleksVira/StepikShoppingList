package ru.virarnd.stepshoplist.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.virarnd.stepshoplist.R

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG: String = "MyTAG"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            Log.d(TAG, "ShopList: $it")
            shopListAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        shopListAdapter = ShopListAdapter()
        val rvShopList = findViewById<RecyclerView>(R.id.rv_shop_list).apply {
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(R.layout.item_shop_enabled, ShopListAdapter.MAX_POOL_SIZE)
            recycledViewPool.setMaxRecycledViews(R.layout.item_shop_disabled, ShopListAdapter.MAX_POOL_SIZE)
        }
        setListeners()

        val removeCallback = SwipeToDeleteCallback(shopListAdapter)
        ItemTouchHelper(removeCallback).attachToRecyclerView(rvShopList)

    }

    private fun setListeners() {
        shopListAdapter.shopItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
        shopListAdapter.shopItemClickListener = {
            Log.d(TAG, "setupRecyclerView: clicked ${it.name}")
        }
        shopListAdapter.shopListItemRemoveListener = {
            viewModel.deleteItem(it)
        }
    }

}