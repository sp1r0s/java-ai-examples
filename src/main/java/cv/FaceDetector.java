package cv;

/**
 *  By downloading, copying, installing or using the software you agree to this license.
 *  If you do not agree to this license, do not download, install,
 *  copy or use the software.
 *
 *
 *   License Agreement
 *   For Open Source Computer Vision Library
 *   (3-clause BSD License)
 *
 *   Copyright (C) 2000-2015, Intel Corporation, all rights reserved.
 *   Copyright (C) 2009-2011, Willow Garage Inc., all rights reserved.
 *   Copyright (C) 2009-2015, NVIDIA Corporation, all rights reserved.
 *   Copyright (C) 2010-2013, Advanced Micro Devices, Inc., all rights reserved.
 *   Copyright (C) 2015, OpenCV Foundation, all rights reserved.
 *   Copyright (C) 2015, Itseez Inc., all rights reserved.
 *   Third party copyrights are property of their respective owners.
 *
 *   Redistribution and use in source and binary forms, with or without modification,
 *   are permitted provided that the following conditions are met:
 *
 *   * Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 *   * Neither the names of the copyright holders nor the names of the contributors
 *   may be used to endorse or promote products derived from this software
 *   without specific prior written permission.
 *
 *   This software is provided by the copyright holders and contributors "as is" and
 *   any express or implied warranties, including, but not limited to, the implied
 *   warranties of merchantability and fitness for a particular purpose are disclaimed.
 *   In no event shall copyright holders or contributors be liable for any direct,
 *   indirect, incidental, special, exemplary, or consequential damages
 *   (including, but not limited to, procurement of substitute goods or services;
 *   loss of use, data, or profits; or business interruption) however caused
 *   and on any theory of liability, whether in contract, strict liability,
 *   or tort (including negligence or otherwise) arising in any way out of
 *   the use of this software, even if advised of the possibility of such damage.
 */

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.lang.reflect.Field;

public class FaceDetector {

    // Have to load the opencv jar and the native library,
    // otherwise it won't work
    public FaceDetector() {
        loadNativeLibrary("lib/opencv", Core.NATIVE_LIBRARY_NAME);
    }

    public BufferedImage detectFace(String imagePath) {
        // Create a face detector from the cascade file in the resources directory.
        String facePropertiesFilePath = getClass().getResource("/lbpcascade_frontalface.xml").getPath();
        CascadeClassifier faceDetector = new CascadeClassifier(facePropertiesFilePath);
        Mat image = Imgcodecs.imread(imagePath);
        // Detect faces in the image.
        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(image, faceDetections);

        System.out.println(String.format("Detected %s faces",
                faceDetections.toArray().length));
        // Draw a bounding box around each face.
        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new org.opencv.core.Point(rect.x, rect.y), new org.opencv.core.Point(rect.x
                    + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
        }

        // Return the image
        return matToBufferedImage(image);
    }

    /**
     * Load the native library.
     * As seen here: http://blog.cedarsoft.com/2010/11/setting-java-library-path-programmatically
     * @param pathToTheLibrary
     * @param libraryName
     */
    private static void loadNativeLibrary(String pathToTheLibrary, String libraryName) {
        try {
            System.setProperty("java.library.path", pathToTheLibrary);
            Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
            System.loadLibrary(libraryName);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage matToBufferedImage(Mat matImage) {
        BufferedImage image = new BufferedImage(matImage.width(), matImage.height(), BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        matImage.get(0, 0, data);
        return image;
    }

}
