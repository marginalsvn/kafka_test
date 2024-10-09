import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;


public class GetMessage {


    public static User get(String topicName, String bootstrapServers) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        Consumer consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicName));
        ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofMillis(10000L));
        User user = null;
        for (ConsumerRecord<Integer, String> record : records)
            user = objectMapper.readValue(record.value(), User.class);
        consumer.close();
        return user;


    }


}
