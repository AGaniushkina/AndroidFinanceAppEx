package com.example.androidfinanceappex.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidfinanceappex.R
import com.example.androidfinanceappex.databinding.RecyclerViewTransactionListItemBinding
import com.example.androidfinanceappex.domain.TransactionEntity
import com.example.androidfinanceappex.domain.enums.TransactionCategory
import com.example.androidfinanceappex.domain.enums.TransactionType
import com.example.androidfinanceappex.presentation.extensions.dateToString
import java.util.*

open class Resources
class TransactionsAdapter(
    private val transactions: List<TransactionEntity>
) : RecyclerView.Adapter<TransactionsAdapter.ViewHolder>()
{
    class ViewHolder(val binding: RecyclerViewTransactionListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerViewTransactionListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            val transaction = transactions[position]
            when (transaction.type) {
                TransactionType.Income -> {
                    tvAmount.setTextColor(
                        ContextCompat.getColor(holder.itemView.context, R.color.green)
                    )
                    tvAmount.text = "+Р${transaction.amount}"
                }
                TransactionType.Expense -> {
                    tvAmount.setTextColor(
                        ContextCompat.getColor(holder.itemView.context, R.color.red)
                    )
                    tvAmount.text = "-Р${transaction.amount}"
                }
            }

            if (!transaction.imagePath.isNullOrEmpty()) {
                Glide.with(holder.itemView.context)
                    .load(transaction.imagePath)
                    .centerCrop()
                    .into(ivItem)
            }
            else{
                when (transaction.category) {
                    TransactionCategory.FastFood -> {
                        ivItem.background = ResourcesCompat.getDrawable(this.root.resources, R.drawable.fastfood, null)
                    }
                    TransactionCategory.Business -> {
                        ivItem.background = ResourcesCompat.getDrawable(this.root.resources, R.drawable.business, null)
                    }
                    TransactionCategory.Entertainment -> {
                        ivItem.background = ResourcesCompat.getDrawable(this.root.resources, R.drawable.entertainment, null)
                    }
                    TransactionCategory.Gifts -> {
                        ivItem.background = ResourcesCompat.getDrawable(this.root.resources, R.drawable.gifts, null)
                    }
                    TransactionCategory.Grocery -> {
                        ivItem.background = ResourcesCompat.getDrawable(this.root.resources, R.drawable.grocery, null)
                    }
                    TransactionCategory.Remittances -> {
                        ivItem.background = ResourcesCompat.getDrawable(this.root.resources, R.drawable.remittances, null)
                    }
                    TransactionCategory.Transport -> {
                        ivItem.background = ResourcesCompat.getDrawable(this.root.resources, R.drawable.transport, null)
                    }
                }
            }
            tvDate.text = Date(transaction.createdAt).dateToString("dd MMMM")
            tvTitle.text = transaction.name
            tvCategory.text= transaction.category.name
        }
    }

    override fun getItemCount(): Int {
        return transactions.count()
    }
}