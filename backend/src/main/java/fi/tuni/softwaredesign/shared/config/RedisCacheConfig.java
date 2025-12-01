package fi.tuni.softwaredesign.shared.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class RedisCacheConfig {

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    String redisHost = System.getenv().getOrDefault("SPRING_REDIS_HOST", "redis");
    int redisPort = Integer.parseInt(System.getenv().getOrDefault("SPRING_REDIS_PORT", "6379"));
    return new LettuceConnectionFactory(redisHost, redisPort);
  }

  @Bean
  public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
    // Create a custom ObjectMapper that allows unwrapped types
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS);

    RedisCacheConfiguration cacheConfig =
        RedisCacheConfiguration.defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(10))
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(
                    new GenericJackson2JsonRedisSerializer(objectMapper)));

    return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfig).build();
  }
}
