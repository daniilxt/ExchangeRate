package ru.daniilxt.feature.favorite.presentation

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentFavoriteBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.domain.model.FilterType
import ru.daniilxt.feature.interactors.IUpdatable
import timber.log.Timber

class FavoriteFragment : BaseFragment<FavoriteViewModel>(R.layout.fragment_favorite), IUpdatable {

    override val binding: FragmentFavoriteBinding by viewBinding(FragmentFavoriteBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .favoriteComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun update(currencyName: String) {
        Timber.i("Update from favorite")
    }

    override fun filterBy(filterType: FilterType) {
    }

    companion object {
        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }
}
