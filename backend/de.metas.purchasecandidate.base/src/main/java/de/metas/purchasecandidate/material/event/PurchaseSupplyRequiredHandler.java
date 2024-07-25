package de.metas.purchasecandidate.material.event;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
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

import java.util.Collection;

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
@Profile(Profiles.PROFILE_App) // we want only one component to bother itself with SupplyRequiredEvent
@RequiredArgsConstructor
public class PurchaseSupplyRequiredHandler implements MaterialEventHandler<SupplyRequiredEvent>
{
	private static final Logger logger = LogManager.getLogger(PurchaseSupplyRequiredHandler.class);

	@NonNull private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final PurchaseCandidateAdvisedEventCreator purchaseOrderAdvisedEventCreator;
	@NonNull private final PostMaterialEventService postMaterialEventService;

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
		final MaterialPlanningContext context = createContext(descriptor);
		if (context == null)
		{
			return; // nothing to do
		}

		purchaseOrderAdvisedEventCreator
				.createPurchaseAdvisedEvent(descriptor, context)
				.ifPresent(postMaterialEventService::enqueueEventNow);
	}

	private MaterialPlanningContext createContext(@NonNull final SupplyRequiredDescriptor materialDemandEvent)
	{
		final OrgId orgId = materialDemandEvent.getOrgId();

		final WarehouseId warehouseId = materialDemandEvent.getWarehouseId();
		final I_M_Warehouse warehouse = warehouseDAO.getById(warehouseId);

		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

		final ProductId productId = ProductId.ofRepoId(materialDemandEvent.getProductId());
		final AttributeSetInstanceId attributeSetInstanceId = AttributeSetInstanceId.ofRepoIdOrNone(materialDemandEvent.getAttributeSetInstanceId());
		final ResourceId plantId = productPlanningDAO.findPlantId(
				orgId.getRepoId(),
				warehouse,
				productId.getRepoId(),
				attributeSetInstanceId.getRepoId());

		final ProductPlanningQuery productPlanningQuery = ProductPlanningQuery.builder()
				.orgId(orgId)
				.warehouseId(warehouseId)
				.plantId(plantId)
				.productId(productId)
				.attributeSetInstanceId(attributeSetInstanceId)
				.build();

		final ProductPlanning productPlanning = productPlanningDAO.find(productPlanningQuery).orElse(null);
		if (productPlanning == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No PP_Product_Planning record found; query={}", productPlanningQuery);
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
