package com.example.eterationcase.feature.home.ui.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import com.example.eterationcase.R
import com.example.eterationcase.databinding.FragmentFilterBottomSheetBinding
import com.example.eterationcase.feature.home.domain.SortFilter
import com.example.eterationcase.feature.home.ui.HomeViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterBottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentFilterBottomSheetBinding
    private val homeViewModel: HomeViewModel by hiltNavGraphViewModels(R.id.nav_main)
    private var selectedSortFilter: SortFilter? = null
    private var selectedBrands = mutableListOf<String>()
    private var selectedModels = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFilterBottomSheetBinding.inflate(inflater, container, false)
        setupLayout()
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet =
                bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let {
                val layoutParams = it.layoutParams
                layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
                it.layoutParams = layoutParams
                val behaviour = BottomSheetBehavior.from(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
                behaviour.isDraggable = false
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupLayout()
    }


    private fun setupLayout() {
        val filter = homeViewModel.filter.value

        selectedSortFilter = filter?.sortFilter ?: SortFilter.OldToNew
        when (selectedSortFilter) {
            SortFilter.OldToNew -> binding.sortOldToNew.isChecked = true
            SortFilter.NewToOld -> binding.sortNewToOld.isChecked = true
            SortFilter.PriceHighToLow -> binding.sortPriceHighToLow.isChecked = true
            SortFilter.PriceLowToHigh -> binding.sortPriceLowToHigh.isChecked = true
            else -> Unit
        }


        selectedBrands = filter?.selectedBrands?.toMutableList() ?: mutableListOf()
        selectedModels = filter?.selectedModels?.toMutableList() ?: mutableListOf()
        val brands = homeViewModel.brands.value?.toMutableList() ?: mutableListOf()
        val models = homeViewModel.models.value?.toMutableList() ?: mutableListOf()

        val brandsAdapter = CheckBoxAdapter { brand, checked ->
            if (checked) {
                selectedBrands.add(brand)
            } else {
                selectedBrands.remove(brand)
            }
        }
        val modelsAdapter = CheckBoxAdapter { model, checked ->
            if (checked) {
                selectedModels.add(model)
            } else {
                selectedModels.remove(model)
            }
        }
        brandsAdapter.setData(brands, selectedBrands)
        modelsAdapter.setData(models, selectedModels)

        binding.brandSearch.doAfterTextChanged {
            if (!it.isNullOrBlank()) {
                val filteredBrands = brands.filter { brand ->
                    brand.contains(it.toString(), ignoreCase = true)
                }
                brandsAdapter.setData(filteredBrands, selectedBrands)
            } else {
                brandsAdapter.setData(brands, selectedBrands)
            }
        }

        binding.modelSearch.doAfterTextChanged {
            if (!it.isNullOrBlank()) {
                val filteredModels = models.filter { model ->
                    model.contains(it.toString(), ignoreCase = true)
                }
                modelsAdapter.setData(filteredModels, selectedBrands)
            } else {
                modelsAdapter.setData(models, selectedBrands)
            }
        }

        binding.brandsList.adapter = brandsAdapter
        binding.modelsList.adapter = modelsAdapter


        binding.sortOldToNew.setOnClickListener {
            selectedSortFilter = SortFilter.OldToNew
        }
        binding.sortNewToOld.setOnClickListener {
            selectedSortFilter = SortFilter.NewToOld
        }
        binding.sortPriceHighToLow.setOnClickListener {
            selectedSortFilter = SortFilter.PriceHighToLow
        }
        binding.sortPriceLowToHigh.setOnClickListener {
            selectedSortFilter = SortFilter.PriceLowToHigh
        }
        binding.applyFiltersButton.setOnClickListener {
            selectedSortFilter?.let {
                homeViewModel.setSortFilter(it)
            }
            homeViewModel.setBrands(selectedBrands)
            homeViewModel.setModels(selectedModels)
            dismiss()
        }

        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }
}