package net.smartlo.kafka.demo.streams;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.smartlo.kafka.demo.entity.SampleModelData;
import net.smartlo.kafka.demo.model.SampleModel;
import net.smartlo.kafka.demo.process.sample.service.SampleModelDataService;
import net.smartlo.kafka.demo.util.serde.StreamsSerdes.SampleModelSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 카프카 스트림 프로세서
 */
@Component
@Slf4j
@AllArgsConstructor
public class ReaderProcessor {

  final SampleModelDataService sampleModelDataService;

  private static final Serde<String> STRING_SERDE = Serdes.String();

  private static final Serde<SampleModel> SAMPLE_MODEL_SERDE = new SampleModelSerde();

  @Autowired
  void buildPipeline(StreamsBuilder streamsBuilder) {
    // source process src-topic을 받는다.
    KStream<String, SampleModel> simpleFirstStream =
        streamsBuilder.stream("src-topic", Consumed.with(STRING_SERDE, STRING_SERDE))
            .mapValues(item -> {
              try {
                return new Gson().fromJson(item, SampleModel.class);
              } catch (Exception e) {
                return null;
              }
            });
    // action - MongoDB에 저장한다.
    ForeachAction<String, SampleModel> simpleAction = (key, sample) -> {
      if (sample != null) {
        sampleModelDataService.save(
            SampleModelData.builder()
                .userName(sample.getUserName())
                .age(sample.getAge())
                .lat(sample.getLat())
                .lon(sample.getLon())
                .build()
        );
        sampleModelDataService.sendMessageDBSave("sample");
      }
    };
    // foreach - DB 저장 Action 실행
    simpleFirstStream.foreach(simpleAction);

    // 싱크 프로세스 - 종료, debug 필요시 print 주석 품
    simpleFirstStream.print(Printed.<String, SampleModel>toSysOut().withLabel("[Smartlo]"));
    simpleFirstStream.to("output-topic", Produced.with(STRING_SERDE, SAMPLE_MODEL_SERDE));
  }
}
