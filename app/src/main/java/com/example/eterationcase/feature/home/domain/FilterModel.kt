package com.example.eterationcase.feature.home.domain

data class FilterModel(
    val query: String = "",
    val sortFilter: SortFilter = SortFilter.OldToNew,
    val selectedBrands: List<String> = emptyList(),
    val selectedModels: List<String> = emptyList()
)

enum class SortFilter {
    OldToNew,
    NewToOld,
    PriceHighToLow,
    PriceLowToHigh;
}