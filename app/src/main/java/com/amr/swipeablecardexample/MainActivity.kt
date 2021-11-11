package com.amr.swipeablecardexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amr.swipeable_card.Action
import com.amr.swipeable_card.SwipeableCard
import com.amr.swipeablecardexample.ui.theme.SwipeableCardExampleTheme

class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SwipeableCardExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Container(listOf(
                        User(1, "Amr Jyniat"),
                        User(2, "Ahmed Smer"),
                        User(3, "Mahmoud Daas"),
                        User(4, "Mariam Mona"),
                    ))
                }
            }
        }
    }

    data class User(
        val id: Int,
        val name: String
    )


    @ExperimentalAnimationApi
    @Composable
    fun Container(items: List<User>) {
        val revealsId = remember { mutableStateListOf<Int>() }

        val context = LocalContext.current

        LazyColumn {
            itemsIndexed(items, key = { _, item -> item.id }) { _, user ->
                SwipeableCard(
                    actions = listOf(
                        Action(R.drawable.ic_share, Color(0xFFFF9800), "Share", Color.White, 85.dp){
                            Toast.makeText(context, "Share Clicked!", Toast.LENGTH_SHORT).show()
                            revealsId.remove(user.id)
                        },
                        Action(R.drawable.ic_repetition, Color.Cyan, "Duplicate", Color.DarkGray){
                            Toast.makeText(context, "Repeated Clicked!", Toast.LENGTH_SHORT).show()
                            revealsId.remove(user.id)
                        },
                    ),
                    paddingValues = PaddingValues(8.dp),
                    isRevealed = revealsId.contains(user.id),
                    onExpand = {
                        if (revealsId.contains(user.id)) return@SwipeableCard
                        revealsId.add(user.id)
                    },
                    onCollapse = {
                        if (!revealsId.contains(user.id)) return@SwipeableCard
                        revealsId.remove(user.id)
                    }
                ) {
                    CardInfo(user)
                }
            }
        }
    }
}

@Composable
fun CardInfo(user: MainActivity.User) {
    Card() {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(color = Color.LightGray)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = user.name,
                color = Color.Black,
                modifier = Modifier
                    .padding(12.dp)
                    .weight(1f),
                textAlign = TextAlign.Start
            )
        }

    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SwipeableCardExampleTheme {

    }
}