package day0702.ordertotal;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

//                                                        id of items   item/order info
public class ProductSalesInfoMapper extends Mapper<LongWritable, Text, IntWritable, Text> {

	@Override
	protected void map(LongWritable key1, Text value1, Context context)
			throws IOException, InterruptedException {
		//input data:order,item
		//judge file name
		//get input HDFS path 
		String path = ((FileSplit)context.getInputSplit()).getPath().getName();
		//get file name
		String fileName = path.substring(path.lastIndexOf("/") + 1);
		
		//parse string
		String data = value1.toString();
		String[] words = data.split(",");
		
		//output
		if(fileName.equals("products")){
			//output item info                          item id                   item name
			context.write(new IntWritable(Integer.parseInt(words[0])), new Text("name:"+words[1]));
		}else{
			//output order info                                item id              order year,amount
			context.write(new IntWritable(Integer.parseInt(words[0])), new Text(words[2]+":"+words[6]));
		}
	}

}


















