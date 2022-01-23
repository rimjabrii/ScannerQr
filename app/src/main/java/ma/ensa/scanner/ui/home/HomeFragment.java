package ma.ensa.scanner.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ma.ensa.scanner.LoginActivity;
import ma.ensa.scanner.MainActivity;
import ma.ensa.scanner.R;
import ma.ensa.scanner.databinding.FragmentHomeBinding;
import ma.ensa.scanner.ui.gallery.GalleryFragment;
import ma.ensa.scanner.ui.slideshow.SlideshowFragment;

public class HomeFragment extends Fragment {
    //vars
    public static final int CAMERA_PERMISSION_CODE = 100;

    //widgets
    private Button camera;
    private Button generate;
    private Button scan;
    Button  signOut;
    private FirebaseAuth auth;

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        auth = FirebaseAuth.getInstance();

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




        signOut = getView().findViewById(R.id.sign_out);

        //camera = getView().findViewById(R.id.camera);
        //generate = getView().findViewById(R.id.generate);
        //scan = getView().findViewById(R.id.scan);

        /*scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), SlideshowFragment.class);
                startActivity(intent);
            }
        });*/
       /* camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
            }
        });*/
        /*generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), GalleryFragment.class);
                startActivity(intent);
            }
        });*/

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();

            }
        });


    }
    public void checkPermission(String permission, int requestCode){
        if(ContextCompat.checkSelfPermission(getActivity(), permission)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(getActivity(), new String[] {permission},
                    requestCode);
        }
        else{
            Toast.makeText(getActivity(), "Permission Already Granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(), "Permission Granted", Toast.LENGTH_SHORT).show();
            }
            else{
                Toast.makeText(getActivity(), "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        Intent i=new Intent(getContext(), LoginActivity.class);
        startActivity(i);

    }
}