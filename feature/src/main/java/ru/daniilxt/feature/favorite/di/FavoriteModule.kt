package ru.daniilxt.feature.favorite.di

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.viewmodel.ViewModelKey
import ru.daniilxt.common.di.viewmodel.ViewModelModule
import ru.daniilxt.feature.database.repository.FavoriteCurrencyRepository
import ru.daniilxt.feature.domain.usecase.GetCurrencyListUseCase
import ru.daniilxt.feature.shared_view_model.SharedCurrencyViewModel

@Module(
    includes = [
        ViewModelModule::class
    ]
)
class FavoriteModule {

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
}
