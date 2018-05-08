/**
 * Copyright 2015-2018 The OpenZipkin Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package zipkin.autoconfigure.storage.kafka;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin.internal.V2StorageComponent;
import zipkin2.storage.StorageComponent;
import zipkin2.storage.kafka.KafkaStorage;

/**
 * This storage accepts Cassandra logs in a specified category. Each log entry is expected to contain
 * a single span, which is TBinaryProtocol big-endian, then base64 encoded. Decoded spans are stored
 * asynchronously.
 */
@Configuration
@EnableConfigurationProperties(ZipkinKafkaStorageProperties.class)
@ConditionalOnProperty(name = "zipkin.storage.type", havingValue = "kafka")
@ConditionalOnMissingBean(StorageComponent.class)
// This component is named .*Cassandra3.* even though the package already says cassandra3 because
// Spring Boot configuration endpoints only printout the simple name of the class
class ZipkinKafkaStorageAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  V2StorageComponent storage(ZipkinKafkaStorageProperties properties) {
    KafkaStorage result = KafkaStorage.newBuilder()
      .bootstrapServers(properties.getBootstrapServers())
      .topic(properties.getTopic())
      .overrides(properties.getOverrides())
      .build();
    return V2StorageComponent.create(result);
  }

  @Bean KafkaStorage v2Storage(V2StorageComponent component) {
    return (KafkaStorage) component.delegate();
  }
}
