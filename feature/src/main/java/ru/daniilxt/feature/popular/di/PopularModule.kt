package ru.daniilxt.feature.popular.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.database.repository.FavoriteCurrencyRepository
import ru.daniilxt.feature.domain.usecase.GetCurrencyListUseCase
import ru.daniilxt.feature.shared_view_model.SharedCurrencyViewModel
import javax.inject.Singleton

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class PopularModule {

    @Provides
    @IntoMap
    @ViewModelKey(SharedCurrencyViewModel::class)
    fun provideViewModel(
        repository: FavoriteCurrencyRepository,
        getCurrencyListUseCase: GetCurrencyListUseCase
    ): ViewModel {
        return SharedCurrencyViewModel(
            repository, getCurrencyListUseCase
        )
    }

    @Provides
    @Singleton
    fun provideViewModelCreator(
        fragment: Fragment,
        viewModelFactory: ViewModelProvider.Factory
    ): SharedCurrencyViewModel {
        return ViewModelProvider(fragment, viewModelFactory).get(SharedCurrencyViewModel::class.java)
    }
}
