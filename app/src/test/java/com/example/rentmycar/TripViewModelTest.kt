package com.example.rentmycar

import com.example.rentmycar.adapter.TripAdapter
import com.example.rentmycar.model.Trip
import com.example.rentmycar.viewmodel.TripViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.*

@RunWith(JUnit4::class)
class TripViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var tripAdapter: TripAdapter
    lateinit var tripViewModel: TripViewModel

    @Before
    fun setUp() {
        val trips = listOf(
            Trip(1, null, null, null, null, null, null, null),
            Trip(2, null, null, null, null, null, null, null),
            Trip(3, null, null, null, null, null, null, null)
        )
        val tripsMutable = MutableLiveData<List<Trip>>()
        tripsMutable.value = trips.toMutableList()

        tripViewModel = mock<TripViewModel> {
            on { getTrips() } doReturn tripsMutable
        }

        val tripList = tripViewModel.getTrips().value!!

        tripAdapter = TripAdapter(null, tripList)
    }

    @Test
    fun tripAdapterCountList() {
        assertEquals(3, tripAdapter.getItemCount())
    }

    @Test
    fun tripAdapterGetList() {
        assertEquals(3, tripAdapter.getList().size)
    }

    @Test
    fun tripViewModelGetTrips() {
        assertEquals(3, tripViewModel.getTrips().value!!.size)
    }
}