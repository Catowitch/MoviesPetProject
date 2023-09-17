package com.example.moviespetproject.ui.mainscreen

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.moviespetproject.R
import com.example.moviespetproject.databinding.FragmentMainScreenBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.moviespetproject.model.FilmDatabase

class MainScreenFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentMainScreenBinding

    private lateinit var recyclerView: RecyclerView

    private lateinit var dialog: AlertDialog


    private val db by lazy {
        Room.databaseBuilder(
            requireActivity().applicationContext,
            FilmDatabase::class.java,
            "films.db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedViewModel.SetDababase(db)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_screen, container, false)

        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setCancelable(false)
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.waiting_dialog, null)
        dialogBuilder.setView(dialogView)
        dialog = dialogBuilder.create()
        //dialog.show()

        sharedViewModel.onFinishLoadAddListener { dialog.dismiss(); Log.d("ON_FINISH_LOADING_EVENT", "Finished loading!") }




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        postponeEnterTransition()
        binding.viewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        recyclerView = binding.mainScreenRv
//        recyclerView.layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = MainScreenAdapter(this, sharedViewModel)



//        (view.parent as ViewGroup).doOnPreDraw { startPostponedEnterTransition() }
    }
}

