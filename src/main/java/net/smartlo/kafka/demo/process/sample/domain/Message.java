package net.smartlo.kafka.demo.process.sample.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Message {

  private String type;
  private String contents;
}
