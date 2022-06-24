package ru.daniilxt.feature.popular.presentation

import by.kirich1409.viewbindingdelegate.viewBinding
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentPopularBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent

class PopularFragment : BaseFragment<PopularViewModel>(R.layout.fragment_popular) {

    override val binding: FragmentPopularBinding by viewBinding(FragmentPopularBinding::bind)

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
