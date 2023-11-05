package site.pnpl.mira.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import site.pnpl.mira.R
import site.pnpl.mira.databinding.FragmentCheckInFactorsBinding
import site.pnpl.mira.ui.main.fragments.CheckInCompletedFragment.Companion.CALLBACK_KEY

class CheckInFactorsFragment : Fragment() {
    private var _binding: FragmentCheckInFactorsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckInFactorsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val key = findNavController().currentBackStackEntry?.arguments?.getString(CALLBACK_KEY)
        binding.next.setOnClickListener {
            findNavController().navigate(R.id.action_factors_to_completed, bundleOf(Pair(CALLBACK_KEY, key)))
        }
        binding.close.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}