package com.example.movieapp.presention.screens.HomeScreen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.movieapp.model.UIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTapScreen(viewModel: AccountViewModel, navController: NavHostController,) {
    LaunchedEffect(Unit) {
        viewModel.getUserToken()
    }

    val userToken = viewModel.userTokenState.collectAsState()
    val sessionId =viewModel.sessionState.collectAsState()
    val userAccountState = viewModel.accountState.collectAsState()

    var generatedUserToken by remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }



    Box(modifier = Modifier.fillMaxSize()){



        Button(onClick = {
            showBottomSheet = true
        }) {
            Text("Show bottom sheet")
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                    if(generatedUserToken.isNotEmpty()){
                        viewModel.getSession(generatedUserToken)
                    }
                    navController.popBackStack()

                },
                sheetState = sheetState,
                modifier = Modifier.fillMaxHeight(.9f),

            ) {

                LazyColumn{
                    item { WebViewScreen("https://www.themoviedb.org/authenticate/$generatedUserToken")} }
            }
        }
        when(val userTokenResult = userToken.value){
            is UIState.Empty -> {}
            is UIState.Error -> {}
            is UIState.Loading -> {
            }
            is UIState.Success -> {
                showBottomSheet =true
                generatedUserToken = userTokenResult.data?.requestToken.orEmpty()
            }
        }
        when(val sessionResult = sessionId.value){
            is UIState.Empty -> {}
            is UIState.Error -> {}
            is UIState.Loading -> {}
            is UIState.Success -> {
                showBottomSheet = false
                val generatedSessionId = sessionResult.data?.sessionId

                if (generatedSessionId?.isNotEmpty()== true){
                    LaunchedEffect(viewModel) {
                    viewModel.getAccount(generatedUserToken.toString())

                    }
                }

            }
        }
        when(val account = userAccountState.value){
            is UIState.Empty -> {

            }
            is UIState.Error -> {

            }
            is UIState.Loading -> {

            }
            is UIState.Success -> {
                Text(text = account.data?.name.orEmpty())
                Text(text = account.data?.id.toString())



            }
        }

        }
}

@Composable
fun WebViewScreen(url: String) {
    AndroidView(factory = { context ->
        WebView(context).apply {
            settings.javaScriptEnabled = true
            webViewClient = WebViewClient()

            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            settings.setSupportZoom(true)

        }

    },
        update = {
            it.loadUrl(url)
        }
    )

}
