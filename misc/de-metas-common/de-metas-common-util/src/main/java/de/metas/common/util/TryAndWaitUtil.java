/*
 * #%L
 * de-metas-common-util
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.common.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;
import java.util.function.Supplier;

@UtilityClass
public class TryAndWaitUtil
{
	public boolean tryAndWait(
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
			logContext.run(); // output some helpful logging
		}

		return conditionIsMet;
	}

	private long computeDeadLineMillis(final long maxWaitSeconds)
	{
		final long nowMillis = System.currentTimeMillis(); // don't use SystemTime.millis(); because it's probably "rigged" for testing purposes,
		final long deadLineMillis = maxWaitSeconds > 0 ? nowMillis + (maxWaitSeconds * 1000L) : Long.MAX_VALUE;
		return deadLineMillis;
	}
}
