package com.birdwastemanagement.app.ui.screens.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.birdwastemanagement.app.data.remote.api.AuthApi
import com.birdwastemanagement.app.data.remote.api.LocationApi
import com.birdwastemanagement.app.data.remote.dto.AreaDto
import com.birdwastemanagement.app.data.remote.dto.DistrictDto
import com.birdwastemanagement.app.data.remote.dto.DivisionDto
import com.birdwastemanagement.app.data.remote.dto.MunicipalityDto
import com.birdwastemanagement.app.data.remote.dto.RegisterRequest
import com.birdwastemanagement.app.data.remote.dto.ServiceCategoryDto
import com.birdwastemanagement.app.data.remote.dto.UpazilaDto
import com.birdwastemanagement.app.data.remote.dto.WardDto
import com.birdwastemanagement.app.domain.model.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegistrationResult(val phone: String, val message: String)

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val locationApi: LocationApi,
    private val authApi: AuthApi
) : ViewModel() {

    private val _registrationState = MutableStateFlow<UiState<RegistrationResult>>(UiState.Success(RegistrationResult("", "")))
    val registrationState: StateFlow<UiState<RegistrationResult>> = _registrationState.asStateFlow()

    private val _divisions = MutableStateFlow<List<DivisionDto>>(emptyList())
    val divisions: StateFlow<List<DivisionDto>> = _divisions.asStateFlow()

    private val _districts = MutableStateFlow<List<DistrictDto>>(emptyList())
    val districts: StateFlow<List<DistrictDto>> = _districts.asStateFlow()

    private val _upazilas = MutableStateFlow<List<UpazilaDto>>(emptyList())
    val upazilas: StateFlow<List<UpazilaDto>> = _upazilas.asStateFlow()

    private val _municipalities = MutableStateFlow<List<MunicipalityDto>>(emptyList())
    val municipalities: StateFlow<List<MunicipalityDto>> = _municipalities.asStateFlow()

    private val _wards = MutableStateFlow<List<WardDto>>(emptyList())
    val wards: StateFlow<List<WardDto>> = _wards.asStateFlow()

    private val _areas = MutableStateFlow<List<AreaDto>>(emptyList())
    val areas: StateFlow<List<AreaDto>> = _areas.asStateFlow()

    private val _serviceCategories = MutableStateFlow<List<ServiceCategoryDto>>(emptyList())
    val serviceCategories: StateFlow<List<ServiceCategoryDto>> = _serviceCategories.asStateFlow()

    init {
        fetchDivisions()
        fetchServiceCategories()
    }

    private fun fetchDivisions() {
        viewModelScope.launch {
            try {
                val response = locationApi.getDivisions()
                if (response.isSuccessful && response.body() != null) {
                    _divisions.value = response.body()!!
                }
            } catch (e: Exception) {
                // Ignore for now
            }
        }
    }

    fun onDivisionSelected(divisionId: Int) {
        _districts.value = emptyList() // clear previous districts
        _upazilas.value = emptyList() // clear previous upazilas
        _municipalities.value = emptyList() // clear previous municipalities
        _wards.value = emptyList() // clear previous wards
        _areas.value = emptyList() // clear previous areas
        fetchDistricts(divisionId)
    }

    private fun fetchDistricts(divisionId: Int) {
        viewModelScope.launch {
            try {
                val response = locationApi.getDistricts(divisionId)
                if (response.isSuccessful && response.body() != null) {
                    _districts.value = response.body()!!
                }
            } catch (e: Exception) {
                // Ignore for now
            }
        }
    }

    fun onDistrictSelected(districtId: Int) {
        _upazilas.value = emptyList() // clear previous upazilas
        _municipalities.value = emptyList() // clear previous municipalities
        _wards.value = emptyList() // clear previous wards
        _areas.value = emptyList() // clear previous areas
        fetchUpazilas(districtId)
    }

    private fun fetchUpazilas(districtId: Int) {
        viewModelScope.launch {
            try {
                val response = locationApi.getUpazilas(districtId)
                if (response.isSuccessful && response.body() != null) {
                    _upazilas.value = response.body()!!
                }
            } catch (e: Exception) {
                // Ignore for now
            }
        }
    }

    fun onUpazilaSelected(upazilaId: Int) {
        _municipalities.value = emptyList() // clear previous municipalities
        _wards.value = emptyList() // clear previous wards
        _areas.value = emptyList() // clear previous areas
        fetchMunicipalities(upazilaId)
    }

    private fun fetchMunicipalities(upazilaId: Int) {
        viewModelScope.launch {
            try {
                val response = locationApi.getMunicipalities(upazilaId)
                if (response.isSuccessful && response.body() != null) {
                    _municipalities.value = response.body()!!
                }
            } catch (e: Exception) {
                // Ignore for now
            }
        }
    }

    fun onMunicipalitySelected(municipalityId: Int) {
        _wards.value = emptyList() // clear previous wards
        _areas.value = emptyList() // clear previous areas
        fetchWards(municipalityId)
    }

    private fun fetchWards(municipalityId: Int) {
        viewModelScope.launch {
            try {
                val response = locationApi.getWards(municipalityId)
                if (response.isSuccessful && response.body() != null) {
                    _wards.value = response.body()!!
                }
            } catch (e: Exception) {
                // Ignore for now
            }
        }
    }

    fun onWardSelected(wardId: Int) {
        _areas.value = emptyList() // clear previous areas
        fetchAreas(wardId)
    }

    private fun fetchAreas(wardId: Int) {
        viewModelScope.launch {
            try {
                val response = locationApi.getAreas(wardId)
                if (response.isSuccessful && response.body() != null) {
                    _areas.value = response.body()!!
                }
            } catch (e: Exception) {
                // Ignore for now
            }
        }
    }

    private fun fetchServiceCategories() {
        viewModelScope.launch {
            try {
                val response = locationApi.getServiceCategories()
                if (response.isSuccessful && response.body() != null) {
                    _serviceCategories.value = response.body()!!
                }
            } catch (e: Exception) {
                // Ignore for now
            }
        }
    }

    fun registerCustomer(request: RegisterRequest) {
        viewModelScope.launch {
            _registrationState.value = UiState.Loading
            try {
                val response = authApi.register(request)
                if (response.isSuccessful && response.body() != null) {
                    val body = response.body()!!
                    _registrationState.value = UiState.Success(
                        RegistrationResult(
                            phone = body.data?.phone ?: request.phone,
                            message = body.message ?: "Registration successful"
                        )
                    )
                } else {
                    // Try to parse the error message
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = try {
                        if (errorBody != null) {
                            val parsedError = com.google.gson.Gson().fromJson(errorBody, com.birdwastemanagement.app.data.remote.dto.ErrorResponse::class.java)
                            parsedError.message ?: "Registration failed"
                        } else {
                            "Registration failed"
                        }
                    } catch (e: Exception) {
                        "Registration failed"
                    }
                    _registrationState.value = UiState.Error(errorMessage)
                }
            } catch (e: java.net.UnknownHostException) {
                _registrationState.value = UiState.Error("No internet connection. Please connect to the internet and try again.")
            } catch (e: java.net.ConnectException) {
                _registrationState.value = UiState.Error("No internet connection. Please connect to the internet and try again.")
            } catch (e: Exception) {
                _registrationState.value = UiState.Error(e.localizedMessage ?: "Network Error")
            }
        }
    }
}
