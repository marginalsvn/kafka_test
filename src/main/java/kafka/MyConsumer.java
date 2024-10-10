package kafka;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;
import utils.JacksonUtils;


public class MyConsumer {
    private String topicName;
    private Properties properties;

    public MyConsumer(String bootstrapServers, String topicName) {
        this.topicName = topicName;
        properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }

    public <T> T get(Class<T> clas) throws JsonProcessingException {
        Consumer consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicName));
        ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(10000L));
        T result = null;
        for (ConsumerRecord<Integer, String> record : records)
            result = JacksonUtils.fromJson(record.value(), clas);
        consumer.close();
        return result;
    }


}
