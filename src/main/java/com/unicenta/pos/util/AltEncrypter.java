//    uniCenta oPOS  - Touch Friendly Point Of Sale
//    Copyright (c) 2009-2017 uniCenta
//    https://unicenta.com
//
//    This file is part of uniCenta oPOS
//
//    uniCenta oPOS is free software: you can redistribute it and/or modify
//    it under the terms of the GNU General Public License as published by
//    the Free Software Foundation, either version 3 of the License, or
//    (at your option) any later version.
//
//   uniCenta oPOS is distributed in the hope that it will be useful,
//    but WITHOUT ANY WARRANTY; without even the implied warranty of
//    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//    GNU General Public License for more details.
//
//    You should have received a copy of the GNU General Public License
//    along with uniCenta oPOS.  If not, see <http://www.gnu.org/licenses/>.


//DES is a terrible/broken choice for an algorithm, this is rewritten using AES, it has likely not been tested and WILL NOT WORK on anything that had already been created using DES.

package com.unicenta.pos.util;

import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;

public class AltEncrypter {

    private Cipher cipherDecrypt;
    private Cipher cipherEncrypt;

    public AltEncrypter(String passPhrase) {
        try {
            SecureRandom sr = SecureRandom.getInstanceStrong();
            sr.setSeed(passPhrase.getBytes(StandardCharsets.UTF_8));
            KeyGenerator kGen = KeyGenerator.getInstance("AES");
            kGen.init(256, sr);
            SecretKey key = kGen.generateKey();

            cipherEncrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            byte[] ivBytes = new byte[cipherEncrypt.getBlockSize()];
            sr.nextBytes(ivBytes);
            IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
            cipherEncrypt.init(Cipher.ENCRYPT_MODE, key, ivSpec);

            cipherDecrypt = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipherDecrypt.init(Cipher.DECRYPT_MODE, key, ivSpec);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }
    }

    public String encrypt(String str) {
        try {
            byte[] encryptedBytes = cipherEncrypt.doFinal(str.getBytes(StandardCharsets.UTF_8));
            byte[] ivBytes = cipherEncrypt.getIV();
            byte[] resultBytes = new byte[ivBytes.length + encryptedBytes.length];
            System.arraycopy(ivBytes, 0, resultBytes, 0, ivBytes.length);
            System.arraycopy(encryptedBytes, 0, resultBytes, ivBytes.length, encryptedBytes.length);
            return StringUtils.byte2hex(resultBytes);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        }
    }

public String decrypt(String str) throws IOException {
    try {
        byte[] resultBytes = StringUtils.hex2byte(str);
        byte[] ivBytes = new byte[cipherDecrypt.getBlockSize()];
        System.arraycopy(resultBytes, 0, ivBytes, 0, ivBytes.length);
        byte[] encryptedBytes = new byte[resultBytes.length - ivBytes.length];
        System.arraycopy(resultBytes, ivBytes.length, encryptedBytes, 0, encryptedBytes.length);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        byte[] keyBytes = cipherDecrypt.getParameters().getEncoded();
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        cipherDecrypt.init(Cipher.DECRYPT_MODE, key, ivSpec);
        byte[] decryptedBytes = cipherDecrypt.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | InvalidAlgorithmParameterException e) {
        throw new RuntimeException(e);
    }
}
}
