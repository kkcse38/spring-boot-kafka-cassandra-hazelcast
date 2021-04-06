package com.java.poc.cassandra.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducer {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	public void sendMesageToKafka(String topicName, String mesage) {
		LOGGER.debug("\n\n** MESSAGE IS PUBLISHED TO KAFKA :{}**\n\n", mesage);
		this.kafkaTemplate.send(topicName, mesage);
	}
}
