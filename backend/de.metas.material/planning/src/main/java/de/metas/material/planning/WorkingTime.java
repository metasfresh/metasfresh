package de.metas.material.planning;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.temporal.TemporalUnit;

import de.metas.quantity.Quantity;
import de.metas.util.time.DurationUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class WorkingTime
{
	Duration duration;
	int cycles;

	TemporalUnit activityTimeUnit;
	Duration durationPerOneUnit;
	int unitsPerCycle;
	@NonNull Quantity qty;

	@Builder
	public WorkingTime(
			@NonNull final Duration durationPerOneUnit,
			final int unitsPerCycle,
			@NonNull final Quantity qty,
			@NonNull final TemporalUnit activityTimeUnit)
	{
		this.durationPerOneUnit = durationPerOneUnit;
		this.unitsPerCycle = unitsPerCycle;
		this.qty = qty;
		this.activityTimeUnit = activityTimeUnit;

		this.cycles = calculateCycles(unitsPerCycle, qty.toBigDecimal());
		this.duration = durationPerOneUnit.multipliedBy(cycles);

	}

	/**
	 * @return how many cycles are needed for given qty and units per cycle
	 */
	private static int calculateCycles(final int unitsPerCycle, final BigDecimal qty)
	{
		if (unitsPerCycle > 0)
		{
			final BigDecimal unitsCycleBD = BigDecimal.valueOf(unitsPerCycle);
			return qty.divide(unitsCycleBD, 0, RoundingMode.UP).intValueExact();
		}
		else
		{
			// consider unitsPerCycle = 1
			return qty.setScale(0, RoundingMode.UP).intValueExact();
		}
	}

	public long toLongUsingActivityTimeUnit()
	{
		return DurationUtils.toLong(getDuration(), activityTimeUnit);
	}

	public BigDecimal toBigDecimalUsingActivityTimeUnit()
	{
		return DurationUtils.toBigDecimal(getDuration(), activityTimeUnit);
	}

}
