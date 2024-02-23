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
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.supplyrequired.SupplyRequiredEvent;
import de.metas.material.planning.IMRPContextFactory;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.material.planning.ddorder.DDOrderAdvisedEventCreator;
import de.metas.material.planning.ddorder.DDOrderPojoSupplier;
import de.metas.material.planning.ppordercandidate.PPOrderCandidateAdvisedEventCreator;
import de.metas.organization.IOrgDAO;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
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
@Profile(Profiles.PROFILE_App) // we want only one component to bother itself with SupplyRequiredEvents
public class SupplyRequiredHandler implements MaterialEventHandler<SupplyRequiredEvent>
{
	private static final Logger logger = LogManager.getLogger(SupplyRequiredHandler.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final DDOrderAdvisedEventCreator dDOrderAdvisedEventCreator;
	private final PPOrderCandidateAdvisedEventCreator ppOrderCandidateAdvisedEventCreator;

	private final PostMaterialEventService postMaterialEventService;
	private final MainDataRequestHandler mainDataRequestHandler;

	public SupplyRequiredHandler(
			@NonNull final DDOrderAdvisedEventCreator dDOrderAdvisedEventCreator,
			@NonNull final PPOrderCandidateAdvisedEventCreator ppOrderCandidateAdvisedEventCreator,
			@NonNull final PostMaterialEventService fireMaterialEventService, final MainDataRequestHandler mainDataRequestHandler)
	{
		this.dDOrderAdvisedEventCreator = dDOrderAdvisedEventCreator;
		this.ppOrderCandidateAdvisedEventCreator = ppOrderCandidateAdvisedEventCreator;
		this.postMaterialEventService = fireMaterialEventService;
		this.mainDataRequestHandler = mainDataRequestHandler;
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
	 * Invokes our {@link DDOrderPojoSupplier} and returns the resulting {@link DDOrder} pojo as {@link DistributionAdvisedOrCreatedEvent}
	 */
	public void handleSupplyRequiredEvent(@NonNull final SupplyRequiredDescriptor descriptor)
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
			Loggables.withLogger(logger, Level.DEBUG).addLog("No PP_Product_Planning record found => nothing to do; query={}", productPlanningQuery);
			return null;
		}

		final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);
		final IMutableMRPContext mrpContext = mrpContextFactory.createInitialMRPContext();

		final I_M_Product product = loadOutOfTrx(materialDescr.getProductId(), I_M_Product.class);
		mrpContext.setM_Product(product);
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