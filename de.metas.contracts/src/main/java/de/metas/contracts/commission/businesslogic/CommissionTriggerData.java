package de.metas.contracts.commission.businesslogic;

import static de.metas.util.lang.CoalesceUtil.coalesce;

import java.time.Instant;

import de.metas.invoicecandidate.InvoiceCandidateId;
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
	InvoiceCandidateId invoiceCandidateId;

	/** Exact timestamp of this trigger. E.g. if one invoice candidate is changed, there might be two triggers wit different timestamps that relate to the same IC. */
	Instant timestamp;

	CommissionPoints forecastedPoints;

	CommissionPoints invoiceablePoints;

	CommissionPoints invoicedPoints;

	@Builder
	private CommissionTriggerData(
			@NonNull final InvoiceCandidateId invoiceCandidateId,
			@NonNull final Instant timestamp,
			@NonNull final CommissionPoints forecastedPoints,
			@NonNull final CommissionPoints invoiceablePoints,
			@NonNull final CommissionPoints invoicedPoints)
	{
		this.invoiceCandidateId = invoiceCandidateId;
		this.timestamp = timestamp;

		this.forecastedPoints = coalesce(forecastedPoints, CommissionPoints.ZERO);
		this.invoiceablePoints = coalesce(invoiceablePoints, CommissionPoints.ZERO);
		this.invoicedPoints = coalesce(invoicedPoints, CommissionPoints.ZERO);
	}
}
