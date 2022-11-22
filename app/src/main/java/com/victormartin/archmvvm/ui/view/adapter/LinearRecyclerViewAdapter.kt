package com.victormartin.archmvvm.ui.view.adapter

import android.graphics.*
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.CircleCropTransformation
import com.victormartin.archmvvm.R
import com.victormartin.archmvvm.databinding.RvListItemBinding
import com.victormartin.archmvvm.databinding.RvLoadingItemBinding
import com.victormartin.archmvvm.domain.model.DataDomainModel
import com.victormartin.archmvvm.ui.view.listener.ItemClickListener
import com.victormartin.archmvvm.util.Constants

class LinearRecyclerViewAdapter(
    private val values: MutableList<DataDomainModel>,
    private val eastIcon: Bitmap,
    private val westIcon: Bitmap,
    private val itemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun addData(dataViews: MutableList<DataDomainModel>) {
        this.values.addAll(dataViews)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): DataDomainModel? {
        return values[position]
    }

    fun addLoadingView() {
        //add loading item
        Handler(Looper.getMainLooper()).postDelayed({
            values.add(DataDomainModel())
            notifyItemInserted(values.size - 1)
        }, 0)
    }

    fun removeLoadingView() {
        //remove loading item
        if (values.size != 0) {
            values.removeAt(values.size - 1)
            notifyItemRemoved(values.size)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == Constants.VIEW_TYPE_ITEM) {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.rv_list_item, parent, false)
            ItemViewHolder(view)
        } else {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.rv_loading_item, parent, false)
            val binding = RvLoadingItemBinding.bind(view)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                binding.progressBar.indeterminateDrawable.colorFilter =
                    BlendModeColorFilter(Color.WHITE, BlendMode.SRC_ATOP)
            } else {
                binding.progressBar.indeterminateDrawable.setColorFilter(
                    Color.WHITE,
                    PorterDuff.Mode.MULTIPLY
                )
            }
            LoadingViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (values[position] == null) {
            Constants.VIEW_TYPE_LOADING
        } else {
            Constants.VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == Constants.VIEW_TYPE_ITEM) {
            val binding = RvListItemBinding.bind(holder.itemView)

            val item = values[position]

            when (item.team.conference) {
                "East" ->
                    binding.playerAvatarImg.load(eastIcon) {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                        scale(Scale.FIT)
                    }
                "West" ->
                    binding.playerAvatarImg.load(westIcon) {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                        scale(Scale.FIT)
                    }
                else ->
                    binding.playerAvatarImg.load(
                        ResourcesCompat.getDrawable(
                            holder.itemView.context.resources,
                            R.mipmap.ic_launcher_foreground,
                            null
                        )
                    ) {
                        crossfade(true)
                        placeholder(R.mipmap.ic_launcher_foreground)
                        transformations(CircleCropTransformation())
                        scale(Scale.FIT)
                    }
            }

            binding.playerId.text = "${item.id}"
            binding.playerName.text = "${item.firstName} ${item.lastName}"
            binding.playerTeam.text = item.team.name ?: ""
            binding.playerPosition.text = item.position

            holder.itemView.setOnClickListener {
                itemClickListener.onItemClick(position, item)
            }
        }
    }
}