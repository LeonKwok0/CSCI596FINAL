package blacklist;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.kafka.BrokerHosts;
import org.apache.storm.kafka.KafkaSpout;
import org.apache.storm.kafka.SpoutConfig;
import org.apache.storm.kafka.StringScheme;
import org.apache.storm.kafka.ZkHosts;
import org.apache.storm.spout.SchemeAsMultiScheme;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;

import scala.actors.threadpool.Arrays;

public class BlackListTopology {

	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
				

		String zks = "192.168.157.21:2181";
		String topic = "mytopic";
		String zkRoot = "/storm";
		String id = "mytopic";
		BrokerHosts hosts = new ZkHosts(zks);
		
		SpoutConfig spoutConf = new SpoutConfig(hosts, topic, zkRoot, id);
		spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
		spoutConf.zkServers = Arrays.asList(new String[]{"192.168.157.21"});
		spoutConf.zkPort = 2181;
		builder.setSpout("kafka_reader", new KafkaSpout(spoutConf));
		
		builder.setBolt("split_bolt", new BlackListSplitBolt()).shuffleGrouping("kafka_reader");
		
		builder.setBolt("blacklist_countbolt", new BlackListTotalByWindowBolt()
				                               .withWindow(BaseWindowedBolt.Duration.seconds(30),  //���ڵĳ��� 
				                            		       BaseWindowedBolt.Duration.seconds(10))  //�����ľ���
				       )
		       .fieldsGrouping("split_bolt", new Fields("userID"));
		
		builder.setBolt("blacklist_mysql_bolt", new BlackListMySQLBolt()).shuffleGrouping("blacklist_countbolt");
		
		Config conf = new Config();
        conf.put("topology.message.timeout.secs", 40000);
		
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("mydemoByWindow", conf, builder.createTopology());
	}
}
















