package net.smartlo.kafka.demo.entity;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * SampleModelData document
 *
 */
@Document
@Data
@Builder
@ToString
public class SampleModelData {
  @Id
  private String id;
  private String userName;
  private int age;
  private String lat;
  private String lon;
}
