package ru.daniilxt.exchange_rate.di.deps

import dagger.Binds
import dagger.Module
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import ru.daniilxt.common.di.FeatureApiHolder
import ru.daniilxt.common.di.FeatureContainer
import ru.daniilxt.common.di.scope.ApplicationScope
import ru.daniilxt.exchange_rate.App
import ru.daniilxt.exchange_rate.root.di.RootApi
import ru.daniilxt.exchange_rate.root.di.RootFeatureHolder
import ru.daniilxt.feature.di.FeatureApi
import ru.daniilxt.feature.di.FeatureFeatureHolder

@Module
interface ComponentHolderModule {

    @ApplicationScope
    @Binds
    fun provideFeatureContainer(application: App): FeatureContainer

    @ApplicationScope
    @Binds
    @ClassKey(RootApi::class)
    @IntoMap
    fun provideMainFeature(rootFeatureHolder: RootFeatureHolder): FeatureApiHolder

    @ApplicationScope
    @Binds
    @ClassKey(FeatureApi::class)
    @IntoMap
    fun provideFeatureFeature(featureHolder: FeatureFeatureHolder): FeatureApiHolder
}
