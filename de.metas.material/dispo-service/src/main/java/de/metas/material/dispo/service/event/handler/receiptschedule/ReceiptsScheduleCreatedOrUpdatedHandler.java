package de.metas.material.dispo.service.event.handler.receiptschedule;

import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.Candidate.CandidateBuilder;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail.PurchaseDetailBuilder;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.receiptschedule.AbstractReceiptScheduleEvent;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-material-dispo-service
 * %%
 * Copyright (C) 2018 metas GmbH
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

abstract class ReceiptsScheduleCreatedOrUpdatedHandler<T extends AbstractReceiptScheduleEvent>
		implements MaterialEventHandler<T>
{

	private final CandidateChangeService candidateChangeHandler;
	private final CandidateRepositoryRetrieval candidateRepositoryRetrieval;

	protected ReceiptsScheduleCreatedOrUpdatedHandler(
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final CandidateRepositoryRetrieval candidateRepositoryRetrieval)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.candidateRepositoryRetrieval = candidateRepositoryRetrieval;
	}

	protected final void handleReceiptScheduleEvent(@NonNull final AbstractReceiptScheduleEvent event)
	{
		final Candidate existingSupplyCandidate = retrieveExistingSupplyCandidateOrNull(event);

		final CandidateBuilder supplyCandidateBuilder = existingSupplyCandidate != null
				? existingSupplyCandidate.toBuilder()
				: prepareInitialSupplyCandidate(event);

		final Candidate supplyCandidate = supplyCandidateBuilder
				.businessCaseDetail(createPurchaseDetail(event))
				.quantity(event.getReservedQuantity())
				.build();

		candidateChangeHandler.onCandidateNewOrChange(supplyCandidate);
	}

	private Candidate retrieveExistingSupplyCandidateOrNull(@NonNull final AbstractReceiptScheduleEvent event)
	{
		final CandidatesQuery query = createCandidatesQuery(event);
		return candidateRepositoryRetrieval.retrieveLatestMatchOrNull(query);
	}

	protected abstract CandidatesQuery createCandidatesQuery(@NonNull final AbstractReceiptScheduleEvent event);

	private final CandidateBuilder prepareInitialSupplyCandidate(@NonNull final AbstractReceiptScheduleEvent event)
	{
		return Candidate.builder()
				.clientAndOrgId(event.getEventDescriptor().getClientAndOrgId())
				.type(CandidateType.SUPPLY)
				.materialDescriptor(event.getMaterialDescriptor())
				.businessCase(CandidateBusinessCase.PURCHASE);
	}

	private PurchaseDetail createPurchaseDetail(final AbstractReceiptScheduleEvent event)
	{
		final PurchaseDetailBuilder purchaseDetailBuilder = PurchaseDetail.builder()
				.qty(event.getMaterialDescriptor().getQuantity())
				.receiptScheduleRepoId(event.getReceiptScheduleId())
				.advised(Flag.FALSE_DONT_UPDATE);

		return updatePurchaseDetailBuilderFromEvent(purchaseDetailBuilder, event)
				.build();
	}

	protected abstract PurchaseDetailBuilder updatePurchaseDetailBuilderFromEvent(
			@NonNull final PurchaseDetailBuilder builder,
			@NonNull final AbstractReceiptScheduleEvent event);
}
