package de.metas.purchasecandidate.material.event;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEventHandler;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.planning.IMRPContextFactory;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

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
public class PurchaseSupplyRequiredHandler implements MaterialEventHandler<SupplyRequiredEvent>
{
	private static final Logger logger = LogManager.getLogger(PurchaseSupplyRequiredHandler.class);

	private final IProductBL productBL = Services.get(IProductBL.class);
	private final PurchaseCandidateAdvisedEventCreator purchaseOrderAdvisedEventCreator;
	private final PostMaterialEventService postMaterialEventService;

	public PurchaseSupplyRequiredHandler(
			@NonNull final PurchaseCandidateAdvisedEventCreator purchaseOrderAdvisedEventCreator,
			@NonNull final PostMaterialEventService fireMaterialEventService)
	{
		this.purchaseOrderAdvisedEventCreator = purchaseOrderAdvisedEventCreator;
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

	private void handleSupplyRequiredEvent(@NonNull final SupplyRequiredDescriptor descriptor)
	{
		final IMutableMRPContext mrpContext = createMRPContextOrNull(descriptor);
		if (mrpContext == null)
		{
			return; // nothing to do
		}

		purchaseOrderAdvisedEventCreator
				.createPurchaseAdvisedEvent(descriptor, mrpContext)
				.ifPresent(postMaterialEventService::postEventAsync);
	}

	private IMutableMRPContext createMRPContextOrNull(@NonNull final SupplyRequiredDescriptor materialDemandEvent)
	{
		final EventDescriptor eventDescr = materialDemandEvent.getEventDescriptor();

		final MaterialDescriptor materialDescr = materialDemandEvent.getMaterialDescriptor();

		final I_M_Warehouse warehouse = loadOutOfTrx(materialDescr.getWarehouseId(), I_M_Warehouse.class);

		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

		final ResourceId plantId = productPlanningDAO.findPlant(
				eventDescr.getOrgId().getRepoId(),
				warehouse,
				materialDescr.getProductId(),
				materialDescr.getAttributeSetInstanceId());

		final ProductPlanningQuery productPlanningQuery = ProductPlanningQuery.builder()
				.orgId(eventDescr.getOrgId())
				.warehouseId(materialDescr.getWarehouseId())
				.plantId(plantId)
				.productId(ProductId.ofRepoId(materialDescr.getProductId()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(materialDescr.getAttributeSetInstanceId()))
				.build();

		final ProductPlanning productPlanning = productPlanningDAO.find(productPlanningQuery).orElse(null);
		if (productPlanning == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No PP_Product_Planning record found; query={}", productPlanningQuery);
			return null;
		}

		final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);
		final IMutableMRPContext mrpContext = mrpContextFactory.createInitialMRPContext();

		mrpContext.setM_Product(productBL.getById(ProductId.ofRepoId(materialDescr.getProductId())));
		mrpContext.setM_AttributeSetInstance_ID(materialDescr.getAttributeSetInstanceId());
		mrpContext.setM_Warehouse(warehouse);
		mrpContext.setDate(TimeUtil.asDate(materialDescr.getDate()));
		mrpContext.setCtx(Env.getCtx());
		mrpContext.setTrxName(ITrx.TRXNAME_ThreadInherited);

		mrpContext.setProductPlanning(productPlanning);
		mrpContext.setPlantId(plantId);

		final I_AD_Org org = loadOutOfTrx(eventDescr.getOrgId(), I_AD_Org.class);
		mrpContext.setAD_Client_ID(org.getAD_Client_ID());
		mrpContext.setAD_Org(org);
		return mrpContext;
	}
}
