package com.tutorial.foody.ui.fragments.favorite.adapters

import android.view.*
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tutorial.foody.R
import com.tutorial.foody.data.database.entities.FavoriteRecipeEntity
import com.tutorial.foody.databinding.FavoriteRecipeRowLayoutBinding
import com.tutorial.foody.ui.DiffUtils
import com.tutorial.foody.ui.fragments.favorite.FavoriteRecipesFragmentDirections

class FavoriteRecipeAdapter(
    private val requireActivity: FragmentActivity
) :
    RecyclerView.Adapter<FavoriteRecipeAdapter.FavoriteRecipeViewHolder>(), ActionMode.Callback {

    private var favoriteRecipes = emptyList<FavoriteRecipeEntity>()

    fun setFavoriteRecipes(newFavoriteRecipes: List<FavoriteRecipeEntity>) {
        val favoriteRecipesDiffUtil = DiffUtils(favoriteRecipes, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteRecipesDiffUtil)
        favoriteRecipes = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteRecipeViewHolder =
        FavoriteRecipeViewHolder.from(parent)

    override fun onBindViewHolder(holder: FavoriteRecipeViewHolder, position: Int) {
        holder.bind(favoriteRecipes[position])
        holder.itemView.setOnLongClickListener {
            requireActivity.startActionMode(this)
            true
        }
    }

    override fun getItemCount(): Int = favoriteRecipes.size

    class FavoriteRecipeViewHolder(
        val binding: FavoriteRecipeRowLayoutBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(favoriteRecipe: FavoriteRecipeEntity) {
            binding.favoriteRecipe = favoriteRecipe
            binding.executePendingBindings()

            /**
             * Single Click Listener
             */
            itemView.setOnClickListener {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentToRecipeDetailsActivity(
                        favoriteRecipe.recipeResult
                    )
                it.findNavController().navigate(action)
            }
        }

        companion object {
            fun from(viewGroup: ViewGroup): FavoriteRecipeViewHolder {
                val inflater = LayoutInflater.from(viewGroup.context)
                val binding = FavoriteRecipeRowLayoutBinding.inflate(inflater, viewGroup, false)
                return FavoriteRecipeViewHolder(binding)
            }
        }

    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.favorite_contextual_menu, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(mode: ActionMode?) {
    }
}