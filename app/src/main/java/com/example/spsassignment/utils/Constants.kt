package com.example.spsassignment.utils

object Constants {
    const val base_url: String = "https://api.themoviedb.org/3/"
    const val imageUrl: String = "https://image.tmdb.org/t/p/w500"
    const val originalImageUrl: String = "https://image.tmdb.org/t/p/original"
    const val access_token: String =
        "Bearer Token"

    //    Search
    const val URL_SEARCH_TV: String = "search/tv"

    //Tv Show
    const val URL_TRENDING_TV: String = "trending/tv/day"
    var URL_TV_DETAILS: String = "tv/%1\$s"
    var URL_SIMILAR_TV: String = "tv/%1\$s/similar"

}