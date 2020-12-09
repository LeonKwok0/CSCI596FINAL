package hotip;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import scala.actors.threadpool.Arrays;

public class HotIPMain {

	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
				

		String zks = "192.168.157.21:2181";
		String topic = "mytopic";
		String zkRoot = "/storm";
		String id = "mytopic";
		BrokerHosts hosts = new ZkHosts(zks);
		
		SpoutConfig spoutConf = new SpoutConfig(hosts, topic, zkRoot, id);
		spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());  //ָ����Kafka�н��յ����ַ���
		spoutConf.zkServers = Arrays.asList(new String[]{"192.168.157.21"});
		spoutConf.zkPort = 2181;
		builder.setSpout("kafka_reader", new KafkaSpout(spoutConf));
		
		builder.setBolt("split_bolt", new HotIPSplitBolt()).shuffleGrouping("kafka_reader");
		
		builder.setBolt("hotip_bolt", new HotIPTotalBolt()).fieldsGrouping("split_bolt", new Fields("ip"));

		Config conf = new Config();
		LocalCluster cluster = new LocalCluster();
		
		cluster.submitTopology("MyHotIP", conf, builder.createTopology());
	}
}












