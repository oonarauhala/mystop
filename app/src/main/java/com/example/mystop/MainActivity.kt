package com.example.mystop

import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

//https://www.raywenderlich.com/2071847-reactive-programming-with-rxandroid-in-kotlin-an-introduction
//TODO make observables disposable
//TODO parse server response
//TODO change listview to recyclerview

class MainActivity : AppCompatActivity() {
    private var PERMISSION_FINE_LOCATION = 1
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val fragmentTimetable = TimetableListFragment()
    private val fragmentMap = MapFragment()
    private lateinit var locationButton: Button
    private var counter = 0

    //create handler
    private val mHandler: Handler = object :
    Handler(Looper.getMainLooper()) {
        override fun handleMessage(inputMessage: Message) {
            if (inputMessage.what == 0) {
                val txt_network = findViewById<TextView>(R.id.txt_network)
                txt_network.text = inputMessage.obj.toString()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        locationButton = findViewById(R.id.button)

        //add fragments
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragmentTimetable, "timetable")
                .add(R.id.fragment_container, fragmentMap, "map")
                .commit()
        }


        //ask for location permission
        // TODO ask permissions for network
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                                                != PackageManager.PERMISSION_GRANTED) {
            //not granted, request
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                                                PERMISSION_FINE_LOCATION)
        } else {
            Log.d("TAG", "onCreate else")
            //permission is granted
            doMainActivity()
        }
    }

    override fun onStart() {
        super.onStart()

        val locationButtonObservable = createButtonClickObservable()
        locationButtonObservable
            .subscribeOn(AndroidSchedulers.mainThread())
            //.observeOn(Schedulers.io())
            //.map { locationButton.text = "${it} ${counter} times" }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                counter += 1
                locationButton.text = "${it} ${counter} times"
            }
    }

    //group functions into one
    private fun doMainActivity() {
        getLocation()
        openConnection()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_FINE_LOCATION -> {
                //if request is cancelled, the result array is empty
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //permission was granted, do things with
                    doMainActivity()
                }
                else {
                    //permission denied
                }
            }
        }
    }

    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnCompleteListener(this) {
            task ->
            if (task.isSuccessful && task.result != null){
                Log.d("TAG", "${task.result?.longitude}")
            }
        }
    }

    private fun openConnection() {
        if (isNetworkAvailable()) {
            val myRunnable = Conn(mHandler)
            val myThread = Thread(myRunnable)
            myThread.start()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE)
            as ConnectivityManager
        return connectivityManager.activeNetworkInfo?.isConnected?:false
    }

    private fun createButtonClickObservable(): Observable<String> {
        return  Observable.create {emitter ->
            locationButton.setOnClickListener {
                emitter.onNext("Button was clicked")
            }
            emitter.setCancellable {
                locationButton.setOnClickListener(null)
            }
        }
    }
}

