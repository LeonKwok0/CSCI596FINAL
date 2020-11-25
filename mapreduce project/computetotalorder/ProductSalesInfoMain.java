package day0702.ordertotal;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class ProductSalesInfoMain {

	public static void main(String[] args) throws Exception {
		//create job
		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(ProductSalesInfoMain.class);
		
		// assign the mapper of jobs and output types
		job.setMapperClass(ProductSalesInfoMapper.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(Text.class);
		
		//assign the reducers of jobs and output types
		job.setReducerClass(ProductSalesInfoReducer.class);
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		
		//input and output of jobs
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//execute
		job.waitForCompletion(true);

	}

}
