package com.example.marketplace.fragments

import LoginViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.MyApplication
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.LoginViewModelFactory
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch


class LoginFragment : Fragment() {
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var editText1: EditText
    private lateinit var editText2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val factory = LoginViewModelFactory(this.requireContext(), Repository())
        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)
        editText1 = view.findViewById(R.id.edittext_name_login_fragment)
        editText2 = view.findViewById(R.id.edittext_password_login_fragment)
        val forgotPassword: TextView = view.findViewById(R.id.textView_login_fragment_click_here)
        val registerButton: Button = view.findViewById(R.id.button_login_fragment_sign_up)
        val login: Button = view.findViewById(R.id.button_login_fragment)
        forgotPassword.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
        }
        registerButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        login.setOnClickListener {
            if (checkTextViews()) {
                loginViewModel.user.value.let {
                    if (it != null) {
                        it.username = editText1.text.toString()
                    }
                    if (it != null) {
                        it.password = editText2.text.toString()
                    }
                }
                lifecycleScope.launch {
                    loginViewModel.login()

                }
                loginViewModel.token.observe(viewLifecycleOwner) {
                    val navBar: BottomNavigationView =
                        requireActivity().findViewById(R.id.bottom_navigation)
                    navBar.visibility = View.VISIBLE
                    MyApplication.username = editText1.text.toString()
                    findNavController().navigate(R.id.action_loginFragment_to_listFragment)
                }
            }
        }
        return view
    }

    private fun checkTextViews(): Boolean {
        var isValid = false
        listOf<EditText>(editText1,editText2).forEach {
            if (it.text.toString().trim().isEmpty()) {
                it.error = "This field cannot be empty."
                isValid = false
            } else {
                isValid = true
            }
        }
        return isValid
    }

}