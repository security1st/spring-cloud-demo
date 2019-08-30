package hl.sc.demo.employee;

import hl.sc.demo.employee.model.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author: hl
 * @created: 2019-08-26 19:33
 **/
@org.springframework.context.annotation.Configuration
public class Configuration {
    @Bean
    ReactiveRedisOperations<String, Employee> redisOperations(ReactiveRedisConnectionFactory factory) {
        Jackson2JsonRedisSerializer<Employee> serializer = new Jackson2JsonRedisSerializer<>(Employee.class);

        RedisSerializationContext.RedisSerializationContextBuilder<String,
                Employee> builder =
                RedisSerializationContext.newSerializationContext(new StringRedisSerializer());

        RedisSerializationContext<String, Employee> context = builder.value(serializer)
                                                                     .build();

        return new ReactiveRedisTemplate<>(factory, context);
    }
}
