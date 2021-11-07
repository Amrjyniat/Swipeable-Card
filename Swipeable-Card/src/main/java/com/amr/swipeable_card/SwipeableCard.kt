package com.amr.swipeable_card

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

const val MIN_DRAG_AMOUNT = 6

@ExperimentalAnimationApi
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun SwipeableCard(
    actions: List<Action>,
    isRevealed: Boolean,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    animationDuration: Int = 500,
    onExpand: () -> Unit,
    onCollapse: () -> Unit,
    cardItself: @Composable () -> Unit
) {

    var cardHeight by remember { mutableStateOf(0) }
    val actionHeightDp = LocalDensity.current.run { cardHeight.toDp() }
    var boxActionsWidth by remember { mutableStateOf(0) }

    val start = paddingValues.calculateStartPadding(LocalLayoutDirection.current)
    val end = paddingValues.calculateEndPadding(LocalLayoutDirection.current)
    val top = paddingValues.calculateTopPadding()
    val bottom = paddingValues.calculateBottomPadding()

    val animateStartPadding by animateDpAsState(
        targetValue = if (isRevealed) 0.dp else start,
        animationSpec = tween(durationMillis = animationDuration)
    )

    val animateEndPadding by animateDpAsState(
        targetValue = if (isRevealed) 0.dp else end,
        animationSpec = tween(durationMillis = animationDuration)
    )

    val transitionState = remember {
        MutableTransitionState(isRevealed).apply {
            targetState = !isRevealed
        }
    }

    val transition = updateTransition(transitionState, "cardTransition")
    val offsetTransitionCard by transition.animateFloat(
        label = "cardOffsetTransition",
        transitionSpec = { tween(durationMillis = animationDuration) },
        targetValueByState = {
            if (isRevealed)
                if (LocalLayoutDirection.current == LayoutDirection.Ltr)
                    -boxActionsWidth.toFloat()
                else
                    boxActionsWidth.toFloat()
            else
                0f
        },
    )

    Box {
        Card(
            modifier = Modifier
                .padding(
                    start = animateStartPadding,
                    end = animateEndPadding,
                    top = top,
                    bottom = bottom
                )
                .onGloballyPositioned { coordinates ->
                    cardHeight = coordinates.size.height
                }
                .fillMaxWidth()
                .offset { IntOffset(offsetTransitionCard.roundToInt(), 0) }
                .pointerInput(Unit) {
                    detectHorizontalDragGestures { _, dragAmount ->
                        Log.i("Tag", "dragAmount: $dragAmount")
                        when {
                            dragAmount >= MIN_DRAG_AMOUNT -> {
                                onCollapse()
                            }
                            dragAmount < -MIN_DRAG_AMOUNT -> {
                                onExpand()
                            }
                        }
                    }
                },
            backgroundColor = Color.White,
            shape = RoundedCornerShape(0.dp),
            elevation = 0.dp,
            border = BorderStroke(0.dp, Color.Transparent),
            content = cardItself
        )

        val transitionActions = updateTransition(transitionState, "actionsTransition")
        val offsetTransitionActions by transitionActions.animateFloat(
            label = "actionsTransition",
            transitionSpec = { tween(durationMillis = animationDuration) },
            targetValueByState = {
                if (isRevealed)
                    0f
                else {
                    if (boxActionsWidth != 0)
                        if (LocalLayoutDirection.current == LayoutDirection.Ltr)
                            boxActionsWidth.toFloat()
                        else
                            -boxActionsWidth.toFloat()
                    else
                        if (LocalLayoutDirection.current == LayoutDirection.Ltr)
                            1000f
                        else
                            -1000f
                }
            },
        )

        Row(
            modifier = Modifier
                .onGloballyPositioned { boxActionsWidth = it.size.width }
                .align(
                    if (LocalLayoutDirection.current == LayoutDirection.Ltr)
                        Alignment.CenterEnd
                    else
                        Alignment.CenterStart
                )
                .offset { IntOffset(offsetTransitionActions.roundToInt(), 0) }
        ) {
            actions.forEach { action ->
                SwipeableAction(actionHeightDp, action)
            }
        }

    }
}

