package com.oelnooc.earthquakesretriever.data.ui.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.oelnooc.earthquakesretriever.R
import com.oelnooc.earthquakesretriever.data.models.EarthquakeClusterItem
import com.oelnooc.earthquakesretriever.data.models.Feature
import com.oelnooc.earthquakesretriever.data.ui.viewmodels.EventMapViewModel
import com.oelnooc.earthquakesretriever.databinding.FragmentEventMapBinding

class EventMapFragment : Fragment(), OnMapReadyCallback {


    private lateinit var googleMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<EarthquakeClusterItem>
    private lateinit var binding: FragmentEventMapBinding
    private val viewModel: EventMapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEventMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            map.viewTreeObserver.addOnGlobalLayoutListener {
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
                mapFragment?.getMapAsync(this@EventMapFragment)
            }

            filterBtn.setOnClickListener {
                val startDate = startDateEt.text.toString().trim()
                val endDate = endDateEt.text.toString().trim()
                viewModel.filterEarthquakesByDate(startDate, endDate)
                Log.d("click", "me has clickeado")
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 2.0f))

        clusterManager = ClusterManager(requireContext(), googleMap)
        googleMap.setOnCameraIdleListener(clusterManager)
        googleMap.setOnMarkerClickListener(clusterManager)

        viewModel.earthquakeData.observe(viewLifecycleOwner) { features ->
            addEarthquakesToMap(features)
        }

        googleMap.setInfoWindowAdapter(object : GoogleMap.InfoWindowAdapter {
            override fun getInfoWindow(marker: Marker): View? {
                return null
            }

            override fun getInfoContents(marker: Marker): View {
                val earthquake = marker.tag as? Feature ?: return View(requireContext())

                val infoWindowView = layoutInflater.inflate(R.layout.custom_info_window, null)

                val titleTextView = infoWindowView.findViewById<TextView>(R.id.titleTextView)
                val magnitudeTextView = infoWindowView.findViewById<TextView>(R.id.magnitudeTextView)
                val depthTextView = infoWindowView.findViewById<TextView>(R.id.depthTextView)
                val locationTextView = infoWindowView.findViewById<TextView>(R.id.locationTextView)

                titleTextView.text = earthquake.properties.title
                magnitudeTextView.text = "Magnitud: ${earthquake.properties.mag}"
                depthTextView.text = "Profundidad: ${earthquake.properties.dMin} km"
                locationTextView.text = "Lugar: ${earthquake.properties.place}"

                return infoWindowView
            }
        })

        viewModel.fetchEarthquakeData()
    }

    private fun addEarthquakesToMap(features: List<Feature>) {
        for (feature in features) {
            val geometry = feature.geometry
            val location = LatLng(geometry.coordinates[1], geometry.coordinates[0])
            val clusterItem = EarthquakeClusterItem(
                location,
                feature.properties.mag,
                feature.properties.title,
                feature.properties.dMin,
                feature.properties.place
            )

            val markerOptions = MarkerOptions()
                .position(location)
                .title(feature.properties.title)
                .snippet("Magnitud: ${feature.properties.mag}")

            val marker = googleMap.addMarker(markerOptions)
            marker?.tag = feature

            marker?.showInfoWindow()

            clusterManager.addItem(clusterItem)
        }

        clusterManager.cluster()
    }
}