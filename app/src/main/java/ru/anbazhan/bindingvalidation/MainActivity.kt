package ru.anbazhan.bindingvalidation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import ru.anbazhan.bindingvalidation.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        binding.lifecycleOwner = this
        val viewModel = ViewModelProvider(this, ViewModelFactory()).get(SimpleViewModel::class.java)
        viewModel.initData()
        binding.viewModel = viewModel
    }
}