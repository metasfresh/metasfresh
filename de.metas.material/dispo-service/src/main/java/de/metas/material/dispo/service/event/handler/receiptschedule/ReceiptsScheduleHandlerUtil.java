package de.metas.material.dispo.service.event.handler.receiptschedule;

import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery.CandidatesQueryBuilder;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.PurchaseDetailsQuery;
import de.metas.material.event.receiptschedule.AbstractReceiptScheduleEvent;
import de.metas.material.event.receiptschedule.ReceiptScheduleCreatedEvent;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
class ReceiptsScheduleHandlerUtil
{
	public CandidatesQuery queryByPurchaseCandidateId(@NonNull final ReceiptScheduleCreatedEvent createdEvent)
	{
		final int purchaseCandidateRepoId = createdEvent.getPurchaseCandidateRepoId();
		if (purchaseCandidateRepoId > 0)
		{
			return prepareQuery(createdEvent)
					.purchaseDetailsQuery(PurchaseDetailsQuery.builder()
							.purchaseCandidateRepoId(purchaseCandidateRepoId)
							.orderLineRepoIdMustBeNull(true)
							.build())
					.build();
		}
		else
		{
			return CandidatesQuery.FALSE;
		}
	}

	public CandidatesQuery queryByReceiptScheduleId(@NonNull final AbstractReceiptScheduleEvent event)
	{
		return prepareQuery(event)
				.purchaseDetailsQuery(PurchaseDetailsQuery.builder()
						.receiptScheduleRepoId(event.getReceiptScheduleId())
						.build())
				.build();
	}

	private CandidatesQueryBuilder prepareQuery(@NonNull final AbstractReceiptScheduleEvent event)
	{
		return CandidatesQuery.builder()
				.type(CandidateType.SUPPLY)
				.businessCase(CandidateBusinessCase.PURCHASE)
				.materialDescriptorQuery(MaterialDescriptorQuery.forDescriptor(event.getMaterialDescriptor()));

	}
}
