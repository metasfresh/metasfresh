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

import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.ExplainedOptional;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.NumberUtils;
import de.metas.util.StringUtils;
import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.junit.jupiter.api.Assertions;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * TODO: move all "waiting" methods to de.metas.common.util.{@link de.metas.common.util.TryAndWaitUtil}.
 */
@UtilityClass
public class StepDefUtil
{
	private static final Logger logger = LogManager.getLogger(StepDefUtil.class);
	private static final String SYS_maxWaitSeconds = "maxWaitSeconds";
	private static ExplainedOptional<Long> _sys_maxWaitSeconds; // lazy

	/**
	 * Waits for the given {@code worker} to supply {@code true}.
	 * Fails if this doesn't happen within the given {@code maxWaitSeconds} timeout.
	 *
	 * @param maxWaitSecondsParam set to a value <=0 to wait forever (use only when developing locally)
	 */
	public void tryAndWait(
			final long maxWaitSecondsParam,
			final long checkingIntervalMs,
			@NonNull final Supplier<Boolean> worker,
			@Nullable final Runnable logContext) throws InterruptedException
	{
		StepDefUtil.<Boolean>tryAndWaitForItem()
				.workerFromBooleanSupplier(worker)
				.logContextUsingRunnable(logContext)
				.maxWaitSeconds((int)maxWaitSecondsParam)
				.checkingIntervalMs(checkingIntervalMs)
				.execute();
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

	public static <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final ItemProvider<T> worker, 
			@Nullable final Runnable logContext) throws InterruptedException
	{
		return StepDefUtil.<T>tryAndWaitForItem()
				.worker(worker)
				.logContextUsingRunnable(logContext)
				.maxWaitSeconds((int)maxWaitSeconds)
				.checkingIntervalMs(checkingIntervalMs)
				.execute();

	}

	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final ItemProvider<T> worker) throws InterruptedException
	{
		return tryAndWaitForItem(maxWaitSeconds, checkingIntervalMs, worker, (Supplier<String>)null);
	}

	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final IQuery<T> query) throws InterruptedException
	{
		return tryAndWaitForItem(query)
				.maxWaitSeconds((int)maxWaitSeconds)
				.checkingIntervalMs(checkingIntervalMs)
				.execute();
	}

	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final IQuery<T> query,
			@NonNull final Function<T, BooleanWithReason> validator) throws InterruptedException
	{
		return tryAndWaitForItem(query)
				.validateUsingFunction(validator)
				.maxWaitSeconds((int)maxWaitSeconds)
				.checkingIntervalMs(checkingIntervalMs)
				.execute();
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
	 * @param maxWaitSecondsParam set to a value <=0 to wait forever (use only when developing locally)
	 */
	@Deprecated
	public <T> T tryAndWaitForItem(
			final long maxWaitSecondsParam,
			final long checkingIntervalMs,
			@NonNull final Supplier<Optional<T>> worker,
			@Nullable final Runnable logContext) throws InterruptedException
	{
		final long maxWaitSeconds = getMaxWaitSecondsEffective(maxWaitSecondsParam);
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

	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final Supplier<Optional<T>> optionalSupplier) throws InterruptedException
	{
		return StepDefUtil.<T>tryAndWaitForItem()
				.workerFromOptionalSupplier(optionalSupplier)
				.maxWaitSeconds((int)maxWaitSeconds)
				.checkingIntervalMs(checkingIntervalMs)
				.execute();
	}

	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final IQuery<T> query,
			@Nullable final Supplier<String> logContext) throws InterruptedException
	{
		return tryAndWaitForItem(query)
				.logContext(logContext)
				.maxWaitSeconds((int)maxWaitSeconds)
				.checkingIntervalMs(checkingIntervalMs)
				.execute();
	}

	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final IQuery<T> query,
			@Nullable final Consumer<T> validator,
			@Nullable final Supplier<String> logContext) throws InterruptedException
	{
		return tryAndWaitForItem(query)
				.validateUsingConsumer(validator)
				.logContext(logContext)
				.maxWaitSeconds((int)maxWaitSeconds)
				.checkingIntervalMs(checkingIntervalMs)
				.execute();
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
		return StepDefUtil.<T>tryAndWaitForItem()
				.worker(worker)
				.logContext(logContext)
				.maxWaitSeconds((int)maxWaitSeconds)
				.checkingIntervalMs(checkingIntervalMs)
				.execute();
	}

	public <T> ItemFetcherExecutor.ItemFetcherExecutorBuilder<T> tryAndWaitForItem()
	{
		return ItemFetcherExecutor.builder();
	}

	public <T> ItemFetcherExecutor.ItemFetcherExecutorBuilder<T> tryAndWaitForItem(@NonNull final IQuery<T> query)
	{
		return ItemFetcherExecutor.<T>builder().query(query);
	}

	static long getMaxWaitSecondsEffective(final long maxWaitSecondsParam)
	{
		final Long sys_maxWaitSeconds = getSysMaxWaitSeconds().orElse(null);
		if (sys_maxWaitSeconds != null)
		{
			final long maxWaitSecondsEffective = sys_maxWaitSeconds;
			logger.info("NOTE: using maxWaitSeconds={}s (from `{}` system property), instead of {}s", maxWaitSecondsEffective, SYS_maxWaitSeconds, maxWaitSecondsParam);
			return maxWaitSecondsEffective;
		}

		return maxWaitSecondsParam;
	}

	@NonNull
	public static ExplainedOptional<Long> getSysMaxWaitSeconds()
	{
		ExplainedOptional<Long> sys_maxWaitSeconds = StepDefUtil._sys_maxWaitSeconds;
		if (sys_maxWaitSeconds == null)
		{
			sys_maxWaitSeconds = StepDefUtil._sys_maxWaitSeconds = computeSysMaxWaitSeconds();
		}
		return sys_maxWaitSeconds;
	}

	private static ExplainedOptional<Long> computeSysMaxWaitSeconds()
	{
		final Integer maxWaitSeconds = StringUtils.trimBlankToOptional(System.getProperty(SYS_maxWaitSeconds))
				.map(value -> NumberUtils.asInteger(value, null))
				.orElse(null);
		if (maxWaitSeconds != null)
		{
			if (Adempiere.isJVMDebugMode())
			{
				return ExplainedOptional.of((long)maxWaitSeconds);
			}
			else
			{
				final String reason = "Ignored -D" + SYS_maxWaitSeconds + "=" + maxWaitSeconds + " because JVM debugging is not enabled";
				logger.info(reason);
				return ExplainedOptional.emptyBecause(reason);
			}
		}
		else
		{
			return ExplainedOptional.emptyBecause("No -D" + SYS_maxWaitSeconds + " set");
		}
	}

	static long computeDeadLineMillis(final long maxWaitSeconds)
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