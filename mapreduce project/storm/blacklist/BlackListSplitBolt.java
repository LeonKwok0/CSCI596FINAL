package day0706.blacklist;

import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class BlackListSplitBolt extends BaseRichBolt {

	private  OutputCollector collector;
	
	public void execute(Tuple tuple) {
		//������־��1,201.105.101.102,http://mystore.jsp/?productid=1,2017020029,2,1
		String log = tuple.getString(0);
		
		//�ִ�
		String[] words = log.split(",");
		
		//���˵���������Ҫ�����־����
		if(words.length == 6){
			//ÿ��user_id��һ����
			this.collector.emit(new Values(Integer.parseInt(words[0]),1));
		}
	}

	public void prepare(Map arg0, TopologyContext arg1, OutputCollector collector) {
		// ��ʼ��
		this.collector = collector;
	}

	public void declareOutputFields(OutputFieldsDeclarer declare) {
		// ����schema�ĸ�ʽ: (user_id,1)
		declare.declare(new Fields("userID","count"));
	}
}















