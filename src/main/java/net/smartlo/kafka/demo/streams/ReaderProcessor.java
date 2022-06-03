package net.smartlo.kafka.demo.streams;

import java.util.Arrays;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReaderProcessor {

  private static final Serde<String> STRING_SERDE = Serdes.String();

  @Autowired
  void buildPipeline(StreamsBuilder streamsBuilder) {
    // source process
    KStream<String, String> simpleFirstStream = streamsBuilder.stream("src-topic",
        Consumed.with(STRING_SERDE, STRING_SERDE));

    // 싱크프로세스
    simpleFirstStream.print(Printed.<String, String>toSysOut().withLabel("[Smartlo]"));
    simpleFirstStream.to("output-topic");
  }
}
