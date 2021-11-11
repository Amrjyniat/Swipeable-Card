<h1 align="center">Swipeable-Card</h1>

<p align="center">A simple utility Compose to add customizable buttons when swiping the card in an easy way.</p>




<p align="center">
<img src="https://github.com/Amrjyniat/Swipeable-Card/blob/master/previews/Screenshot.png" width="250" height="450"/>
</p>


## Import:

This library is available as a gradle dependency via [JitPack.io](http://JitPack.io "JitPack.io"). Just add the following code:
1. Add Jitpack.io to your root build.gradle:
```
allprojects {
     repositories {
     	...
     	maven { url "https://jitpack.io" }
     }
}
```
Note: if you faced an issue with the first step [check this out](https://stackoverflow.com/questions/69163511/build-was-configured-to-prefer-settings-repositories-over-project-repositories-b) 

2. Add dependency:
  `implementation 'com.github.Amrjyniat:Swipeable-Card:1.0.0'`


## Features
- Customize the background, text color and icon of the button.
- Add unlimited buttons to the card by passing a list of `SwipeableAction`.
- Work with both LTR and RTL layout directions.
- You don't need to edit your card to make it swipeable, just add your card compose to the `SwipeableCard`.

![Alt Text](https://github.com/Amrjyniat/Swipeable-Card/blob/master/previews/video.gif)

## How to use:
	SwipeableCard(
		actions = listOf(
		  Action(R.drawable.ic_share, Orange, "Share", Color.White, 85.dp){},
		),
		isRevealed = revealIds.contains(task.taskId),
		paddingValues = PaddingValues(16.dp),
		animationDuration = 500,
		onExpand = {},
		onCollapse = {}
	) { 
		Card() 
	}

You have three customizable areas in `SwipeableCard` composable fun:
1. Your original card.

2. The buttons that will show when swiping the card, pass a list of `Action` which has multiple attributes:
	- `iconRes`: Drawable resource icon.
	- `color`: Background of the button.
	- `text`: Text of the button.
	- `textColor`: Text color of the button.
	- `withInDp`: Specify button width in DB, otherwise will wrap the content. 
	- `onAction`: Lambda triggered when button clicked. 

3. Attributes:
	- `isRevealed`: Boolean value that Reveals the card to show the buttons when being true.
	- `paddingValues`: Set card padding.
	- `animationDuration`: Specify the Reveal speed by passing duration in milliseconds. 
	- `onExpand`:  Lambda triggered when revealing the card.
	- `onCollpase`:  Lambda triggered when collapsing the card.

## Sample:

You can check out the [sample app](https://github.com/Amrjyniat/Swipeable-Card/tree/master/app/src/main "sample app") in the project to take a closer look of how to use it in real work.

## References:
This library inspired from [here](https://proandroiddev.com/swipe-to-reveal-in-jetpack-compose-6ffa8928a4c2), Thanks Skyyo!
