package coding.universe.mojcompose.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@[Module InstallIn(SingletonComponent::class)]
internal interface NetworkBinding {
    @[Binds Singleton]
    fun bindPostRepository(postRepositoryImpl: PostRepositoryImpl) : PostRepository
}