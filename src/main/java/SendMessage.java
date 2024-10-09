import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class SendMessage {
    private static String topicName;
    private static Properties properties;
    ObjectMapper objectMapper = new ObjectMapper();

    public SendMessage(String bootstrapServers, String topicName) {
        this.topicName = topicName;
        properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    }


    public static void send(String record) {
        Producer producer = new KafkaProducer(properties);
        producer.send(new ProducerRecord(topicName, record));
    }


    public String toJson() throws JsonProcessingException {
        List hobbies = new ArrayList<>();
        Collections.addAll(hobbies, "reading", "traveling", "swimming");
        User user = new User("John", 30, hobbies);
        String json = objectMapper.writeValueAsString(user);
        System.out.println(json);
        return json;
    }


}

