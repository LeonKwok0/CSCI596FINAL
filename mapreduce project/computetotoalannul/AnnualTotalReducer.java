package day0702.annualtotal;

import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

//                                                 year        amount          order      
public class AnnualTotalReducer extends Reducer<IntWritable, DoubleWritable, IntWritable, Text> {

	@Override
	protected void reduce(IntWritable k3, Iterable<DoubleWritable> v3,Context context)
			throws IOException, InterruptedException {
		// compute sum of amount and count
		double totalCount = 0;
		double totalMoney = 0;
		for(DoubleWritable v:v3){
			totalCount ++;
			totalMoney += v.get();
		}
		
		// k3 year           order+sale amount
		context.write(k3, new Text(totalCount+"\t"+totalMoney));
	}

}
