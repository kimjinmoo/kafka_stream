package net.smartlo.kafka.demo.process.sample.controller;

import com.github.javafaker.Faker;
import java.util.Locale;
import lombok.AllArgsConstructor;
import net.smartlo.kafka.demo.model.SampleModel;
import net.smartlo.kafka.demo.process.sample.service.SampleModelDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
  public ResponseEntity<String> fetchVersion() {
    return ResponseEntity
        .ok()
        .body("1.0.0");
  }

  @GetMapping("/test/kafka")
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
}
