import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.testcontainers.kafka.ConfluentKafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.Assertions.*;


@Testcontainers
public class KafkaMethodsTest {

    @Container
    public ConfluentKafkaContainer kafkaContainer = new ConfluentKafkaContainer("confluentinc/cp-kafka:latest");


    @Test
    void userTest() throws JsonProcessingException {

        String bootstrapServers = kafkaContainer.getBootstrapServers();
        String topicName = "Users";
        SendMessage service = new SendMessage(bootstrapServers, topicName);
        service.send(service.toJson());

        User user = GetMessage.get(topicName, bootstrapServers);

        assertThat(user.getName()).isEqualTo("John");
        assertThat(user.getAge()).isEqualTo(30);
        assertThat(user.getHobbies()).containsExactlyInAnyOrder("swimming", "traveling", "reading");
    }
}