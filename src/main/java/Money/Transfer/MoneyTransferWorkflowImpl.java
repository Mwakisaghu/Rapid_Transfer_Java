package Money.Transfer;

import java.time.Duration;

public class MoneyTransferWorkflowImpl {
    private static final String WITHDRAW = "withdraw";

    // Handling retries
    private final RetryOptions retryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();
}