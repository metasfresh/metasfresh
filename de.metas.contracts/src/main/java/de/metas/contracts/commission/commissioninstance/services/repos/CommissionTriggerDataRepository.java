package de.metas.contracts.commission.commissioninstance.services.repos;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerData.CommissionTriggerDataBuilder;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.Money;
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

	public CommissionTriggerData getForInvoiceCandiateId(@NonNull final InvoiceCandidateId invoiceCandidateId, final boolean candidateDeleted)
	{
		return createCommissionTriggerData(load(invoiceCandidateId, I_C_Invoice_Candidate.class), candidateDeleted);
	}

	private CommissionTriggerData createCommissionTriggerData(@NonNull final I_C_Invoice_Candidate icRecord, final boolean candidateDeleted)
	{
		final Money forecastNetAmt = icRecordHelper.extractForecastNetAmt(icRecord);
		final Money netAmtToInvoice = icRecordHelper.extractNetAmtToInvoice(icRecord);
		final Money invoicedNetAmount = icRecordHelper.extractInvoicedNetAmt(icRecord);

		final CommissionTriggerDataBuilder builder = CommissionTriggerData.builder()
				.invoiceCandidateWasDeleted(candidateDeleted)
				.invoiceCandidateId(InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID()))
				.timestamp(TimeUtil.asInstant(icRecord.getUpdated()));
		if (candidateDeleted)
		{
			builder
					.forecastedPoints(CommissionPoints.ZERO)
					.invoiceablePoints(CommissionPoints.ZERO)
					.invoicedPoints(CommissionPoints.ZERO);
		}
		else
		{
			builder
					.forecastedPoints(CommissionPoints.of(forecastNetAmt.toBigDecimal()))
					.invoiceablePoints(CommissionPoints.of(netAmtToInvoice.toBigDecimal()))
					.invoicedPoints(CommissionPoints.of(invoicedNetAmount.toBigDecimal()));
		}
		return builder.build();
	}
}
