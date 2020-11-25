package day0702.annualtotal;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// year and amount
public class AnnualTotalMapper extends Mapper<LongWritable, Text, IntWritable, DoubleWritable> {

	@Override
	protected void map(LongWritable key1, Text value1,Context context)
			throws IOException, InterruptedException {
		//data 
		String data = value1.toString();
		//split string
		String[] words = data.split(",");
		
		//output year and amount
		context.write(new IntWritable(Integer.parseInt(words[2].substring(0, 4))), 
		              new DoubleWritable(Double.parseDouble(words[6])));
	}

}
