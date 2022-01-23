package ma.ensa.scanner.ui.slideshow;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import ma.ensa.scanner.Capture;
import ma.ensa.scanner.MainActivity;
import ma.ensa.scanner.Occupation;
import ma.ensa.scanner.R;
import ma.ensa.scanner.RetrofitAPI;
import ma.ensa.scanner.databinding.FragmentSlideshowBinding;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SlideshowFragment extends Fragment {
    Button bt_scan;


    private FragmentSlideshowBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        return root;
    }

    public void onActivityCreated(Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);
        bt_scan=getView().findViewById(R.id.bt_scan);
        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = IntentIntegrator.forSupportFragment(SlideshowFragment.this);

                integrator.setOrientationLocked(false);
                integrator.setPrompt("Scan QR code");
                integrator.setBeepEnabled(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);


                integrator.initiateScan();
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult =IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        Calendar c= Calendar.getInstance();
        c.setTime(new Date());
        SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
        int h=8;
        int m=25;
        String a = "";
        if (h<10 && m<25) {
            a="8:25 to 10:25";


        } else if (h<12 && m<20) {
            a="10:30 to 12:20";

        }else if (h<15 && m<20) {
            a="13:30 to 15:20";

        } else if (h<17 && m<20) {
            a="15:30 to 17:20";

        }
        else{
            a="8:25 to 10:25";
        }


        if(intentResult.getContents()!=null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            builder.setIcon(R.mipmap.scanor);

            builder.setTitle("Vos Qr Informations:");
            builder.setMessage("intentResult.getContents()");
            builder.setMessage("Vous avez choisis  la Salle :   "+intentResult.getContents().replace("\"","")+"\n"+"\n"+"Pour le créneau :  "+a);
            String finalA = a;
            String finalA1 = a;
            String finalA2 = a;
            builder.setPositiveButton("Occuper", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                    String namesalle=intentResult.getContents().replace("\"","");
                    String crenauhr= finalA2;

                    postData(date,namesalle,crenauhr);


                }
            });
            builder.create().show();
        }else{
            Toast.makeText(getContext(), "ooops", Toast.LENGTH_SHORT).show();

        }
    }
    private void postData(String date, String namesalle,String crenauhr) {

        // below line is for displaying our progress bar.


        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.114.141:3000/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);



        // passing data from our text fields to our modal class.
        Occupation occupation = new Occupation(date,namesalle,crenauhr);

        // calling a method to create a post and passing our modal class.
        Call<Occupation> call = retrofitAPI.createPost(occupation);

        // on below line we are executing our method.
        call.enqueue(new Callback<Occupation>() {
            @Override
            public void onResponse(Call<Occupation> call, Response<Occupation> response) {
                // this method is called when we get response from our api.
                Toast.makeText(getActivity(), "Data added to API", Toast.LENGTH_SHORT).show();

                // below line is for hiding our progress bar.


                // on below line we are setting empty text
                // to our both edit text.


                // we are getting response from our body
                // and passing it to our modal class.
                Occupation responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\n"+"date : " + responseFromAPI.getDate()+ "\n" + "namesalle : " + responseFromAPI.getNamesalle() + "\n" + "crenauhr : " + responseFromAPI.getCrenauhr();
                Toast.makeText(getContext(), "You Occupied This Class Successfully", Toast.LENGTH_SHORT).show();
                // below line we are setting our
                // string to our text view.

            }

            @Override
            public void onFailure(Call<Occupation> call, Throwable t) {
                Toast.makeText(getContext(), "Verify Your Network ", Toast.LENGTH_SHORT).show();
                // setting text to our text view when
                // we get error response from API.

            }
        });
    }

        public void onDestroyView() {
        super.onDestroyView();
        binding = null;

}
}