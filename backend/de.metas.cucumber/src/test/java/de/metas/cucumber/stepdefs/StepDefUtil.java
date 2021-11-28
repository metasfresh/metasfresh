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

import io.cucumber.java.en.And;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.function.Supplier;

@UtilityClass
public class StepDefUtil
{
	public boolean tryAndWait(final long maxWaitSeconds, final long checkingIntervalMs, final Supplier<Boolean> worker ) throws InterruptedException
	{
		final long nowMillis = System.currentTimeMillis(); // don't use SystemTime.millis(); because it's probably "rigged" for testing purposes,
		final long deadLineMillis = nowMillis + (maxWaitSeconds * 1000L);

		boolean conditionIsMet = false;

		while (System.currentTimeMillis() < deadLineMillis && !conditionIsMet)
		{
			Thread.sleep(checkingIntervalMs);
			conditionIsMet = worker.get();
		}

		return conditionIsMet;
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
}
