package ru.virarnd.stepshoplist.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import ru.virarnd.stepshoplist.R

class MainActivity : AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    companion object {
        private val TAG: String = "MyTAG"
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private var shopItemContainerView: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        shopItemContainerView = findViewById(R.id.shop_item_container)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            Log.d(TAG, "ShopList: $it")
            shopListAdapter.submitList(it)
        }
        val buttonAddItem = findViewById<FloatingActionButton>(R.id.button_add_shop_item)
        buttonAddItem.setOnClickListener {
            if (isVerticalLayout()) {
                startActivity(ShopItemActivity.newIntentAddItem(this))
            } else {
                launchItemFragment(ShopItemFragment.newInstanceAddItem())
            }
        }
    }

    private fun isVerticalLayout(): Boolean {
        return shopItemContainerView == null
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
            if (isVerticalLayout()) {
                startActivity(ShopItemActivity.newIntentEditItem(this, it.id))
            } else {
                launchItemFragment(ShopItemFragment.newInstanceEditItem(it.id))
            }
        }
        shopListAdapter.shopListItemRemoveListener = {
            viewModel.deleteItem(it)
        }
    }

    private fun launchItemFragment(newFragment: ShopItemFragment) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, newFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun onFragmentEditingFinished() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    override fun onEditingFinished() {
        onFragmentEditingFinished()
    }

}