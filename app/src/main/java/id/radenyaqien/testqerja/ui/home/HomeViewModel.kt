package id.radenyaqien.testqerja.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import id.radenyaqien.testqerja.domain.model.Job
import id.radenyaqien.testqerja.domain.repository.IJobRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val jobRepo: IJobRepository
) : ViewModel() {
    private val _pagingdata: MutableStateFlow<PagingData<Job>> =
        MutableStateFlow(PagingData.empty())
    val pagingdata: Flow<PagingData<Job>>
        get() = _pagingdata
    private val _effect: Channel<HomeEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        getdata()
    }

    private fun getdata(
        desc: String = "",
        location: String = "",
        fullTime: Boolean = false
    ) = viewModelScope.launch {
        jobRepo.getAllJob(desc, location, fullTime)
            .cachedIn(viewModelScope)
            .collect {
                _pagingdata.value = it
            }
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Navigate -> {
                _effect.trySend(HomeEffect(event.id))
            }
            is HomeEvent.GetData -> {
                getdata(desc = event.desc, event.location, event.fullTime)
            }
        }
    }
}

sealed class HomeEvent {
    data class Navigate(val id: String) : HomeEvent()
    data class GetData(
        val desc: String,
        val location: String,
        val fullTime: Boolean
    ) : HomeEvent()
}