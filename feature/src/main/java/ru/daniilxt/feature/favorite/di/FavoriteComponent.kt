package ru.daniilxt.feature.favorite.di
import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import ru.daniilxt.common.di.scope.ScreenScope
import ru.daniilxt.feature.favorite.presentation.FavoriteFragment

@Subcomponent(
    modules = [
        FavoriteModule::class,
    ]
)
@ScreenScope
interface FavoriteComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance fragment: Fragment): FavoriteComponent
    }

    fun inject(favoriteFragment: FavoriteFragment)
}
