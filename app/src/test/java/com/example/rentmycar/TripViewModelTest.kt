//package com.example.rentmycar
//
//import com.example.rentmycar.viewmodel.TripViewModel
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.runner.RunWith
//import org.junit.runners.JUnit4
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
//
//@RunWith(JUnit4::class)
//class TripViewModelTest {
////    @get:Rule
////    val instantTaskExecutorRule = InstantTaskExecutorRule()
//
//    @Mock
//    lateinit var tripViewModel: TripViewModel
//
//    @Before
//    fun setUp() {
//        MockitoAnnotations.initMocks(this)
//        this.tripViewModel = TripViewModel()
//    }
//
//    @Test
//    fun test() {
//        // Mock API response
//        Mockito.`when`(this.userService.getRepositories(ArgumentMatchers.anyString())).thenAnswer {
//            return@thenAnswer Maybe.just(ArgumentMatchers.anyList<Repository>())
//        }
//        // Attach fake observer
//        val observer = mock(Observer::class.java) as Observer<LiveDataResult<List<Repository>>>
//        this.mainViewModel.repositoriesLiveData.observeForever(observer)
//        // Invoke
//        this.mainViewModel.fetchUserRepositories(ArgumentMatchers.anyString())
//        // Verify
//        assertNotNull(this.mainViewModel.repositoriesLiveData.value)
//        assertEquals(LiveDataResult.Status.SUCCESS, this.mainViewModel.repositoriesLiveData.value?.status)
//    }
//}