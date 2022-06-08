package net.smartlo.kafka.demo.util.serde;

import net.smartlo.kafka.demo.model.SampleModel;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

/**
 *
 * 스트림 Serde
 *
 */
public class StreamsSerdes {
  public static final class SampleModelSerde extends Serdes.WrapperSerde<SampleModel> {

    public SampleModelSerde() {
      super(new JsonSerializer<>(), new JsonDeserializer<>(SampleModel.class));
    }
  }
}
