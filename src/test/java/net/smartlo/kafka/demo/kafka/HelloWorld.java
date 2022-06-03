package net.smartlo.kafka.demo.kafka;


import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class HelloWorld {

  @Test
  public void helloWorld() {
    Properties props = new Properties();

    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "test_app");
    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.182:9092");

    Serde<String> stringSerde = Serdes.String();

    StreamsBuilder streamsBuilder = new StreamsBuilder();
    // source process
    KStream<String, String> simpleFirstStream = streamsBuilder.stream("src-topic",
        Consumed.with(stringSerde, stringSerde));

    // stream process
    KStream<String, String> upperCasedStream = simpleFirstStream.mapValues(o -> o.toUpperCase());

    // sink process
    upperCasedStream.to("out-topic", Produced.with(stringSerde, stringSerde));
    upperCasedStream.print(Printed.<String, String>toSysOut().withLabel("Yelling App"));

    // topology
    Topology topology = streamsBuilder.build();
    log.info("topology : {}", topology.describe());

    KafkaStreams kafkaStreams = new KafkaStreams(topology, props);

    try {
      log.info("Hello World Yelling App Started");
      kafkaStreams.start();
      Thread.sleep(35000);
      log.info("Shutting down the Yelling APP now");
      kafkaStreams.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
