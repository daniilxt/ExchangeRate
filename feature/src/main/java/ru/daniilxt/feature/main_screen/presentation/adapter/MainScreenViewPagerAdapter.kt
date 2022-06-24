package ru.daniilxt.feature.main_screen.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainScreenViewPagerAdapter(fragment: Fragment, private val fragments: List<Fragment>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        if (position >= itemCount) {
            throw IllegalStateException("Unsupported position for $TAG")
        }
        return fragments[position]
    }

    companion object {
        val TAG = MainScreenViewPagerAdapter::class.simpleName
    }
}
