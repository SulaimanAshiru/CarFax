package net.carfax.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.carfax.api.ApiInterface

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MyHiltEntryPoint {
    var apiInterface: ApiInterface
}