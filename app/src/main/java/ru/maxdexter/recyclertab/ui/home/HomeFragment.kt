package ru.maxdexter.recyclertab.ui.home

import RoomEvent
import android.content.ClipData
import android.graphics.Canvas
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ru.maxdexter.recyclertab.R
import ru.maxdexter.recyclertab.databinding.FragmentHomeBinding
import ru.maxdexter.recyclertab.ui.adapters.ColorDividerItemDecoration
import ru.maxdexter.recyclertab.ui.adapters.ViewColorAdapter
import ru.maxdexter.recyclertab.ui.customview.EventRectangleView
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var adapterColor: ViewColorAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        adapterColor =  ViewColorAdapter {
            when (it) {
                true -> {
//                    binding.event.visibility = View.VISIBLE
//                    binding.event.addView(Button(requireContext()))
                }
            }
        }
        val root: View = binding.root

        adapterColor.submitList( listOf(
            RoomEvent(1, "9:00", "11:55",Color.GREEN, true),
            RoomEvent(2, "11:55", "15:40",Color.WHITE, false),
            RoomEvent(3, "15:10", "17:20",Color.GREEN, true),
            RoomEvent(4, "17:20", "17:50",Color.WHITE, false),
            RoomEvent(5, "17:50", "19:00",Color.GREEN, true),


        ))


       binding.recyclerCustom.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL )
        binding.recyclerCustom.adapter = adapterColor
       // binding.recyclerCustom.addItemDecoration(ColorDividerItemDecoration())


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}