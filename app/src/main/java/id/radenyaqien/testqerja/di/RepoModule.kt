package id.radenyaqien.testqerja.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.radenyaqien.testqerja.data.JobRepositoryImpl
import id.radenyaqien.testqerja.domain.repository.IJobRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun bindAkunRepository(repository: JobRepositoryImpl): IJobRepository
}