package de.metas.material.planning.event;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ddorder.DDOrderAdvisedEventCreator;
import de.metas.material.planning.ddorder.DDOrderPojoSupplier;
import de.metas.material.planning.impl.MaterialPlanningContext;
import de.metas.material.planning.ppordercandidate.PPOrderCandidateAdvisedEventCreator;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
 * #%L
 * metasfresh-material-planning
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
@Profile(Profiles.PROFILE_App) // we want only one component to bother itself with SupplyRequiredEvents
public class SupplyRequiredHandler implements MaterialEventHandler<SupplyRequiredEvent>
{
	private static final Logger logger = LogManager.getLogger(SupplyRequiredHandler.class);

	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);

	private final DDOrderAdvisedEventCreator dDOrderAdvisedEventCreator;
	private final PPOrderCandidateAdvisedEventCreator ppOrderCandidateAdvisedEventCreator;

	private final PostMaterialEventService postMaterialEventService;

	public SupplyRequiredHandler(
			@NonNull final DDOrderAdvisedEventCreator dDOrderAdvisedEventCreator,
			@NonNull final PPOrderCandidateAdvisedEventCreator ppOrderCandidateAdvisedEventCreator,
			@NonNull final PostMaterialEventService fireMaterialEventService)
	{
		this.dDOrderAdvisedEventCreator = dDOrderAdvisedEventCreator;
		this.ppOrderCandidateAdvisedEventCreator = ppOrderCandidateAdvisedEventCreator;
		this.postMaterialEventService = fireMaterialEventService;
	}

	@Override
	public Collection<Class<? extends SupplyRequiredEvent>> getHandledEventType()
	{
		return ImmutableList.of(SupplyRequiredEvent.class);
	}

	@Override
	public void handleEvent(@NonNull final SupplyRequiredEvent event)
	{
		handleSupplyRequiredEvent(event.getSupplyRequiredDescriptor());
	}

	/**
	 * Invokes our {@link DDOrderPojoSupplier} and returns the resulting {@link DDOrder} pojo as DistributionAdvisedOrCreatedEvent
	 */
	public void handleSupplyRequiredEvent(@NonNull final SupplyRequiredDescriptor descriptor)
	{
		if(descriptor.getMaterialDescriptor().getQuantity().signum() != 0)
		{
			SupplyRequiredHandlerUtils.updateMainData(descriptor);
		}

		final IMaterialPlanningContext mrpContext = createMRPContextOrNull(descriptor);
		if (mrpContext == null)
		{
			return; // nothing to do
		}

		final List<MaterialEvent> events = new ArrayList<>();

		events.addAll(dDOrderAdvisedEventCreator.createDDOrderAdvisedEvents(descriptor, mrpContext));
		events.addAll(ppOrderCandidateAdvisedEventCreator.createPPOrderCandidateAdvisedEvents(descriptor, mrpContext));

		events.forEach(postMaterialEventService::enqueueEventNow);
	}

	@Nullable
	private IMaterialPlanningContext createMRPContextOrNull(@NonNull final SupplyRequiredDescriptor materialDemandEvent)
	{
		final EventDescriptor eventDescr = materialDemandEvent.getEventDescriptor();

		final MaterialDescriptor materialDescr = materialDemandEvent.getMaterialDescriptor();

		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

		final ResourceId plantId = productPlanningDAO.findPlantId(
				eventDescr.getOrgId().getRepoId(),
				warehouseDAO.getById(materialDescr.getWarehouseId()),
				materialDescr.getProductId(),
				materialDescr.getAttributeSetInstanceId());

		final ProductPlanningQuery productPlanningQuery = ProductPlanningQuery.builder()
				.orgId(eventDescr.getOrgId())
				.warehouseId(materialDescr.getWarehouseId())
				.plantId(plantId)
				.productId(ProductId.ofRepoId(materialDescr.getProductId()))
				.includeWithNullProductId(false)
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(materialDescr.getAttributeSetInstanceId()))
				.build();

		final ProductPlanning productPlanning = productPlanningDAO.find(productPlanningQuery).orElse(null);
		if (productPlanning == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No PP_Product_Planning record found => nothing to do; query={}", productPlanningQuery);
			return null;
		}

		final IMaterialPlanningContext mrpContext = new MaterialPlanningContext();

		mrpContext.setProductId(ProductId.ofRepoId(materialDescr.getProductId()));
		mrpContext.setAttributeSetInstanceId(AttributeSetInstanceId.ofRepoIdOrNone(materialDescr.getAttributeSetInstanceId()));
		mrpContext.setWarehouseId(materialDescr.getWarehouseId());
		//mrpContext.setDate(TimeUtil.asDate(materialDescr.getDate()));
		mrpContext.setCtx(Env.getCtx());
		mrpContext.setTrxName(ITrx.TRXNAME_ThreadInherited);

		mrpContext.setProductPlanning(productPlanning);
		mrpContext.setPlantId(plantId);

		mrpContext.setClientId(eventDescr.getClientId());
		mrpContext.setOrgId(eventDescr.getOrgId());
		return mrpContext;
	}
}