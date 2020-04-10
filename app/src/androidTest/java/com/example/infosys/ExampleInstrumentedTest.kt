package com.example.infosys

import android.content.Context
import android.net.ConnectivityManager
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.infosys.Model.DataResponse
import com.example.infosys.Network.mainModule
import com.example.infosys.Repository.DataRepository
import com.example.infosys.View.Fragment.InnovatFragment
import com.example.infosys.ViewModel.CountryViewModel
import org.hamcrest.CoreMatchers
import org.junit.*
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext
import org.koin.standalone.inject
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    lateinit var connectivityManager:ConnectivityManager


    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.infosys", appContext.packageName)
    }

    // Test to check the number of items in the adapter
    @Test
    fun countProgramsWithViewAssertion() {
       launchFragmentInContainer<InnovatFragment>()
        Espresso.onView(ViewMatchers.withId(R.id.canada_recyclerView))
            .check(CustomAssertions.hasItemCount(14))
    }

    //Test for Network check
    @Before
    fun beforeNetworkCheck(){
        connectivityManager = getApplicationContext<Context>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

         }

    @Test
    fun networkCheck(){
        assertTrue(connectivityManager.activeNetworkInfo!=null && connectivityManager.activeNetworkInfo.isConnected)
    }





}
