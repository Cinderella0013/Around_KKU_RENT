package com.example.kkurent

sealed class Screen (val route: String, val name: String){
    data object Post: Screen(route = "post_screen", name = "Post")
    data object Item: Screen(route = "item_screen", name = "Item")

}