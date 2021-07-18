package com.turtlemint.assignment.di

import androidx.room.Room
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.turtlemint.assignment.BuildConfig
import com.turtlemint.assignment.data.api.ApiService
import com.turtlemint.assignment.data.db.AppDatabase
import com.turtlemint.assignment.data.repository.CommentRepository
import com.turtlemint.assignment.data.repository.CommentRepositoryImpl
import com.turtlemint.assignment.data.repository.IssueRepository
import com.turtlemint.assignment.data.repository.IssueRepositoryImpl
import com.turtlemint.assignment.ui.comment.CommentViewModel
import com.turtlemint.assignment.ui.issue.IssueViewModel
import com.turtlemint.assignment.util.DATABASE_NAME
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val remoteModules = module {
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .build()
    }

    single(createOnStart = false) { get<Retrofit>().create(ApiService::class.java) }
}

val databaseModules = module {
    single {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java, DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single(createOnStart = false) {
        val database: AppDatabase = get()
        database.issueDao()
    }

    single(createOnStart = false) {
        val database: AppDatabase = get()
        database.commentDao()
    }
}

val repositoryModules = module {
    single<IssueRepository> { IssueRepositoryImpl(get(), get()) }
    single<CommentRepository> { CommentRepositoryImpl(get(), get()) }
}

val viewModelModules = module {
    viewModel { IssueViewModel(get()) }
    viewModel { CommentViewModel(get()) }
}

