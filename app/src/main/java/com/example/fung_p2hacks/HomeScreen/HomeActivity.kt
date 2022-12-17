package com.example.fung_p2hacks.HomeScreen

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fung_p2hacks.WalkingScreen.WalkingActivity

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FuNG_p2hacksTheme{
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ){
                    Root(
                        viewModel = viewModel()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Root(
    viewModel: HomeScreenViewModel,
) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { it != ModalBottomSheetValue.HalfExpanded }
    )
    val coroutineScope = rememberCoroutineScope()

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = { BottomSheet() },
        modifier = Modifier.fillMaxSize()
    ) {
        HomeBodyComposable(
            viewModel = viewModel,
            modalOpener = {
                coroutineScope.launch {
                    if (sheetState.isVisible) sheetState.hide()
                    else sheetState.show()
                }
            },
        )
    }
}

@Composable
fun HomeBodyComposable(
    viewModel: HomeScreenViewModel,
    modalOpener: () -> Unit,
) {
    var ticketNumber by remember { mutableStateOf(-1) }

    //If any ticket chosen, move activity and destroy this activity.
    if (ticketNumber != -1) {
        val intent = Intent(LocalContext.current, WalkingActivity::class.java)
        intent.putExtra("walkingType", ticketNumber)
        LocalContext.current.startActivity(intent)
        (LocalContext.current as Activity).finish()
    }

    Column {
        TopBarComposable(modalOpener)
        TicketComposable(
            listOf(
                { ticketNumber = 0 },
                { ticketNumber = 1 },
                { ticketNumber = 2 },
                { ticketNumber = 3 },
                { ticketNumber = 4 }
            )
        )
        FriendsActivityListComposable()
        UserHistoryComposable()
    }
}