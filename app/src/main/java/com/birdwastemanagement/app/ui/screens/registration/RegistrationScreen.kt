package com.birdwastemanagement.app.ui.screens.registration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import com.birdwastemanagement.app.R
import com.birdwastemanagement.app.data.remote.dto.RegisterRequest
import com.birdwastemanagement.app.domain.model.UiState
import com.birdwastemanagement.app.ui.screens.registration.RegistrationResult
import com.birdwastemanagement.app.ui.theme.DeepForestGreen
import com.birdwastemanagement.app.ui.utils.LocalSnackbarHostState
import com.birdwastemanagement.app.utils.localizeDigits
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen(
    onBackClick: () -> Unit,
    onNavigateToOtp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val registrationState by viewModel.registrationState.collectAsState()
    val snackbarHostState = LocalSnackbarHostState.current
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    LaunchedEffect(registrationState) {
        if (registrationState is UiState.Success) {
            val result = (registrationState as UiState.Success<RegistrationResult>).data
            if (result.phone.isNotEmpty()) {
                onNavigateToOtp(result.phone, result.message)
            }
        } else if (registrationState is UiState.Error) {
            snackbarHostState.showSnackbar((registrationState as UiState.Error).message)
        }
    }

    val divisions by viewModel.divisions.collectAsState()
    val divisionOptions = divisions.map { it.name }

    val districts by viewModel.districts.collectAsState()
    val districtOptions = districts.map { it.name }

    val upazilas by viewModel.upazilas.collectAsState()
    val upazilaOptions = upazilas.map { it.name }

    val municipalities by viewModel.municipalities.collectAsState()
    val municipalityOptions = municipalities.map { it.name }

    val wards by viewModel.wards.collectAsState()
    val wardOptions = wards.map { "${it.wardNo} - ${it.name}" }

    val areas by viewModel.areas.collectAsState()
    val areaOptions = areas.map { it.name }

    val serviceCategories by viewModel.serviceCategories.collectAsState()
    val serviceOptions = serviceCategories.map { "${it.name} - ৳ ${it.monthlyFee.toDouble().toInt()}".localizeDigits() }

    // Form States
    var name by remember { mutableStateOf("") }
    var fatherName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var nid by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var divisionId by remember { mutableStateOf<Int?>(null) }
    var districtId by remember { mutableStateOf<Int?>(null) }
    var upazilaId by remember { mutableStateOf<Int?>(null) }
    var municipalityId by remember { mutableStateOf<Int?>(null) }
    var wardId by remember { mutableStateOf<Int?>(null) }
    var areaId by remember { mutableStateOf<Int?>(null) }
    var holdingNo by remember { mutableStateOf("") }
    var roadNo by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var serviceCategoryId by remember { mutableStateOf<Int?>(null) }
    var password by remember { mutableStateOf("") }
    var retypePassword by remember { mutableStateOf("") }
    var declaration by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.customer_registration), color = Color.White) },
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(8.dp)) }

            item {
                SectionTitle(title = stringResource(R.string.personal_info))
            }

            item { RegistrationTextField(value = name, onValueChange = { name = it }, label = stringResource(R.string.customer_name)) }
            item { RegistrationTextField(value = fatherName, onValueChange = { fatherName = it }, label = stringResource(R.string.father_husband_name)) }
            item { RegistrationTextField(value = phone, onValueChange = { phone = it }, label = stringResource(R.string.mobile_number), keyboardType = KeyboardType.Phone) }
            item { RegistrationTextField(value = nid, onValueChange = { nid = it }, label = stringResource(R.string.nid_number), keyboardType = KeyboardType.Number) }
            item { RegistrationTextField(value = email, onValueChange = { email = it }, label = stringResource(R.string.email_optional), keyboardType = KeyboardType.Email) }

            item { 
                RegistrationDropdownField(
                    label = stringResource(R.string.gender), 
                    options = listOf("পুরুষ", "মহিলা", "অন্যান্য"),
                    onOptionSelected = { index ->
                        gender = listOf("পুরুষ", "মহিলা", "অন্যান্য")[index]
                    }
                ) 
            }
            item { 
                RegistrationDatePickerField(label = stringResource(R.string.dob)) { selectedDate ->
                    dob = selectedDate
                } 
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionTitle(title = stringResource(R.string.address_service_info))
            }

            item { 
                RegistrationDropdownField(
                    label = stringResource(R.string.division), 
                    options = if (divisionOptions.isEmpty()) listOf("Loading...") else divisionOptions,
                    onOptionSelected = { index ->
                        if (divisions.isNotEmpty()) {
                            divisionId = divisions[index].id
                            viewModel.onDivisionSelected(divisions[index].id)
                        }
                    }
                ) 
            }
            item { 
                RegistrationDropdownField(
                    label = stringResource(R.string.district), 
                    options = if (divisions.isEmpty()) listOf("Select Division first") else if (districtOptions.isEmpty()) listOf("Loading...") else districtOptions,
                    onOptionSelected = { index ->
                        if (districts.isNotEmpty()) {
                            districtId = districts[index].id
                            viewModel.onDistrictSelected(districts[index].id)
                        }
                    }
                ) 
            }
            item { 
                RegistrationDropdownField(
                    label = stringResource(R.string.upazila), 
                    options = if (districts.isEmpty()) listOf("Select District first") else if (upazilaOptions.isEmpty()) listOf("Loading...") else upazilaOptions,
                    onOptionSelected = { index ->
                        if (upazilas.isNotEmpty()) {
                            upazilaId = upazilas[index].id
                            viewModel.onUpazilaSelected(upazilas[index].id)
                        }
                    }
                ) 
            }
            item { 
                RegistrationDropdownField(
                    label = stringResource(R.string.municipality), 
                    options = if (upazilas.isEmpty()) listOf("Select Upazila first") else if (municipalityOptions.isEmpty()) listOf("Loading...") else municipalityOptions,
                    onOptionSelected = { index ->
                        if (municipalities.isNotEmpty()) {
                            municipalityId = municipalities[index].id
                            viewModel.onMunicipalitySelected(municipalities[index].id)
                        }
                    }
                ) 
            }
            item { 
                RegistrationDropdownField(
                    label = stringResource(R.string.ward), 
                    options = if (municipalities.isEmpty()) listOf("Select Municipality first") else if (wardOptions.isEmpty()) listOf("Loading...") else wardOptions,
                    onOptionSelected = { index ->
                        if (wards.isNotEmpty()) {
                            wardId = wards[index].id
                            viewModel.onWardSelected(wards[index].id)
                        }
                    }
                ) 
            }
            item { 
                RegistrationDropdownField(
                    label = stringResource(R.string.mahalla_village), 
                    options = if (wards.isEmpty()) listOf("Select Ward first") else if (areaOptions.isEmpty()) listOf("Loading...") else areaOptions,
                    onOptionSelected = { index ->
                        if (areas.isNotEmpty()) {
                            areaId = areas[index].id
                        }
                    }
                ) 
            }

            item { RegistrationTextField(value = holdingNo, onValueChange = { holdingNo = it }, label = stringResource(R.string.holding_number)) }
            item { RegistrationTextField(value = roadNo, onValueChange = { roadNo = it }, label = stringResource(R.string.road_number)) }
            item { RegistrationTextField(value = address, onValueChange = { address = it }, label = stringResource(R.string.detailed_address)) }
            item { 
                RegistrationDropdownField(
                    label = stringResource(R.string.service_type), 
                    options = if (serviceOptions.isEmpty()) listOf("Loading...") else serviceOptions,
                    onOptionSelected = { index ->
                        if (serviceCategories.isNotEmpty()) {
                            serviceCategoryId = serviceCategories[index].id
                        }
                    }
                ) 
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                SectionTitle(title = "Security & Declaration")
            }

            item { RegistrationPasswordField(value = password, onValueChange = { password = it }, label = stringResource(R.string.password_hint)) }
            item { RegistrationPasswordField(value = retypePassword, onValueChange = { retypePassword = it }, label = stringResource(R.string.retype_password)) }

            item {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = declaration,
                        onCheckedChange = { declaration = it },
                        colors = CheckboxDefaults.colors(checkedColor = DeepForestGreen)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.declaration),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // We no longer show inline error, because we use a Snackbar for errors
            /*if (registrationState is UiState.Error) {
                item {
                    Text(
                        text = (registrationState as UiState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }*/

            item {
                val isFormValid = name.isNotBlank() && fatherName.isNotBlank() && phone.isNotBlank() && 
                    nid.isNotBlank() && gender.isNotBlank() && dob.isNotBlank() && 
                    divisionId != null && districtId != null && upazilaId != null && 
                    municipalityId != null && wardId != null && areaId != null && 
                    holdingNo.isNotBlank() && serviceCategoryId != null && 
                    password.isNotBlank() && password == retypePassword && declaration

                Button(
                    onClick = {
                        if (com.birdwastemanagement.app.utils.NetworkUtils.isNetworkAvailable(context)) {
                            val request = RegisterRequest(
                                name = name,
                                fatherOrHusbandName = fatherName,
                                phone = phone,
                                nidNumber = nid,
                                email = email.ifBlank { null },
                                gender = gender,
                                dob = dob,
                                divisionId = divisionId!!,
                                districtId = districtId!!,
                                upazilaId = upazilaId!!,
                                municipalityId = municipalityId!!,
                                wardId = wardId!!,
                                areaId = areaId!!,
                                holdingNo = holdingNo,
                                roadNo = roadNo.ifBlank { null },
                                fullAddress = address.ifBlank { null },
                                serviceCategoryId = serviceCategoryId!!,
                                password = password,
                                passwordConfirmation = retypePassword,
                                declaration = declaration
                            )
                            viewModel.registerCustomer(request)
                        } else {
                            scope.launch {
                                snackbarHostState.showSnackbar("No internet connection. Please connect to the internet and try again.")
                            }
                        }
                    },
                    enabled = isFormValid && registrationState !is UiState.Loading,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = DeepForestGreen),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                ) {
                    if (registrationState is UiState.Loading) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text(text = stringResource(R.string.submit_application), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(32.dp)) }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = DeepForestGreen,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationDropdownField(
    label: String, 
    options: List<String>,
    onOptionSelected: ((Int) -> Unit)? = null
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedOptionText,
            onValueChange = { },
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = DeepForestGreen,
                unfocusedBorderColor = Color.LightGray,
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEachIndexed { index, selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        selectedOptionText = selectionOption
                        expanded = false
                        onOptionSelected?.invoke(index)
                    }
                )
            }
        }
    }
}

@Composable
fun RegistrationTextField(value: String, onValueChange: (String) -> Unit, label: String, keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DeepForestGreen,
            unfocusedBorderColor = Color.LightGray,
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun RegistrationPasswordField(value: String, onValueChange: (String) -> Unit, label: String) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(imageVector = image, contentDescription = "Toggle password visibility", tint = Color.Gray)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DeepForestGreen,
            unfocusedBorderColor = Color.LightGray,
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationDatePickerField(label: String, onDateSelected: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    val datePickerState = rememberDatePickerState()

    OutlinedTextField(
        value = text,
        onValueChange = { },
        readOnly = true,
        label = { Text(label) },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = DeepForestGreen,
            unfocusedBorderColor = Color.LightGray,
        ),
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date")
            }
        }
    )

    if (showDialog) {
        DatePickerDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                androidx.compose.material3.TextButton(onClick = {
                    datePickerState.selectedDateMillis?.let { millis ->
                        val formattedDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(millis))
                        text = formattedDate
                        onDateSelected(formattedDate)
                    }
                    showDialog = false
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                androidx.compose.material3.TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}
