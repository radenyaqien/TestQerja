package id.radenyaqien.testqerja.ui.detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.radenyaqien.testqerja.domain.model.Job
import id.radenyaqien.testqerja.domain.repository.IJobRepository
import id.radenyaqien.testqerja.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val jobRepository: IJobRepository
) : ViewModel() {
    private val _model: MutableState<Job?> = mutableStateOf(null)
    val model get() = _model.value
    fun getdetail(id: String) = viewModelScope.launch {
        val result = jobRepository.getJobById(id)

        result.collect {
            when (it) {
                is Resource.Error -> {}
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    _model.value = it.data
                }
            }
        }

    }
}