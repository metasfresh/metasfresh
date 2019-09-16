package de.metas.contracts.commission.businesslogic;

import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.time.Instant;

import javax.annotation.Nullable;

import lombok.Builder;
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

/** "basically" an invoice candidate */
@Value
public class CommissionTrigger
{
	CommissionTriggerId id;

	/** Exact timestamp of this trigger. E.g. if one invoice candidate is changed, there might be two triggers wit different timestamps that relate to the same IC. */
	Instant timestamp;

	Customer customer;

	/** The direct beneficiary; usually the customer's "direct" sales rep. Will probably be part of a hierarchy. */
	Beneficiary beneficiary;

	CommissionPoints forecastedPoints;

	CommissionPoints pointsToInvoice;

	CommissionPoints invoicedPoints;

	@Builder
	private CommissionTrigger(
			@NonNull final CommissionTriggerId id,
			@NonNull final Instant timestamp,
			@NonNull final Customer customer,
			@NonNull final Beneficiary beneficiary,
			@Nullable final CommissionPoints forecastedPoints,
			@Nullable final CommissionPoints pointsToInvoice,
			@Nullable final CommissionPoints invoicedPoints)
	{
		this.id = id;
		this.timestamp = timestamp;
		this.customer = customer;
		this.beneficiary = beneficiary;
		this.forecastedPoints = coalesce(forecastedPoints, CommissionPoints.ZERO);
		this.pointsToInvoice = coalesce(pointsToInvoice, CommissionPoints.ZERO);
		this.invoicedPoints = coalesce(invoicedPoints, CommissionPoints.ZERO);
	}

	public CommissionPoints getCommissionBase()
	{
		return forecastedPoints.add(pointsToInvoice).add(invoicedPoints);
	}
}
