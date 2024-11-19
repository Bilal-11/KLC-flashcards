package com.example.flashcards.data

data class FlashUiState(
  val start : String,
  val end : String,
  val quiz : List<Int> = listOf(),
  val current : Int = 1,
  val curnum : Int = 1,
  val disp : Boolean = false
)
