package ru.daniilxt.feature.main_screen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayoutMediator
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentMainScreenBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.favorite.presentation.FavoriteFragment
import ru.daniilxt.feature.main_screen.presentation.adapter.MainScreenViewPagerAdapter
import ru.daniilxt.feature.popular.presentation.PopularFragment

class MainScreenFragment : BaseFragment<MainScreenViewModel>(R.layout.fragment_main_screen) {

    private var _binding: FragmentMainScreenBinding? = null
    override val binding get() = requireNotNull(_binding)

    private val mainScreenViewPagerAdapter by lazy {
        MainScreenViewPagerAdapter(
            this, listOf(PopularFragment.newInstance(), FavoriteFragment.newInstance())
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
    }

    override fun setupViews() {
        super.setupViews()
        initViewPager()
    }

    private fun initViewPager() {
        binding.viewPager.adapter = mainScreenViewPagerAdapter
        val titles = listOf(getString(R.string.popular), getString(R.string.favourite))

        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .mainScreenComponentFactory()
            .create(this)
            .inject(this)
    }
}
