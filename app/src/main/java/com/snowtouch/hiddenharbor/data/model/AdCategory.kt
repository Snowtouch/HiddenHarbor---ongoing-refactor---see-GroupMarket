package com.snowtouch.hiddenharbor.data.model

import com.snowtouch.hiddenharbor.R


data class AdCategory(
    val categoryName: Int,
    val adSubcategoryList: List<Int>
)
val adCategories = listOf(
    AdCategory(
        categoryName = R.string.category_home_and_garden,
        adSubcategoryList = listOf(
            R.string.subcategory_furniture,
            R.string.subcategory_home_appliances,
            R.string.subcategory_gardening_tools,
            R.string.subcategory_decorations,
            R.string.subcategory_kitchenware
        )
    ),
    AdCategory(
        categoryName = R.string.category_electronics,
        adSubcategoryList = listOf(
            R.string.subcategory_smartphones_and_tvs,
            R.string.subcategory_laptops_and_computers,
            R.string.subcategory_audio_and_photography_equipment,
            R.string.subcategory_video_games,
            R.string.subcategory_electronic_accessories
        )
    ),
    AdCategory(
        categoryName = R.string.category_fashion,
        adSubcategoryList = listOf(
            R.string.subcategory_womens_clothing,
            R.string.subcategory_mens_clothing,
            R.string.subcategory_shoes,
            R.string.subcategory_jewelry,
            R.string.subcategory_accessories
        )
    ),
    AdCategory(
        categoryName = R.string.category_automotive,
        adSubcategoryList = listOf(
            R.string.subcategory_passenger_cars,
            R.string.subcategory_commercial_vehicles,
            R.string.subcategory_motorcycles,
            R.string.subcategory_car_parts,
            R.string.subcategory_motorcycle_accessories,
            R.string.subcategory_tools
        )
    ),
    AdCategory(
        categoryName = R.string.category_real_estate,
        adSubcategoryList = listOf(
            R.string.subcategory_apartments,
            R.string.subcategory_houses,
            R.string.subcategory_land,
            R.string.subcategory_commercial_properties
        )
    ),
    AdCategory(
        categoryName = R.string.category_pets,
        adSubcategoryList = listOf(
            R.string.subcategory_dogs,
            R.string.subcategory_cats,
            R.string.subcategory_livestock,
            R.string.subcategory_other_animals,
            R.string.subcategory_pet_accessories
        )
    ),
    AdCategory(
        categoryName = R.string.category_miscellaneous,
        adSubcategoryList = listOf(
            R.string.subcategory_antiques_and_collectibles,
            R.string.subcategory_sports_and_recreation,
            R.string.subcategory_books_and_magazines,
            R.string.subcategory_music_and_instruments,
            R.string.subcategory_health_and_beauty
        )
    )
)
