package de.metas.material.dispo.service.event.handler.ddorder;

import java.util.Collection;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;

import de.metas.Profiles;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.ProductionDetail.Flag;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.ddorder.AbstractDDOrderEvent;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderCreatedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
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

@Service
@Profile(Profiles.PROFILE_MaterialDispo)
public class DDOrderCreatedHandler extends DDOrderAdvisedOrCreatedHandler<DDOrderCreatedEvent>
{
	public DDOrderCreatedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryCommands,
			@NonNull final CandidateChangeService candidateChangeHandler)
	{
		super(
				candidateRepository,
				candidateRepositoryCommands,
				candidateChangeHandler);
	}

	@Override
	public Collection<Class<? extends DDOrderCreatedEvent>> getHandeledEventType()
	{
		return ImmutableList.of(DDOrderCreatedEvent.class);
	}

	@Override
	public void validateEvent(@NonNull final DDOrderCreatedEvent event)
	{
		event.validate();
	}

	@Override
	public void handleEvent(DDOrderCreatedEvent event)
	{
		handleAbstractDDOrderEvent(event);
	}

	@Override
	protected Flag extractIsAdviseEvent(@NonNull final AbstractDDOrderEvent ddOrderEvent)
	{
		return Flag.FALSE_DONT_UPDATE;
	}

	@Override
	protected CandidatesQuery createPreExistingCandidatesQuery(
			@NonNull final AbstractDDOrderEvent ddOrderEvent,
			@NonNull final DDOrderLine ddOrderLine,
			@NonNull final CandidateType candidateType)
	{
		final DDOrderCreatedEvent ddOrderCreatedEvent = cast(ddOrderEvent);

		final DDOrder ddOrder = ddOrderCreatedEvent.getDdOrder();
		final int groupId = ddOrder.getMaterialDispoGroupId();
		if (groupId <= 0)
		{
			// returned false, but don't write another log message; we already logged in the other createQuery() method
			return CandidatesQuery.FALSE;
		}

		final CandidatesQuery query = CandidatesQuery.builder()
				.type(candidateType)
				.businessCase(CandidateBusinessCase.PRODUCTION)
				.groupId(groupId)
				.materialDescriptorQuery(
						createMaterialDescriptorQuery(
								ddOrderLine.getProductDescriptor()))
				.build();

		return query;
	}

	private DDOrderCreatedEvent cast(@NonNull final AbstractDDOrderEvent ddOrderEvent)
	{
		final DDOrderCreatedEvent ddOrderAdvisedEvent = (DDOrderCreatedEvent)ddOrderEvent;
		return ddOrderAdvisedEvent;
	}

	private static MaterialDescriptorQuery createMaterialDescriptorQuery(
			@NonNull final ProductDescriptor productDescriptor)
	{
		return MaterialDescriptorQuery.builder()
				.productId(productDescriptor.getProductId())
				.storageAttributesKey(productDescriptor.getStorageAttributesKey())
				.build();
	}
}
