package de.metas.material.dispo.service.event.handler.receiptschedule;

import java.util.Collection;

import com.google.common.collect.ImmutableList;

import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.PurchaseDetailsQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.receiptschedule.AbstractReceiptScheduleEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class ReceiptsScheduleCreatedHandler
		extends ReceiptsScheduleCreatedOrUpdatedHandler<ReceiptScheduleCreatedEvent>
{
	public ReceiptsScheduleCreatedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		super(candidateChangeHandler, candidateRepositoryRetrieval);
	}

	@Override
	public Collection<Class<? extends ReceiptScheduleCreatedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(ReceiptScheduleCreatedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final ReceiptScheduleCreatedEvent event)
	{
		handleReceiptScheduleEvent(event);
	}

	@Override
	protected CandidatesQuery createCandidatesQuery(@NonNull final AbstractReceiptScheduleEvent event)
	{
		final ReceiptScheduleCreatedEvent receiptScheduleCreatedEvent = (ReceiptScheduleCreatedEvent)event;
		if (receiptScheduleCreatedEvent.getPurchaseCandidateRepoId() <= 0)
		{
			return CandidatesQuery.FALSE;
		}

		final PurchaseDetailsQuery purchaseDetailsQuery = PurchaseDetailsQuery.builder()
				.purchaseCandidateRepoId(receiptScheduleCreatedEvent.getPurchaseCandidateRepoId())
				.build();

		return CandidatesQuery.builder()
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PURCHASE)
				.purchaseDetailsQuery(purchaseDetailsQuery)
				.build();
	}

	@Override
	protected int extractPurchaseCandidateRepoId(@NonNull final AbstractReceiptScheduleEvent event)
	{
		final ReceiptScheduleCreatedEvent receiptScheduleCreatedEvent = (ReceiptScheduleCreatedEvent)event;
		return receiptScheduleCreatedEvent.getPurchaseCandidateRepoId();
	}

	@Override
	protected int extractOrderLineRepoId(@NonNull final AbstractReceiptScheduleEvent event)
	{
		final ReceiptScheduleCreatedEvent receiptScheduleCreatedEvent = (ReceiptScheduleCreatedEvent)event;
		return receiptScheduleCreatedEvent.getOrderLineDescriptor().getOrderLineId();
	}
}
