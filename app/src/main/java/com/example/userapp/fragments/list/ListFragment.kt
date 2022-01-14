package com.example.userapp.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.userapp.R
import com.example.userapp.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_list.view.*


class ListFragment : Fragment() {

    private var isListEmpty = false
    private val userViewModel: UserViewModel by lazy {
        ViewModelProvider(this)[UserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_list, container, false)

        val adapter = ListAdapter()
        view.recyclerView.adapter = adapter
        view.recyclerView.layoutManager = LinearLayoutManager(context)

        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }

        userViewModel.readAllData.observe(viewLifecycleOwner, Observer { usersList ->
            isListEmpty = usersList.isEmpty()

            adapter.setData(usersList)
//            Log.d("OBSERVER", usersList.last().firstName)

        })

//        SHOW MENU
        setHasOptionsMenu(true)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.my_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteItem) {
            Log.d("Logger", item.itemId.toString())

            if (isListEmpty){
                Toast.makeText(requireContext(), "Users list is empty!", Toast.LENGTH_SHORT).show()
            } else {
                dropUsersTable()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dropUsersTable(){
        val alertBuilder = AlertDialog.Builder(requireContext())

        alertBuilder.setPositiveButton("yes"){ _, _ ->
            Log.d("LOGGER", "YES")
            userViewModel.deleteAllUsers()
            isListEmpty = true
            Toast.makeText(requireContext(), "Successfully Removed Everything", Toast.LENGTH_SHORT).show()
        }
        alertBuilder.setNegativeButton("no") { _, _ ->
            Log.d("LOGGER", "YES")
        }
        alertBuilder.setTitle("Delete all users")
        alertBuilder.setMessage("Are you sure?")
        alertBuilder.create().show()

    }

}