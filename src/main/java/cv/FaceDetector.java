package cv;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.lang.reflect.Field;

public class FaceDetector {

    public FaceDetector() {
        loadNativeLibrary("lib/opencv_linux", Core.NATIVE_LIBRARY_NAME);
    }

    public BufferedImage detectFace(String imagePath) {
        // Create a face detector from the cascade file in the resources directory.
        CascadeClassifier faceDetector = new CascadeClassifier(getClass().getResource("/lbpcascade_frontalface.xml").getPath());
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
        return mat2Img(image);
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

    private BufferedImage mat2Img(Mat matImage) {
        BufferedImage image = new BufferedImage(matImage.width(), matImage.height(), BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster raster = image.getRaster();
        DataBufferByte dataBuffer = (DataBufferByte) raster.getDataBuffer();
        byte[] data = dataBuffer.getData();
        matImage.get(0, 0, data);
        return image;
    }

}
