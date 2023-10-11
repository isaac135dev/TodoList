package com.example.todolist.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.example.todolist.R
import com.example.todolist.databinding.FragmentHeaderFragmentBinding

class HeaderFragment : Fragment(R.layout.fragment_header_fragment) {

    private var binding: FragmentHeaderFragmentBinding? = null

    companion object {
        fun newInstance(title: String, subtitle: String, angle: Int, background: Int, tvAngle: Int): HeaderFragment {
            return HeaderFragment().apply {
                arguments = bundleOf(
                    "title" to title,
                    "subtitle" to subtitle,
                    "angle" to angle,
                    "background" to background,
                    "tvAngle" to tvAngle
                )
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeaderFragmentBinding.bind(view)

        val title = arguments?.getString("title") ?: "Default Title"
        val subtitle = arguments?.getString("subtitle") ?: "Default Subtitle"
        val angle = arguments?.getInt("angle") ?: 0F
        val tvAngle = arguments?.getInt("tvAngle") ?: 0F
        val background = arguments?.getInt("background") ?: R.color.black

        binding?.apply {
            vHeader.rotation = angle.toFloat()
            vHeader.setBackgroundColor(ContextCompat.getColor(requireContext(), background))
            tvTitleHeader.text = title
            tvSubtitleHeader.text = subtitle
            hvFragment.rotation = tvAngle.toFloat()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}