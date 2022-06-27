package ru.daniilxt.feature.di

import dagger.BindsInstance
import dagger.Component
import ru.daniilxt.common.di.CommonApi
import ru.daniilxt.common.di.scope.FeatureScope
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.feature.favorite.di.FavoriteComponent
import ru.daniilxt.feature.main_screen.di.MainScreenComponent
import ru.daniilxt.feature.popular.di.PopularComponent

@Component(
    dependencies = [
        FeatureDependencies::class,
    ],
    modules = [
        FeatureModule::class,
        FeatureDataModule::class,
        RoomDatabaseModule::class,
    ]
)
@FeatureScope
interface FeatureComponent {

    fun mainScreenComponentFactory(): MainScreenComponent.Factory
    fun popularComponentFactory(): PopularComponent.Factory
    fun favoriteComponentFactory(): FavoriteComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance featureRouter: FeatureRouter,
            deps: FeatureDependencies
        ): FeatureComponent
    }

    @Component(
        dependencies = [
            CommonApi::class,
        ]
    )
    interface FeatureDependenciesComponent : FeatureDependencies
}
