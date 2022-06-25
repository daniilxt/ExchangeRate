package ru.daniilxt.feature.popular.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.domain.usecase.GetCurrencyListUseCase
import ru.daniilxt.feature.popular.presentation.PopularViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class PopularModule {

    @Provides
    @IntoMap
    @ViewModelKey(PopularViewModel::class)
    fun provideViewModel(
        navigator: FeatureRouter,
        getCurrencyListUseCase: GetCurrencyListUseCase
    ): ViewModel {
        return PopularViewModel(
            navigator, getCurrencyListUseCase
        )
    }

    @Provides
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): PopularViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(PopularViewModel::class.java)
    }
}
