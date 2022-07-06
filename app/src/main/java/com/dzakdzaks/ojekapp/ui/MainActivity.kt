package com.dzakdzaks.ojekapp.ui

import android.os.Bundle
import com.dzakdzaks.ojekapp.data.local.DataPreferences
import com.dzakdzaks.ojekapp.data.local.entity.User
import com.dzakdzaks.ojekapp.databinding.ActivityMainBinding
import com.dzakdzaks.ojekapp.event.StateEventSubscriber
import org.koin.androidx.scope.ScopeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ScopeActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        DataPreferences.get.remove(DataPreferences.Key.TOKEN)

        binding.run {
            btnCustomerRegister.setOnClickListener {
                viewModel.registerCustomer(etCustomerRegisterUsername.text.toString(), etCustomerRegisterPassword.text.toString())
            }
            btnCustomerLogin.setOnClickListener {
                viewModel.loginCustomer(etCustomerLoginUsername.text.toString(), etCustomerLoginPassword.text.toString())
            }
            btnGetCustomer.setOnClickListener {
                viewModel.getCustomer()
            }

            btnDriverRegister.setOnClickListener {
                viewModel.registerDriver(etDriverRegisterUsername.text.toString(), etDriverRegisterPassword.text.toString())
            }
            btnDriverLogin.setOnClickListener {
                viewModel.loginDriver(etDriverLoginUsername.text.toString(), etDriverLoginPassword.text.toString())
            }
            btnGetDriver.setOnClickListener {
                viewModel.getDriver()
            }
        }


        viewModel.subscribeRegisterCustomer(subscribeRegisterCustomer())
        viewModel.subscribeLoginCustomer(subscribeLoginCustomer())
        viewModel.subscribeGetCustomer(subscribeGetCustomer())

        viewModel.subscribeRegisterDriver(subscribeRegisterDriver())
        viewModel.subscribeLoginDriver(subscribeLoginDriver())
        viewModel.subscribeGetDriver(subscribeGetDriver())
    }

    // Customer Subscribers
    private fun subscribeRegisterCustomer() = object : StateEventSubscriber<Boolean> {
        override fun onIdle() {
            binding.tvResultCustomer.append("idle register customer...\n")
        }

        override fun onLoading() {
            binding.tvResultCustomer.append("onloading register customer...\n")
        }

        override fun onFailure(throwable: Throwable) {
            binding.tvResultCustomer.append("${throwable.message} onFailure register customer...\n")
        }

        override fun onSuccess(data: Boolean) {
            binding.tvResultCustomer.append("$data onSuccess register customer...\n")
        }

    }

    private fun subscribeLoginCustomer() = object : StateEventSubscriber<User> {
        override fun onIdle() {
            binding.tvResultCustomer.append("idle login customer...\n")
        }

        override fun onLoading() {
            binding.tvResultCustomer.append("onloading login customer...\n")
        }

        override fun onFailure(throwable: Throwable) {
            binding.tvResultCustomer.append("${throwable.message} onFailure login customer...\n")
        }

        override fun onSuccess(data: User) {
            viewModel.saveCustomerToken(data.token)
            binding.tvResultCustomer.append("$data onSuccess login customer...\n")
        }

    }

    private fun subscribeGetCustomer() = object : StateEventSubscriber<User> {
        override fun onIdle() {
            binding.tvResultCustomer.append("idle get customer...\n")
        }

        override fun onLoading() {
            binding.tvResultCustomer.append("onloading get customer...\n")
        }

        override fun onFailure(throwable: Throwable) {
            binding.tvResultCustomer.append("${throwable.message} onFailure get customer...\n")
        }

        override fun onSuccess(data: User) {
            binding.tvResultCustomer.append("$data onSuccess get customer...\n")
        }

    }


    // Driver Subscribers
    private fun subscribeRegisterDriver() = object : StateEventSubscriber<Boolean> {
        override fun onIdle() {
            binding.tvResultDriver.append("idle register driver...\n")
        }

        override fun onLoading() {
            binding.tvResultDriver.append("onloading register driver...\n")
        }

        override fun onFailure(throwable: Throwable) {
            binding.tvResultDriver.append("${throwable.message} onFailure register driver...\n")
        }

        override fun onSuccess(data: Boolean) {
            binding.tvResultDriver.append("$data onSuccess register driver...\n")
        }

    }

    private fun subscribeLoginDriver() = object : StateEventSubscriber<User> {
        override fun onIdle() {
            binding.tvResultDriver.append("idle login driver...\n")
        }

        override fun onLoading() {
            binding.tvResultDriver.append("onloading login driver...\n")
        }

        override fun onFailure(throwable: Throwable) {
            binding.tvResultDriver.append("${throwable.message} onFailure login driver...\n")
        }

        override fun onSuccess(data: User) {
            viewModel.saveDriverToken(data.token)
            binding.tvResultDriver.append("$data onSuccess login driver...\n")
        }

    }

    private fun subscribeGetDriver() = object : StateEventSubscriber<User> {
        override fun onIdle() {
            binding.tvResultDriver.append("idle get driver...\n")
        }

        override fun onLoading() {
            binding.tvResultDriver.append("onloading get driver...\n")
        }

        override fun onFailure(throwable: Throwable) {
            binding.tvResultDriver.append("${throwable.message} onFailure get driver...\n")
        }

        override fun onSuccess(data: User) {
            binding.tvResultDriver.append("$data onSuccess get driver...\n")
        }

    }
}