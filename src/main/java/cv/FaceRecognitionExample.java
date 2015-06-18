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
        loadImage = new JButton("Load Image from disk");
        exit = new JButton("Exit");
        fileChooser = new JFileChooser();
        faceDetector = new FaceDetector();

        loadImage.addActionListener(this);
        exit.addActionListener(this);
        JPanel buttonPanel = new JPanel();
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
