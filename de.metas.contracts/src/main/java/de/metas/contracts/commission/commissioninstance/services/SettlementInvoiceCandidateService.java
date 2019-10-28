package de.metas.contracts.commission.commissioninstance.services;

import static java.math.BigDecimal.ZERO;

import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;

import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementFact;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.settlement.CommissionSettlementState;
import de.metas.contracts.commission.commissioninstance.services.repos.CommissionSettlementShareRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.util.Services;
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
public class SettlementInvoiceCandidateService
{
	private final CommissionSettlementShareRepository commissionSettlementShareRepository;

	private final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	public SettlementInvoiceCandidateService(
			@NonNull final CommissionSettlementShareRepository commissionSettlementShareRepository)
	{
		this.commissionSettlementShareRepository = commissionSettlementShareRepository;
	}

	public void syncSettlementICToCommissionInstance(
			@NonNull final InvoiceCandidateId invoiceCandidateId,
			final boolean candidateDeleted)
	{
		final I_C_Invoice_Candidate settlementICRecord = invoiceCandDAO.getById(invoiceCandidateId);

		final CommissionSettlementShare settlementShare = commissionSettlementShareRepository.getByInvoiceCandidateId(invoiceCandidateId);

		//
		// pointsToSettle fact
		final CommissionPoints newPointsToSettle = CommissionPoints.of(candidateDeleted ? ZERO : settlementICRecord.getQtyToInvoice());
		final CommissionPoints pointsToSettleDelta = newPointsToSettle.subtract(settlementShare.getPointsToSettleSum());
		if (!pointsToSettleDelta.isZero())
		{
			final CommissionSettlementFact fact = CommissionSettlementFact.builder()
					.settlementInvoiceCandidateId(invoiceCandidateId)
					.timestamp(TimeUtil.asInstant(settlementICRecord.getUpdated()))
					.state(CommissionSettlementState.TO_SETTLE)
					.points(pointsToSettleDelta)
					.build();
			settlementShare.addFact(fact);
		}

		//
		// settledPoints fact
		final CommissionPoints settledPoints = CommissionPoints.of(candidateDeleted ? ZERO : settlementICRecord.getQtyInvoiced());
		final CommissionPoints settledPointsDelta = settledPoints.subtract(settlementShare.getSettledPointsSum());
		if (!settledPointsDelta.isZero())
		{
			final CommissionSettlementFact fact = CommissionSettlementFact.builder()
					.settlementInvoiceCandidateId(invoiceCandidateId)
					.timestamp(TimeUtil.asInstant(settlementICRecord.getUpdated()))
					.state(CommissionSettlementState.SETTLED)
					.points(settledPointsDelta)
					.build();
			settlementShare.addFact(fact);
		}

		commissionSettlementShareRepository.save(settlementShare);
	}
}
