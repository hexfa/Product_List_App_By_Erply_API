package com.pim.feature.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pim.R
import com.pim.data.model.ProductWithImage
import com.pim.data.model.product_list.Name
import com.pim.databinding.ItemProductBinding
import com.pim.databinding.ListItemLoadingBinding
import com.squareup.picasso.Picasso

class ProductAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mData: MutableList<ProductWithImage> = mutableListOf()
    private val loading = 0
    private val item = 1
    private var isLoadingAdded = false


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        var viewHolder: RecyclerView.ViewHolder? = null

        when (viewType) {
            item -> {
                val binding: ItemProductBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_product,
                    parent,
                    false
                )
                viewHolder = ProductViewHolder(binding)
            }
            loading -> {
                val binding: ListItemLoadingBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.list_item_loading, parent, false
                )
                viewHolder = LoadingVH(binding)
            }
        }
        return viewHolder!!
    }


    fun addAll(data: List<ProductWithImage>) {
        for (photo in data) {
            add(photo)
        }
    }

    private fun add(photo: ProductWithImage) {
        mData.add(photo)
        notifyItemInserted(mData.size - 1)
    }

    class ProductViewHolder(private var mBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(
            mBinding.root
        ) {
        @SuppressLint("SetTextI18n")
        fun bind(product: ProductWithImage) {
            mBinding.txtTitle.text = product.name.en
            mBinding.txtPrice.text = "Price : \$ ${product.price}"
            mBinding.txtCost.text = "Cost : \$ ${product.cost}"

            if (product.url != null) {
                Log.d("test", product.url)
                Picasso.get()
                    .load(product.url)
                    .placeholder(R.drawable.ic_baseline_bubble_chart_24)
                    .into(mBinding.productImage)
            } else {
                mBinding.productImage.setImageResource(R.drawable.ic_baseline_bubble_chart_24)
            }
        }
    }


    class LoadingVH(binding: ListItemLoadingBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == mData.size - 1 && isLoadingAdded) loading else item
    }

    fun addLoadingFooter() {
        isLoadingAdded = true
        add(ProductWithImage(Name(""), 0, 0.0, 0, ""))
    }


    fun removeLoadingFooter() {
        isLoadingAdded = false
        val position: Int = mData.size - 1
        if (position > 0) {
            mData.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val photo = mData[position]
        when (getItemViewType(position)) {
            item -> {
                val photoViewHolder: ProductViewHolder = holder as ProductViewHolder
                photo.let { photoViewHolder.bind(it) }
            }


        }

    }

}