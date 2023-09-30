package com.tarc.edu.etrack.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
//
//        val rootView = inflater.inflate(R.layout.fragment_stationdetail, container, false)
//        val name = arguments?.getString("stationName")
//
//        val notificationsList = mutableListOf<NotificationData>()
//
//        val database = FirebaseDatabase.getInstance()
//        val stationRef = database.getReference("Station/$name") // Adjust the reference path
//
//        stationRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // ... (your existing code)
//
//                val currentTime = getCurrentDeviceTime()
////                val stationOpen = isStationOpen(currentTime, openTime, closeTime)
////
////                // Generate the notification message
////                val notificationMessage = generateNotificationMessage(name, stationOpen)
////
////                notificationsList.add(NotificationData(stationName, message))
////
////                // Create a RecyclerView to display the notifications
////                val recyclerView =
////                    rootView.findViewById<RecyclerView>(R.id.recyclerViewNotification)
////                val adapter = NotificationAdapter(notificationsList)
//                recyclerView.adapter = adapter
//                recyclerView.layoutManager = LinearLayoutManager(requireContext())
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Handle errors
//            }
//        })
//        return rootView
//    }
//
//    private fun generateNotificationMessage(stationName: String, isOpen: Boolean): String {
//        return if (isOpen) {
//            "Station $stationName is open."
//        } else {
//            "Station $stationName is closed."
//        }
//    }
//
//    private fun isStationOpen(currentTime: String, openTime: String, closeTime: String): Boolean {
//        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
//        val currentTimeDate = sdf.parse(currentTime)
//        val openTimeDate = sdf.parse(openTime)
//        val closeTimeDate = sdf.parse(closeTime)
//
//        return currentTimeDate.after(openTimeDate) && currentTimeDate.before(closeTimeDate)
//    }
//
//    private fun getCurrentDeviceTime(): String {
//        val currentTimeMillis = System.currentTimeMillis()
//        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
//        val currentTimeDate = Date(currentTimeMillis)
//        return sdf.format(currentTimeDate)
//    }
        return view
    }
}

