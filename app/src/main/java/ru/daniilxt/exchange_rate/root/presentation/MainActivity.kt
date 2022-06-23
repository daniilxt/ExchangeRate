package ru.daniilxt.exchange_rate.root.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import ru.daniilxt.common.di.FeatureUtils
import ru.daniilxt.exchange_rate.R
import ru.daniilxt.exchange_rate.databinding.ActivityMainBinding
import ru.daniilxt.exchange_rate.navigation.Navigator
import ru.daniilxt.exchange_rate.root.di.RootApi
import ru.daniilxt.exchange_rate.root.di.RootComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var activityViewModel: MainActivityViewModel

    @Inject
    lateinit var navigator: Navigator

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inject()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val navController = navHostFragment.navController
        navigator.attach(navController, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        navigator.detach()
    }

    private fun inject() {
        FeatureUtils.getFeature<RootComponent>(this, RootApi::class.java)
            .mainActivityComponentFactory()
            .create(this)
            .inject(this)
    }
}
