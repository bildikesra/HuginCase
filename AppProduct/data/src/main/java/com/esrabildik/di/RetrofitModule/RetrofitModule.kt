package com.esrabildik.di.RetrofitModule

import com.esrabildik.domain.repository.ProductRepository
import com.esrabildik.domain.usecase.GetProductByIdUseCase
import com.esrabildik.domain.usecase.GetProductCategoryName
import com.esrabildik.domain.usecase.GetProductUseCase
import com.esrabildik.domain.util.BASE_URL
import com.esrabildik.data.remote.ProductApi
import com.esrabildik.repository.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    //https://dummyjson.com/products
    @Provides
    fun provideProductApi() : ProductApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApi::class.java)
    }

    @Provides
    fun provideProductRepository(api : ProductApi) : ProductRepository {
        return ProductRepositoryImpl(api)
    }

    @Provides
    fun provideGetProductsUseCase(repository: ProductRepository) : GetProductUseCase {
        return GetProductUseCase(repository)
    }

    @Provides
    fun provideGetProductByIdUseCase(repository: ProductRepository) : GetProductByIdUseCase {
        return GetProductByIdUseCase(repository)
    }

    @Provides
    fun provideGetProductByName(repository: ProductRepository) : GetProductCategoryName {
        return GetProductCategoryName(repository)
    }
}