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
