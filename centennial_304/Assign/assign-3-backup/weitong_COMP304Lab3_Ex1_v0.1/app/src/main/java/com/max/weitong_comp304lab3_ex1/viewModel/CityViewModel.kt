package com.max.weitong_comp304lab3_ex1.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.max.weitong_comp304lab3_ex1.data.City
import com.max.weitong_comp304lab3_ex1.data.CityRepository
import com.max.weitong_comp304lab3_ex1.view.CityUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CityViewModel(private val cityRepository: CityRepository)  : ViewModel() {
    private val _cityUIState = MutableStateFlow(CityUIState())
    val cityUIState: StateFlow<CityUIState> = _cityUIState
    init {
        loadUsers() // ViewModel 初始化时加载数据
    }
    fun loadUsers() {
        viewModelScope.launch {
            _cityUIState.value = CityUIState(isLoading = true)
            try {
                val cityList = cityRepository.getCity()
                _cityUIState.value = CityUIState(city = cityList)
            } catch (e: Exception) {
                _cityUIState.value = CityUIState(error = "加载失败")
            }
        }
    }
    fun getCity(id: String): City {
        var item:City? = _cityUIState.value.city.find { it.id == id.toInt() };
        if(item ==null){
            item = City(1, "Toronto", 43.86103683452462, -79.23287065483638 )
        }
        return item
    }
//    private val repository = cityRepository()
//    val users: StateFlow<List<User>> = repository.getUsers()
//        .stateIn(
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(5000),
//            initialValue = emptyList()
//        )
}
