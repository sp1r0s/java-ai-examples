/*
* Licensed to the Apache Software Foundation (ASF) under one or more
* contributor license agreements. See the NOTICE file distributed with
* this work for additional information regarding copyright ownership.
* The ASF licenses this file to You under the Apache License, Version 2.0
* (the "License"); you may not use this file except in compliance with
* the License. You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import com.google.common.collect.Lists;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.ml.Pipeline;
import org.apache.spark.ml.PipelineModel;
import org.apache.spark.ml.PipelineStage;
import org.apache.spark.ml.classification.LogisticRegression;
import org.apache.spark.ml.feature.HashingTF;
import org.apache.spark.ml.feature.Tokenizer;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import java.util.List;

/**
 *
 * A Simple Java Text Classification Pipeline example.
 *
 * This is an example based on the apache spark examples
 * modified to run in Java successfully.
 */
public class SparkMLExample {

    public static void main (String... args) {

        // Spark configuration
        SparkConf sparkConf = new SparkConf()
                .setAppName("simple-spark-ml-example")
                .setMaster("local"); // Run Spark locally with one thread.
        JavaSparkContext javaSparkContext = new JavaSparkContext(sparkConf);
        SQLContext sqlContext = new SQLContext(javaSparkContext);

        // Create the documents that are going to be used for training
        List<LabeledDocument> localTraining = Lists.newArrayList(
                new LabeledDocument(0L, "a b c d e spark java", 1.0),
                new LabeledDocument(1L, "b d", 0.0),
                new LabeledDocument(2L, "spark f g h", 0.0),
                new LabeledDocument(3L, "hadoop mapreduce", 0.0),
                new LabeledDocument(4L, "b djava", 1.0),
                new LabeledDocument(5L, "java asd32r", 1.0));
        DataFrame training = sqlContext.createDataFrame(javaSparkContext.parallelize(localTraining), LabeledDocument.class);

        // Configure an ML pipeline, which consists of three stages: tokenizer, hashingTF, and lr.
        Tokenizer tokenizer = new Tokenizer()
                .setInputCol("text")
                .setOutputCol("words");
        HashingTF hashingTF = new HashingTF()
                .setNumFeatures(1000)
                .setInputCol(tokenizer.getOutputCol())
                .setOutputCol("features");
        LogisticRegression lr = new LogisticRegression()
                .setMaxIter(10)
                .setRegParam(0.01);
        Pipeline pipeline = new Pipeline()
                .setStages(new PipelineStage[]{tokenizer, hashingTF, lr});

        // Fit the pipeline to the training documents.
        PipelineModel model = pipeline.fit(training);

        // Prepare test documents, which are unlabeled.
        List<Document> localTest = Lists.newArrayList(
                new Document(0L, "java  i j k"),
                new Document(1L, "l m n"),
                new Document(2L, "mapreduce spark"),
                new Document(3L, "apache hadoop java"));
        DataFrame test = sqlContext.createDataFrame(javaSparkContext.parallelize(localTest), Document.class);

        // Make predictions on test documents.
        DataFrame predictions = model.transform(test);
        for (Row r: predictions.select("id", "text", "probability", "prediction").collect()) {
            System.out.println("("
                    + r.get(0)
                    + ", " + r.get(1)
                    + ") --> prob=" + r.get(2)
                    + ", prediction=" + r.get(3)
            );
        }

        javaSparkContext.stop();
    }

}