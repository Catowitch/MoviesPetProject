package com.example.moviespetproject.ui.mainscreen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviespetproject.model.CharacterProps
import com.example.moviespetproject.model.FilmDatabase
import com.example.moviespetproject.model.FilmProps
import com.example.moviespetproject.model.FilmResult
import com.example.moviespetproject.model.SwCharacter
import com.example.moviespetproject.network.SwBaseResponseResult
import com.example.moviespetproject.network.starWarsApi
import kotlinx.coroutines.launch
import java.net.URL

class SharedViewModel: ViewModel() {

    private val _films = MutableLiveData<FilmResult>()
    val films: LiveData<FilmResult> = _films

    private val _characters = MutableLiveData<MutableMap<Int, CharacterProps>>(mutableMapOf())
    val characters: LiveData<MutableMap<Int,CharacterProps>> = _characters

    private val _currentCharecter = MutableLiveData<CharacterProps>()
    val currentCharacter: LiveData<CharacterProps> = _currentCharecter

    private val _currentEpisode = MutableLiveData<Int>(0)
    val currentEpisode: LiveData<Int> = _currentEpisode

    private val _film = MutableLiveData<SwBaseResponseResult<FilmProps>>()
    val film: LiveData<SwBaseResponseResult<FilmProps>> = _film

    private var db: FilmDatabase? = null

    //private val onFinishLoad: MutableSet<()->Unit> = mutableSetOf()

    //fun onFinishLoadAddListener(action: ()->Unit) = onFinishLoad.add(action)
    //fun onFinishLoadRemoveListener(action: ()->Unit) = onFinishLoad.remove(action)


    init {
        getFilmsData()
    }

    fun getFilmsData()
    {
        viewModelScope.launch {
            try {
                _films.value = starWarsApi.retrofitService.films()

                //Log.d("Get films Data", films.value.toString())
                filmsLoadedSuccessfully = true

                // If films are loaded save them to local db
                db?.let { filmDb->
                    films.value?.let { filmDb.dao.insertFilmsTest(it) }
                }
            }catch (e: Exception){
                Log.e("FAILED_LOAD_FILMS", e.message.toString())
                filmsLoadedSuccessfully = false
            }


            db?.let {
                dbFilmsEmpty = it.dao.isEmpty()
                if(!dbFilmsEmpty)
                    loadFilms(it.dao.getFilmsFromDbTest())
                if(dbFilmsEmpty && !filmsLoadedSuccessfully){
                    //Toast.makeText(context, "ERROR: films could not be loaded!", Toast.LENGTH_LONG).show()
                }
            }

            _films.value?.result = films.value?.result?.sortedBy { it.properties.episodeNumber }!!

            loadCharacters()
        }
    }


    private fun loadCharacters(){

        if(!filmsLoadedSuccessfully)
            return
        // Get all charaterers
        val set = mutableSetOf<String>()
        films.value?.result?.let {
            repeat(it.size){
                films.value?.result?.get(it)?.properties?.charactersURLs?.let { set.addAll(it) }
            }
        }

        // Load characters from db
        var dbCharaters: List<SwCharacter> = mutableListOf()
        db?.let{
            dbCharactersEmpty = it.dao.isCharactersEmpty()
            if(!dbCharactersEmpty){
                dbCharaters = it.dao.getCharacters()
            }
        }

        //Load characters
        set.forEach{ setElement ->
            val id = getUriLastSubstring(setElement).toIntOrNull()

            // set characters from db is any availiable
            if(dbCharaters.any { getUriLastSubstring(it.result.properties.url).toIntOrNull() == id }){
                id?.let {
                    _characters.value?.set(id, dbCharaters.find {  getUriLastSubstring(it.result.properties.url).toIntOrNull() == id }!!.result.properties)
                    _characters.postValue(_characters.value)
                }
            }

            // load character from api
            if(characters.value?.containsKey(id) == false){
            viewModelScope.launch {
                id?.let {
                    try {
                        val character = starWarsApi.retrofitService.character(id.toString())
                        if(characters.value?.containsKey(id) == false){
                            characters.value?.set(it, character.result.properties)
                            _characters.postValue(_characters.value)
                        }
                        db?.dao?.upsertCharacter(SwCharacter(character.id, character.message, character.result))
                    }catch (e:Exception){
                        Log.e("Retrofit_Get_Character_Exception", e.message.toString())
                    }
                }
            }
            }
        }
    }

    fun setCurrentCharacter(id:Int){
        _currentCharecter.value = characters.value?.get(id)
    }

    fun setFilm(){
        if(films.value == null || films.value!!.result.isEmpty())
            return
        when(currentEpisode.value){
            1 -> _film.value = films.value?.result?.get(1)
            2 -> _film.value = films.value?.result?.get(2)
            3 -> _film.value = films.value?.result?.get(3)
            4 -> _film.value = films.value?.result?.get(4)
            5 -> _film.value = films.value?.result?.get(5)
            else -> _film.value = films.value?.result?.get(0)
        }
    }

    fun loadFilms(films: FilmResult){
        _films.value = films
    }

    fun SetDababase(db:FilmDatabase){
        this.db = db
    }

    fun SetCurrentEpisode(episodeNum: Int){
        _currentEpisode.value = episodeNum
    }

    companion object{
        var filmsLoadedSuccessfully: Boolean = false
        var dbFilmsEmpty: Boolean = true
        var dbCharactersEmpty: Boolean = true
        fun getUriLastSubstring(url: String?):String{
            val uri = URL(url)
            val path: String = uri.path
            return path.substring(path.lastIndexOf('/') + 1)
        }
    }

}
