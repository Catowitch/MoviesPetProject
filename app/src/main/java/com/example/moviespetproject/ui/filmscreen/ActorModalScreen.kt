package com.example.moviespetproject.ui.filmscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import com.example.moviespetproject.R
import com.example.moviespetproject.databinding.ActorSheetBinding
import com.example.moviespetproject.ui.mainscreen.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ActorModalScreen(): BottomSheetDialogFragment() {

    private lateinit var binding: ActorSheetBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)
        val behavior = (dialog as? BottomSheetDialog)?.behavior
        behavior?.let {
            behavior.isFitToContents= true
            behavior.isDraggable =false
            behavior.maxHeight = 200
            behavior.setPeekHeight(200, false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.actor_sheet, container, false)
        binding.viewModel = sharedViewModel
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        const val TAG = "ModalBottomSheet"
    }
}