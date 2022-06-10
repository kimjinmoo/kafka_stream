package net.smartlo.kafka.demo.process.sample.service;


import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.smartlo.kafka.demo.entity.SampleModelData;
import net.smartlo.kafka.demo.model.SampleModel;
import net.smartlo.kafka.demo.process.sample.domain.Message;
import net.smartlo.kafka.demo.process.sample.repository.SampleModelDataRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * SampleModelData 서비스
 */
@Service
@AllArgsConstructor
@Slf4j
public class SampleModelDataService {

  final SampleModelDataRepository sampleModelDataRepository;

  final KafkaTemplate kafkaTemplate;

  private SimpMessagingTemplate simpMessagingTemplate;

  /**
   * 샘플 모델 데이터 저장
   *
   * @param data SampleModelData 객체
   */
  @Transactional
  public void save(SampleModelData data) {
    // 데이터를 저장한다.
    sampleModelDataRepository.save(data);
  }

  /**
   * 전체 데이터 카운터를 가져온다.
   *
   * @return 전체 카운트
   */
  public long count() {
    return sampleModelDataRepository.count();
  }

  /**
   * 카프카 샘플 데이터를 전달한다.
   *
   * @param sampleModel
   */
  public void sendKafkaSample(SampleModel sampleModel) {
    kafkaTemplate.send("src-topic", new Gson().toJson(sampleModel));
  }

  /**
   * 웹소켓을 전송한다.
   *
   * @param message
   */
  public void sendMessage(String message) {
    simpMessagingTemplate.convertAndSend("/kafka", Message
        .builder()
        .type("sample")
        .contents(message)
        .build()
    );
  }
}
