package com.unicenta.pos.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class QRCodeTest {

    @Test
    void shouldPrintQRCode() throws Exception {
        // Name
        QRObject name = new QRObject(1,  "Hugh Stevenson");
        byte nameTagByte = (byte) name.getTag();
        byte nameLengthByte = (byte) name.getLength();
        byte[] nameBytes = {nameTagByte, nameLengthByte};
        String nameString = new String(nameBytes, StandardCharsets.UTF_8)+name.getValue();

        //vat number
        QRObject vat = new QRObject(2, "1234567890");
        byte vatTagByte = (byte) vat.getTag();
        byte vatLengthByte = (byte) vat.getLength();
        byte[] vatBytes = {vatTagByte, vatLengthByte};
        String vatString = new String(vatBytes, StandardCharsets.UTF_8)+vat.getValue();

        // date
        QRObject date = new QRObject(3, "2021-11-28 09:30:00");
        byte dateTagByte = (byte) date.getTag();
        byte dateLengthByte = (byte) date.getLength();
        byte[] dateBytes = {dateTagByte, dateLengthByte};
        String dateString = new String(dateBytes, StandardCharsets.UTF_8)+date.getValue();

        // amount
        QRObject amount = new QRObject(4, "1000.00");
        byte amountTagByte = (byte) amount.getTag();
        byte amountLengthByte = (byte) amount.getLength();
        byte[] amountBytes = {amountTagByte, amountLengthByte};
        String amountString = new String(amountBytes, StandardCharsets.UTF_8)+amount.getValue();


        // Vat amount
        QRObject vatAmount = new QRObject(5, "150.00");
        byte vatAmountTag = (byte) vatAmount.getTag();
        byte vatAmountLengthByte = (byte) vatAmount.getLength();
        byte[] vatAmountBytes = {vatAmountTag, vatAmountLengthByte};
        String vatAmountString = new String(vatAmountBytes, StandardCharsets.UTF_8)+vatAmount.getValue();


        //barcode
        String barCodeString = nameString+vatString+dateString+amountString+vatAmountString;
        String encodedBarcode = Base64.getEncoder().encodeToString(barCodeString.getBytes(StandardCharsets.UTF_8));

        assert encodedBarcode != null;

        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(encodedBarcode, BarcodeFormat.QR_CODE, 200, 200);

        BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

        File outputfile = new File("");
        assert  ImageIO.write(bufferedImage, "jpg", outputfile);

    }
}