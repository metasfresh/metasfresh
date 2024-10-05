package de.metas.payment.sumup.server;

import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.payment.sumup.SumUpService;
import de.metas.payment.sumup.repository.BulkUpdateByQueryResult;
import de.metas.util.NumberUtils;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Duration;

@Component
@Profile(Profiles.PROFILE_App)
@RequiredArgsConstructor
public class SumUpPendingTransactionContinuousUpdater
{
	@NonNull private static final Logger logger = LogManager.getLogger(SumUpPendingTransactionContinuousUpdater.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	@NonNull private final SumUpService sumUpService;

	private static final String SYSCONFIG_PollIntervalInSeconds = "de.metas.payment.sumup.pendingTransactionsUpdate.pollIntervalInSeconds";
	private static final Duration DEFAULT_PollInterval = Duration.ofSeconds(10);
	private static final Duration DEEP_SLEEP_DURATION = Duration.ofSeconds(60);

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
			final Duration pollInterval = getPollInterval();
			final boolean isEnabled = pollInterval != null;

			if (!sleep(isEnabled ? pollInterval : DEEP_SLEEP_DURATION))
			{
				logger.info("Got interrupt request. Exiting.");
				return;
			}

			if (isEnabled)
			{
				bulkUpdatePendingTransactionsNoFail();
			}
		}
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private static boolean sleep(final Duration duration)
	{
		try
		{
			Thread.sleep(duration.toMillis());
			return true;
		}
		catch (InterruptedException e)
		{
			return false;
		}
	}

	private Duration getPollInterval()
	{
		final String valueStr = StringUtils.trimBlankToNull(sysConfigBL.getValue(SYSCONFIG_PollIntervalInSeconds));
		if (valueStr == null)
		{
			return DEFAULT_PollInterval;
		}

		if ("-".equals(valueStr))
		{
			return null;
		}

		final Integer valueInt = NumberUtils.asInteger(valueStr, null);
		if (valueInt == null || valueInt <= 0)
		{
			return null;
		}

		return Duration.ofSeconds(valueInt);
	}

	private void bulkUpdatePendingTransactionsNoFail()
	{
		try
		{
			final BulkUpdateByQueryResult result = sumUpService.bulkUpdatePendingTransactions(false);
			if (!result.isZero())
			{
				logger.info("Pending transactions updated: {}", result);
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed to process. Ignored.", ex);
		}
	}
}
