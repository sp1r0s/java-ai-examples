#Artificial Intelligence examples in Java

Examples
--------

* A Machine Learning regression example using <a href="www.cs.waikato.ac.nz/ml/weka/" target="target">Weka</a>.
* A Machine Learning classification example using <a href="https://spark.apache.org/" target="target">Apache Spark</a>.
* A Natural Language Processing example using the <a href="http://nlp.stanford.edu/software/index.shtml" target="target">Stanford NLP</a>.
* A Computer Vision example using <a href="http://opencv.org/" target="target">OpenCV</a>(*).

How to run the examples
-----------------------

Each example has a main method you can use to run it.

(*): The computer vision example requires the native opencv library.
     Windows users do not need to build the native library it is pre-build and in the lib directory.
     For other OSes it's required to build OpenCV from sources.
     The jar included in the project was built from the 3.0.0 opencv version.
     <p>To build from sources:
     <ol>
       <li>Follow the instructions here: <a href="http://docs.opencv.org/master/d9/d52/tutorial_java_dev_intro.html" target="target">Introduction to Java Development</a> (Ignore the git checkout 2.4)</li>
       <li>Move the native library to the "lib/opencv" directory</li>
     </ol>
     </p>
