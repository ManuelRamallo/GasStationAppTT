package com.mramallo.gasstationapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.mramallo.gasstationapp.R
import com.mramallo.gasstationapp.databinding.ActivityMainBinding
import com.mramallo.gasstationapp.ui.gasStationDetail.GasStationDetailFragment
import com.mramallo.gasstationapp.ui.gastStations.GasStationsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), GasStationDetailFragment.OnGasStationDetailFragment, GasStationsFragment.OnGasStationFragment {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbarTop)
        onChangeToolbarTitle(getString(R.string.main_title_toolbar), false)

        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment

    }

    override fun onChangeToolbarTitle(title: String?, showBack: Boolean) {
        binding.toolbarTop.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(showBack)
        if(!showBack) binding.toolbarTop.titleMarginStart = 250 else binding.toolbarTop.titleMarginStart = 0
        if(showBack) binding.toolbarTop.setNavigationIcon(R.mipmap.ic_back_foreground)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}