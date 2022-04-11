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
import de.metas.common.util.StringUtils;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;
import org.junit.jupiter.api.Assertions;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@UtilityClass
public class StepDefUtil
{
	/**
	 * Waits for the given {@code worker} to supply {@code true}.
	 * Fails if this doesn't happen within the given {@code maxWaitSeconds} timeout.
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
	 * @param maxWaitSeconds set to a value <=0 to wait forever (use only when developing locally)
	 */
	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final Supplier<Optional<T>> worker,
			@Nullable final Runnable logContext) throws InterruptedException
	{
		final long deadLineMillis = computeDeadLineMillis(maxWaitSeconds);

		while (deadLineMillis > System.currentTimeMillis())
		{
			Thread.sleep(checkingIntervalMs);
			final Optional<T> workerResult = worker.get();
			if(workerResult.isPresent())
			{
				return workerResult.get();
			}
		}

		if (logContext != null)
		{
			logContext.run();
		}
		Assertions.fail("the given spllier didn't succeed within the "+maxWaitSeconds+"second timeout");
		return null;
				
	}

	public <T> T tryAndWaitForItem(
			final long maxWaitSeconds,
			final long checkingIntervalMs,
			@NonNull final Supplier<Optional<T>> worker) throws InterruptedException
	{
		return tryAndWaitForItem(maxWaitSeconds, checkingIntervalMs, worker, null);
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


	private long computeDeadLineMillis(final long maxWaitSeconds)
	{
		final long nowMillis = System.currentTimeMillis(); // don't use SystemTime.millis(); because it's probably "rigged" for testing purposes,
		final long deadLineMillis = maxWaitSeconds > 0 ? nowMillis + (maxWaitSeconds * 1000L): Long.MAX_VALUE;
		return deadLineMillis;
	}

	@Given("^wait (.*)s")
	public void waitFor(final int secondsToWait) throws InterruptedException
	{
		Thread.sleep(secondsToWait * 1000);
	}
}
