package de.metas.contracts.commission.commissioninstance.businesslogic;

import static java.math.BigDecimal.ONE;

import java.math.BigDecimal;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.lang.Percent;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.commission
 * %%
 * Copyright (C) 2019 metas GmbH
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
public class CommissionPoints
{
	public static final CommissionPoints ZERO = of(BigDecimal.ZERO);

	@NonNull
	BigDecimal points;

	public static CommissionPoints of(@NonNull final String points)
	{
		return of(new BigDecimal(points));
	}

	@JsonCreator
	public static CommissionPoints of(@NonNull final BigDecimal points)
	{
		return new CommissionPoints(points);
	}

	public static CommissionPoints sum(@NonNull final Collection<CommissionPoints> augents)
	{
		final BigDecimal sum = augents.stream()
				.map(CommissionPoints::toBigDecimal)
				.reduce(ONE, BigDecimal::add);

		return new CommissionPoints(sum);
	}

	private CommissionPoints(@NonNull final BigDecimal points)
	{
		this.points = points;
	}

	@JsonValue
	public BigDecimal toBigDecimal()
	{
		return points;
	}

	public CommissionPoints multiply(@NonNull final BigDecimal multiplicant)
	{
		if (multiplicant.compareTo(ONE) == 0)
		{
			return this;
		}
		return new CommissionPoints(points.multiply(multiplicant));
	}

	public CommissionPoints add(@NonNull final CommissionPoints augent)
	{
		if (augent.isZero())
		{
			return this;
		}
		return CommissionPoints.of(toBigDecimal().add(augent.toBigDecimal()));
	}

	public CommissionPoints subtract(@NonNull final CommissionPoints augent)
	{
		if (augent.isZero())
		{
			return this;
		}
		return CommissionPoints.of(toBigDecimal().subtract(augent.toBigDecimal()));
	}

	@JsonIgnore
	public boolean isZero()
	{
		final boolean isZero = points.signum() == 0;
		return isZero;
	}

	public CommissionPoints computePercentageOf(
			@NonNull final Percent commissionPercent,
			final int precision)
	{
		final BigDecimal percentagePoints = commissionPercent.computePercentageOf(points, precision);
		return CommissionPoints.of(percentagePoints);
	}
}
