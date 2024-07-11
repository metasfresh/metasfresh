package de.metas.material.planning.event;

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.Profiles;
import de.metas.logging.LogManager;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.MaterialEvent;
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
import de.metas.material.planning.ddorder.DDOrderAdvisedEventCreator;
import de.metas.material.planning.ppordercandidate.PPOrderCandidateAdvisedEventCreator;
import de.metas.organization.IOrgDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
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
	@NonNull private final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);
	@NonNull private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final DDOrderAdvisedEventCreator dDOrderAdvisedEventCreator;
	@NonNull private final PPOrderCandidateAdvisedEventCreator ppOrderCandidateAdvisedEventCreator;
	@NonNull private final PostMaterialEventService postMaterialEventService;
	@NonNull private final MainDataRequestHandler mainDataRequestHandler;

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
		updateMainData(descriptor);

		final IMutableMRPContext mrpContext = createMRPContextOrNull(descriptor);
		if (mrpContext == null)
		{
			return; // nothing to do
		}

		final List<MaterialEvent> events = new ArrayList<>();

		events.addAll(dDOrderAdvisedEventCreator.createDDOrderAdvisedEvents(descriptor, mrpContext));
		events.addAll(ppOrderCandidateAdvisedEventCreator.createPPOrderCandidateAdvisedEvents(descriptor, mrpContext));

		events.forEach(postMaterialEventService::postEventAsync);
	}

	private IMutableMRPContext createMRPContextOrNull(@NonNull final SupplyRequiredDescriptor materialDemandEvent)
	{
		final EventDescriptor eventDescriptor = materialDemandEvent.getEventDescriptor();

		final MaterialDescriptor materialDescriptor = materialDemandEvent.getMaterialDescriptor();

		final I_M_Warehouse warehouse = warehouseDAO.getById(materialDescriptor.getWarehouseId());

		final ResourceId plantId = productPlanningDAO.findPlant(
				eventDescriptor.getOrgId().getRepoId(),
				warehouse,
				materialDescriptor.getProductId(),
				materialDescriptor.getAttributeSetInstanceId());

		final ProductPlanningQuery productPlanningQuery = ProductPlanningQuery.builder()
				.orgId(eventDescriptor.getOrgId())
				.warehouseId(materialDescriptor.getWarehouseId())
				.plantId(plantId)
				.productId(ProductId.ofRepoId(materialDescriptor.getProductId()))
				.attributeSetInstanceId(AttributeSetInstanceId.ofRepoId(materialDescriptor.getAttributeSetInstanceId()))
				.build();

		final ProductPlanning productPlanning = productPlanningDAO.find(productPlanningQuery).orElse(null);
		if (productPlanning == null)
		{
			Loggables.withLogger(logger, Level.DEBUG).addLog("No PP_Product_Planning record found => nothing to do; query={}", productPlanningQuery);
			return null;
		}

		final IMutableMRPContext mrpContext = mrpContextFactory.createInitialMRPContext();

		final I_M_Product product = productBL.getById(ProductId.ofRepoId(materialDescriptor.getProductId()));
		mrpContext.setM_Product(product);
		mrpContext.setM_AttributeSetInstance_ID(materialDescriptor.getAttributeSetInstanceId());
		mrpContext.setM_Warehouse(warehouse);
		mrpContext.setDate(TimeUtil.asDate(materialDescriptor.getDate()));
		mrpContext.setCtx(Env.getCtx());
		mrpContext.setTrxName(ITrx.TRXNAME_ThreadInherited);

		mrpContext.setProductPlanning(productPlanning);
		mrpContext.setPlantId(plantId);

		final I_AD_Org org = orgDAO.getById(eventDescriptor.getOrgId());
		mrpContext.setAD_Client_ID(org.getAD_Client_ID());
		mrpContext.setAD_Org(org);
		return mrpContext;
	}

	private void updateMainData(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		if (supplyRequiredDescriptor.isSimulated())
		{
			return;
		}

		final ZoneId orgTimezone = orgDAO.getTimeZone(supplyRequiredDescriptor.getEventDescriptor().getOrgId());

		final MainDataRecordIdentifier mainDataRecordIdentifier = MainDataRecordIdentifier
				.createForMaterial(supplyRequiredDescriptor.getMaterialDescriptor(), orgTimezone);

		final UpdateMainDataRequest updateMainDataRequest = UpdateMainDataRequest.builder()
				.identifier(mainDataRecordIdentifier)
				.qtySupplyRequired(supplyRequiredDescriptor.getMaterialDescriptor().getQuantity())
				.build();

		mainDataRequestHandler.handleDataUpdateRequest(updateMainDataRequest);
	}
}