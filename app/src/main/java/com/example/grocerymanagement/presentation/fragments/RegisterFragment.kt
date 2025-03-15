package com.example.grocerymanagement.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.grocerymanagement.R
import com.example.grocerymanagement.data.model.CreateUserReq
import com.example.grocerymanagement.databinding.FragmentRegisterBinding
import com.example.grocerymanagement.presentation.viewModel.RegisterViewModel

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.alreadyUser.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.frameContainer, LoginFragment())
                .commit()
        }

        binding.signUpBtn.setOnClickListener {
            val fullName = binding.fullName.text.toString().trim()
            val email = binding.mobileNumber.text.toString().trim()
            val password = binding.password.text.toString().trim()
            val confirmPassword = binding.secondPassword.text.toString().trim()

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                showToast("Vui lòng nhập đầy đủ thông tin")
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                showToast("Email không hợp lệ!")
                return@setOnClickListener
            }

            if (password.length < 6) {
                showToast("Mật khẩu phải có ít nhất 6 ký tự!")
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                showToast("Mật khẩu không khớp!")
                return@setOnClickListener
            }

            binding.progressBar.visibility = View.VISIBLE
            registerViewModel.registerUser(CreateUserReq(fullName, email, password))
        }

        registerViewModel.registerStatus.observe(viewLifecycleOwner, Observer { (success, message) ->
            binding.progressBar.visibility = View.INVISIBLE
            showToast(message)

            if (success) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.frameContainer, LoginFragment())
                    .commit()
            }
        })
    }

    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

