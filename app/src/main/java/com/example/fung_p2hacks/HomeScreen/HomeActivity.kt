package com.example.fung_p2hacks.HomeScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.example.fung_p2hacks.ui.theme.FuNG_p2hacksTheme
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FuNG_p2hacksTheme {
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
    viewModel: HomeScreenViewModel
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
            }
        )
    }
}

@Composable
fun HomeBodyComposable(
    viewModel: HomeScreenViewModel,
    modalOpener: () -> Unit
) {
    Column {
        TopBarComposable(modalOpener)
        TicketComposable()
        FriendsActivityListComposable()
//        MyHistoryComposable()
    }
}