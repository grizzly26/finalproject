package com.example.finalproject.ui_components

import MainListItem
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.finalproject.MainViewModel
import com.example.finalproject.utils.DrawerEvents
import com.example.finalproject.utils.ListItem
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    onClick: (ListItem) -> Unit
) {
    // Создаем состояния для навигационного ящика и заголовка TopBar
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val mainList = mainViewModel.mainList
    val topBarTitle = remember { mutableStateOf("Удочки") }

    // Загружаем элементы из выбранной категории при инициализации
    LaunchedEffect(Unit) {
        mainViewModel.getAllItemsByCategory(topBarTitle.value)
    }

    // Используем ModalNavigationDrawer из Material3
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerMenu { event ->
                when (event) {
                    is DrawerEvents.OnItemClick -> {
                        topBarTitle.value = event.title
                        mainViewModel.getAllItemsByCategory(event.title)
                    }
                }
                coroutineScope.launch {
                    drawerState.close()
                }
            }
        }
    ) {
        // Создаем Scaffold с TopAppBar и Drawer
        Scaffold(
            topBar = {
                MainTopBar(
                    title = topBarTitle.value,
                    drawerState = drawerState
                ) {
                    // Обработка клика по кнопке "Избранные"
                    topBarTitle.value = "Избранные"
                    mainViewModel.getFavorites()
                }
            }
        ) { paddingValues ->
            // LazyColumn для отображения списка элементов
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                items(mainList.value) { item ->
                    MainListItem(item = item) { listItem ->
                        onClick(listItem) // Обработка нажатия на элемент
                    }
                }
            }
        }
    }
}
