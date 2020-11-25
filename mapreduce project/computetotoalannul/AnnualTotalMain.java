import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class AnnualTotalMain {

	public static void main(String[] args) throws Exception {
		//create job
		Job job = Job.getInstance(new Configuration());
		job.setJarByClass(AnnualTotalMain.class);
		
		//assign mappers of jobs and output types 
		job.setMapperClass(AnnualTotalMapper.class);
		job.setMapOutputKeyClass(IntWritable.class);
		job.setMapOutputValueClass(DoubleWritable.class);
		
		//assign the reducers of jobs and output types
		job.setReducerClass(AnnualTotalReducer.class);
		job.setOutputKeyClass(IntWritable.class);
		job.setOutputValueClass(Text.class);
		
		//input and output of jobs
		FileInputFormat.setInputPaths(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		//execute
		job.waitForCompletion(true);
	}

}
