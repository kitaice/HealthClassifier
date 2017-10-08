import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;

public class random {
    public static ArrayList<ArrayList<Double>> create(FileWriter writer, int size, ArrayList<ArrayList<Double>> data) throws IOException {
        DecimalFormat df = new DecimalFormat("#.######");
        for(int i=0;i<size;i++) data.add(new ArrayList<Double>());
        for(int i=0;i<size;i++){
            if(Math.random()<0.9){
                data.get(i).add(Double.parseDouble(df.format(Math.random()*40+60)));    
            }
            else {
                data.get(i).add(Double.parseDouble(df.format(Math.random()*25)+100));    
            }
        
            if(Math.random()<0.7) {
                double temp =Math.random()*30+58.0;
                data.get(i).add(Double.parseDouble(df.format(temp)));
                data.get(i).add(Double.parseDouble(df.format(temp+Math.random()*30)));

            }
            else {
                double temp =Math.random()*25+88.0;
                data.get(i).add(Double.parseDouble(df.format(temp)));
                data.get(i).add(Double.parseDouble(df.format(temp+Math.random()*35)));
            }
            
            if(Math.random()<0.66) {
                data.get(i).add(Double.parseDouble(df.format(Math.random()*8.5+17)));
            }
            else{
                data.get(i).add(Double.parseDouble(df.format(Math.random()*5+24)));
            }
            
            if(Math.random()<0.88) {
                double temp =Math.random()*30+70.0;
                data.get(i).add(Double.parseDouble(df.format(temp)));
                data.get(i).add(Double.parseDouble(df.format(temp+Math.random()*40)));
            }
            else{
                double temp =Math.random()*25+95.0;
                data.get(i).add(Double.parseDouble(df.format(temp)));
                data.get(i).add(Double.parseDouble(df.format(temp+Math.random()*30)));
            }
            
            if(Math.random()<0.69) {
                data.get(i).add(Double.parseDouble(df.format(Math.random()*200)));
            }
            else{
                data.get(i).add(Double.parseDouble(df.format(Math.random()*25+195)));
            }
        }
        for(int i=0;i<7;i++){
            writer.append("##A"+(i+1));
            writer.append('\n');
        }
        for(int i=0;i<size;i++){
            for(int j=0;j<7;j++){
                String tmp=Double.toString(data.get(i).get(j));
                writer.append(tmp);
                writer.append(',');
            }
            int type = 0;
            if (size < 300) {
                type = 1;
            }
            int classifier = writeClassifier(data, i, type);
            writer.append(String.valueOf(classifier));
            if (i != (size - 1)) writer.append('\n');
        }
        writer.flush();
        writer.close();
        return data;
    }

    /*
     * type = 0 is test
     *      = 1 is train
     *
     * heartbeat:60-100
     * low blood pressure 60~90 
     * high blood pressure 90~120
     * BMI  18~25
     * blood sugar 70~100
     * Postprandial blood sugar <140
     * blood cholestrol <200
     * */
    public static int writeClassifier(ArrayList<ArrayList<Double>> data, int pos, int type) {
        int classifier = 0;
        if ((data.get(pos).get(0) > 60.0) && (data.get(pos).get(0) < 100.0) &&
               (data.get(pos).get(1) > 60.0) && (data.get(pos).get(1) < 90.0) &&
               (data.get(pos).get(2) > 90.0) && (data.get(pos).get(2) < 120.0) &&
               (data.get(pos).get(3) > 18.0) && (data.get(pos).get(3) < 25.0) &&
               (data.get(pos).get(4) > 70.0) && (data.get(pos).get(4) < 100.0) &&
               (data.get(pos).get(5) < 140.0) && (data.get(pos).get(6) < 200.0)) {
            classifier = 1;
        }
        if((Math.random() > 0.9) && (type == 1)) {
            System.out.println("mutating training data");
            classifier = Math.abs(classifier - 1);
        }

        return classifier;
    }

    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            System.out.println("usage: java random <generator type: test or train>");
            System.exit(-1);
        }
        FileWriter writer;
        int count = 0;
        if (args[0].equals("test")) {
            writer = new FileWriter("test.csv");
            count = 500;
        } else if (args[0].equals("train")) {
            writer = new FileWriter("train.csv");
            count = 100;
        } else {
            writer = null;
            System.out.println("usage: java random <generator type: test or train>");
            System.exit(-1);
        }
        
        ArrayList<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
        
        data = create(writer,count,data);
    }
}
