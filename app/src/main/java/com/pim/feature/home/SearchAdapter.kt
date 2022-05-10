package com.pim.feature.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.pim.R
import com.pim.data.model.ProductWithImage
import com.pim.databinding.ItemProductBinding
import com.squareup.picasso.Picasso

class SearchAdapter :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    var set: Set<String> = HashSet()
    private var mData: List<ProductWithImage>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ProductWithImage>?) {
        mData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val binding: ItemProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photo = mData?.get(position)
        photo?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return if (mData != null) {
            mData!!.size

        } else {
            0
        }
    }

    inner class ViewHolder(private var mBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(
            mBinding.root
        ) {
        @SuppressLint("SetTextI18n")
        fun bind(product: ProductWithImage) {
            mBinding.txtTitle.text = product.name.en
            mBinding.txtPrice.text = "Price : ${product.price}"
            mBinding.txtCost.text = "Cost : ${product.cost}"
            if (product.url != null) {
                Picasso.get()
                    .load(product.url)
                    .placeholder(R.drawable.ic_baseline_bubble_chart_24)
                    .into(mBinding.productImage)
            } else {
                mBinding.productImage.setImageResource(R.drawable.ic_baseline_bubble_chart_24)
            }
        }
    }

}