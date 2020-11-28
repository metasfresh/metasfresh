package de.metas.contracts.commission.commissioninstance.businesslogic.settlement;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.invoicecandidate.InvoiceCandidateId;
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

@Value
public class CommissionSettlementFact
{
	InvoiceCandidateId settlementInvoiceCandidateId;

	/** This fact's timestamp; note that we need chronology, but don't care for a particular timezone. */
	Instant timestamp;

	CommissionSettlementState state;

	CommissionPoints points;

	@JsonCreator
	@Builder
	private CommissionSettlementFact(
			@JsonProperty("settlementInvoiceCandidateId") @NonNull final InvoiceCandidateId settlementInvoiceCandidateId,
			@JsonProperty("timestamp") @NonNull final Instant timestamp,
			@JsonProperty("state") @NonNull final CommissionSettlementState state,
			@JsonProperty("points") @NonNull final CommissionPoints points)
	{
		this.settlementInvoiceCandidateId = settlementInvoiceCandidateId;
		this.timestamp = timestamp;
		this.state = state;
		this.points = points;
	}
}
