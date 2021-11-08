package com.amr.swipeable_card

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Action(
    @DrawableRes val iconRes: Int? = null,
    val color: Color,
    val text: String = "",
    val textColor: Color = Color.White,
    val widthInDp: Dp = Dp.Unspecified,
    val onAction: () -> Unit
)

@Composable
fun SwipeableAction(
    actionHeightDp: Dp,
    action: Action,
) {
    Box(
        modifier = Modifier
            .height(actionHeightDp)
            .width(action.widthInDp)
            .clickable { action.onAction() }
            .background(color = action.color)
            .padding(6.dp)
    ) {
        if (action.iconRes != null) {
            Icon(
                painter = painterResource(id = action.iconRes),
                tint = Color.Unspecified,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(bottom = 10.dp),
                contentDescription = "",
            )
        }
        Text(
            text = action.text,
            color = action.textColor,
            modifier = Modifier
                .padding(top = 4.dp)
                .align(
                    if (action.iconRes != null)
                        Alignment.BottomCenter
                    else
                        Alignment.Center
                )
        )
    }
}