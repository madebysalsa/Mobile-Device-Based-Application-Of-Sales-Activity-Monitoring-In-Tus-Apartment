package com.example.apartemenurbansky;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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


public class EditCanvasing extends AppCompatActivity {
    private EditText mName, mBreed, mBirth, mSpecies;
    private CircleImageView mPicture;
    private FloatingActionButton mFabChoosePic;

    Calendar myCalendar = Calendar.getInstance();
    private static final int REQUEST_LOCATION = 1;
    Geocoder geocoder;
    List<Address> addresses;
    LocationManager locationManager;
    double lat, longi;
    //private int mGender = 0;
    //public static final int GENDER_UNKNOWN = 0;
    //public static final int GENDER_MALE = 1;
    //public static final int GENDER_FEMALE = 2;

    private String name, species, breed, picture, birth, jenis_event;
    private int id, daftar_event;

    private Menu action;
    private Bitmap bitmap;
    private PreferenceHelper preferenceHelper;
    private ApiInterface apiInterface;
    private Spinner gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_canvasing);
        ActivityCompat.requestPermissions( this,
                new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        preferenceHelper = new PreferenceHelper(this);
        mName = findViewById(R.id.name);
        mName.setText(preferenceHelper.getUsername());
        mBreed = findViewById(R.id.breed);
        mBirth = findViewById(R.id.birth);
        mPicture = findViewById(R.id.picture);
        mFabChoosePic = findViewById(R.id.fabChoosePic);
        gender = findViewById(R.id.jenis_kegiatan);
        mBirth = findViewById(R.id.birth);
        mSpecies = findViewById(R.id.species);

        mName.setFocusableInTouchMode(false);
        mBreed.setFocusableInTouchMode(false);
        mBirth.setFocusableInTouchMode(false);
        mBirth.setFocusable(true);

        setBirth();
        setmBreed();


        mFabChoosePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });


        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        name = intent.getStringExtra("name");
        species = intent.getStringExtra("species");
        breed = intent.getStringExtra("breed");
        birth = intent.getStringExtra("birth");
        picture = intent.getStringExtra("picture");
        jenis_event = intent.getStringExtra("gender");

        setDataFromIntentExtra();

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
                EditCanvasing.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                EditCanvasing.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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

    private void setmBreed(){
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
            mBreed.setText(fullAddress);
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private void setSpinnerPost(){
        daftar_event = gender.getSelectedItemPosition();
        String key = "insert";

        if(daftar_event == 1){
            jenis_event = "Pameran";
            postData(key);
        } else if(daftar_event == 2){
            jenis_event = "Event Kawasan";
            postData(key);
        } else if(daftar_event == 3){
            jenis_event = "Canvasing";
            postData(key);
        } else if(daftar_event == 4){
            jenis_event = "Flyring";
            postData(key);
        } else if(daftar_event == 5){
            jenis_event = "Follow Up Database";
            postData(key);
        } else if(daftar_event == 6){
            jenis_event = "Digital";
            postData(key);
        } else if(daftar_event == 7){
            jenis_event = "Call In";
            postData(key);
        } else if(daftar_event == 8){
            jenis_event = "Guest In";
            postData(key);
        } else {
            Toast.makeText(EditCanvasing.this, "Pilih Event Terlebih Dahulu", Toast.LENGTH_SHORT).show();
        }
    }


    private void setDataFromIntentExtra() {

        if (id != 0) {

            readMode();
            getSupportActionBar().setTitle("Edit " + name.toString());

            mName.setText(name);
            mBreed.setText(breed);
            mBirth.setText(birth);
            mSpecies.setText(species);

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.skipMemoryCache(true);
            requestOptions.diskCacheStrategy(DiskCacheStrategy.NONE);
            requestOptions.placeholder(R.drawable.logo);
            requestOptions.error(R.drawable.logo);

            Glide.with(EditCanvasing.this)
                    .load(picture)
                    .apply(requestOptions)
                    .into(mPicture);

            if(jenis_event.equals("Pameran")) {
                gender.setSelection(1);
            } else if(jenis_event.equals("Event Kawasan")) {
                gender.setSelection(2);
            } else if(jenis_event.equals("Canvasing")) {
                gender.setSelection(3);
            } else if(jenis_event.equals("Flyring")) {
                gender.setSelection(4);
            } else if(jenis_event.equals("Follow Up Database")) {
                gender.setSelection(5);
            } else if(jenis_event.equals("Digital")) {
                gender.setSelection(6);
            } else if(jenis_event.equals("Call In")) {
                gender.setSelection(7);
            } else if(jenis_event.equals("Guest In")) {
                gender.setSelection(8);
            } else {
                gender.setSelection(0);
            }


        } else {
        editMode();
        setmBreed();
        setBirth();
            getSupportActionBar().setTitle("Tambah Kegiatan Sales");
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_editor, menu);
        action = menu;
        action.findItem(R.id.menu_save).setVisible(false);

        if (id == 0){

            action.findItem(R.id.menu_edit).setVisible(false);
            action.findItem(R.id.menu_delete).setVisible(false);
            action.findItem(R.id.menu_save).setVisible(true);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                this.finish();

                return true;
            case R.id.menu_edit:
                //Edit

                editMode();

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mName, InputMethodManager.SHOW_IMPLICIT);

                action.findItem(R.id.menu_edit).setVisible(false);
                action.findItem(R.id.menu_delete).setVisible(false);
                action.findItem(R.id.menu_save).setVisible(true);

                return true;
            case R.id.menu_save:
                //Save

                if (id == 0) {
                    setSpinnerPost();


                } else {

                    updateData("update", id);
                    action.findItem(R.id.menu_edit).setVisible(true);
                    action.findItem(R.id.menu_save).setVisible(false);
                    action.findItem(R.id.menu_delete).setVisible(true);

                    readMode();
                }

                return true;
            case R.id.menu_delete:

                AlertDialog.Builder dialog = new AlertDialog.Builder(EditCanvasing.this);
                dialog.setMessage("Hapus Kegiatan Sales?");
                dialog.setPositiveButton("Ya" ,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        deleteData("delete", id);
                    }
                });
                dialog.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setBirth() {
        //String myFormat = "dd MMMM yyyy"; //In which you need put here
        //SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        mBirth.setText(sdf.format(myCalendar.getTime()));
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void chooseFile() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                mPicture.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void postData(final String key) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        readMode();

        String name = mName.getText().toString().trim();
        String breed = mBreed.getText().toString().trim();
        String species = mSpecies.getText().toString().trim();
        String gender = jenis_event;
        String birth = mBirth.getText().toString().trim();
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Pets> call = apiInterface.insertPet(key, name, species, breed, gender, birth, picture);

        call.enqueue(new Callback<Pets>() {
            @Override
            public void onResponse(Call<Pets> call, Response<Pets> response) {

                progressDialog.dismiss();

                Log.i(EditCanvasing.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    finish();
                } else {
                    Toast.makeText(EditCanvasing.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Pets> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditCanvasing.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateData(final String key, final int id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating...");
        progressDialog.show();

        readMode();

        String name = mName.getText().toString().trim();
        String breed = mBreed.getText().toString().trim();
        String species = mSpecies.getText().toString().trim();
        String gender = jenis_event;
        String birth = mBirth.getText().toString().trim();
        String picture = null;
        if (bitmap == null) {
            picture = "";
        } else {
            picture = getStringImage(bitmap);
        }

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Pets> call = apiInterface.updatePet(key, id,name, species, breed, gender, birth, picture);

        call.enqueue(new Callback<Pets>() {
            @Override
            public void onResponse(Call<Pets> call, Response<Pets> response) {

                progressDialog.dismiss();

                Log.i(EditCanvasing.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditCanvasing.this, message, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditCanvasing.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Pets> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditCanvasing.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteData(final String key, final int id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        readMode();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<Pets> call = apiInterface.deletePet(key, id);

        call.enqueue(new Callback<Pets>() {
            @Override
            public void onResponse(Call<Pets> call, Response<Pets> response) {

                progressDialog.dismiss();

                Log.i(EditCanvasing.class.getSimpleName(), response.toString());

                String value = response.body().getValue();
                String message = response.body().getMassage();

                if (value.equals("1")){
                    Toast.makeText(EditCanvasing.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(EditCanvasing.this, message, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Pets> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(EditCanvasing.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void readMode(){

        //mName.setFocusableInTouchMode(false);
        mBreed.setFocusableInTouchMode(false);
        gender.setFocusableInTouchMode(false);
        mSpecies.setFocusableInTouchMode(false);
        mName.setFocusable(false);
        mBreed.setFocusable(false);
        gender.setFocusable(false);
        mSpecies.setFocusable(false);
        gender.setEnabled(false);
        mBirth.setEnabled(false);

        mFabChoosePic.setVisibility(View.INVISIBLE);

    }

    private void editMode(){

        //mName.setFocusableInTouchMode(true);
        mBreed.setFocusableInTouchMode(false);
        mSpecies.setFocusableInTouchMode(true);
        mBirth.setEnabled(true);

        mFabChoosePic.setVisibility(View.VISIBLE);
    }

}
