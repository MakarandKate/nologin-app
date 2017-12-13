package in.pecule.nologin.Classes;

import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by xsheru on 12/12/17.
 */

public class CipherClass {
    private Cipher cipher;
    private SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;

    int iterationCount = 2048;

    public String encryptAES(String input, String secret) throws Exception {
        String secretKeyAlgorithm = "PBKDF2WithHmacSHA1";
        String salt = "12345678";

        SecretKeyFactory secretKeyFactory;
        KeySpec keySpec;
        SecretKey secretKeyTemp;

        secretKeyFactory = SecretKeyFactory.getInstance(secretKeyAlgorithm);

        keySpec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), iterationCount, 256);
        secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
        secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");

        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] UTF8Data = cipher.doFinal(input.getBytes());
        String output = Base64.encodeToString(UTF8Data, Base64.NO_WRAP);

        Log.e("ENCRYPTED", output);
        Log.e("IV", new String(Base64.encodeToString(cipher.getIV(), Base64.NO_WRAP)));
        return output;
    }

    public String decryptAES(String input, String secret) throws Exception {
        String secretKeyAlgorithm = "PBKDF2WithHmacSHA1";
        String salt = "12345678";

        SecretKeyFactory secretKeyFactory;
        KeySpec keySpec, keySpec1;
        SecretKey secretKeyTemp, secretKeyTemp1;

        secretKeyFactory = SecretKeyFactory.getInstance(secretKeyAlgorithm);

        keySpec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), iterationCount, 256);
        secretKeyTemp = secretKeyFactory.generateSecret(keySpec);
        secretKey = new SecretKeySpec(secretKeyTemp.getEncoded(), "AES");

        keySpec1 = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), iterationCount, 128);
        secretKeyTemp1 = secretKeyFactory.generateSecret(keySpec1);
        ivParameterSpec = new IvParameterSpec(secretKeyTemp1.getEncoded());

        cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);

        byte[] decryptedData = Base64.decode(input, Base64.NO_WRAP);
        byte[] UTF8Data = cipher.doFinal(decryptedData);
        String output = new String(UTF8Data, "UTF8");

        Log.e("DECRYPTED", output);
        Log.e("IV", new String(Base64.encodeToString(cipher.getIV(), Base64.NO_WRAP)));
        return output;
    }

    private void encrypt() throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        String Test = "qwertyuiopasdfghjklzxcvbnm";
        String password = "qwer1234";

        byte[] salt = new String("12345678").getBytes("Utf8");
        int iterationCount = 2048;
        int keyStrength = 256;

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyStrength);
        SecretKey tmp = factory.generateSecret(spec);

        Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, tmp);
        byte[] decrypted = c.doFinal(Test.getBytes());
        String output = Base64.encodeToString(decrypted, Base64.NO_WRAP);
        byte[] iv = c.getIV();

        Log.e("encrypt OUTPUT: ", output);
        Log.e("encrypt IV:", new String(Base64.encodeToString(iv, Base64.NO_WRAP)));
    }

//    try {
//        encryptAES("qwertyuiopasdfghjklzxcvbnm", "qwer1234");
//    } catch (Exception e) {
//        e1.printStackTrace();
//    }
//
//    try {
//        encrypt();
//    } catch (UnsupportedEncodingException e) {
//        e.printStackTrace();
//    } catch (NoSuchAlgorithmException e) {
//        e.printStackTrace();
//    } catch (InvalidKeySpecException e) {
//        e.printStackTrace();
//    } catch (NoSuchPaddingException e) {
//        e.printStackTrace();
//    } catch (InvalidKeyException e) {
//        e.printStackTrace();
//    } catch (BadPaddingException e) {
//        e.printStackTrace();
//    } catch (IllegalBlockSizeException e) {
//        e.printStackTrace();
//    }
//
//    try {
//        decryptAES("oIJJgTxr912CMZVZEoeMqc891LpbRzxj6IEHNqjC9V0=", "qwer1234");
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
}
