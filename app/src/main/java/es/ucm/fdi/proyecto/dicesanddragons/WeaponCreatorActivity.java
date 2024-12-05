package es.ucm.fdi.proyecto.dicesanddragons;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class WeaponCreatorActivity extends AppCompatActivity {

    private ImageView weaponPreviewImage;
    private SeekBar brightnessSeekBar;
    private EditText fileNameEditText;
    private Bitmap originalWeaponBitmap; // Para almacenar la imagen original seleccionada
    private int currentColorFilter = Color.TRANSPARENT; // Filtro de color aplicado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weapon_creator_activity);

        // Inicializar vistas
        weaponPreviewImage = findViewById(R.id.weaponPreviewImage);
        brightnessSeekBar = findViewById(R.id.brightnessModificationSeekBar);
        fileNameEditText = findViewById(R.id.fileNameEditText);

        // Configurar los listeners
        setupWeaponSelectionListeners();
        setupBrightnessListener();
        setupColorPicker();

        // Configurar el listener del botón de guardar
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> saveImage());
    }


    private void saveImage() {
        // Obtener el nombre del archivo
        String fileName = fileNameEditText.getText().toString().trim();
        if (fileName.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa un nombre para la imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aplicar los filtros y crear la nueva imagen
        Bitmap finalBitmap = applyFiltersToBitmap(originalWeaponBitmap);

        // Guardar la imagen en la galería
        saveImageToGallery(finalBitmap, fileName);
    }

    // Convertir la imagen de la vista en un Bitmap
    private Bitmap getBitmapFromImageView(ImageView imageView) {
        // Obtener la imagen que está mostrando el ImageView
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        return drawable.getBitmap();
    }

    private Bitmap applyFiltersToBitmap(Bitmap originalBitmap) {
        if (originalBitmap == null) return null;

        Bitmap filteredBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

        int width = filteredBitmap.getWidth();
        int height = filteredBitmap.getHeight();

        // Obtener el brillo actual de la barra
        int brightnessProgress = brightnessSeekBar.getProgress();
        float brightnessFactor = (brightnessProgress - 100) / 100.0f; // Rango de -1 a 1

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = filteredBitmap.getPixel(x, y);

                // Extraer componentes RGB
                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);

                // Ajustar el brillo
                r = (int) (r + r * brightnessFactor);
                g = (int) (g + g * brightnessFactor);
                b = (int) (b + b * brightnessFactor);

                // Aplicar el filtro de color seleccionado
                if (currentColorFilter != Color.TRANSPARENT) {
                    int filterR = Color.red(currentColorFilter);
                    int filterG = Color.green(currentColorFilter);
                    int filterB = Color.blue(currentColorFilter);

                    r = (r + filterR) / 2;
                    g = (g + filterG) / 2;
                    b = (b + filterB) / 2;
                }

                // Limitar los valores a 0-255
                r = Math.min(Math.max(r, 0), 255);
                g = Math.min(Math.max(g, 0), 255);
                b = Math.min(Math.max(b, 0), 255);

                filteredBitmap.setPixel(x, y, Color.rgb(r, g, b));
            }
        }

        return filteredBitmap;
    }



    private void setupColorPicker() {
        findViewById(R.id.colorPickerButton).setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Selecciona un color");
            String[] colors = {"Rojo", "Verde", "Azul", "Amarillo"};
            int[] colorValues = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

            builder.setItems(colors, (dialog, which) -> {
                // Obtener el color seleccionado
                currentColorFilter = colorValues[which];

                // Actualizar la vista previa con el filtro seleccionado
                Bitmap previewBitmap = applyFiltersToBitmap(originalWeaponBitmap);
                weaponPreviewImage.setImageBitmap(previewBitmap);
            });
            builder.show();
        });
    }



    private void saveImageToGallery(Bitmap bitmap, String fileName) {
        try {
            // Guardar la imagen en el directorio de imágenes del dispositivo
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName + ".png");
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/DicesAndDragons"); // Crear carpeta específica

            // Insertar los valores en el ContentResolver para obtener una URI donde se guardará la imagen
            Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            // Si se ha creado la URI, guardamos la imagen
            if (imageUri != null) {
                OutputStream fos = getContentResolver().openOutputStream(imageUri);
                if (fos != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos); // Guardamos como PNG
                    fos.close();
                    Toast.makeText(this, "Imagen guardada correctamente", Toast.LENGTH_SHORT).show();

                    // Avisar al sistema para que lo reconozca en la galería
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, imageUri));
                }
            } else {
                Toast.makeText(this, "No se pudo guardar la imagen", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al guardar la imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupWeaponSelectionListeners() {
        ImageView weaponImage1 = findViewById(R.id.weaponImage1);
        ImageView weaponImage2 = findViewById(R.id.weaponImage2);
        ImageView weaponImage3 = findViewById(R.id.weaponImage3);
        ImageView weaponImage4 = findViewById(R.id.weaponImage4);
        ImageView weaponImage5 = findViewById(R.id.weaponImage5);
        ImageView weaponImage6 = findViewById(R.id.weaponImage6);

        weaponImage1.setOnClickListener(v -> setWeaponPreview(R.drawable.arco));
        weaponImage2.setOnClickListener(v -> setWeaponPreview(R.drawable.espada));
        weaponImage3.setOnClickListener(v -> setWeaponPreview(R.drawable.arma));
        weaponImage4.setOnClickListener(v -> setWeaponPreview(R.drawable.daga));
        weaponImage5.setOnClickListener(v -> setWeaponPreview(R.drawable.martillo));
        weaponImage6.setOnClickListener(v -> setWeaponPreview(R.drawable.mazo));
    }

    private void setWeaponPreview(int drawableId) {
        originalWeaponBitmap = BitmapFactory.decodeResource(getResources(), drawableId);
        weaponPreviewImage.setImageBitmap(originalWeaponBitmap);
        Toast.makeText(this, "Arma seleccionada", Toast.LENGTH_SHORT).show();
    }

    private void setupBrightnessListener() {
        brightnessSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Aplicar el brillo dinámicamente en función del progreso de la barra
                Bitmap previewBitmap = applyBrightnessToBitmap(originalWeaponBitmap, progress);
                weaponPreviewImage.setImageBitmap(previewBitmap);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // No se necesita acción
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // No se necesita acción
            }
        });
    }

    private Bitmap applyBrightnessToBitmap(Bitmap originalBitmap, int progress) {
        if (originalBitmap == null) return null;

        // Crear una copia del bitmap original
        Bitmap adjustedBitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);

        int width = adjustedBitmap.getWidth();
        int height = adjustedBitmap.getHeight();

        // Calcular el factor de brillo
        float brightnessFactor = (progress - 100) / 100.0f; // Rango de -1 a 1

        // Aplicar el ajuste de brillo a cada píxel
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int pixel = adjustedBitmap.getPixel(x, y);

                // Extraer los componentes RGB
                int r = Color.red(pixel);
                int g = Color.green(pixel);
                int b = Color.blue(pixel);

                // Modificar el brillo
                r = (int) (r + r * brightnessFactor);
                g = (int) (g + g * brightnessFactor);
                b = (int) (b + b * brightnessFactor);

                // Limitar los valores a 0-255
                r = Math.min(Math.max(r, 0), 255);
                g = Math.min(Math.max(g, 0), 255);
                b = Math.min(Math.max(b, 0), 255);

                // Asignar el nuevo color al píxel
                adjustedBitmap.setPixel(x, y, Color.rgb(r, g, b));
            }
        }

        return adjustedBitmap;
    }
}
