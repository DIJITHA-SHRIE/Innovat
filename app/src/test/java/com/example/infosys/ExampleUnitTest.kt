package com.example.infosys

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.infosys.Model.DataResponse
import com.example.infosys.Network.mainModule
import com.example.infosys.Repository.DataRepository
import com.example.infosys.ViewModel.CountryViewModel
import org.hamcrest.CoreMatchers
import org.junit.*

import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest: KoinTest {
    val viewModel: CountryViewModel by inject()
    val repository: DataRepository by inject()


    @Mock
    lateinit var uiData: Observer<DataResponse>

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        startKoin(listOf(mainModule))
        }
    @After
    fun cleanUp(){
        stopKoin()
    }

    @Test
    fun testDetail(){

        viewModel.canadaResponseData.observeForever(uiData)
        val value = viewModel.canadaResponseData.value
        Mockito.verify(uiData).onChanged(DataResponse(value!!.id,value!!.title,value!!.rows))
        Assert.assertNotNull(viewModel.canadaResponseData.value)

    }

    @Test
    fun titleTest(){
        Assert.assertThat(viewModel.canadaResponseData.value!!.title,
            CoreMatchers.equalTo("About canada")
        )

    }

}
