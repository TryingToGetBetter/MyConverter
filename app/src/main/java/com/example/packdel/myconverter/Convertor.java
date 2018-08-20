package com.example.packdel.myconverter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Convertor{

    private static final String AES_KEY = "AMP";
    private static final long ONE_KB = 1024;
    private static final long ONE_MB = ONE_KB * ONE_KB;
    private static final long ONE_GB = ONE_KB * ONE_MB;

    static byte[] InputStream2byteArray(InputStream inputStream){

        try {

            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();

            // this is storage overwritten on each iteration with bytes
            int bufferSize = 1024;
            byte[] bytes = new byte[bufferSize];

            // we need to know how may bytes were read to write them to the byteBuffer
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                byteBuffer.write(bytes, 0, len);
            }

            // and then we can return your byte array.
            return  byteBuffer.toByteArray();

        }catch (Exception e){

        }

        return null;
    }

    static String byteArray2base64(byte[] bytes){

        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    static byte[] base64toByteArray(String base64String){

        return Base64.decode(base64String, Base64.DEFAULT);
    }

    static Bitmap base64toBitmap(String base64String){

        String base64Image = base64String.split(",")[1];

        byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);

        return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
    }

    static String bitmap2base64(Bitmap bitmap){

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        byte[] byteArray = byteArrayOutputStream .toByteArray();

        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    static String file2Base64(File file){

        // File file = new File(Environment.getExternalStorageDirectory() + "/hello-4.wav");

        try {

            InputStream inputStream = new java.io.FileInputStream(file);

            byte[] bytes = InputStream2byteArray(inputStream);

            return byteArray2base64(bytes);


        }catch (Exception e) {

        }

        return null;
    }

    static File base64toFile(String base64String, String pathName){

        byte[] bytes = base64toByteArray(base64String);

        File file = new File(pathName);

        FileOutputStream fos = null;

        try {

            fos = new FileOutputStream(file);

            fos.write(bytes);

            return file;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    static SecretKey generateKey() {

        return new SecretKeySpec(AES_KEY.getBytes(), "AES");
    }

    static byte[] encrypt(String message, SecretKey secret) {
        /* Encrypt the message. */
        try {
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES"); //"AES/ECB/PKCS5Padding"
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            return cipher.doFinal(message.getBytes("UTF-8"));
        }catch (Exception e){

        }
        return null;
    }

    static String decrypt(byte[] cipherText, SecretKey secret){
        /* Decrypt the message, given derived encContentValues and initialization vector. */
        try {
            Cipher cipher = null;
            cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            return new String(cipher.doFinal(cipherText), "UTF-8");
        }catch (Exception e){

        }
        return null;
    }

}
