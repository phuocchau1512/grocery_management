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
            val fullName = binding.fullName.text.toString()
            val email = binding.mobileNumber.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.secondPassword.text.toString()

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password == confirmPassword) {
                binding.progressBar.visibility = View.VISIBLE
                registerViewModel.registerUser(CreateUserReq(fullName, email, password))
            } else {
                Toast.makeText(requireContext(), "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show()
            }
        }

        registerViewModel.registerStatus.observe(viewLifecycleOwner, Observer { success ->
            if (success.first) {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), "Đăng ký thành công!", Toast.LENGTH_SHORT).show()
            } else {
                binding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(requireContext(), "Lỗi đăng ký", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
