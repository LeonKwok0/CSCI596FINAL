package blacklist;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;

public class BlackListTotalByWindowBolt extends BaseWindowedBolt {

	private OutputCollector collector;
	
	private Map<Integer, Integer> result = new HashMap<Integer, Integer>();
	
	public void execute(TupleWindow inputWindow) {
		List<Tuple> list = inputWindow.get();
		
		for(Tuple t:list){
			int userID = t.getIntegerByField("userID");
			int count = t.getIntegerByField("count");
			
			if(result.containsKey(userID)){
				int total = result.get(userID);
				result.put(userID, total+count);
			}else{
				result.put(userID, count);
			}
			
			System.out.println("ͳ�ƵĽ��: " + result);
		
			if(result.get(userID) > 4){
				this.collector.emit(new Values(userID,result.get(userID)));
			}
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("userid","PV"));
	}

	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		this.collector = collector;
	}
}












