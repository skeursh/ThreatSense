package com.threatsense.consumer.db;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LogRepository extends ReactiveMongoRepository<LogEntity, String> {}
