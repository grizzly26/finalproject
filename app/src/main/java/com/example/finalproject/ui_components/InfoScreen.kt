package com.example.finalproject.ui_components

import AssetImage
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.finalproject.utils.ListItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InfoScreen(item: ListItem) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Общие отступы для контента
    ) {
        // Карточка с изображением и текстом
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp), // Отступы внутри карточки
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp) // Внутренние отступы внутри карточки
            ) {
                // Изображение элемента
                AssetImage(
                    imageName = item.imageName,
                    contentDescription = item.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp) // Размер изображения
                        .padding(bottom = 16.dp) // Отступ снизу изображения
                )

                // Заголовок или описание элемента
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Дополнительное описание или текст
                Text(
                    text = "Описание:",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Загрузка HTML контента
                HtmlLoader(htmlName = item.htmlName)
            }
        }
    }
}

@Composable
fun HtmlLoader(htmlName: String) {
    val context = LocalContext.current
    val assetManager = context.assets
    val inputStream = assetManager.open("html/$htmlName")
    val size = inputStream.available()
    val buffer = ByteArray(size)
    inputStream.read(buffer)
    val htmlString = String(buffer)

    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = WebViewClient()
            loadData(htmlString, "text/html", "utf-8")
            settings.javaScriptEnabled = true // Включение JavaScript для улучшенного отображения контента
        }
    }, modifier = Modifier.fillMaxSize())
}
