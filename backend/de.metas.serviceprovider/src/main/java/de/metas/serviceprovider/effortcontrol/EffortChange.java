/*
 * #%L
 * de.metas.serviceprovider.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.serviceprovider.effortcontrol;

import com.google.common.collect.ImmutableSet;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

@Value
public class EffortChange
{
	@NonNull
	ClientId clientId;

	@Nullable
	EffortInfo currentEffortValues;

	@Nullable
	EffortInfo oldEffortValues;

	@Builder
	public EffortChange(
			@Nullable final EffortInfo currentEffortValues,
			@Nullable final EffortInfo oldEffortValues,
			@NonNull final ClientId clientId)
	{
		if (currentEffortValues == null && oldEffortValues == null)
		{
			throw new AdempiereException("At least one effort value must be present to compute effort change");
		}

		this.currentEffortValues = currentEffortValues;
		this.oldEffortValues = oldEffortValues;
		this.clientId = clientId;
	}

	@NonNull
	public Set<EffortTarget> listTargets()
	{
		return Stream.of(oldEffortValues, currentEffortValues)
				.filter(Objects::nonNull)
				.map(EffortInfo::getEffortTarget)
				.collect(ImmutableSet.toImmutableSet());
	}

	@NonNull
	public BigDecimal getDeltaBudgetForTarget(@NonNull final EffortTarget target)
	{
		return getDeltaForTarget(target, EffortInfo::getBudget, null/*sameTargetDeltaProvider*/);
	}

	@NonNull
	public BigDecimal getDeltaInvoiceableHoursForTarget(@NonNull final EffortTarget target)
	{
		return getDeltaForTarget(target, EffortInfo::getInvoiceableHours, null/*sameTargetDeltaProvider*/);
	}

	@NonNull
	public Duration getDeltaEffortSumForTarget(@NonNull final EffortTarget target)
	{
		return Duration.ofSeconds(getDeltaForTarget(target,
													EffortInfo::getEffortSumInSeconds,
													null/*sameTargetDeltaProvider*/).longValueExact());
	}

	@NonNull
	public Duration getPendingEffortSumForTarget(@NonNull final EffortTarget target)
	{
		return Duration.ofSeconds(getDeltaForTarget(target,
													EffortInfo::getPendingEffortInSeconds,
													this::resolveDeltaPendingEffortWithinSameTarget)
										  .longValueExact());
	}

	@NonNull
	private BigDecimal resolveDeltaPendingEffortWithinSameTarget()
	{
		Check.assumeNotNull(oldEffortValues, "At this point oldEffortValues should not be null");
		Check.assumeNotNull(currentEffortValues, "At this point currentEffortValues should not be null");

		Check.assume(EffortTarget.equals(oldEffortValues.getEffortTarget(), currentEffortValues.getEffortTarget()), "At this point we have the same target!");

		if (statusChangedToInvoiced())
		{
			return oldEffortValues.getPendingEffortInSeconds().negate();
		}
		else if (statusChangedFromInvoiced())
		{
			return currentEffortValues.getPendingEffortInSeconds();
		}

		Check.assume(currentEffortValues.isInvoiced() == oldEffortValues.isInvoiced(), "At this point it's clear the invoiced status is not changed!");

		final boolean oldAndCurrentInvoiced = currentEffortValues.isInvoiced();

		if (oldAndCurrentInvoiced)
		{
			return BigDecimal.ZERO;
		}

		return currentEffortValues.getPendingEffortInSeconds().subtract(oldEffortValues.getPendingEffortInSeconds());
	}

	@NonNull
	private BigDecimal getDeltaForTarget(
			@NonNull final EffortTarget target,
			@NonNull final Function<EffortInfo, BigDecimal> valueProvider,
			@Nullable final Supplier<BigDecimal> sameTargetDeltaProvider)
	{
		final boolean hasTargetChanged = hasTargetChanged();

		final boolean isOldTarget = Optional.ofNullable(oldEffortValues)
				.map(EffortInfo::getEffortTarget)
				.filter(target::equals)
				.isPresent();

		if (hasTargetChanged && isOldTarget)
		{
			Check.assumeNotNull(oldEffortValues, "At this point oldEffortValues should not be null");

			return valueProvider.apply(oldEffortValues).negate();
		}
		else if (hasTargetChanged)
		{
			return valueProvider.apply(currentEffortValues);
		}
		else
		{
			//dev-note: same target
			if (sameTargetDeltaProvider != null)
			{
				return sameTargetDeltaProvider.get();
			}

			Check.assumeNotNull(oldEffortValues, "At this point oldEffortValues should not be null");
			Check.assumeNotNull(currentEffortValues, "At this point currentEffortValues should not be null");

			return valueProvider.apply(currentEffortValues).subtract(valueProvider.apply(oldEffortValues));
		}
	}

	private boolean hasTargetChanged()
	{
		if (currentEffortValues == null || oldEffortValues == null)
		{
			return true;
		}

		return !EffortTarget.equals(currentEffortValues.getEffortTarget(), oldEffortValues.getEffortTarget());
	}

	private boolean statusChangedToInvoiced()
	{
		if (oldEffortValues == null || currentEffortValues == null)
		{
			return false;
		}

		return !oldEffortValues.isInvoiced() && currentEffortValues.isInvoiced();
	}

	private boolean statusChangedFromInvoiced()
	{
		if (oldEffortValues == null || currentEffortValues == null)
		{
			return false;
		}

		return oldEffortValues.isInvoiced() && !currentEffortValues.isInvoiced();
	}
}