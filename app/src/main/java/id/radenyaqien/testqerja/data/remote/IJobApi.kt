package id.radenyaqien.testqerja.data.remote

import id.radenyaqien.testqerja.data.remote.Dto.JobsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IJobApi {


    @GET("positions.json")
    suspend fun fetchJobs(
        @Query("description") description: String,
        @Query("location") location: String,
        @Query("full_time") fulltime: Boolean,
        @Query("page") page: Int
    ): List<JobsDto?>

    @GET("positions/{ID}")
    suspend fun fetchJob(
        @Path("ID") id: String
    ): JobsDto
}