package com.java.poc.cassandra.config;

import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionConfig;
import com.hazelcast.config.*;

@Configuration
public class HazelCastConfiguration {

	public Config hazelCastConfig() {
		Config config = new Config();
		config.setInstanceName("hazelcast-instance")
		.addMapConfig(new MapConfig()
				.setName("hazelcastconfiguration")
				.setTimeToLiveSeconds(-1)
				.setEvictionConfig(new EvictionConfig().setEvictionPolicy(EvictionPolicy.LRU)));
		return config;
	}
}
