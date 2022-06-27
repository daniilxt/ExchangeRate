package ru.daniilxt.feature.dialogs

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import androidx.core.view.children
import androidx.core.view.get
import androidx.fragment.app.DialogFragment
import com.google.android.material.radiobutton.MaterialRadioButton
import ru.daniilxt.common.extensions.setDebounceClickListener
import ru.daniilxt.feature.R
import ru.daniilxt.feature.databinding.FragmentFilterDialogBinding
import ru.daniilxt.feature.domain.model.FilterType

class FilterDialogFragment : DialogFragment() {
    private var _binding: FragmentFilterDialogBinding? = null
    private val binding get() = requireNotNull(_binding)
    private var onOkClickListener: ((filterType: FilterType) -> Unit)? = null

    private val dialogContext get() = binding.root.context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterDialogBinding.inflate(inflater, container, false)

        dialog?.window?.setDimAmount(DIM_ALPHA)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setupDialogView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.mbOk.setDebounceClickListener {
            onOkClickListener?.invoke(getFilterType())
            dialog?.dismiss()
        }
    }

    fun setOnOkClickListener(onClick: (filterType: FilterType) -> Unit) {
        onOkClickListener = onClick
    }

    private fun setupDialogView() {
        (binding.radioGroup[0] as MaterialRadioButton).isChecked = true
    }

    private fun getFilterType(): FilterType {
        val rb =
            binding.radioGroup.getChildAt(binding.radioGroup.checkedIndex) as MaterialRadioButton
        return when (rb.text) {
            dialogContext.getString(R.string.alphabetically_asc) -> FilterType.ALPHABETICALLY_ASC
            dialogContext.getString(R.string.alphabetically_desc) -> FilterType.ALPHABETICALLY_DESC
            dialogContext.getString(R.string.value_asc) -> FilterType.VALUE_ASC
            dialogContext.getString(R.string.value_desc) -> FilterType.VALUE_DESC
            else -> throw IllegalArgumentException("Incorrect state")
        }
    }

    companion object {
        val RadioGroup.checkedIndex: Int
            get() = children
                .indexOfFirst { it.id == checkedRadioButtonId }

        private const val DIM_ALPHA = 0.25F
        fun newInstance(): FilterDialogFragment {
            return FilterDialogFragment()
        }
    }
}
