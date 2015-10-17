package ml;

/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
**/

import com.google.common.io.Resources;
import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.io.*;
import java.util.ArrayList;

public class WekaBatteryPredictionExample {

    private static Classifier cls;

    static {
        try {
            String trainingSetFile = Resources.getResource("training-set.txt").getFile();
            Instances trainingSet = loadDatasetFromTxt(trainingSetFile);

            cls = new LinearRegression();
            cls.buildClassifier(trainingSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static double predictBatteryLife(double timeCharged) throws Exception {
        return cls.classifyInstance(new DenseInstance(1.0, new double[]{timeCharged}));
    }

    private static Instances loadDatasetFromTxt(String txtFile) throws IOException {
        ArrayList<Attribute> atts = new ArrayList<>(2);
        atts.add(new Attribute("time_charged", Attribute.NUMERIC));
        atts.add(new Attribute("battery_lasted_time", Attribute.NUMERIC));
        Instances data = new Instances("battery-prediction-training-set", atts, 0);
        data.setClassIndex(1);

        File file = new File(txtFile);
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

        return data;
    }

    public static void main(String... args) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input;
        while ((input = in.readLine()) != null && input.length() != 0) {
            Double timeCharged = Double.valueOf(input);
            if (timeCharged != null) {
                System.out.println(predictBatteryLife(timeCharged));
            }
        }
    }
}
