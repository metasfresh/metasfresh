package de.metas.payment.sumup.server;

import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.SumUpTransactionStatusChangedEvent;
import de.metas.payment.sumup.SumUpTransactionStatusChangedListener;
import de.metas.payment.sumup.repository.BulkUpdateByQueryResult;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class SumUpPendingTransactionContinuousUpdater implements SumUpTransactionStatusChangedListener
{
	private static final String SYSCONFIG_PollIntervalInSeconds = "de.metas.payment.sumup.pendingTransactionsUpdate.pollIntervalInSeconds";
	private static final Duration DEFAULT_PollInterval = Duration.ofSeconds(2);

	private static final int DEFAULT_NoPendingTransactionsDelayMultiplier = 5;

	@NonNull private static final Logger logger = LogManager.getLogger(SumUpPendingTransactionContinuousUpdater.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final SumUpService sumUpService;

	@NonNull private final AtomicBoolean pendingTransactionsDetected = new AtomicBoolean(false);

	@PostConstruct
	public void postConstruct()
	{
		final Thread thread = new Thread(this::runNow);
		thread.setDaemon(true);
		thread.setName("SumUp-pending-transaction-updater");
		thread.start();
		logger.info("Started {}", thread);
	}

	private void runNow()
	{
		while (true)
		{
			final Duration delay = getPollInterval();
			for (int i = 1; i <= DEFAULT_NoPendingTransactionsDelayMultiplier; i++)
			{
				if (!sleep(delay))
				{
					logger.info("Got interrupt request. Exiting.");
					return;
				}

				if (pendingTransactionsDetected.get())
				{
					logger.debug("Pending transactions detected.");
					break;
				}
			}

			bulkUpdatePendingTransactionsNoFail();
		}
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private static boolean sleep(final Duration duration)
	{
		try
		{
			logger.debug("Sleeping {}", duration);
			Thread.sleep(duration.toMillis());
			return true;
		}
		catch (InterruptedException e)
		{
			return false;
		}
	}

	private void bulkUpdatePendingTransactionsNoFail()
	{
		try
		{
			final BulkUpdateByQueryResult result = sumUpService.bulkUpdatePendingTransactions(false);
			if (!result.isZero())
			{
				logger.debug("Pending transactions updated: {}", result);
			}

			// Set the pending transactions flag as long as we get some updates
			pendingTransactionsDetected.set(!result.isZero());
		}
		catch (final Exception ex)
		{
			logger.warn("Failed to process. Ignored.", ex);
		}
	}

	private Duration getPollInterval()
	{
		final int valueInt = sysConfigBL.getPositiveIntValue(SYSCONFIG_PollIntervalInSeconds, 0);
		return valueInt > 0 ? Duration.ofSeconds(valueInt) : DEFAULT_PollInterval;
	}

	@Override
	public void onStatusChanged(@NonNull final SumUpTransactionStatusChangedEvent event)
	{
		if (event.getStatusNew().isPending())
		{
			pendingTransactionsDetected.set(true);
		}
	}
}
