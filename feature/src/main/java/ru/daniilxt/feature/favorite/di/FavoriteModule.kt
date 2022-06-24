package ru.daniilxt.feature.favorite.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.favorite.presentation.FavoriteViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class FavoriteModule {

    @Provides
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter
    ): ViewModel {
        return FavoriteViewModel(
            navigator
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): FavoriteViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(FavoriteViewModel::class.java)
    }
}
