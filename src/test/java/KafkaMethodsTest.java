import com.fasterxml.jackson.core.JsonProcessingException;
import kafka.MyConsumer;
import kafka.MyProducer;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import utils.JacksonUtils;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@Testcontainers
public class KafkaMethodsTest {

    @Container
    public ConfluentKafkaContainer kafkaContainer = new ConfluentKafkaContainer("confluentinc/cp-kafka:latest");
    String topicName = "Users";
    MyProducer myProducer;
    MyConsumer myConsumer;

    @BeforeEach
    void setUp() {
        String bootstrapServers = kafkaContainer.getBootstrapServers();
        myProducer = new MyProducer(bootstrapServers, topicName);
        myConsumer = new MyConsumer(bootstrapServers, topicName);
    }

    @Test
    void userTest() throws JsonProcessingException {

        List hobbies = new ArrayList<>();
        hobbies.addAll(List.of("reading", "traveling", "swimming"));
        User user = new User("John", 30, hobbies);

        myProducer.send(JacksonUtils.toJson(user));
        myConsumer.get(User.class);

        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getAge()).isEqualTo(30);
        assertThat(user.getHobbies()).containsExactlyInAnyOrder("swimming", "traveling", "reading");
    }
}