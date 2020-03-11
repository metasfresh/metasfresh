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

package de.metas.contracts.commission.commissioninstance.services;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.organization.OrgId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class CommissionTriggerRequest
{
	boolean candidateDeleted;

	@NonNull
	Instant timestamp;

	@NonNull
	OrgId orgId;

	InvoiceCandidateId invoiceCandidateId;

	@NonNull
	CommissionPoints forecastCommissionPoints;

	@NonNull
	CommissionPoints commissionPointsToInvoice;

	@NonNull
	CommissionPoints invoicedCommissionPoints;

	@NonNull
	Percent tradedCommissionPercent;
}
