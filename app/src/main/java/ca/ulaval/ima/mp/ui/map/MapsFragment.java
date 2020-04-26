package ca.ulaval.ima.mp.ui.map;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import ca.ulaval.ima.mp.R;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;



public class MapsFragment extends Fragment implements OnMapReadyCallback{

    MapView mapView;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        View root = inflater.inflate(R.layout.fragment_maps, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().show();


        // L'API de google n'a jamais fonctionner dans notre cas.
//        mapView = root.findViewById(R.id.mapview);
//        mapView.onCreate(savedInstanceState);
//        mapView.getMapAsync(this);

        return root;
    }


    @Override
    public void onMapReady(GoogleMap map) {
        LatLng ulaval = new LatLng(46.779156, -71.276083);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(ulaval, 15));

        map.addMarker(new MarkerOptions()
                .title("ULaval")
                .snippet("Vous êtes ici pour toujours ;-)")
                .position(ulaval));
    }




}







