package cv;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class FaceRecognitionExample extends JFrame implements ActionListener {

    private JPanel imagePanel;
    private JLabel imageLabel;
    private final JButton loadImage;
    private final JButton exit;
    private final JFileChooser fileChooser;
    private final FaceDetector faceDetector;

    public FaceRecognitionExample()  {
        imagePanel = new JPanel();
        imageLabel = new JLabel();
        JPanel buttonPanel = new JPanel();
        loadImage = new JButton("Load");
        exit = new JButton("Exit");
        fileChooser = new JFileChooser();
        faceDetector = new FaceDetector();

        loadImage.addActionListener(this);
        exit.addActionListener(this);
        buttonPanel.add(loadImage);
        buttonPanel.add(exit);
        imagePanel.add(imageLabel);

        this.add(imagePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.PAGE_END);
        this.setMinimumSize(new Dimension(600, 500));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loadImage) {
            int returnVal = fileChooser.showOpenDialog(FaceRecognitionExample.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File image = fileChooser.getSelectedFile();
                drawImage(image);
            }
        } else if (e.getSource() == exit) {
            System.exit(0);
        }
    }

    private void drawImage(File image) {
        BufferedImage modifiedImageWithRectaglesDrawnAroundFaces = faceDetector.detectFace(image.getPath());
        imageLabel.setIcon(new ImageIcon(modifiedImageWithRectaglesDrawnAroundFaces));
        imagePanel.revalidate();
        this.pack();
    }

    public static void main(String... args) {
        new FaceRecognitionExample();
    }
}
