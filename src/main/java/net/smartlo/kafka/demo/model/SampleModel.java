package net.smartlo.kafka.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * 샘플 모델
 *
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SampleModel {

  // 유저명
  private String userName;
  // 나이
  private int age;
  // 위도
  private String lat;
  // 경도
  private String lon;
}
