package ru.daniilxt.feature.main_screen.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
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
import ru.daniilxt.feature.interactors.IUpdatable
import ru.daniilxt.feature.main_screen.presentation.adapter.MainScreenViewPagerAdapter
import ru.daniilxt.feature.popular.presentation.PopularFragment

class MainScreenFragment : BaseFragment<MainScreenViewModel>(R.layout.fragment_main_screen) {

    private var _binding: FragmentMainScreenBinding? = null
    override val binding get() = requireNotNull(_binding)

    private val currentViewPagerFrg: Fragment?
        get() {
            return childFragmentManager.findFragmentByTag(FRAGMENT_TAG + binding.viewPager.currentItem)
        }

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

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.currencyTitles.observe {
            setSpinnerListAdapter(binding.spinnerCurrency.spinnerText, it)
        }
    }

    private fun initViewPager() {
        binding.viewPager.adapter = mainScreenViewPagerAdapter
        val titles = listOf(getString(R.string.popular), getString(R.string.favourite))

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun setSpinnerListAdapter(spinner: AutoCompleteTextView, data: List<String>) {
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_text_item, data)
        spinner.setText(data.first())
        spinner.setAdapter(adapter)
        binding.spinnerCurrency.spinnerText.addTextChangedListener(beforeTextChanged = { _, _, _, _ ->
            // Call update rv in view pager child frg
            (currentViewPagerFrg as? IUpdatable)?.update()
        })
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .mainScreenComponentFactory()
            .create(this)
            .inject(this)
    }

    companion object {
        private const val FRAGMENT_TAG = "f"
    }
}
