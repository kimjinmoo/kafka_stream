package net.smartlo.kafka.demo.process.sample.repository;

import net.smartlo.kafka.demo.entity.SampleModelData;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * 샘플 모델 MongoDB Repository
 *
 */
public interface SampleModelDataRepository extends MongoRepository<SampleModelData, String> {
}
