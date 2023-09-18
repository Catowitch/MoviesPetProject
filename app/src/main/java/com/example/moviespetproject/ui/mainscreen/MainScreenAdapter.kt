package com.example.moviespetproject.ui.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.moviespetproject.R
import com.example.moviespetproject.ui.Data

class MainScreenAdapter(private val parentFragment: Fragment, val viewModel: SharedViewModel): RecyclerView.Adapter<MainScreenAdapter.MainScreenViewHolder>() {

    class MainScreenViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val text: TextView = view.findViewById(R.id.episode_name)
        val image: ImageView = view.findViewById(R.id.sharedElement)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainScreenViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.main_screen_rv_item, parent, false)
        return MainScreenViewHolder(layout)
    }


    override fun getItemCount(): Int = 6


    override fun onBindViewHolder(holder: MainScreenViewHolder, position: Int) {
        holder.text.text = "Episode ${position+1}"
        holder.image.setImageResource(Data.images[position])
        holder.view.setOnClickListener { onMovieClick(it,position) }
    }


    private fun onMovieClick(view: View, position:Int){
        if(SharedViewModel.dbFilmsEmpty && !SharedViewModel.filmsLoadedSuccessfully){
            viewModel.getFilmsData()
            Toast.makeText(view.context, "Error, could not load data, check your internet connection or try again later!", Toast.LENGTH_LONG).show()

            return
        }

        if(viewModel!=null){
            viewModel.SetCurrentEpisode(position)
            viewModel.setFilm()
            parentFragment.findNavController().navigate(R.id.action_mainScreenFragment_to_filmFragment)
        }
    }
}