package example;

import com.oracle.bmc.ClientConfiguration;
import com.oracle.bmc.retrier.DefaultRetryCondition;
import com.oracle.bmc.retrier.RetryConfiguration;
import com.oracle.bmc.waiter.ExponentialBackoffDelayStrategyWithJitter;
import com.oracle.bmc.waiter.MaxAttemptsTerminationStrategy;
import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import jakarta.inject.Singleton;

import java.util.concurrent.TimeUnit;

@Singleton
public class ConfigurationCustomizer implements BeanCreatedEventListener<ClientConfiguration.ClientConfigurationBuilder> {
    public ClientConfiguration.ClientConfigurationBuilder onCreated(BeanCreatedEvent<ClientConfiguration.ClientConfigurationBuilder> event) {
        return event.getBean().retryConfiguration(
                RetryConfiguration.builder()
                        .retryCondition(new DefaultRetryCondition())
                        .delayStrategy(new ExponentialBackoffDelayStrategyWithJitter(TimeUnit.MINUTES.toMillis(5)))
                        .terminationStrategy(new MaxAttemptsTerminationStrategy(10))
                        .build()
        );
    }
}
