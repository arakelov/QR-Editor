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
 * Created by А on 20.11.2017.
 */

public final class QRcreate {


    public static final int WIDTH = Resources.getSystem().getDisplayMetrics().widthPixels;
    public static final int HEIGHT = WIDTH;
    public static int count;




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
