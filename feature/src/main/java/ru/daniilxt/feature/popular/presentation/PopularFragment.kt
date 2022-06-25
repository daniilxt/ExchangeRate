package ru.daniilxt.feature.popular.presentation

import by.kirich1409.viewbindingdelegate.viewBinding
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentPopularBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.interactors.IUpdatable
import ru.daniilxt.feature.shared_adapter.CurrencyAdapter
import timber.log.Timber

class PopularFragment : BaseFragment<PopularViewModel>(R.layout.fragment_popular), IUpdatable {

    override val binding: FragmentPopularBinding by viewBinding(FragmentPopularBinding::bind)

    private val currencyAdapter by lazy {
        CurrencyAdapter {
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

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .popularComponentFactory()
            .create(this)
            .inject(this)
    }

    override fun update() {
        Timber.i("Update from popoular")
    }

    companion object {
        fun newInstance(): PopularFragment {
            return PopularFragment()
        }
    }
}
