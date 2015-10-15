package ml;

import com.google.common.io.Resources;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.io.*;
import java.util.ArrayList;

public class BatteryLifePrediction {

    private static Classifier cls;

    static {
        Instances trainingSet = getDataSet();

        cls = new LinearRegression();
        try {
            cls.buildClassifier(trainingSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double predictBatteryLife(double timeCharged) throws Exception {
        return cls.classifyInstance(new DenseInstance(1.0, new double[]{timeCharged}));
    }

    private static Instances getDataSet() {
        String trainingSetFile = Resources.getResource("training-set.txt").getFile();
        ArrayList<Attribute> atts = new ArrayList<>(2);
        atts.add(new Attribute("time_charged", Attribute.NUMERIC));
        atts.add(new Attribute("battery_lasted_time", Attribute.NUMERIC));
        Instances data = new Instances("battery-prediction-training-set", atts, 0);
        data.setClassIndex(1);

        try {
            File file = new File(trainingSetFile);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String line;
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                double[] newInst = new double[2];
                newInst[0] = Double.valueOf(values[0]);
                newInst[1] = Double.valueOf(values[1]);

                data.add(new DenseInstance(1.0, newInst));
            }
            br.close();
            fr.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;
    }

    public static void main(String... args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while ((input = in.readLine()) != null && input.length() != 0) {
            Double timeCharged = Double.valueOf(input);
            if (timeCharged != null) {
                System.out.println(predictBatteryLife(timeCharged));
            }
        }
    }
}
