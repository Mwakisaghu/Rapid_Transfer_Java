package Money.Transfer;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import io.temporal.common.RetryOptions;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import java.time.Duration;
import java.util.HashMap;

public class MoneyTransferWorkflowImpl {
    private static final String WITHDRAW = "withdraw";

    // Handling retries
    private final RetryOptions retryOptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();

    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            // Specifying timeout options
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            .setRetryOptions(retryOptions)
            .build();

    // Map
    private final Map<String, ActivityOptions> perActivityMethodOptions = new HashMap<String, ActivityOptions>(){{
        put(WITHDRAW, ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
    }};
    private final AccountActivity account = Workflow.newActivityStub(AccountActivity.class, defaultActivityOptions, perActivityMethodOptions);

    // Activity method executions can be orchestrated here or from within other Activity methods.
    @Override
    public void transfer(String fromAccountId, String toAccountId, String referenceId, double amount) {

        account.withdraw(fromAccountId, referenceId, amount);
        account.deposit(toAccountId, referenceId, amount);
    }
}