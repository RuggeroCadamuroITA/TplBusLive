package com.example.bustrackerfvg.ui

import android.graphics.Bitmap
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun DetailScreen(stopCode: String) {
    val url = "https://realtime.tplfvg.it/?stopcode=$stopCode"

    // Stato per mostrare una rotellina di caricamento mentre la pagina si apre
    var isLoading by remember { mutableStateOf(true) }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    // --- CONFIGURAZIONE POTENZIATA ---

                    // 1. Abilita JavaScript (Fondamentale)
                    settings.javaScriptEnabled = true

                    // 2. Abilita il DOM Storage (CRUCIALE per il sito TPL FVG)
                    // Senza questo, il sito non riesce a caricare gli script dinamici
                    settings.domStorageEnabled = true

                    // 3. Migliora la visualizzazione su mobile
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.builtInZoomControls = true
                    settings.displayZoomControls = false // Nasconde i pulsanti + e - brutti

                    // 4. Cache e Mixed Content
                    settings.cacheMode = WebSettings.LOAD_DEFAULT
                    settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE

                    // Configurazione del Client per gestire il caricamento
                    webViewClient = object : WebViewClient() {
                        // Quando la pagina inizia a caricare
                        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                            isLoading = true
                        }

                        // Quando ha finito di caricare
                        override fun onPageFinished(view: WebView?, url: String?) {
                            isLoading = false
                        }
                    }

                    loadUrl(url)
                }
            },
            update = { webView ->
                // Assicuriamoci che carichi l'URL corretto se cambia
                if (webView.url != url && url.isNotEmpty()) {
                    webView.loadUrl(url)
                }
            }
        )

        // Se sta caricando, mostra la rotellina al centro
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}