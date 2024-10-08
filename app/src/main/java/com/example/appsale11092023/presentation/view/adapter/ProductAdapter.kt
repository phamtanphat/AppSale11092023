package com.example.appsale11092023.presentation.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.appsale11092023.R
import com.example.appsale11092023.common.AppCommon
import com.example.appsale11092023.data.model.Product
import com.example.appsale11092023.util.StringUtils

class ProductAdapter(
    private var context: Context? = null
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var listProduct: List<Product?> = emptyList()

    fun setListProduct(listProduct: List<Product?>) {
        if (listProduct.isEmpty()) return
        this.listProduct = listProduct
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var imageView: ImageView? = null
        private var textViewName: TextView? = null
        private var textViewAddress: TextView? = null
        private var textViewPrice: TextView? = null

        init {
            imageView = view.findViewById(R.id.image_view_product)
            textViewName = view.findViewById(R.id.text_view_product_name)
            textViewAddress = view.findViewById(R.id.text_view_product_address)
            textViewPrice = view.findViewById(R.id.text_view_product_price)
        }

        fun bind(context: Context, product: Product?) {
            product?.let {
                imageView?.let {
                    Glide.with(context)
                        .load(AppCommon.BASE_URL + product.image)
                        .placeholder(R.drawable.ic_logo)
                        .into(it)
                }

                textViewName?.text = it.name
                textViewAddress?.text = it.address
                textViewPrice?.text = String.format(
                    "Giá: %s VND",
                    StringUtils.formatCurrency(product.price ?: 0)
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.layout_product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        context?.let {
            holder.bind(it, listProduct.getOrNull(position))
        }
    }

    override fun getItemCount(): Int {
        return listProduct.size
    }
}