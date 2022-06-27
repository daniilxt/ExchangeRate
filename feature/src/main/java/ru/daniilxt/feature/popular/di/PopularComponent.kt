package ru.daniilxt.feature.popular.di

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.popular.presentation.PopularFragment
import ru.daniilxt.feature.shared_view_model.di.SharedViewModelModule

@Subcomponent(
    modules = [
        SharedViewModelModule::class,
    ]
)
@ScreenScope
interface PopularComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): PopularComponent
    }

    fun inject(popularFragment: PopularFragment)
}
