@file:Suppress("DEPRECATION")

package com.example.infosys.View.Fragment

import android.content.Context
import android.net.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.infosys.Model.DataResponse
import com.example.infosys.R
import com.example.infosys.Utilities.InjectorUtils
import com.example.infosys.View.Adapter.DataAdapter
import com.example.infosys.ViewModel.CountryViewModel
import com.example.infosys.ViewModel.RoomDataViewModel
import com.example.infosys.databinding.FragmentInnovatBinding
import org.koin.android.viewmodel.ext.android.getViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [InnovatFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [InnovatFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InnovatFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    var isConnected: Boolean = false
    var monitoringConnectivity: Boolean = false
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    private lateinit var binding: FragmentInnovatBinding
    lateinit var countryViewModel: CountryViewModel
    private lateinit var roomUser: DataResponse

    var activity = getActivity() as? Context

    private val roomDataViewModel: RoomDataViewModel by viewModels {
        InjectorUtils.provideRoomDataViewModelFactory(requireActivity())
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (isOnline(requireActivity()) && roomDataViewModel.getDataCountFromRoomVM == 0 ) {
            binding.swipeRefresh.isRefreshing = true

            countryViewModel.getCanadaDetailsOrie().observe(viewLifecycleOwner, Observer(function = fun(canadaList: DataResponse?) {
                    canadaList?.let {
                        binding.swipeRefresh.isRefreshing = false
                        requireActivity().title = canadaList.title
                        val dataAdapter =
                            DataAdapter(
                                canadaList.rows,
                                requireActivity()
                            )
                        binding.canadaRecyclerView.adapter = dataAdapter

                        // store data in room
                        if (roomDataViewModel.getDataCountFromRoomVM > 0) {
                            roomDataViewModel.updateDataToRoomVM(canadaList.title, canadaList.rows, 1)

                        } else {
                            roomUser = DataResponse(0, canadaList.title, canadaList.rows)
                            roomDataViewModel.insertAllDataToRoomVM(roomUser)
                        }
                    }
                })
            )

            countryViewModel.toastError.observe(viewLifecycleOwner, Observer { res ->
                if (res != null) {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(activity, res, Toast.LENGTH_LONG).show()
                }

            })

        } else {
            binding.progressBar.visibility = View.GONE

            if (roomDataViewModel.getDataCountFromRoomVM > 0) this.roomDataViewModel.getAllDataFromRoomVM.observe(viewLifecycleOwner, Observer { res ->
                roomUser = res
                Log.i("getAllRoom", roomUser.title)
                requireActivity().title = roomUser.title
                val dataAdapter = DataAdapter(
                        roomUser.rows,
                        requireActivity()
                    )
                binding.canadaRecyclerView.adapter = dataAdapter

            }) else {
                Toast.makeText(activity, resources.getString(R.string.no_internet), Toast.LENGTH_LONG).show()
                val connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager.activeNetworkInfo
                isConnected = networkInfo != null && networkInfo.isConnected
                if (!isConnected) {
                    connectivityManager.registerNetworkCallback(
                        NetworkRequest.Builder()
                            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                            .build(), connectivityCallback
                    )
                    monitoringConnectivity = true
                }
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        countryViewModel = getViewModel<CountryViewModel>()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_innovat, container, false)
        binding.setLifecycleOwner(this)
        binding.canadaRecyclerView.layoutManager = LinearLayoutManager(
            activity,
            LinearLayoutManager.VERTICAL, false
        )
        binding.swipeRefresh.setOnRefreshListener(this)
        binding.swipeRefresh.setColorSchemeResources(
            R.color.colorPrimary,
            android.R.color.holo_green_dark,
            android.R.color.holo_orange_dark,
            android.R.color.holo_blue_dark
        )
        return binding.root
    }
    fun isOnline(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo != null && networkInfo.isConnected
    }

    override fun onRefresh() {
        if (isOnline(requireActivity()) && roomDataViewModel.getDataCountFromRoomVM == 0 ) {
            countryViewModel.refreshUser()
        } else {
            binding.swipeRefresh.isRefreshing = false
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context

    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment InnovatFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InnovatFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private val connectivityCallback: ConnectivityManager.NetworkCallback =
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isConnected = true
                try {
                    fragmentManager!!.beginTransaction().replace(R.id.details_fragment, InnovatFragment()).commit()
                } catch (e: Exception) {
                }
            }
            override fun onLost(network: Network) {
                isConnected = false
            }
        }
}
