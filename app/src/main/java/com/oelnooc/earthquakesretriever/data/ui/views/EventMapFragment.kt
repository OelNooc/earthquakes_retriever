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
import com.google.maps.android.clustering.ClusterManager
import com.oelnooc.earthquakesretriever.R
import com.oelnooc.earthquakesretriever.data.models.EarthquakeClusterItem
import com.oelnooc.earthquakesretriever.data.models.Feature
import com.oelnooc.earthquakesretriever.data.ui.viewmodels.EventMapViewModel

class EventMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var clusterManager: ClusterManager<EarthquakeClusterItem>
    private val viewModel: EventMapViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_event_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

                val view = layoutInflater.inflate(R.layout.custom_info_window, null)

                val magnitud = "Magnitud: ${earthquake.properties.mag}"

                val profundidad = "Profundidad: ${earthquake.properties.dMin} km"

                val place = "Lugar: ${earthquake.properties.place}"

                Log.d("Magnitud", earthquake.properties.mag.toString())
                Log.d("profundidad", earthquake.properties.dMin.toString())
                Log.d("lugar", earthquake.properties.place)

                view.findViewById<TextView>(R.id.titleTextView).text = earthquake.properties.title
                view.findViewById<TextView>(R.id.magnitudeTextView).text = magnitud
                view.findViewById<TextView>(R.id.depthTextView).text = profundidad
                view.findViewById<TextView>(R.id.locationTextView).text = place

                return view
            }
        })

        viewModel.fetchEarthquakeData()
    }

    private fun addEarthquakesToMap(features: List<Feature>) {
        for (feature in features) {
            val geometry = feature.geometry
            val location = LatLng(geometry.coordinates[1], geometry.coordinates[0])
            val clusterItem = EarthquakeClusterItem(location,
                feature.properties.mag,
                feature.properties.title,
                feature.properties.dMin,
                feature.properties.place)
            clusterManager.addItem(clusterItem)
        }

        clusterManager.cluster()
    }

}