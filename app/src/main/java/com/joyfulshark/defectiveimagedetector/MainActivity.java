package com.joyfulshark.defectiveimagedetector;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView _imvTestedImage;
    Button _btnSelectImage;
    Button _btnRunTest;
    String _testedImageFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _imvTestedImage = findViewById(R.id.imv_tested_image);
        _btnSelectImage = findViewById(R.id.btn_select_image);
        _btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                startActivityForResult(intent, 0);
            }
        });
        _btnRunTest = findViewById(R.id.btn_run_test);
        _btnRunTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isDefective = DefectiveImageDetector.isDefective(_testedImageFilePath);
                if(isDefective) {
                    Toast.makeText(MainActivity.this, "Image is defective!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Image is OK", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == RESULT_OK && data != null) {
            try {
                Uri testedImageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), testedImageUri);
                _imvTestedImage.setImageBitmap(bitmap);
                _testedImageFilePath = testedImageUri.getPath();
            } catch (Exception e) {
                Toast.makeText(this, "Failed to open file!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
