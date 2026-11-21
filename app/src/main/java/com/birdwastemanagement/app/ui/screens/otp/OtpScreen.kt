package com.birdwastemanagement.app.ui.screens.otp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.birdwastemanagement.app.ui.theme.DeepForestGreen
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.birdwastemanagement.app.R
import com.birdwastemanagement.app.domain.model.UiState
import com.birdwastemanagement.app.ui.utils.LocalSnackbarHostState
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpScreen(
    phone: String,
    message: String? = null,
    onBackClick: () -> Unit,
    onVerifySuccess: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OtpViewModel = hiltViewModel()
) {
    val verifyState by viewModel.verifyState.collectAsState()
    val resendState by viewModel.resendState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current

    LaunchedEffect(verifyState) {
        if (verifyState is UiState.Success && (verifyState as UiState.Success<String>).data.isNotEmpty()) {
            onVerifySuccess((verifyState as UiState.Success<String>).data)
        } else if (verifyState is UiState.Error) {
            snackbarHostState.showSnackbar("[ERROR]${(verifyState as UiState.Error).message}")
        }
    }

    LaunchedEffect(resendState) {
        if (resendState is UiState.Success && (resendState as UiState.Success<String>).data.isNotEmpty()) {
            snackbarHostState.showSnackbar("[SUCCESS]${(resendState as UiState.Success<String>).data}")
        } else if (resendState is UiState.Error) {
            snackbarHostState.showSnackbar("[ERROR]${(resendState as UiState.Error).message}")
        }
    }

    var otpCode by remember { mutableStateOf("") }
    var timeLeft by remember { mutableIntStateOf(120) }
    var showDialog by remember { mutableStateOf(message != null) }

    if (showDialog && message != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { 
                Text(
                    text = stringResource(R.string.success),
                    color = DeepForestGreen,
                    fontWeight = FontWeight.Bold
                ) 
            },
            text = { 
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium
                ) 
            },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("OK", color = DeepForestGreen, fontWeight = FontWeight.Bold)
                }
            }
        )
    }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
            timeLeft--
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.otp_verification), color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = DeepForestGreen
                )
            )
        },
        modifier = modifier
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.enter_otp),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = DeepForestGreen
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.otp_sent_to, phone),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = otpCode,
                onValueChange = { if (it.length <= 6) otpCode = it },
                label = { Text(stringResource(R.string.six_digit_otp)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            val context = androidx.compose.ui.platform.LocalContext.current
            val scope = rememberCoroutineScope()

            Button(
                onClick = { 
                    if (com.birdwastemanagement.app.utils.NetworkUtils.isNetworkAvailable(context)) {
                        viewModel.verifyOtp(phone, otpCode)
                    } else {
                        scope.launch {
                            snackbarHostState.showSnackbar("[ERROR]${context.getString(R.string.no_internet)}")
                        }
                    }
                },
                enabled = otpCode.length == 6 && verifyState !is UiState.Loading,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                if (verifyState is UiState.Loading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(text = stringResource(R.string.verify_otp), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (timeLeft > 0) {
                Text(
                    text = stringResource(
                        R.string.resend_code_in,
                        (timeLeft / 60).toString(),
                        (timeLeft % 60).toString().padStart(2, '0')
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            } else {
                TextButton(
                    onClick = { 
                        if (com.birdwastemanagement.app.utils.NetworkUtils.isNetworkAvailable(context)) {
                            viewModel.resendOtp(phone)
                            timeLeft = 120
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("[ERROR]${context.getString(R.string.no_internet)}")
                            }
                        }
                    },
                    enabled = resendState !is UiState.Loading
                ) {
                    if (resendState is UiState.Loading) {
                        CircularProgressIndicator(color = DeepForestGreen, modifier = Modifier.size(24.dp))
                    } else {
                        Text(stringResource(R.string.resend_otp), color = DeepForestGreen, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
