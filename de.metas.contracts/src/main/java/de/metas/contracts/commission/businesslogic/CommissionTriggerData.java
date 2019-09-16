package de.metas.contracts.commission.businesslogic;

import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.time.Instant;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.contracts
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
public class CommissionTriggerData
{
	/** Exact timestamp of this trigger. E.g. if one invoice candidate is changed, there might be two triggers wit different timestamps that relate to the same IC. */
	@NonNull
	Instant timestamp;

	@NonNull
	CommissionPoints forecastedPoints;

	@NonNull
	CommissionPoints pointsToInvoice;

	@NonNull
	CommissionPoints invoicedPoints;

	@Builder
	private CommissionTriggerData(
			@NonNull final Instant timestamp,
			@NonNull final CommissionPoints forecastedPoints,
			@NonNull final CommissionPoints pointsToInvoice,
			@NonNull final CommissionPoints invoicedPoints)
	{
		this.timestamp = timestamp;

		this.forecastedPoints = coalesce(forecastedPoints, CommissionPoints.ZERO);
		this.pointsToInvoice = coalesce(pointsToInvoice, CommissionPoints.ZERO);
		this.invoicedPoints = coalesce(invoicedPoints, CommissionPoints.ZERO);
	}
}
