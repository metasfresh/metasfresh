package de.metas.contracts.commission.services;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.util.Optional;

import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.businesslogic.Beneficiary;
import de.metas.contracts.commission.businesslogic.CommissionPoints;
import de.metas.contracts.commission.businesslogic.CommissionTrigger;
import de.metas.contracts.commission.businesslogic.CommissionTriggerData;
import de.metas.contracts.commission.businesslogic.CommissionTriggerId;
import de.metas.contracts.commission.businesslogic.Customer;
import de.metas.contracts.commission.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.InvoiceCandidateId;
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

@Service
public class CommissionTriggerFactory
{
	private final InvoiceCandidateRecordHelper icRecordHelper;

	public CommissionTriggerFactory(@NonNull final InvoiceCandidateRecordHelper icRecordHelper)
	{
		this.icRecordHelper = icRecordHelper;
	}

	public Optional<CommissionTrigger> createForId(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final I_C_Invoice_Candidate icRecord = loadOutOfTrx(invoiceCandidateId, I_C_Invoice_Candidate.class);
		return createForRecord(icRecord);
	}

	private Optional<CommissionTrigger> createForRecord(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final BPartnerId salesRepId = BPartnerId.ofRepoIdOrNull(icRecord.getC_BPartner_SalesRep_ID());
		if (salesRepId == null)
		{
			return Optional.empty();
		}

		final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoIdOrNull(icRecord.getC_Invoice_Candidate_ID());
		if (invoiceCandidateId == null)
		{
			return Optional.empty();
		}

		final CommissionTrigger trigger = CommissionTrigger.builder()
				.customer(new Customer(BPartnerId.ofRepoId(icRecord.getBill_BPartner_ID())))
				.timestamp(TimeUtil.asInstant(icRecord.getUpdated()))
				.id(new CommissionTriggerId(invoiceCandidateId.getRepoId()))
				.beneficiary(new Beneficiary(salesRepId))
				.commissionTriggerData(createCommissionTriggerData(icRecord))
				.build();

		return Optional.of(trigger);
	}

	private CommissionTriggerData createCommissionTriggerData(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final Money forecastNetAmt = icRecordHelper.extractForecastNetAmt(icRecord);
		final Money netAmtToInvoice = icRecordHelper.extractNetAmtToInvoice(icRecord);
		final Money invoicedNetAmount = icRecordHelper.extractInvoicedNetAmt(icRecord);

		final CommissionTriggerData commissionTrigerData = CommissionTriggerData.builder()
				.forecastedPoints(CommissionPoints.of(forecastNetAmt.toBigDecimal()))
				.pointsToInvoice(CommissionPoints.of(netAmtToInvoice.toBigDecimal()))
				.invoicedPoints(CommissionPoints.of(invoicedNetAmount.toBigDecimal()))
				.build();
		return commissionTrigerData;
	}

}
