package com.karakelyan.QREditor;

import android.content.res.Resources;
import android.graphics.Bitmap;

import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Аrakelov Vsevolod on 20.11.2017.
 * This class represents a QR code bitmap object which will used in Customize and Success activities
 */

public final class QRcreate {

    //Finding of the width of device for future picture
    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = WIDTH;
    //ident from borders which is represent width of silent area
    public static int count;



        //method according with input parameters(color of QR code, color of contrast silent area, correction level and text for coding)
        //will create and return a bitmap of generated QR code
        public Bitmap qrBitmap(int front, int back, int lvl, String... params) {

            Map<EncodeHintType, Object> hints = new HashMap<>();

            if(lvl==7)
            {hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);}
            else if (lvl==15)
            {hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);}
            else if(lvl==25)
            {hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);}
            else if(lvl==30)
            {hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);}

            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            Bitmap bitmap = null;

            for (int i = 0; i < params.length; i++) {
                try {
                    BitMatrix matrix = new QRCodeWriter().encode(
                            params[i],
                            com.google.zxing.BarcodeFormat.QR_CODE,
                            WIDTH, HEIGHT, hints);

                    bitmap = matrixToBitmap(front, back, matrix);

                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
            return bitmap;
        }


    //this method is for converting bitmap matrix from method above to bitmap
    private Bitmap matrixToBitmap(int front, int back, BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        count=0;
        boolean firstEnter = true;
        boolean firstBlackPoint = false;

        for (int x = 0; x < width; x++) {
            if((firstBlackPoint)&(firstEnter)) {
                count = x;
                firstEnter=false;
            }

                for (int y = 0; y < height; y++) {
                    image.setPixel(x, y, matrix.get(x, y) ? front : back);

                if (matrix.get(x,y)){
                    firstBlackPoint=true;
                }
            }

        }
        return image;
    }
}
