package edu.cmu.west.schenleyquest;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import android.location.Location;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class DirectionHint extends FragmentActivity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {

	/*
     * Define a request code to send to Google Play services
     * This code is returned in Activity.onActivityResult
     */
    public final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    
 // Debugging tag for the application
    public static final String APPTAG = "SchenleyQuest";
    
 // Stores the current instantiation of the location client in this object
    private LocationClient mLocationClient;
    
    private Location mCurrentLocation;
    
    private GoogleMap mMap;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mLocationClient = new LocationClient(this, this, this);
		setContentView(R.layout.activity_direction_hint);
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	}
	
	/*
     * Called when the Activity becomes visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
        // Connect the client.
        mLocationClient.connect();
        
    }
    
    /*
     * Called when the Activity is no longer visible.
     */
    @Override
    protected void onStop() {
        // Disconnecting the client invalidates it.
        mLocationClient.disconnect();
        super.onStop();
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.direction_hint, menu);
		return true;
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // Choose what to do based on the request code
        switch (requestCode) {

            // If the request code matches the code sent in onConnectionFailed
            case CONNECTION_FAILURE_RESOLUTION_REQUEST :

                switch (resultCode) {
                    // If Google Play services resolved the problem
                    case Activity.RESULT_OK:

                        // Log the result
                        Log.d(APPTAG, getString(R.string.resolved));

                    break;

                    // If any other result was returned by Google Play services
                    default:
                        // Log the result
                        Log.d(APPTAG, getString(R.string.no_resolution));

                    break;
                }

            // If any other request code was received
            default:
               // Report that this Activity received an unknown requestCode
               Log.d(APPTAG,
                       getString(R.string.unknown_activity_request_code, requestCode));

               break;
        }
    }

	/*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle bundle) { 
    	//TextView currentLocationText =(TextView)findViewById(R.id.textViewCurrentLocation);
    	mCurrentLocation = mLocationClient.getLastLocation();
    	CameraPosition cameraPosition = new CameraPosition.Builder()
        .target(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()))      // Sets the center of the map to Mountain View
        .zoom(14)                   // Sets the zoom
        .bearing(90)                // Sets the orientation of the camera to east
        .tilt(0)                   // Sets the tilt of the camera to 30 degrees
        .build();                   // Creates a CameraPosition from the builder
    	mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    	
    	mMap.addMarker(new MarkerOptions()
        .position(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()))
        .title("You are here")
        .snippet("SchenleyQuest")
        .icon(BitmapDescriptorFactory.defaultMarker(
                  BitmapDescriptorFactory.HUE_AZURE))).showInfoWindow();
    	
    	mMap.addMarker(new MarkerOptions()
        .position(new LatLng(40.44219345, -79.9504763))
        .title("Your destination")
        .snippet("SchenleyQuest")
        .icon(BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_RED))).showInfoWindow();
    	
    	mMap.addPolyline(new PolylineOptions()
    	.add(new LatLng(40.44219345, -79.9504763), new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())).width(5).color(Color.RED));
    	
    	
		float[] results = new float[1];
		Location.distanceBetween(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude(), 40.44219345, -79.9504763, results);
		//currentLocationText.setText(String.valueOf(results[0]));
    }

    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onDisconnected() { }

    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {

                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this, CONNECTION_FAILURE_RESOLUTION_REQUEST);

                /*
                * Thrown if Google Play services canceled the original
                * PendingIntent
                */

            } catch (IntentSender.SendIntentException e) {

                // Log the error
                e.printStackTrace();
            }
        } else {

            // If no resolution is available, display a dialog to the user with the error.
            showErrorDialog(connectionResult.getErrorCode());
        }
    }
    
    /**
     * Define a DialogFragment to display the error dialog generated in
     * showErrorDialog.
     */
    public static class ErrorDialogFragment extends DialogFragment {

        // Global field to contain the error dialog
        private Dialog mDialog;

        /**
         * Default constructor. Sets the dialog field to null
         */
        public ErrorDialogFragment() {
            super();
            mDialog = null;
        }

        /**
         * Set the dialog to display
         *
         * @param dialog An error dialog
         */
        public void setDialog(Dialog dialog) {
            mDialog = dialog;
        }

        /*
         * This method must return a Dialog to the DialogFragment.
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return mDialog;
        }
    }
    
    /**
     * Show a dialog returned by Google Play services for the
     * connection error code
     *
     * @param errorCode An error code returned from onConnectionFailed
     */
    private void showErrorDialog(int errorCode) {

        // Get the error dialog from Google Play services
        Dialog errorDialog = GooglePlayServicesUtil.getErrorDialog(
            errorCode,
            this,
            CONNECTION_FAILURE_RESOLUTION_REQUEST);

        // If Google Play services can provide an error dialog
        if (errorDialog != null) {

            // Create a new DialogFragment in which to show the error dialog
            ErrorDialogFragment errorFragment = new ErrorDialogFragment();

            // Set the dialog in the DialogFragment
            errorFragment.setDialog(errorDialog);

            // Show the error dialog in the DialogFragment
            errorFragment.show(getSupportFragmentManager(), APPTAG);
        }
    }

}
