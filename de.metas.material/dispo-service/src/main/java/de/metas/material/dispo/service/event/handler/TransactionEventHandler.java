package de.metas.material.dispo.service.event.handler;

import org.springframework.stereotype.Service;

import de.metas.material.dispo.Candidate;
import de.metas.material.dispo.Candidate.Type;
import de.metas.material.dispo.service.CandidateChangeHandler;
import de.metas.material.dispo.service.StockCandidateFactory;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.TransactionEvent;
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
@Service
public class TransactionEventHandler
{
	private final StockCandidateFactory stockCandidateService;

	private final CandidateChangeHandler candidateChangeHandler;

	public TransactionEventHandler(
			@NonNull final StockCandidateFactory stockCandidateService,
			@NonNull final CandidateChangeHandler candidateChangeHandler)
	{
		this.candidateChangeHandler = candidateChangeHandler;
		this.stockCandidateService = stockCandidateService;
	}

	public void handleTransactionEvent(final TransactionEvent event)
	{
		if (event.isTransactionDeleted())
		{
			candidateChangeHandler.onCandidateDelete(event.getReference());
			return;
		}

		final MaterialDescriptor materialDescr = event.getMaterialDescr();

		final EventDescr eventDescr = event.getEventDescr();

		final Candidate candidate = Candidate.builder()
				.type(Type.STOCK)
				.clientId(eventDescr.getClientId())
				.orgId(eventDescr.getOrgId())
				.warehouseId(materialDescr.getWarehouseId())
				.date(materialDescr.getDate())
				.productId(materialDescr.getProductId())
				.quantity(materialDescr.getQty())
				.reference(event.getReference())
				.build();

		stockCandidateService.addOrUpdateStock(candidate);
	}

}
