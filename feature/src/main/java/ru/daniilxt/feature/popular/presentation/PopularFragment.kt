package ru.daniilxt.feature.popular.presentation

import by.kirich1409.viewbindingdelegate.viewBinding
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentPopularBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.domain.model.FilterType
import ru.daniilxt.feature.interactors.IUpdatable
import ru.daniilxt.feature.shared_adapter.CurrencyAdapter
import ru.daniilxt.feature.shared_view_model.SharedCurrencyViewModel

class PopularFragment :
    BaseFragment<SharedCurrencyViewModel>(R.layout.fragment_popular), IUpdatable {

    override val binding: FragmentPopularBinding by viewBinding(FragmentPopularBinding::bind)

    private val currencyAdapter by lazy {
        CurrencyAdapter {
            viewModel.changeFavoriteState(it)
        }
    }

    override fun setupViews() {
        super.setupViews()
        binding.rvPopular.adapter = currencyAdapter
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.currencyList.observe {
            currencyAdapter.bindData(it)
        }
    }

    override fun update(currencyName: String) {
        viewModel.updateCurrencyInfo(currencyName)
    }

    override fun load(currencyName: String) {
        viewModel.loadCurrencyInfo(currencyName)
    }

    override fun filterBy(filterType: FilterType) {
        viewModel.filterBy(filterType)
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .popularComponentFactory()
            .create(this)
            .inject(this)
    }

    companion object {
        fun newInstance(): PopularFragment {
            return PopularFragment()
        }
    }
}
