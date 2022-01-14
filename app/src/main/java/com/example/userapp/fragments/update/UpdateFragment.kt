package com.example.userapp.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.userapp.R
import com.example.userapp.model.User
import com.example.userapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*


class UpdateFragment : Fragment() {

    private val updateArgs by navArgs<UpdateFragmentArgs>()
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update, container, false)

        view.updateFirstNameEditText.setText(updateArgs.editUser.firstName)
        view.updateLastNameEditText.setText(updateArgs.editUser.lastName)
        view.updateAgeEditText.setText(updateArgs.editUser.age.toString())

        view.updateButton.setOnClickListener {
            updateUserRecord()
        }


//        SHOW MENU
        setHasOptionsMenu(true)

        return view

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.my_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteItem) {
            removeUserRecord()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun updateUserRecord() {
        val firstName = updateFirstNameEditText.text.toString()
        val lastName = updateLastNameEditText.text.toString()
        val age = updateAgeEditText.text

        if (validateInputs(firstName, lastName, age)){
            val newUser = User(updateArgs.editUser.id, firstName, lastName, age.toString().toInt())

            userViewModel.updateUser(newUser)

            Toast.makeText(requireContext(), "User updated", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
        else{
            Toast.makeText(requireContext(), "Please Fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateInputs(fName: String, lName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(fName) || TextUtils.isEmpty(lName) || age.isEmpty())
    }

    private fun removeUserRecord() {
        val alertBuilder = AlertDialog.Builder(requireContext())

        alertBuilder.setPositiveButton("yes"){ _, _ ->
            Log.d("LOGGER", "YES")
            userViewModel.deleteUser(updateArgs.editUser)
            Toast.makeText(requireContext(), "Successfully Removed User", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
        alertBuilder.setNegativeButton("no") { _, _ ->
            Log.d("LOGGER", "NO")
        }
        alertBuilder.setTitle("Delete User")
        alertBuilder.setMessage("Are you sure?")
        alertBuilder.create().show()
    }

}