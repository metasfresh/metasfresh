package de.metas.acct.open_items.updater;

import de.metas.acct.open_items.FAOpenItemsService;
import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import java.time.Duration;

public class FactAcctOpenItemsToUpdateDBTableWatcher implements Runnable
{
	private static final Logger logger = LogManager.getLogger(FactAcctOpenItemsToUpdateDBTableWatcher.class);
	private final ISysConfigBL sysConfigBL;
	private final FAOpenItemsService faOpenItemsService;

	private static final String SYSCONFIG_PollIntervalInSeconds = "de.metas.acct.fact_acct_openItems_to_update.pollIntervalInSeconds";
	private static final Duration DEFAULT_PollInterval = Duration.ofSeconds(10);

	@Builder
	private FactAcctOpenItemsToUpdateDBTableWatcher(
			@NonNull final ISysConfigBL sysConfigBL,
			@NonNull final FAOpenItemsService faOpenItemsService)
	{
		this.sysConfigBL = sysConfigBL;
		this.faOpenItemsService = faOpenItemsService;
	}

	@Override
	public void run()
	{
		while (true)
		{
			final Duration pollInterval = getPollInterval();
			logger.debug("Sleeping {}", pollInterval);
			try
			{
				Thread.sleep(pollInterval.toMillis());
			}
			catch (InterruptedException e)
			{
				logger.info("Got interrupt request. Exiting.");
				return;
			}

			try
			{
				processNow();
			}
			catch (final Exception ex)
			{
				logger.warn("Failed to process. Ignored.", ex);
			}
		}
	}

	private void processNow()
	{
		boolean mightHaveMore;
		do
		{
			final int processedCount = faOpenItemsService.processScheduled();
			mightHaveMore = processedCount > 0;
		}
		while (mightHaveMore);
	}

	private Duration getPollInterval()
	{
		final int pollIntervalInSeconds = sysConfigBL.getIntValue(SYSCONFIG_PollIntervalInSeconds, -1);
		return pollIntervalInSeconds > 0
				? Duration.ofSeconds(pollIntervalInSeconds)
				: DEFAULT_PollInterval;
	}
}
