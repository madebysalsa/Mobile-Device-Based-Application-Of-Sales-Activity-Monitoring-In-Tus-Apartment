package com.example.apartemenurbansky;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AbsenPulang extends AppCompatActivity {
    private EditText nama, lokasi, waktu_absen;
    private Button btn_tambah;
    String  nama_absen_pulang, lokasi_absen_pulang, waktu, foto;
    CircleImageView foto_absen;
    Calendar myCalendar = Calendar.getInstance();
    private Bitmap bitmap;
    private static final int CAMERA_REQUEST = 1888;
    private static final int REQUEST_LOCATION = 1;
    Geocoder geocoder;
    List<Address> addresses;
    LocationManager locationManager;
    double lat, longi;
    Uri imageUri;
    private PreferenceHelper preferenceHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absen_pulang);
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        preferenceHelper = new PreferenceHelper(this);
        nama = findViewById(R.id.nama_absen_pulang);
        nama.setText(preferenceHelper.getUsername());
        nama.setFocusableInTouchMode(false);
        lokasi = findViewById(R.id.lokasi_absen_pulang);
        lokasi.setFocusableInTouchMode(false);
        setLokasi();
        waktu_absen = findViewById(R.id.waktu);
        setTanggal();
        waktu_absen.setFocusableInTouchMode(false);
        btn_tambah = findViewById(R.id.btn_tambah);
        foto_absen = findViewById(R.id.foto);

        btn_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nama_absen_pulang = nama.getText().toString();
                lokasi_absen_pulang = lokasi.getText().toString();
                waktu = waktu_absen.getText().toString();
                if (bitmap == null) {
                    foto = "";
                } else {
                    foto = getStringImage(bitmap);
                }

                if(nama_absen_pulang.trim().equals("")){
                    nama.setError("Nama Harus Dipilih");
                } if(lokasi_absen_pulang.trim().equals("")){
                    lokasi.setError("No. Telepon Harus Diisi");
                }  if(waktu.trim().equals("")){
                    waktu_absen.setError("Tanggal Daftar Harus Diisi");
                }  else{
                    createData();
                }
            }
        });


        foto_absen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intentCamera, CAMERA_REQUEST);
                //chooseFile();
            }
        });
    }
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                AbsenPulang.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                AbsenPulang.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                lat = locationGPS.getLatitude();
                longi = locationGPS.getLongitude();
            } else {
                Toast.makeText(this, "Unable to find location.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setLokasi(){
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }

        geocoder = new Geocoder(this, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, longi, 1);

            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getAdminArea();

            String fullAddress = address+", "+city;
            lokasi.setText(fullAddress);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            foto_absen.setImageBitmap(bitmap);

        }
    }

    private void createData(){

        ApiInterface ardData = ApiClient.getApiClient().create(ApiInterface.class);
        Call<DataAbsenPulang> call = ardData.addAbsenPulang("insert", nama_absen_pulang, lokasi_absen_pulang, waktu, foto);

        call.enqueue(new Callback<DataAbsenPulang>() {
            @Override
            public void onResponse(Call<DataAbsenPulang> call, Response<DataAbsenPulang> response) {
                String kode = response.body().getValue();
                String pesan = response.body().getMassage();

                Toast.makeText(AbsenPulang.this, "Kode : "+kode+" | Pesan : "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<DataAbsenPulang> call, Throwable t) {
                Toast.makeText(AbsenPulang.this, "Gagal Menghubungi Server | "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void setTanggal() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        waktu_absen.setText(sdf.format(myCalendar.getTime()));
    }
}
