package de.metas.contracts.commission.commissioninstance.services;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.time.LocalDate;
import java.util.Optional;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.util.lang.Percent;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import de.metas.bpartner.BPartnerId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Customer;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionTriggerDataRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
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
	private final CommissionTriggerDataRepository commissionTriggerDataRepository;

	private final MoneyService moneyService;

	public CommissionTriggerFactory(@NonNull final CommissionTriggerDataRepository commissionTriggerDataRepository,
			@NonNull final MoneyService moneyService)
	{
		this.commissionTriggerDataRepository = commissionTriggerDataRepository;
		this.moneyService = moneyService;
	}

	public Optional<CommissionTrigger> createForNewSalesInvoiceCandidate(@NonNull final InvoiceCandidateId invoiceCandidateId)
	{
		final I_C_Invoice_Candidate icRecord = load(invoiceCandidateId, I_C_Invoice_Candidate.class);
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
				.customer(Customer.of(BPartnerId.ofRepoId(icRecord.getBill_BPartner_ID())))
				.beneficiary(Beneficiary.of(salesRepId))
				.commissionTriggerData(commissionTriggerDataRepository.getForInvoiceCandidateId(invoiceCandidateId, false/* candidateDeleted */))
				.build();

		return Optional.of(trigger);
	}

	public CommissionTrigger createForForecastQtyAndPrice(
			@NonNull final OrgId orgId,
			@NonNull final ProductPrice productPrice,
			@NonNull final Quantity forecastQty,
			@NonNull final BPartnerId salesRepId,
			@NonNull final BPartnerId customerId)
	{
		final Money forecastNetAmt = moneyService.multiply(forecastQty, productPrice);

		final CommissionTriggerRequest commissionTriggerRequest = CommissionTriggerRequest
				.builder()
				.orgId(orgId)
				.forecastCommissionPoints(CommissionPoints.of(forecastNetAmt.toBigDecimal()))
				.invoicedCommissionPoints(CommissionPoints.ZERO)
				.commissionPointsToInvoice(CommissionPoints.ZERO)
				.tradedCommissionPercent(Percent.ZERO)
				.candidateDeleted(Boolean.FALSE)
				.timestamp(TimeUtil.asInstant(LocalDate.now()))
				.build();

		return CommissionTrigger.builder()
				.customer(Customer.of(customerId))
				.beneficiary(Beneficiary.of(salesRepId))
				.commissionTriggerData(commissionTriggerDataRepository.createForRequest(commissionTriggerRequest))
				.build();
	}

}
