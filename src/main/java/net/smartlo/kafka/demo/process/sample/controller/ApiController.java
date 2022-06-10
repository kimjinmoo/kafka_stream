package net.smartlo.kafka.demo.process.sample.controller;

import com.github.javafaker.Faker;
import java.util.Locale;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import net.smartlo.kafka.demo.model.SampleModel;
import net.smartlo.kafka.demo.process.sample.service.SampleModelDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 샘플 컨트롤러이다.
 */
@RestController()
@RequestMapping("/api")
@AllArgsConstructor
public class ApiController {

  final SampleModelDataService sampleModelDataService;

  @GetMapping("/version")
  @CrossOrigin(origins = "*")
  public ResponseEntity<String> fetchVersion() {
    return ResponseEntity
        .ok()
        .body("1.0.0");
  }

  @PostMapping("/test/kafka")
  @CrossOrigin(origins = "*")
  public ResponseEntity<Void> kafkaTest() {
    sampleModelDataService.sendKafkaSample(
        new SampleModel(
            Faker.instance(Locale.KOREA).name().fullName(),
            20,
            Faker.instance(Locale.KOREA).address().latitude(),
            Faker.instance(Locale.KOREA).address().longitude()
        )
    );
    return ResponseEntity.ok().build();
  }

  @PostMapping("/test/kafka/100000")
  @CrossOrigin(origins = "*")
  public ResponseEntity<Void> kafkaTest10000() {
    // 8코어로 돌린다.
    ForkJoinPool forkjoinPool = new ForkJoinPool(8);
    forkjoinPool.submit(() -> {
      // 10000개 데이터를 등록한다.
      IntStream.range(0, 100000)
          .parallel()
          .forEach(index -> {
            sampleModelDataService.sendKafkaSample(
                new SampleModel(
                    Faker.instance(Locale.KOREA).name().fullName(),
                    20,
                    Faker.instance(Locale.KOREA).address().latitude(),
                    Faker.instance(Locale.KOREA).address().longitude()
                )
            );
          });
    });
    return ResponseEntity.ok().build();
  }

  @GetMapping("/test/kafka/count")
  @CrossOrigin(origins = "*")
  public ResponseEntity<Long> fetchAllCount() {
    return ResponseEntity.ok(sampleModelDataService.count());
  }

  @PutMapping("/sendMessage")
  public ResponseEntity<String> testMessage() {
    sampleModelDataService.sendMessage(Faker.instance(Locale.KOREA).app().name());
    return ResponseEntity.ok("OK");
  }
}
