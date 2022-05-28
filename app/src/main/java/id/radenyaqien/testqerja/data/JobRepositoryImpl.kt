package id.radenyaqien.testqerja.data

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import id.radenyaqien.testqerja.data.paging.MyPagingSource
import id.radenyaqien.testqerja.data.remote.IJobApi
import id.radenyaqien.testqerja.domain.model.Job
import id.radenyaqien.testqerja.domain.repository.IJobRepository
import id.radenyaqien.testqerja.util.Constant.ITEMS_PER_PAGE
import id.radenyaqien.testqerja.util.Resource
import id.radenyaqien.testqerja.util.toJob
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject

class JobRepositoryImpl @Inject constructor(
    private val jobApi: IJobApi
) : IJobRepository {
    override fun getAllJob(
        desk: String,
        location: String,
        fullTime: Boolean,
    ): Flow<PagingData<Job>> {

        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                MyPagingSource(
                    jobApi,
                    desk,
                    location,
                    fullTime,
                )
            }
        ).flow


    }

    override suspend fun getJobById(id: String) = flow<Resource<Job>> {
        runCatching {
            jobApi.fetchJob(id)
        }.onSuccess {
            emit(Resource.Success(it.toJob()))
        }.onFailure {
            Log.e(it.localizedMessage, "safeApiCall2: " + it.localizedMessage)
            when (it) {
                is HttpException -> {
                    emit(
                        Resource.Error(
                            "Invalid Id"
                        )
                    )
                }
                else -> {
                    emit(Resource.Error("Terjadi kesalahan"))
                }
            }

        }
    }.flowOn(Dispatchers.IO)


}