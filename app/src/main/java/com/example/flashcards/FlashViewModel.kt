package com.example.flashcards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flashcards.data.FlashUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FlashViewModel : ViewModel() {
  private val _uiState = MutableStateFlow(FlashUiState("",""))
  val uiState : StateFlow<FlashUiState> =_uiState.asStateFlow()

  fun setStart(startValue :String){
    _uiState.update {currentState ->
      currentState.copy(
        start = startValue
      )
    }
  }

  fun setEnd(endValue :String){
    _uiState.update {currentState ->
      currentState.copy(
        end = endValue
      )
    }
  }

  fun setCurrent(currentValue :Int){
    _uiState.update {currentState ->
      currentState.copy(
        current = currentValue
      )
    }
  }

  fun setCurnum(curnumValue :Int){
    _uiState.update {currentState ->
      currentState.copy(
        curnum = curnumValue
      )
    }
  }

  fun setQuiz(quizValue :List<Int>){
    _uiState.update {currentState ->
      currentState.copy(
        quiz = quizValue
      )
    }
  }

  fun computeQuiz(){

    try {
      uiState.value.start.toInt()
      uiState.value.end.toInt()
    }
    catch (e:Exception){
      return
    }

    if(uiState.value.start.toInt()<1 || uiState.value.end.toInt()>2300){
      return
    }
    _uiState.update {currentState ->
      currentState.copy(
        quiz = (uiState.value.start.toInt()..uiState.value.end.toInt()).shuffled()
      )
    }
    setCurnum(1)
    setCurrent(uiState.value.quiz.first())
  }

  fun iterate(){
    val index = uiState.value.quiz.indexOf(uiState.value.current)
    if(index+1 >= uiState.value.quiz.size){
      return
    }
    setCurnum(uiState.value.curnum+1)
    setCurrent(uiState.value.quiz[index+1])
  }

  fun reviterate(){
    val index = uiState.value.quiz.indexOf(uiState.value.current)
    if(index-1 < 0){
      return
    }
    setCurnum(uiState.value.curnum-1)
    setCurrent(uiState.value.quiz[index-1])
  }

  fun reset(){
    setCurnum(1)
    setCurrent(1)
    setQuiz(listOf())
  }


}