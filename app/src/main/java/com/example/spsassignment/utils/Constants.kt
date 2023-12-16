package com.example.spsassignment.utils

object Constants {
    const val base_url: String = "https://api.themoviedb.org/3/"
    const val imageUrl: String = "https://image.tmdb.org/t/p/w500"
    const val originalImageUrl: String = "https://image.tmdb.org/t/p/original"
    const val access_token: String =
        "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1NWY5ZmYzMzhlNGY4MTc2Y2Q1OTM3MDZiODJmNTAxMSIsInN1YiI6IjVkYzk1ZmQ3NDcwZWFkMDAxMzk4N2ZhOSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.4q29og1uwwWqxFp63WK2W3qM8Wh0ZOq_N_waEmhkh2U"

    //    Search
    const val URL_SEARCH_TV: String = "search/tv"

    //Tv Show
    const val URL_TRENDING_TV: String = "trending/tv/day"
    var URL_TV_DETAILS: String = "tv/%1\$s"
    var URL_SIMILAR_TV: String = "tv/%1\$s/similar"

}