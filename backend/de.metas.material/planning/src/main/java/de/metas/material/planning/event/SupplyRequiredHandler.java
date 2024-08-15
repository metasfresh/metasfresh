package de.metas.material.planning.event;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.NoSupplyAdviceEvent;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Warehouse;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

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
@RequiredArgsConstructor
public class SupplyRequiredHandler implements MaterialEventHandler<SupplyRequiredEvent>
{
	private static final Logger logger = LogManager.getLogger(SupplyRequiredHandler.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	@NonNull private final PostMaterialEventService postMaterialEventService;
	@NonNull private final MainDataRequestHandler mainDataRequestHandler;
	@NonNull private final List<SupplyRequiredAdvisor> supplyRequiredAdvisors;

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

	private void handleSupplyRequiredEvent(@NonNull final SupplyRequiredDescriptor descriptor)
	{
		final ArrayList<MaterialEvent> events = new ArrayList<>();

		final MaterialPlanningContext context = createContextOrNull(descriptor);
		if (context != null)
		{
			for (final SupplyRequiredAdvisor advisor : supplyRequiredAdvisors)
			{
				events.addAll(advisor.createAdvisedEvents(descriptor, context));
			}
		}

		if (events.isEmpty())
		{
			final NoSupplyAdviceEvent noSupplyAdviceEvent = NoSupplyAdviceEvent.of(descriptor.withNewEventId());
			Loggables.addLog("No advice events were created. Firing {}", noSupplyAdviceEvent);

			postMaterialEventService.enqueueEventNow(noSupplyAdviceEvent);
		}
		else
		{
			events.forEach(postMaterialEventService::enqueueEventNow);
		}
	}

	private MaterialPlanningContext createContextOrNull(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		final OrgId orgId = supplyRequiredDescriptor.getOrgId();

		final WarehouseId warehouseId = supplyRequiredDescriptor.getWarehouseId();
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);

		final ProductId productId = ProductId.ofRepoId(supplyRequiredDescriptor.getProductId());
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(supplyRequiredDescriptor.getAttributeSetInstanceId());
		final ResourceId plantId = productPlanningDAO.findPlantIfExists(orgId, warehouse, productId, attributeSetInstanceId).orElse(null);
		if (plantId == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No plant found for {}, {}, {}, {}", orgId, warehouse, productId, attributeSetInstanceId);
			return null;
		}

		final ProductPlanningQuery productPlanningQuery = ProductPlanningQuery.builder()
				.orgId(orgId)
				.warehouseId(warehouseId)
				.plantId(plantId)
				.productId(productId)
				.includeWithNullProductId(false)
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();

		final ProductPlanning productPlanning = productPlanningDAO.find(productPlanningQuery).orElse(null);
		if (productPlanning == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No PP_Product_Planning record found => nothing to do; query={}", productPlanningQuery);
			return null;
		}

		final I_AD_Org org = orgDAO.getById(orgId);

		return MaterialPlanningContext.builder()
				.productId(productId)
				.attributeSetInstanceId(attributeSetInstanceId)
				.warehouseId(warehouseId)
				.productPlanning(productPlanning)
				.plantId(plantId)
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(org.getAD_Client_ID(), org.getAD_Org_ID()))
				.build();
	}
}