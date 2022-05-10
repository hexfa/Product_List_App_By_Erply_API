package com.pim.feature.home

import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pim.data.InfoContainer
import com.pim.data.model.ProductWithImage
import com.pim.data.model.image.ImageList
import com.pim.data.model.product_list.ProductList
import com.pim.data.repo.ImageRepository
import com.pim.data.repo.ProductRepository
import com.pim.feature.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val productRepository: ProductRepository,
) : AppViewModel() {
    private val mTAG = "HomeViewModel"

    private val _firstPageList: MutableLiveData<List<ProductWithImage>> = MutableLiveData()
    val firstPageList: LiveData<List<ProductWithImage>> = _firstPageList
    private val _nextPageList: MutableLiveData<List<ProductWithImage>> = MutableLiveData()
    val nextPageList: LiveData<List<ProductWithImage>> = _nextPageList

    private val _searchList = MutableLiveData<List<ProductWithImage>>()
    val searchList: LiveData<List<ProductWithImage>> = _searchList

    var searchArray = mutableListOf<ProductWithImage>()

    init {
        InfoContainer.token?.let {
            productRepository.getProductList(it, "0", "10")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : SingleObserver<ProductList> {
                    override fun onSubscribe(disposable: Disposable) {
                        compositeDisposable.add(disposable)
                    }

                    override fun onSuccess(list: ProductList) {
                        createProductWithImage(
                            list, InfoContainer.token!!,
                            isFirstPage = true,
                            isForSearch = false
                        )
                    }

                    override fun onError(throwable: Throwable) {
                        Log.e("page", throwable.message.toString())
                    }

                })
        }
    }

    fun getProductList(token: String, skip: String, take: String) {
        productRepository.getProductList(token, skip, take)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ProductList> {
                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(list: ProductList) {

                    createProductWithImage(list, token, isFirstPage = false, isForSearch = false)
                }

                override fun onError(throwable: Throwable) {

                    Log.e(mTAG, throwable.message.toString())
                }

            })
    }

    fun getProductListSearch(filter: String, token: String, skip: String, take: String) {

        productRepository.getProductListSearch(filter, token, skip, take)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ProductList> {
                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(list: ProductList) {

                    createProductWithImage(list, token, isFirstPage = false, isForSearch = true)
                }

                override fun onError(throwable: Throwable) {
                    Log.e(mTAG, throwable.message.toString())
                }

            })
    }


/*    fun getProductListSort(sort: String, token: String, skip: String, take: String) {
        productRepository.getProductListSort(sort, token, skip, take)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ProductList> {
                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(list: ProductList) {
                    createProductWithImage(list, token, false,false)
                }

                override fun onError(throwable: Throwable) {
                    Log.e(mTAG, throwable.message.toString())
                }

            })
    }*/

    private fun createProductWithImage(
        list: ProductList,
        token: String,
        isFirstPage: Boolean,
        isForSearch: Boolean

    ) {
        var productIds = ""
        list.forEach {
            productIds = productIds + it.id.toString() + ","

        }
        Log.d("search onSuccess***************", productIds)

        getProductsImage(token, productIds, list, isFirstPage, isForSearch)
    }

    private fun getProductsImage(
        token: String,
        productIds: String,
        list: ProductList,
        isFirstPage: Boolean,
        isForSearch: Boolean
    ) {
        imageRepository.getImage(token, productIds)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ImageList> {
                override fun onSubscribe(disposable: Disposable) {
                    compositeDisposable.add(disposable)
                }

                override fun onSuccess(listImage: ImageList) {
                    Log.d("search onSuccess***************", list.toString())

                    val listProductWithImage: List<ProductWithImage> = list.map {
                        val image = listImage.images.find { image ->
                            image.productId == it.id
                        }
                        var url: String? = null
                        if (image != null) {
                            url =
                                "https://cdn-sb.erply.com/images/${InfoContainer.clientCode}/${image.key}?width=220&amp;height=240"
                        }
                        Log.d(
                            "search onSuccess#####################",
                            "${it.name},${it.id},${it.price},${it.cost},${url}"
                        )

                        ProductWithImage(it.name, it.id, it.price, it.cost, url)
                    }
                    when {
                        isFirstPage -> {
                            _firstPageList.value = listProductWithImage
                        }
                        isForSearch -> {
                            _searchList.value = listProductWithImage
                            searchArray.addAll(listProductWithImage)
                        }
                        else -> {
                            Log.d("search else", listProductWithImage.toString())

                            _nextPageList.value = listProductWithImage
                        }
                    }
                }

                override fun onError(throwable: Throwable) {
                    Log.e(mTAG, throwable.message.toString())
                }

            })
    }

    fun isTablet(context: Context): Boolean {
        return ((context.resources.configuration.screenLayout
                and Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE)
    }
}