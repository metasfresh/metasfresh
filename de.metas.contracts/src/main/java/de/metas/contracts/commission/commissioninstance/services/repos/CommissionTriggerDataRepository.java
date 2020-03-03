package de.metas.contracts.commission.commissioninstance.services.repos;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import de.metas.contracts.commission.commissioninstance.services.CommissionTriggerRequest;
import de.metas.util.lang.Percent;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerData.CommissionTriggerDataBuilder;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.organization.OrgId;
import lombok.NonNull;

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

@Repository
public class CommissionTriggerDataRepository
{
	private final InvoiceCandidateRecordHelper icRecordHelper;

	public CommissionTriggerDataRepository(@NonNull final InvoiceCandidateRecordHelper icRecordHelper)
	{
		this.icRecordHelper = icRecordHelper;
	}

	public CommissionTriggerData getForInvoiceCandidateId(@NonNull final InvoiceCandidateId invoiceCandidateId, final boolean candidateDeleted)
	{
		final I_C_Invoice_Candidate icRecord = load(invoiceCandidateId, I_C_Invoice_Candidate.class);

		final CommissionTriggerRequest commissionTriggerRequest =
				CommissionTriggerRequest
						.builder()
						.orgId(OrgId.ofRepoId(icRecord.getAD_Org_ID()))
						.candidateDeleted(candidateDeleted)
						.invoiceCandidateId(invoiceCandidateId)
						.forecastCommissionPoints( icRecordHelper.extractForecastCommissionPoints(icRecord) )
						.commissionPointsToInvoice( icRecordHelper.extractCommissionPointsToInvoice(icRecord) )
						.invoicedCommissionPoints( icRecordHelper.extractInvoicedCommissionPoints(icRecord) )
						.tradedCommissionPercent( icRecordHelper.extractTradedCommissionPercent(icRecord) )
						.timestamp( TimeUtil.asInstant( icRecord.getUpdated() ) )
						.build();

		return createForRequest(commissionTriggerRequest);
	}

	public CommissionTriggerData createForRequest(final CommissionTriggerRequest commissionTriggerRequest)
	{
		final CommissionTriggerDataBuilder builder = CommissionTriggerData
				.builder()
				.orgId(commissionTriggerRequest.getOrgId())
				.invoiceCandidateWasDeleted( commissionTriggerRequest.isCandidateDeleted() )
				.invoiceCandidateId( commissionTriggerRequest.getInvoiceCandidateId() )
				.timestamp( commissionTriggerRequest.getTimestamp() );

		if ( commissionTriggerRequest.isCandidateDeleted() )
		{
			builder
					.forecastedPoints(CommissionPoints.ZERO)
					.invoiceablePoints(CommissionPoints.ZERO)
					.invoicedPoints(CommissionPoints.ZERO)
					.tradedCommissionPercent(Percent.ZERO);
		}
		else
		{
			builder
					.forecastedPoints( commissionTriggerRequest.getForecastCommissionPoints() )
					.invoiceablePoints( commissionTriggerRequest.getCommissionPointsToInvoice() )
					.invoicedPoints( commissionTriggerRequest.getInvoicedCommissionPoints() )
					.tradedCommissionPercent( commissionTriggerRequest.getTradedCommissionPercent() );
		}
		return builder.build();
	}
}
