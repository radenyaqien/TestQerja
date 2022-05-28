package id.radenyaqien.testqerja.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.radenyaqien.testqerja.data.remote.IJobApi
import id.radenyaqien.testqerja.domain.model.Job
import id.radenyaqien.testqerja.util.toJob
import javax.inject.Inject

class MyPagingSource @Inject constructor(
    private val api: IJobApi,
    private val desk: String,
    private val location: String,
    private val fullTime: Boolean
) : PagingSource<Int, Job>() {
    override fun getRefreshKey(state: PagingState<Int, Job>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Job> {
        val currentPage = params.key ?: 1
        return try {
            val response = api.fetchJobs(
                description = desk,
                location = location,
                fulltime = fullTime,
                page = currentPage
            )
            val endOfPaginationReached = response.isEmpty()
            if (response.isNotEmpty()) {
                val res = response.filterNotNull()
                LoadResult.Page(
                    data = res.map {
                        it.toJob()
                    },
                    prevKey = if (currentPage == 1) null else currentPage - 1,
                    nextKey = if (endOfPaginationReached) null else currentPage + 1
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}