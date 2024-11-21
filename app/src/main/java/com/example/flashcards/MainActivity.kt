package com.example.flashcards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flashcards.ui.theme.FlashcardsTheme
import data.KMPair
import data.kanjiList
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    enableEdgeToEdge()
    setContent {
      FlashcardsTheme(
        dynamicColor = false
      ) {
        Surface {
          val vm : FlashViewModel = viewModel()
          AppNav(viewModel = vm)
        }


      }
    }
  }
}

val zen_old_mincho = FontFamily(
  Font(R.font.zenoldminchoregular)
)

@Composable
fun QuizCard(
  modifier: Modifier,
  text : String,
  fSize : Int
){
  Card(
    elevation = CardDefaults.cardElevation(
      defaultElevation = 6.dp
    ),
    modifier = modifier
      .height(300.dp)
      .fillMaxWidth()
  ) {
    Column(
      verticalArrangement = Arrangement.Center,
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier
        .fillMaxSize()
    ) {
      Text(
        text = text,
        fontSize = fSize.sp,
        textAlign = TextAlign.Center,
        lineHeight = (fSize+8).sp,
        fontFamily = zen_old_mincho
      )
    }
  }
}

@Composable
fun QuizScreen(
  viewModel: FlashViewModel = viewModel(),
  kanjiID : Int = 1,
  toQuery : () -> Unit = {}
){
  Column(
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier
      .fillMaxSize()
      .padding(12.dp)
      .verticalScroll(rememberScrollState())
  ) {
    val uiState by viewModel.uiState.collectAsState()
    Row(
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ){
      Text("${uiState.curnum}/${uiState.quiz.size}")
      Text("${uiState.current}")
    }

    QuizCard(modifier = Modifier.fillMaxHeight(0.5F), kanjiList[uiState.current-1].kanji,108)
    Spacer(modifier = Modifier.height(20.dp))

    if(uiState.disp){
      QuizCard(modifier = Modifier.fillMaxHeight(0.5F), kanjiList[uiState.current-1].meaning,40)
    }
    else{
      QuizCard(modifier = Modifier.fillMaxHeight(0.5F), "",36)
    }


//    Text(uiState.quiz.toString())

    Row (
      horizontalArrangement = Arrangement.SpaceAround,
      modifier = Modifier.fillMaxWidth().padding(2.dp)
    ) {

      Button(onClick = {
        viewModel.reviterate()
      },
        modifier = Modifier.weight(1f)) {
        Text("Back")
      }

      Button(onClick = {
        viewModel.iterate()
      },
        modifier = Modifier
          .weight(2f)
          .padding(horizontal = 2.dp, vertical = 0.dp)) {
        Text("Next")
      }

      Button(onClick = {
        viewModel.reset()
        toQuery()
      },
        modifier = Modifier.weight(1f)) {
        Text("Reset")
      }


    }
  }
}

@Composable
fun QueryScreen(
  viewModel: FlashViewModel = viewModel(),
  toFlash : () -> Unit = {},
  modifier: Modifier = Modifier
){
  Column (
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
    modifier = modifier
      .fillMaxSize()
      .padding(12.dp)
      .verticalScroll(rememberScrollState())
  ){
    val uiState by viewModel.uiState.collectAsState()
    Text("Enter Kanji Ref: ", fontSize = 42.sp, fontFamily = zen_old_mincho)
    OutlinedTextField(
      value = uiState.start,
      onValueChange = {viewModel.setStart(it)},
      label = {Text("Start")},
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Number
      ),
    )
    OutlinedTextField(
      value = uiState.end,
      onValueChange = {viewModel.setEnd(it)},
      label = {Text("End")},
      keyboardOptions = KeyboardOptions(
        imeAction = ImeAction.Done,
        keyboardType = KeyboardType.Number
      ),
      keyboardActions = KeyboardActions(onDone = {
        viewModel.computeQuiz()
        toFlash()
      })
    )
    Button(onClick = {
      viewModel.computeQuiz()
      toFlash()
    }) {
      Text("Revise")
    }
  }
}