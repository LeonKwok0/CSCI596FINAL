package hotip;

import java.util.HashMap;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class HotIPTotalBolt extends BaseRichBolt {

	private OutputCollector collector;
	
	private Map<String, Integer> result = new HashMap<String, Integer>();
	
	public void execute(Tuple tuple) {
		String ip = tuple.getStringByField("ip");
		int count = tuple.getIntegerByField("count");
		
		if(result.containsKey(ip)){
			int total = result.get(ip);
			result.put(ip, total+count);
		}else{
			result.put(ip, count);
		}
		
		System.out.println("Hot IP�Ľ��:" + result);

		this.collector.emit(new Values(ip,result.get(ip)));
	}

	public void prepare(Map arg0, TopologyContext arg1, OutputCollector collector) {
		this.collector = collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declare) {
		declare.declare(new Fields("ip","total"));
	}

}
