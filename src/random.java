package test1;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
//import java.util.Random;
import java.util.*;

public class random {
    public static ArrayList<ArrayList<Double>> create(FileWriter writer, int size, ArrayList<ArrayList<Double>> train )throws IOException {
    	DecimalFormat df = new DecimalFormat("#.######");
    	for(int i=0;i<size;i++) train.add(new ArrayList<Double>());
		for(int i=0;i<size;i++){
			//for(int j=0;j<7;j++)train.get(i).add(Double.parseDouble(df.format(Math.random())));	
			if(Math.random()<0.9){
				train.get(i).add(Double.parseDouble(df.format(Math.random()*40+60)));	
			}
			else {
				train.get(i).add(Double.parseDouble(df.format(Math.random()*25)+100));	
			}
		
			if(Math.random()<0.7) {
				double temp =Math.random()*30+58.0;
				train.get(i).add(Double.parseDouble(df.format(temp)));
				train.get(i).add(Double.parseDouble(df.format(temp+Math.random()*30)));

			}
			else {
				double temp =Math.random()*25+88.0;
				train.get(i).add(Double.parseDouble(df.format(temp)));
				train.get(i).add(Double.parseDouble(df.format(temp+Math.random()*35)));
			}
			
			if(Math.random()<0.66) {
				train.get(i).add(Double.parseDouble(df.format(Math.random()*8.5+17)));
			}
			else{
				train.get(i).add(Double.parseDouble(df.format(Math.random()*5+24)));
			}
			
			if(Math.random()<0.88) {
				double temp =Math.random()*30+70.0;
				train.get(i).add(Double.parseDouble(df.format(temp)));
				train.get(i).add(Double.parseDouble(df.format(temp+Math.random()*40)));
			}
			else{
				double temp =Math.random()*25+95.0;
				train.get(i).add(Double.parseDouble(df.format(temp)));
				train.get(i).add(Double.parseDouble(df.format(temp+Math.random()*30)));
			}
			
			if(Math.random()<0.69) {
				train.get(i).add(Double.parseDouble(df.format(Math.random()*200)));
			}
			else{
				train.get(i).add(Double.parseDouble(df.format(Math.random()*25+195)));
			}
		}
		for(int i=0;i<7;i++){
			writer.append("##A"+(i+1));
			writer.append('\n');
		}
		for(int i=0;i<size;i++){
			for(int j=0;j<7;j++){
				String tmp=Double.toString(train.get(i).get(j));
				writer.append(tmp);
				writer.append(',');
			}
			if(Math.random()<0.7) {
				//String tmp=Double.toString(1);
				writer.append("1");
			} else {
				//String tmp=Double.toString(0);
				writer.append("0");
			}
			if(i!=49) writer.append('\n');
		}
		writer.flush();
		writer.close();
		return train;
    }
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FileWriter writer = new FileWriter("train.csv");
		FileWriter writer1 = new FileWriter("test.csv");
		
		/*
		 * heartbeat:60-100
		 * low blood pressure 60~90 
		 * high blood pressure 90~120
		 * BMI  18~25
		 * blood sugar 70~100
		 * Postprandial blood sugar <140
		 * blood cholestrol <200
		 * */
		
		ArrayList<ArrayList<Double>> train = new ArrayList<ArrayList<Double>>();
		ArrayList<ArrayList<Double>> test = new ArrayList<ArrayList<Double>>();
		
		train = create(writer,50,train);
		test = create(writer1,500,test);
	}

}
