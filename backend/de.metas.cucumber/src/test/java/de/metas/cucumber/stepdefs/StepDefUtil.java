/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2021 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.cucumber.stepdefs;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.common.util.StringUtils;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * TODO: move all "waiting" methods to de.metas.common.util.{@link de.metas.common.util.TryAndWaitUtil}.
 */
@UtilityClass
public class StepDefUtil
{
	private static final Logger logger = LogManager.getLogger(StepDefUtil.class);

	/**
	 * Waits for the given {@code worker} to supply {@code true}.
	 * Fails if this doesn't happen within the given {@code maxWaitSeconds} timeout.
	 *
	 * @param maxWaitSeconds set to a value <=0 to wait forever (use only when developing locally)
	 */
	public void tryAndWait(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final Supplier<Boolean> worker,
			@Nullable final Runnable logContext) throws InterruptedException
	{
		final long deadLineMillis = computeDeadLineMillis(maxWaitSeconds);

		boolean conditionIsMet = false;

		while (deadLineMillis > System.currentTimeMillis() && !conditionIsMet)
		{
			//noinspection BusyWait
			Thread.sleep(checkingIntervalMs);
			conditionIsMet = worker.get();
		}

		if (!conditionIsMet && logContext != null)
		{
			logContext.run();
		}

		assertThat(conditionIsMet).as("Condition was not met within the %s second timeout", maxWaitSeconds).isTrue();
	}

	public int extractId(@NonNull final String idOrIdentifier, @NonNull final StepDefData<?> stepDefDataTable)
	{
		try
		{
			return Integer.parseInt(idOrIdentifier);
		}
		catch (final NumberFormatException exception)
		{
			final Object model = stepDefDataTable.get(idOrIdentifier);

			return InterfaceWrapperHelper.getId(model);
		}
	}

	@And("^wait for (.*)s$")
	public void waitFor(final int waitingTimeSec) throws InterruptedException
	{
		final long waitingTimeMillis = waitingTimeSec * 1000L;
		Thread.sleep(waitingTimeMillis);
	}

	/**
	 * Waits for the given {@code worker} to supply an optional that is present.
	 * Fails if this doesn't happen within the given {@code maxWaitSeconds} timeout.
	 *
	 * @param maxWaitSeconds set to a value <=0 to wait forever (use only when developing locally)
	 */
	@Deprecated
	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final ItemProvider<T> worker,
			@Nullable final Runnable logContext) throws InterruptedException
	{
		final long deadLineMillis = computeDeadLineMillis(maxWaitSeconds);

		ItemProvider.ProviderResult<T> lastWorkerResult = null;
		while (deadLineMillis > System.currentTimeMillis())
		{
			//noinspection BusyWait
			Thread.sleep(checkingIntervalMs);

			lastWorkerResult = worker.execute();
			if (lastWorkerResult.isResultFound())
			{
				return lastWorkerResult.getResult();
			}
		}

		if (logContext != null)
		{
			logContext.run();
		}
		Assertions.fail("the given supplier didn't succeed within the " + maxWaitSeconds + "second timeout. "
				+ "The logging output of the last try is:\n" + (lastWorkerResult == null ? "<null>" : lastWorkerResult.getLog()));
		return null;

	}

	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final ItemProvider<T> worker) throws InterruptedException
	{
		return tryAndWaitForItem(maxWaitSeconds, checkingIntervalMs, worker, (Supplier<String>)null);
	}

	public void tryAndWait(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final Supplier<Boolean> worker) throws InterruptedException
	{
		tryAndWait(maxWaitSeconds, checkingIntervalMs, worker, null);
	}

	/**
	 * Waits for the given {@code worker} to supply an optional that is present.
	 * Fails if this doesn't happen within the given {@code maxWaitSeconds} timeout.
	 *
	 * @param maxWaitSeconds set to a value <=0 to wait forever (use only when developing locally)
	 */
	@Deprecated
	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final Supplier<Optional<T>> worker,
			@Nullable final Runnable logContext) throws InterruptedException
	{
		final long deadLineMillis = computeDeadLineMillis(maxWaitSeconds);

		try
		{
			while (deadLineMillis > System.currentTimeMillis())
			{
				//noinspection BusyWait
				Thread.sleep(checkingIntervalMs);
				final Optional<T> workerResult = worker.get();
				if (workerResult.isPresent())
				{
					return workerResult.get();
				}
			}
		}
		catch (final Exception e)
		{
			if (logContext != null)
			{
				logContext.run();
			}

			throw e;
		}

		if (logContext != null)
		{
			logContext.run();
		}
		Assertions.fail("the given supplier didn't succeed within the " + maxWaitSeconds + "second timeout");
		return null;
	}

	/**
	 * Waits for the given {@code worker} to supply an optional that is present.
	 * Fails if this doesn't happen within the given {@code maxWaitSeconds} timeout.
	 *
	 * @param maxWaitSeconds set to a value <=0 to wait forever (use only when developing locally)
	 */
	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final Supplier<Optional<T>> worker,
			@Nullable final Supplier<String> logContext) throws InterruptedException
	{
		final long deadLineMillis = computeDeadLineMillis(maxWaitSeconds);

		try
		{
			while (deadLineMillis > System.currentTimeMillis())
			{
				//noinspection BusyWait
				Thread.sleep(checkingIntervalMs);
				final Optional<T> workerResult = worker.get();
				if (workerResult.isPresent())
				{
					return workerResult.get();
				}
			}
		}
		catch (final Exception e)
		{
			if (logContext != null)
			{
				final String contextMessage = logContext.get();

				throw new RuntimeException(contextMessage, e);
			}

			throw e;
		}

		final String context = Optional.ofNullable(logContext).map(Supplier::get).orElse("Context not provided!");

		Assertions.fail("the given supplier didn't succeed within the " + maxWaitSeconds + "second timeout! \n Context: " + context);
		return null;
	}

	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final Supplier<Optional<T>> worker) throws InterruptedException
	{
		return tryAndWaitForItem(maxWaitSeconds, checkingIntervalMs, worker, (Supplier<String>)null);
	}

	/**
	 * Waits for the given {@code worker} to supply an optional that is present.
	 * Fails if this doesn't happen within the given {@code maxWaitSeconds} timeout.
	 *
	 * @param maxWaitSeconds set to a value <=0 to wait forever (use only when developing locally)
	 */
	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final ItemProvider<T> worker,
			@Nullable final Supplier<String> logContext) throws InterruptedException
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();

		//
		// First try, lets be optimistic that we might get the result on first try
		ItemProvider.ProviderResult<T> lastWorkerResult = worker.execute();
		if (lastWorkerResult.isResultFound())
		{
			return lastWorkerResult.getResult();
		}
		else
		{
			logger.info("Got no result on first try because: {}", lastWorkerResult.getLog());
		}

		//
		// Wait given millis and then invoke the worker again and again until max wait time
		int iteration = 1; // NOTE: start from 1 because we already had the first iteration above
		final long deadLineMillis = computeDeadLineMillis(maxWaitSeconds);
		while (deadLineMillis > System.currentTimeMillis())
		{
			iteration++;
			logger.info("Waiting {}ms (iteration={}, accumulated wait time={}, maxWaitSeconds={})", checkingIntervalMs, iteration, stopwatch, maxWaitSeconds);
			//noinspection BusyWait
			Thread.sleep(checkingIntervalMs);

			lastWorkerResult = worker.execute();
			if (lastWorkerResult.isResultFound())
			{
				stopwatch.stop();
				final T resultValue = lastWorkerResult.getResult();
				logger.info("Got result after {} tries and it took {}: {}", iteration, stopwatch, resultValue);
				return resultValue;
			}
			else
			{
				logger.info("After {} tries and {}, got NO result because: {}", iteration, stopwatch, lastWorkerResult.getLog());
			}
		}

		//
		// Got no result
		final String context = Optional.ofNullable(logContext).map(Supplier::get).orElse("not provided");
		Assertions.fail("the given supplier didn't succeed within the " + maxWaitSeconds + " seconds timeout (" + iteration + " tries). "
				+ "The logging output of the last try is:\n" + lastWorkerResult.getLog()
				+ "\n Context: " + context);
		return null; // will never get here because fail throws
	}

	private static long computeDeadLineMillis(final long maxWaitSeconds)
	{
		final long nowMillis = System.currentTimeMillis(); // don't use SystemTime.millis(); because it's probably "rigged" for testing purposes,
		return maxWaitSeconds > 0 ? nowMillis + (maxWaitSeconds * 1000L) : Long.MAX_VALUE;
	}

	@NonNull
	public ImmutableList<String> extractIdentifiers(@NonNull final String identifier)
	{
		return Arrays.stream(identifier.split(","))
				.map(StringUtils::trim)
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	public List<String> splitIdentifiers(@NonNull final String identifiers)
	{
		return Arrays.asList(identifiers.split(","));
	}

	public void validateErrorMessage(@NonNull final Exception e, @Nullable final String errorMessage) throws Exception
	{
		if (Check.isNotBlank(errorMessage))
		{
			assertThat(e.getMessage()).contains(errorMessage);
		}
		else
		{
			throw e;
		}
	}

	public List<String> splitByColon(@NonNull final String s)
	{
		return Arrays.asList(s.split(":"));
	}

	@NonNull
	public static String writeRowAsString(@NonNull final Map<String, String> row)
	{
		return JsonObjectMapperHolder.toJson(row);
	}
}