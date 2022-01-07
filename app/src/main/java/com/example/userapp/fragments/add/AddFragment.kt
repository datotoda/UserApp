package com.example.userapp.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.example.userapp.R
import com.example.userapp.model.User
import com.example.userapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*


class addFragment : Fragment() {

    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add, container, false)

        view.addButton.setOnClickListener {
            createUserRecord()
        }

        return view

    }

    private fun createUserRecord() {
        val firstName = firstNameEditText.text.toString()
        val lastName = lastNameEditText.text.toString()
        val age = ageNameEditText.text

        if (validateInputs(firstName, lastName, age)){
            val newUser = User(0, firstName, lastName, age.toString().toInt())

            userViewModel.addUser(newUser)

            Toast.makeText(requireContext(), "User created", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        }
        else{
            Toast.makeText(requireContext(), "Please Fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(fName: String, lName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(fName) && TextUtils.isEmpty(lName) && age.isNotEmpty())
    }

}