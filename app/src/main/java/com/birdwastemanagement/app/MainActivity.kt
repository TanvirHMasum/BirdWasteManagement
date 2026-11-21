package com.birdwastemanagement.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.birdwastemanagement.app.ui.navigation.AppNavGraph
import com.birdwastemanagement.app.ui.theme.BirdWasteManagementTheme
import dagger.hilt.android.AndroidEntryPoint

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.birdwastemanagement.app.ui.utils.LocalSnackbarHostState

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Set Bangla as the default language if no preference is set yet
        if (AppCompatDelegate.getApplicationLocales().isEmpty) {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("bn"))
        }

        setContent {
            BirdWasteManagementTheme {
                val snackbarHostState = remember { SnackbarHostState() }
                
                CompositionLocalProvider(
                    LocalSnackbarHostState provides snackbarHostState
                ) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState) { data ->
                                val isSuccess = data.visuals.message.startsWith("[SUCCESS]")
                                val displayMessage = data.visuals.message.removePrefix("[SUCCESS]").removePrefix("[ERROR]")
                                Snackbar(
                                    modifier = Modifier.padding(12.dp),
                                    containerColor = if (isSuccess) Color(0xFF4CAF50) else Color(0xFFD32F2F), // Green for success, Red for warning
                                    contentColor = Color.White
                                ) {
                                    androidx.compose.material3.Text(displayMessage)
                                }
                            }
                        }
                    ) { innerPadding ->
                        Surface(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            AppNavGraph()
                        }
                    }
                }
            }
        }
    }
}
