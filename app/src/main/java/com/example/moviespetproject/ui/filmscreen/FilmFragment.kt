package com.example.moviespetproject.ui.filmscreen

import android.app.ActionBar.LayoutParams
import android.graphics.Color
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat
import androidx.core.view.doOnPreDraw
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.moviespetproject.R
import com.example.moviespetproject.databinding.FragmentFilmBinding
import com.example.moviespetproject.model.CharacterProps
import com.example.moviespetproject.ui.mainscreen.MainScreenFragment
import com.example.moviespetproject.ui.mainscreen.SharedViewModel
import kotlinx.coroutines.coroutineScope
import kotlin.math.log

class FilmFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentFilmBinding

    private val createdChips = mutableListOf<Int>()
    private val currentFilmChars = mutableListOf<Int>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_film, container, false)
        //Log.d("Film transition name", "transition_item${sharedViewModel.currentEpisode.value}")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //postponeEnterTransition()
        sharedViewModel.setFilm()
        binding.viewModel = sharedViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.sharedElement.setImageResource(Data.images[sharedViewModel.currentEpisode.value?:0])

        val actorNames = mutableListOf<String>()

        sharedViewModel.films.value?.result?.get(sharedViewModel.currentEpisode.value?:0)?.properties?.charactersURLs?.forEach{
            val id = SharedViewModel.getUriLastSubstring(it).toInt()
            currentFilmChars.add(id)
            sharedViewModel.characters?.value?.get(id)
                ?.let { it1 ->
                    actorNames.add(it1.name)
                    createdChips.add(SharedViewModel.getUriLastSubstring(it).toInt())
            }
        }

        val observer = Observer<MutableMap<Int,CharacterProps>>{ map ->
            Log.d("OBSERVER", "triggered!")
            map.forEach{
                if(!createdChips.contains(it.key) && currentFilmChars.contains(it.key)){
                    createActorChip(it.value.name)
                    createdChips.add(it.key)
                }
            }
        }
        sharedViewModel.characters.observe(viewLifecycleOwner, observer)
        createActorChips(actorNames)

        Log.d("CREATING_CHIPS", "Chips are created!")
        Log.d("Chips", binding.actorsFlow.referencedIds.toString())
        //(view.parent as ViewGroup).doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun createActorChips(actors: List<String>) {
        Log.d("CREATING_CHIP", "Trying to create chips with list of actors: ${actors.toString()}")


        val ids: MutableList<Int> = mutableListOf()
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        actors.forEach{actorName->
            //Log.d("CREATING_CHIP", actorName)
            val actorView = TextView(ContextThemeWrapper(requireContext(), R.style.Actor))
            val newId = View.generateViewId()
            ids.add(newId)
            actorView.id = newId
            actorView.layoutParams = params
            actorView.text= actorName
            actorView.setTextColor(ResourcesCompat.getColor(resources, R.color.black,null))
            actorView.setOnClickListener {
                Toast.makeText(requireContext(), "You pressed $actorName", Toast.LENGTH_LONG).show()
            }
            binding.filmConstraintLayout.addView(actorView)

        }
        binding.actorsFlow.referencedIds = ids.toIntArray()
    }

    private fun createActorChip(actor: String){
        Log.d("CREATING_CHIP", actor)
        val actorView = TextView(ContextThemeWrapper(requireContext(), R.style.Actor))
        val newId = View.generateViewId()
        actorView.id = newId
        actorView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        actorView.text= actor
        actorView.setTextColor(ResourcesCompat.getColor(resources, R.color.black,null))
        actorView.setOnClickListener{
            Toast.makeText(requireContext(), "You pressed $actor", Toast.LENGTH_LONG).show()
        }
        binding.filmConstraintLayout.addView(actorView)
        binding.actorsFlow.referencedIds = intArrayOf(*binding.actorsFlow.referencedIds, newId)
    }

}