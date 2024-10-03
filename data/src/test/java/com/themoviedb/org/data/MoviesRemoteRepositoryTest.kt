package com.themoviedb.org.data

import com.themoviedb.org.data.data_source.local.MoviesLocalDataSource
import com.themoviedb.org.data.data_source.remote.MoviesApiService
import com.themoviedb.org.data.data_source.remote.MoviesRemoteDataSourceImpl
import com.themoviedb.org.data.repository.MoviesRepositoryImpl

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesRemoteRepositoryTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: MoviesApiService
    private lateinit var moviesRemoteDataSourceImpl: MoviesRemoteDataSourceImpl

    val moviesLocalDataSource: MoviesLocalDataSource = mock()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        // Initialize MockWebServer
        mockWebServer = MockWebServer()

        // Create Retrofit client with mock URL from MockWebServer
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }).build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApiService::class.java)

        // Create a repository with a mocked ApiService
        moviesRemoteDataSourceImpl = MoviesRemoteDataSourceImpl(apiService)

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()  // Stopping MockWebServer
        Dispatchers.resetMain()   // Resetting the test dispatcher
    }

    @Test
    fun testGetMoviesFlow() = runTest {

        val mockResponse = MockResponse()
            .setBody(testResponse)  // JSON response from server
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        val moviesRepositoryImpl = MoviesRepositoryImpl(moviesRemoteDataSourceImpl, moviesLocalDataSource)
        // Get the result from Flow
        val result = moviesRepositoryImpl.searchMovies("te").toList()

        val actualMoviesList = result.get(1).data
        // Compare the result with the expected one
        assertEquals(1, actualMoviesList?.size)
        assertEquals(1205594, actualMoviesList?.get(0)?.id)
        assertEquals(0.088f, actualMoviesList?.get(0)?.popularity)
        assertEquals("Eva. Hero Girl.", actualMoviesList?.get(0)?.title)

    }

    @Test
    fun testGetMoviesError() = runTest {
        // Simulate server error (code 500)
        val mockResponse = MockResponse()
            .setResponseCode(500)
            .setBody("""{"error": "Server Error"}""")

        mockWebServer.enqueue(mockResponse)

        val moviesRepositoryImpl = MoviesRepositoryImpl(moviesRemoteDataSourceImpl, moviesLocalDataSource)
        val result = moviesRepositoryImpl.searchMovies("te").toList()
        assertEquals("Server Error", result.get(1).message)
    }

    val testResponse = "{\n" +
            "    \"page\": 1,\n" +
            "    \"results\": [\n" +
            "        {\n" +
            "            \"adult\": false,\n" +
            "            \"backdrop_path\": null,\n" +
            "            \"genre_ids\": [\n" +
            "                99\n" +
            "            ],\n" +
            "            \"id\": 1205594,\n" +
            "            \"original_language\": \"kk\",\n" +
            "            \"original_title\": \"Ева. Батыр қыз\",\n" +
            "            \"overview\": \"Eva Zhigalina became a trailblazer in the world of Kokpar in Kazakhstan – a traditional male game of nomadic peoples. In a traditional society where the participation of women in Kokpar has been prohibited, Eva overcomes social and cultural barriers to pursue her passion for this brutal game.\",\n" +
            "            \"popularity\": 0.088,\n" +
            "            \"poster_path\": \"/dGqV3CL751zY9DiruUGweEocvJc.jpg\",\n" +
            "            \"release_date\": \"2023-11-18\",\n" +
            "            \"title\": \"Eva. Hero Girl.\",\n" +
            "            \"video\": false,\n" +
            "            \"vote_average\": 0.0,\n" +
            "            \"vote_count\": 0\n" +
            "        }\n" +
            "    ],\n" +
            "    \"total_pages\": 1,\n" +
            "    \"total_results\": 1\n" +
            "}"


}