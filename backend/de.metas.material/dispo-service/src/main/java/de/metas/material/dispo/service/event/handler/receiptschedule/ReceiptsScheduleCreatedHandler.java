package de.metas.material.dispo.service.event.handler.receiptschedule;

import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.businesscase.PurchaseDetail.PurchaseDetailBuilder;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.receiptschedule.AbstractReceiptScheduleEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

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

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
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
	public Collection<Class<? extends ReceiptScheduleCreatedEvent>> getHandledEventType()
	{
		return ImmutableList.of(ReceiptScheduleCreatedEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final ReceiptScheduleCreatedEvent event)
	{
		handleReceiptScheduleEvent(event);
	}

	/** creates a query to look for existing records with the events purchase-candidate-repoId. */
	@Override
	protected CandidatesQuery createCandidatesQuery(@NonNull final AbstractReceiptScheduleEvent event)
	{
		final ReceiptScheduleCreatedEvent createdEvent = ReceiptScheduleCreatedEvent.cast(event);
		return ReceiptsScheduleHandlerUtil.queryByPurchaseCandidateId(createdEvent);
	}

	@Override
	protected PurchaseDetailBuilder updatePurchaseDetailBuilderFromEvent(
			@NonNull final PurchaseDetailBuilder builder,
			@NonNull final AbstractReceiptScheduleEvent event)
	{
		final ReceiptScheduleCreatedEvent createdEvent = ReceiptScheduleCreatedEvent.cast(event);

		builder.orderLineRepoId(createdEvent.getOrderLineDescriptor().getOrderLineId());
		builder.orderedQty(createdEvent.getMaterialDescriptor().getQuantity());
		builder.purchaseCandidateRepoId(createdEvent.getPurchaseCandidateRepoId());
		builder.vendorRepoId(createdEvent.getVendorId());

		return builder;
	}
}
