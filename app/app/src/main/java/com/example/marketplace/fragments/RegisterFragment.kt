package com.example.marketplace.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.marketplace.R
import com.example.marketplace.repository.Repository
import com.example.marketplace.viewmodels.RegisterViewModel
import com.example.marketplace.viewmodels.RegisterViewModelFactory
import kotlinx.coroutines.launch

class RegisterFragment : Fragment() {
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = RegisterViewModelFactory(this.requireContext(), Repository())
        registerViewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val logIn: TextView = view.findViewById(R.id.textView_login_fragment_log_in)
        val userName: EditText = view.findViewById(R.id.user_name)
        val phoneNumber: EditText = view.findViewById(R.id.phone_number)
        val email: EditText = view.findViewById(R.id.email)
        val password: EditText = view.findViewById(R.id.password)
        val register : Button = view.findViewById(R.id.button_register_fragment_register)
        logIn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        register.setOnClickListener {
            registerViewModel.user.value.let {
                if (it != null) {
                    it.username = userName.text.toString()
                }
                if (it != null) {
                    it.password = password.text.toString()
                }
                if (it != null) {
                    it.email = email.text.toString()
                }
                if (it != null) {
                    it.phone_number = phoneNumber.text.toString()
                }
            }

            lifecycleScope.launch {
                registerViewModel.register()
            }
        }
        return view
    }
}