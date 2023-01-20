package de.metas.material.dispo.service.event.handler.ddorder;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.view.ddorderdetail.DDOrderDetailRequestHandler;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateId;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.repository.CandidateRepositoryRetrieval;
import de.metas.material.dispo.commons.repository.CandidateRepositoryWriteService;
import de.metas.material.dispo.commons.repository.query.CandidatesQuery;
import de.metas.material.dispo.commons.repository.query.DistributionDetailsQuery;
import de.metas.material.dispo.commons.repository.query.MaterialDescriptorQuery;
import de.metas.material.dispo.commons.repository.query.SimulatedQueryQualifier;
import de.metas.material.dispo.service.candidatechange.CandidateChangeService;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.AbstractDDOrderEvent;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderCreatedEvent;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.pporder.MaterialDispoGroupId;
import de.metas.util.Loggables;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

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
	private static final Logger logger = LogManager.getLogger(DDOrderCreatedHandler.class);

	public DDOrderCreatedHandler(
			@NonNull final CandidateRepositoryRetrieval candidateRepository,
			@NonNull final CandidateRepositoryWriteService candidateRepositoryCommands,
			@NonNull final CandidateChangeService candidateChangeHandler,
			@NonNull final DDOrderDetailRequestHandler ddOrderDetailRequestHandler,
			@NonNull final MainDataRequestHandler mainDataRequestHandler)
	{
		super(
				candidateRepository,
				candidateRepositoryCommands,
				candidateChangeHandler,
				ddOrderDetailRequestHandler,
				mainDataRequestHandler);
	}

	@Override
	public Collection<Class<? extends DDOrderCreatedEvent>> getHandledEventType()
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
		final SupplyRequiredDescriptor supplyRequiredDescriptor = ddOrderEvent.getSupplyRequiredDescriptor();
		if (supplyRequiredDescriptor != null && supplyRequiredDescriptor.getSupplyCandidateId() > 0)
		{
			return CandidatesQuery.fromId(CandidateId.ofRepoId(supplyRequiredDescriptor.getSupplyCandidateId()));
		}

		final DDOrderCreatedEvent ddOrderCreatedEvent = cast(ddOrderEvent);

		final DDOrder ddOrder = ddOrderCreatedEvent.getDdOrder();
		final MaterialDispoGroupId groupId = ddOrder.getMaterialDispoGroupId();

		final SimulatedQueryQualifier simulatedQueryQualifier = ddOrder.isSimulated()
				? SimulatedQueryQualifier.ONLY_SIMULATED
				: SimulatedQueryQualifier.EXCLUDE_SIMULATED;

		final CandidatesQuery.CandidatesQueryBuilder candidatesQueryBuilder = CandidatesQuery.builder()
				.type(candidateType)
				.businessCase(CandidateBusinessCase.DISTRIBUTION)
				.groupId(groupId)
				.simulatedQueryQualifier(simulatedQueryQualifier)
				.materialDescriptorQuery(
						createMaterialDescriptorQuery(
								ddOrderLine.getProductDescriptor()));

		if (groupId == null)
		{
			if (ddOrderLine.getDdOrderLineId() <= 0 || ddOrderEvent.getDdOrder().getDdOrderId() <= 0)
			{
				Loggables.withLogger(logger, Level.INFO).addLog(
						"Creating query that matches no candidate, because groupId is null and distribution info are missing!");
				return CandidatesQuery.FALSE;
			}
			else
			{
				candidatesQueryBuilder.distributionDetailsQuery(DistributionDetailsQuery.builder()
																		.ddOrderId(ddOrderEvent.getDdOrder().getDdOrderId())
																		.ddOrderLineId(ddOrderLine.getDdOrderLineId())
																		.build());	
			}
		}

		return candidatesQueryBuilder
				.build();
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
