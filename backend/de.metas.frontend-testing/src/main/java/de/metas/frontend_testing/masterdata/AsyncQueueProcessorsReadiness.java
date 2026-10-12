package de.metas.frontend_testing.masterdata;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Stopwatch;
import de.metas.async.model.validator.Main;
import de.metas.async.processor.IQueueProcessorExecutorService;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import java.time.Duration;
import java.util.function.BooleanSupplier;

/**
 * Test-support readiness gate (frontend testing only): blocks until the async queue processors have been started.
 *
 * <p>Why: the app reports healthy (and serves the frontend-testing endpoints) before its async queue processors are
 * started. They start only {@value Main#SYSCONFIG_ASYNC_INIT_DELAY_MILLIS} milliseconds after the app's init (see {@link Main}).
 * A masterdata command that polls for the outcome of an async workpackage (e.g. the shipment schedules of a freshly
 * completed sales order) would otherwise spend its whole poll budget waiting for processors that are not running yet,
 * and time out with "found 0" on the first test after app start.
 *
 * <p>Call this right before such a poll, so the poll budget measures the async work, not the processors' start-up delay.
 * Once the processors are started, this returns immediately.
 *
 * <p>Note: {@link IQueueProcessorExecutorService#isInitialized()} turns true when the processors' start begins, so they may still be
 * registering when this returns. That is fine: workpackages are queued in the DB and picked up as soon as the processors run.
 */
public final class AsyncQueueProcessorsReadiness
{
	private static final Logger logger = LogManager.getLogger(AsyncQueueProcessorsReadiness.class);

	/**
	 * Same default as {@link Main} uses when the SysConfig is not set.
	 */
	private static final int DEFAULT_INIT_DELAY_MILLIS = 3 * 60 * 1000;
	/**
	 * The init delay counts from the app's init, so from any later point the processors are due in less than the delay.
	 * The margin covers the time the processors' start itself takes.
	 */
	private static final Duration MAX_WAIT_MARGIN = Duration.ofSeconds(30);
	private static final Duration POLL_INTERVAL = Duration.ofMillis(500);

	/**
	 * Same system property as checked in {@link Main} (private there). When set, the processors are never started.
	 */
	private static final String SYSTEM_PROPERTY_ASYNC_DISABLE = "async_disable";

	private AsyncQueueProcessorsReadiness() {}

	public static void awaitStarted()
	{
		final IQueueProcessorExecutorService queueProcessorExecutorService = Services.get(IQueueProcessorExecutorService.class);
		if (queueProcessorExecutorService.isInitialized())
		{
			return;
		}

		if (Boolean.parseBoolean(System.getProperty(SYSTEM_PROPERTY_ASYNC_DISABLE)))
		{
			throw new AdempiereException("The async queue processors are not started because " + SYSTEM_PROPERTY_ASYNC_DISABLE + "=true");
		}

		final int initDelayMillis = Services.get(ISysConfigBL.class).getIntValue(Main.SYSCONFIG_ASYNC_INIT_DELAY_MILLIS, DEFAULT_INIT_DELAY_MILLIS);
		final Duration timeout = Duration.ofMillis(Math.max(initDelayMillis, 0)).plus(MAX_WAIT_MARGIN);

		awaitStarted(queueProcessorExecutorService::isInitialized, timeout, POLL_INTERVAL);
	}

	@VisibleForTesting
	static void awaitStarted(
			@NonNull final BooleanSupplier isStarted,
			@NonNull final Duration timeout,
			@NonNull final Duration pollInterval)
	{
		if (isStarted.getAsBoolean())
		{
			return;
		}

		final Stopwatch stopwatch = Stopwatch.createStarted();
		logger.info("Async queue processors are not started yet. Waiting for them (at most {})...", timeout);
		while (!isStarted.getAsBoolean())
		{
			if (stopwatch.elapsed().compareTo(timeout) > 0)
			{
				throw new AdempiereException("The async queue processors were not started within " + timeout
						+ ". Check " + Main.SYSCONFIG_ASYNC_INIT_DELAY_MILLIS + ".");
			}
			sleep(pollInterval);
		}

		logger.info("Async queue processors are started. Waited {}", stopwatch.stop());
	}

	private static void sleep(@NonNull final Duration duration)
	{
		try
		{
			Thread.sleep(duration.toMillis());
		}
		catch (final InterruptedException e)
		{
			Thread.currentThread().interrupt();
			throw new AdempiereException(e);
		}
	}
}
