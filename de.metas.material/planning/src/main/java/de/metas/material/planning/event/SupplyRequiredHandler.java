package de.metas.material.planning.event;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Loggables;
import org.adempiere.util.PlainStringLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.stereotype.Service;

import de.metas.material.event.DemandHandlerAuditEvent;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.planning.IMRPContextFactory;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ddorder.DDOrderPojoSupplier;
import lombok.NonNull;

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
public class SupplyRequiredHandler
{
	private final DDOrderAdvisedOrCreatedEventCreator dDOrderAdvisedOrCreatedEventCreator;

	private final PPOrderAdvisedOrCreatedEventCreator pPOrderAdvisedOrCreatedEventCreator;

	private final MaterialEventService materialEventService;

	public SupplyRequiredHandler(
			@NonNull final DDOrderAdvisedOrCreatedEventCreator dDOrderAdvisedOrCreatedEventCreator,
			@NonNull final PPOrderAdvisedOrCreatedEventCreator pPOrderAdvisedOrCreatedEventCreator,
			@NonNull final MaterialEventService materialEventService)
	{
		this.dDOrderAdvisedOrCreatedEventCreator = dDOrderAdvisedOrCreatedEventCreator;
		this.pPOrderAdvisedOrCreatedEventCreator = pPOrderAdvisedOrCreatedEventCreator;
		this.materialEventService = materialEventService;
	}

	/**
	 * Invokes our {@link DDOrderPojoSupplier} and returns the resulting {@link DDOrder} pojo as {@link DistributionAdvisedOrCreatedEvent}
	 *
	 * @param materialDemandEvent
	 */
	public void handleSupplyRequiredEvent(@NonNull final SupplyRequiredDescriptor descriptor)
	{
		final PlainStringLoggable plainStringLoggable = new PlainStringLoggable();
		try (final IAutoCloseable closable = Loggables.temporarySetLoggable(plainStringLoggable);)
		{
			handleSupplyRequiredEvent0(descriptor);
		}

		if (!plainStringLoggable.isEmpty())
		{
			final List<String> singleMessages = plainStringLoggable.getSingleMessages();

			final DemandHandlerAuditEvent demandHandlerAuditEvent = DemandHandlerAuditEvent.builder()
					.eventDescriptor(descriptor.getEventDescr().createNew())
					.descr(descriptor.getMaterialDescriptor())
					.orderLineId(descriptor.getOrderLineId())
					.messages(singleMessages)
					.build();

			materialEventService.fireEvent(demandHandlerAuditEvent);
		}
	}

	private void handleSupplyRequiredEvent0(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		final IMutableMRPContext mrpContext = mkMRPContext(supplyRequiredDescriptor);

		final List<MaterialEvent> events = new ArrayList<>();
		events.addAll(dDOrderAdvisedOrCreatedEventCreator.createDistributionAdvisedEvents(supplyRequiredDescriptor, mrpContext));
		events.addAll(pPOrderAdvisedOrCreatedEventCreator.createProductionAdvisedEvents(supplyRequiredDescriptor, mrpContext));

		events.forEach(e -> materialEventService.fireEvent(e));
	}

	private IMutableMRPContext mkMRPContext(@NonNull final SupplyRequiredDescriptor materialDemandEvent)
	{
		final EventDescriptor eventDescr = materialDemandEvent.getEventDescr();

		final MaterialDescriptor materialDescr = materialDemandEvent.getMaterialDescriptor();

		final I_M_Warehouse warehouse = load(materialDescr.getWarehouseId(), I_M_Warehouse.class);

		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

		final I_S_Resource plant = productPlanningDAO.findPlant(Env.getCtx(),
				eventDescr.getOrgId(),
				warehouse,
				materialDescr.getProductId(),
				materialDescr.getAttributeSetInstanceId());

		final I_PP_Product_Planning productPlanning = productPlanningDAO.find(Env.getCtx(),
				eventDescr.getOrgId(),
				materialDescr.getWarehouseId(),
				plant.getS_Resource_ID(),
				materialDescr.getProductId(),
				materialDescr.getAttributeSetInstanceId());

		final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);
		final IMutableMRPContext mrpContext = mrpContextFactory.createInitialMRPContext();

		final I_M_Product product = load(materialDescr.getProductId(), I_M_Product.class);
		mrpContext.setM_Product(product);
		mrpContext.setM_AttributeSetInstance_ID(materialDescr.getAttributeSetInstanceId());
		mrpContext.setM_Warehouse(warehouse);
		mrpContext.setDate(materialDescr.getDate());
		mrpContext.setCtx(Env.getCtx());
		mrpContext.setTrxName(ITrx.TRXNAME_ThreadInherited);
		mrpContext.setRequireDRP(true); // DRP means distribution resource planning? i.e. "consider making DD_Orders"?

		mrpContext.setProductPlanning(productPlanning);
		mrpContext.setPlant(plant);

		final I_AD_Org org = load(eventDescr.getOrgId(), I_AD_Org.class);
		mrpContext.setAD_Client_ID(org.getAD_Client_ID());
		mrpContext.setAD_Org(org);
		return mrpContext;
	}
}
