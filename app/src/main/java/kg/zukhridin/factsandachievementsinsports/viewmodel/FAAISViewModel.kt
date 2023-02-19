package kg.zukhridin.factsandachievementsinsports.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kg.zukhridin.factsandachievementsinsports.dto.FAAISDto
import kg.zukhridin.factsandachievementsinsports.repository.FAAISRepository
import kg.zukhridin.factsandachievementsinsports.repository.impl.FAAISRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FAAISViewModel @Inject constructor(private val repository: FAAISRepository) : ViewModel() {
    val faais: LiveData<List<FAAISDto>> = repository.faais
    fun insert(faaisDto: FAAISDto) = viewModelScope.launch {
        repository.insertFAAIS(faaisDto)
    }
}