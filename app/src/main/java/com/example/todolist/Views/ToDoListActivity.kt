package com.example.todolist.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.Models.ToDoListItem
import com.example.todolist.Others.OnItemDeleteListener
import com.example.todolist.ViewModels.SwipeToDeleteCallback
import com.example.todolist.ViewModels.ToDoListItemViewModel
import com.example.todolist.ViewModels.ToDoLitsAdapter
import com.example.todolist.databinding.ActivityToDoListBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ToDoListActivity : AppCompatActivity(), OnItemDeleteListener {

    private lateinit var binding: ActivityToDoListBinding
    private lateinit var viewModel: ToDoListItemViewModel
    private lateinit var adapter: ToDoLitsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityToDoListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ToDoLitsAdapter()
        binding.floatingActionButton.setOnClickListener {
            navigateToNewItem()
        }
        initializeViews()
        userToDoList()


    }

    private fun initializeViews() {
        viewModel = ViewModelProvider(this)[ToDoListItemViewModel::class.java]
    }

    private fun navigateToNewItem() {
        val intent = Intent(this, NewItemActivity::class.java)
        startActivity(intent)
    }

    private fun bottonNavigationView() {

    }

    private fun userToDoList() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        val swipeToDeleteCallback = SwipeToDeleteCallback(adapter, this,this)
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        if (userId != null) {
            val db = FirebaseFirestore.getInstance()
            val query = db.collection("users")
                .document(userId)
                .collection("todos")

            binding.rvToDoListItems.layoutManager = LinearLayoutManager(this)

            binding.rvToDoListItems.adapter = adapter
            itemTouchHelper.attachToRecyclerView(binding.rvToDoListItems)

            query.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                val items = mutableListOf<ToDoListItem>()
                snapshot?.documents?.forEach { document ->
                    document.toObject(ToDoListItem::class.java)?.let { item ->
                        items.add(item)
                    }
                }
                adapter.setItems(items)
            }
        }

    }


    override fun onDeleteItem(itemId: String) {
        deleteItem(itemId)
    }

    private fun deleteItem(itemId: String) {
        val db = FirebaseFirestore.getInstance()
        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId != null) {
            db.collection("users")
                .document(userId)
                .collection("todos")
                .document(itemId)
                .delete()
                .addOnSuccessListener {
                    val position = findItemPositionById(itemId)
                    if (position != -1) {
                        adapter.deleteItem(position) // Elimina el elemento del conjunto de datos
                        adapter.notifyItemRemoved(position) // Notifica al adaptador que el elemento se ha eliminado
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al eliminar el elemento: $e", Toast.LENGTH_SHORT)
                        .show()
                }
        }
    }

    private fun findItemPositionById(itemId: String): Int {
        return adapter.findItemPositionById(itemId)
    }

}