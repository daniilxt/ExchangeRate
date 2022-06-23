package ru.daniilxt.exchange_rate.di.app

import dagger.Module
import dagger.Provides
import ru.daniilxt.common.di.scope.ApplicationScope
import ru.daniilxt.feature.FeatureRouter
import ru.daniilxt.exchange_rate.navigation.Navigator
@Module
class NavigationModule {
    @ApplicationScope
    @Provides
    fun provideNavigator(): Navigator = Navigator()

    @ApplicationScope
    @Provides
    fun provideSplashRouter(navigator: Navigator): FeatureRouter = navigator
}
