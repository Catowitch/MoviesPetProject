package com.example.moviespetproject.ui.filmscreen

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.moviespetproject.R
import com.example.moviespetproject.databinding.FragmentFilmBinding
import com.example.moviespetproject.model.CharacterProps
import com.example.moviespetproject.ui.Data
import com.example.moviespetproject.ui.mainscreen.SharedViewModel

class FilmFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var binding: FragmentFilmBinding

    private val createdChips = mutableListOf<Int>()
    private val currentFilmChars = mutableListOf<Int>()
    private lateinit var actorModalScreen: ActorModalScreen



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

        actorModalScreen = ActorModalScreen()

        val actorNames = mutableListOf<Pair<Int,String>>()

        sharedViewModel.films.value?.result?.get(sharedViewModel.currentEpisode.value?:0)?.properties?.charactersURLs?.forEach{
            val id = SharedViewModel.getUriLastSubstring(it).toInt()
            currentFilmChars.add(id)
            sharedViewModel.characters.value?.get(id)
                ?.let { it1 ->
                    actorNames.add(id to it1.name)
                    createdChips.add(SharedViewModel.getUriLastSubstring(it).toInt())
            }
        }

        val observer = Observer<MutableMap<Int,CharacterProps>>{ map ->
            map.forEach{
                if(!createdChips.contains(it.key) && currentFilmChars.contains(it.key)){
                    createActorChip(it.key to it.value.name)
                    createdChips.add(it.key)
                }
            }
        }
        sharedViewModel.characters.observe(viewLifecycleOwner, observer)
        createActorChips(actorNames)

        //(view.parent as ViewGroup).doOnPreDraw { startPostponedEnterTransition() }
    }

    private fun createActorChips(actors: List<Pair<Int,String>>) {
        val ids: MutableList<Int> = mutableListOf()
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        actors.forEach{actor->
            //Log.d("CREATING_CHIP", actorName)
            val actorView = TextView(ContextThemeWrapper(requireContext(), R.style.Actor))
            val newId = View.generateViewId()
            ids.add(newId)
            actorView.id = newId
            actorView.layoutParams = params
            actorView.text= actor.second
            actorView.setTextColor(ResourcesCompat.getColor(resources, R.color.black,null))
            actorView.setOnClickListener {
                sharedViewModel.setCurrentCharacter(actor.first)
                if(!actorModalScreen.isAdded)
                    actorModalScreen.show(childFragmentManager, ActorModalScreen.TAG)
            }
            binding.filmConstraintLayout.addView(actorView)

        }
        binding.actorsFlow.referencedIds = intArrayOf(*binding.actorsFlow.referencedIds, *ids.toIntArray())
    }

    private fun createActorChip(actor: Pair<Int,String>){
        val actorView = TextView(ContextThemeWrapper(requireContext(), R.style.Actor))
        val newId = View.generateViewId()
        actorView.id = newId
        actorView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        actorView.text= actor.second
        actorView.setTextColor(ResourcesCompat.getColor(resources, R.color.black,null))
        actorView.setOnClickListener{
            sharedViewModel.setCurrentCharacter(actor.first)
            if(!actorModalScreen.isAdded)
                actorModalScreen.show(childFragmentManager, ActorModalScreen.TAG)
        }
        binding.filmConstraintLayout.addView(actorView)
        binding.actorsFlow.referencedIds = intArrayOf(*binding.actorsFlow.referencedIds, newId)
    }

}