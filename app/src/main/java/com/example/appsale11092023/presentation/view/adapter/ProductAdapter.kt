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

class ProductAdapter(
    private var context: Context? = null
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {
    private var listProduct: List<Product?> = emptyList()

    fun setListProduct(listProduct: List<Product?>) {
        this.listProduct = listProduct
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var imageView: ImageView = view.findViewById(R.id.imageView)
        private var textViewName: TextView = view.findViewById(R.id.textViewName)
        private var textViewAddress: TextView = view.findViewById(R.id.textViewAddress)
        private var textViewPrice: TextView = view.findViewById(R.id.textViewPrice)

        fun bind(context: Context, product: Product?) {
            product?.let {
                Glide.with(context)
                    .load(AppCommon.BASE_URL + product.image)
                    .placeholder(R.drawable.ic_logo)
                    .into(imageView)
                textViewName.text = it.name
                textViewAddress.text = it.address
                textViewPrice.text = it.price.toString()
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