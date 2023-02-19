package kg.zukhridin.factsandachievementsinsports.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kg.zukhridin.factsandachievementsinsports.repository.FAAISRepository
import kg.zukhridin.factsandachievementsinsports.repository.impl.FAAISRepositoryImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
interface RepoModule {
    @Binds
    @Singleton
    fun bindRepository(impl: FAAISRepositoryImpl): FAAISRepository
}