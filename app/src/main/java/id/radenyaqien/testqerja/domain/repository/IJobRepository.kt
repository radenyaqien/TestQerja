package id.radenyaqien.testqerja.domain.repository

import androidx.paging.PagingData
import id.radenyaqien.testqerja.domain.model.Job
import id.radenyaqien.testqerja.util.Resource
import kotlinx.coroutines.flow.Flow

interface IJobRepository {

    fun getAllJob(
        desk: String,
        location: String,
        fullTime: Boolean,
    ): Flow<PagingData<Job>>

    suspend fun getJobById(id: String): Flow<Resource<Job>>

}