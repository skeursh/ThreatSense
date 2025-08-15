package com.threatsense.consumer.db;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AlertRepository extends ReactiveMongoRepository<AlertEntity, String> {}
