package ru.daniilxt.feature.main_screen.presentation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.daniilxt.common.base.BaseFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.common.extensions.setLightStatusBar
import ru.daniilxt.common.extensions.setStatusBarColor
import ru.daniilxt.common.extensions.showDialog
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentMainScreenBinding
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureComponent
import ru.daniilxt.feature.dialogs.FilterDialogFragment
import ru.daniilxt.feature.favorite.presentation.FavoriteFragment
import ru.daniilxt.feature.interactors.IUpdatable
import ru.daniilxt.feature.main_screen.presentation.adapter.MainScreenViewPagerAdapter
import ru.daniilxt.feature.popular.presentation.PopularFragment

class MainScreenFragment : BaseFragment<MainScreenViewModel>(R.layout.fragment_main_screen) {

    override val binding: FragmentMainScreenBinding by viewBinding(FragmentMainScreenBinding::bind)

    // Gets the current child fragment in the view pager
    private val currentViewPagerFrg: Fragment?
        get() {
            return childFragmentManager.findFragmentByTag(
                FRAGMENT_TAG + binding.viewPager.currentItem
            )
        }

    private val mainScreenViewPagerAdapter by lazy {
        MainScreenViewPagerAdapter(
            this, listOf(PopularFragment.newInstance(), FavoriteFragment.newInstance())
        )
    }

    private val viewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            (currentViewPagerFrg as? IUpdatable)?.load(binding.spinnerCurrency.spinnerText.text.toString())
        }
    }

    private val filterDialog by lazy {
        FilterDialogFragment.newInstance().apply {
            setOnOkClickListener {
                (currentViewPagerFrg as? IUpdatable)?.filterBy(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().setStatusBarColor(R.color.white)
        requireView().setLightStatusBar()
        requireActivity().window.navigationBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)
    }

    override fun setupViews() {
        super.setupViews()
        initViewPager()
        setupButtons()
    }

    override fun setupViewModelSubscriber() {
        super.setupViewModelSubscriber()
        viewModel.currencyTitles.observe {
            if (it.isNotEmpty()) {
                setupSpinnerListAdapter(binding.spinnerCurrency.spinnerText, it)
            }
        }
    }

    override fun inject() {
        FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
            .mainScreenComponentFactory()
            .create(this)
            .inject(this)
    }

    private fun initViewPager() {
        binding.viewPager.adapter = mainScreenViewPagerAdapter
        binding.viewPager.registerOnPageChangeCallback(viewPagerCallback)

        val titles = listOf(getString(R.string.popular), getString(R.string.favourite))

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun setupButtons() {
        binding.mbFilter.setDebounceClickListener {
            parentFragmentManager.showDialog(filterDialog)
        }
    }

    private fun setupSpinnerListAdapter(spinner: AutoCompleteTextView, data: List<String>) {
        binding.spinnerCurrency.spinnerText.addTextChangedListener(beforeTextChanged = { _, _, _, _ ->
            // Call update rv in view pager child frg
            lifecycleScope.launch {
                delay(100L)
                (currentViewPagerFrg as? IUpdatable)?.update(binding.spinnerCurrency.spinnerText.text.toString())
            }
        })
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_text_item, data)
        spinner.setText(data.first())
        spinner.setAdapter(adapter)
    }

    companion object {
        private const val FRAGMENT_TAG = "f"
    }
}
