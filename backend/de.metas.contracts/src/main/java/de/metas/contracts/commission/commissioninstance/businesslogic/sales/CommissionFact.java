package de.metas.contracts.commission.commissioninstance.businesslogic.sales;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;
import java.util.Optional;

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
public class CommissionFact
{
	/** This fact's timestamp; note that we need chronology, but don't care for a particular timezone. */
	Instant timestamp;

	CommissionState state;

	CommissionPoints points;

	@JsonCreator
	@Builder
	private CommissionFact(
			@JsonProperty("timestamp") @NonNull final Instant timestamp,
			@JsonProperty("state") @NonNull final CommissionState state,
			@JsonProperty("points") @NonNull final CommissionPoints points)
	{
		this.timestamp = timestamp;
		this.state = state;
		this.points = points;
	}

	public static Optional<CommissionFact> createFact(
			@NonNull final Instant timestamp,
			@NonNull final CommissionState state,
			@NonNull final CommissionPoints currentCommissionPoints,
			@NonNull final CommissionPoints previousCommissionPoints)
	{
		final CommissionPoints points = currentCommissionPoints.subtract(previousCommissionPoints);

		if (points.isZero())
		{
			return Optional.empty(); // a zero-points fact would not change anything, so don't bother creating it
		}

		final CommissionFact fact = CommissionFact.builder()
				.state(state)
				.points(points)
				.timestamp(timestamp)
				.build();
		return Optional.of(fact);
	}
}
