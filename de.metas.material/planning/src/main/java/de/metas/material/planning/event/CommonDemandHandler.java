package de.metas.material.planning.event;

import static org.adempiere.model.InterfaceWrapperHelper.load;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.PlainStringLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.eevolution.model.I_DD_NetworkDistributionLine;
import org.eevolution.model.I_PP_Product_Planning;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;

import de.metas.material.event.DemandHandlerAuditEvent;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.ddorder.DistributionAdvisedEvent;
import de.metas.material.event.pporder.PPOrder;
import de.metas.material.event.pporder.ProductionAdvisedEvent;
import de.metas.material.planning.IMRPContextFactory;
import de.metas.material.planning.IMRPNoteBuilder;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ddorder.DDOrderDemandMatcher;
import de.metas.material.planning.ddorder.DDOrderPojoSupplier;
import de.metas.material.planning.impl.SimpleMRPNoteBuilder;
import de.metas.material.planning.pporder.PPOrderDemandMatcher;
import de.metas.material.planning.pporder.PPOrderPojoSupplier;
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
public class CommonDemandHandler
{
	@Autowired
	private DDOrderDemandMatcher ddOrderDemandMatcher;

	@Autowired
	private DDOrderPojoSupplier ddOrderPojoSupplier;

	@Autowired
	private PPOrderDemandMatcher ppOrderDemandMatcher;

	@Autowired
	private PPOrderPojoSupplier ppOrderPojoSupplier;

	@Autowired
	private MaterialEventService materialEventService;

	/**
	 * Invokes our {@link DDOrderPojoSupplier} and returns the resulting {@link DDOrder} pojo as {@link DistributionAdvisedEvent}
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

	private void handleSupplyRequiredEvent0(@NonNull final SupplyRequiredDescriptor materialDemandDescr)
	{
		final IMutableMRPContext mrpContext = mkMRPContext(materialDemandDescr);

		if (ddOrderDemandMatcher.matches(mrpContext))
		{
			final List<DDOrder> ddOrders = ddOrderPojoSupplier
					.supplyPojos(
							mkRequest(materialDemandDescr, mrpContext),
							mkMRPNotesCollector());

			for (final DDOrder ddOrder : ddOrders)
			{
				for (final DDOrderLine ddOrderLine : ddOrder.getLines())
				{
					Check.errorIf(ddOrderLine.getNetworkDistributionLineId() <= 0,
							"Every DDOrderLine pojo created by this planner needs to have detworkDistributionLineId > 0, but this one hasn't; ddOrderLine={}",
							ddOrderLine);

					final I_DD_NetworkDistributionLine networkLine = InterfaceWrapperHelper.create(
							mrpContext.getCtx(),
							ddOrderLine.getNetworkDistributionLineId(),
							I_DD_NetworkDistributionLine.class,
							mrpContext.getTrxName());

					final DistributionAdvisedEvent distributionPlanEvent = DistributionAdvisedEvent.builder()
							.eventDescriptor(materialDemandDescr.getEventDescr().createNew())
							.fromWarehouseId(networkLine.getM_WarehouseSource_ID())
							.toWarehouseId(networkLine.getM_Warehouse_ID())
							.ddOrder(ddOrder)
							.build();

					materialEventService.fireEvent(distributionPlanEvent);
				}
			}
		}

		if (ppOrderDemandMatcher.matches(mrpContext))
		{
			final PPOrder ppOrder = ppOrderPojoSupplier
					.supplyPPOrderPojoWithLines(
							mkRequest(materialDemandDescr, mrpContext),
							mkMRPNotesCollector());

			final ProductionAdvisedEvent event = ProductionAdvisedEvent.builder()
					.eventDescriptor(materialDemandDescr.getEventDescr().createNew())
					.ppOrder(ppOrder)
					.build();

			materialEventService.fireEvent(event);
		}
	}

	private IMutableMRPContext mkMRPContext(@NonNull final SupplyRequiredDescriptor materialDemandEvent)
	{
		final EventDescriptor eventDescr = materialDemandEvent.getEventDescr();

		final MaterialDescriptor materialDescr = materialDemandEvent.getMaterialDescriptor();

		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final I_AD_Org org = load(eventDescr.getOrgId(), I_AD_Org.class);
		final I_M_Warehouse warehouse = load(materialDescr.getWarehouseId(), I_M_Warehouse.class);

		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

		final I_S_Resource plant = productPlanningDAO.findPlant(ctx,
				eventDescr.getOrgId(),
				warehouse,
				materialDescr.getProductId(),
				materialDescr.getAttributeSetInstanceId());

		final I_PP_Product_Planning productPlanning = productPlanningDAO.find(ctx,
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
		mrpContext.setCtx(ctx);
		mrpContext.setTrxName(trxName);
		mrpContext.setRequireDRP(true); // DRP means distribution resource planning? i.e. "consider making DD_Orders"?

		mrpContext.setProductPlanning(productPlanning);
		mrpContext.setPlant(plant);

		mrpContext.setAD_Client_ID(org.getAD_Client_ID());
		mrpContext.setAD_Org(org);
		return mrpContext;
	}

	private IMaterialRequest mkRequest(
			@NonNull final SupplyRequiredDescriptor materialDemandEvent,
			@NonNull final IMaterialPlanningContext mrpContext)
	{
		return MaterialRequest.builder()
				.qtyToSupply(materialDemandEvent.getMaterialDescriptor().getQuantity())
				.mrpContext(mrpContext)
				.mrpDemandBPartnerId(-1)
				.mrpDemandOrderLineSOId(materialDemandEvent.getOrderLineId())
				.demandDate(materialDemandEvent.getMaterialDescriptor().getDate())
				.build();
	}

	@VisibleForTesting
	IMRPNotesCollector mkMRPNotesCollector()
	{
		return new IMRPNotesCollector()
		{
			@Override
			public IMRPNoteBuilder newMRPNoteBuilder(final IMaterialPlanningContext mrpContext, final String mrpErrorCode)
			{
				final SimpleMRPNoteBuilder simpleMRPNoteBuilder = new SimpleMRPNoteBuilder(this, mrps -> Collections.emptySet());
				return simpleMRPNoteBuilder;
			}

			@Override
			public void collectNote(final IMRPNoteBuilder noteBuilder)
			{
				// as long as newMRPNoteBuilder() creates a SimpleMRPNoteBuilder with *this* as its node collector,
				// the following invocation would cause a StackOverFlowError. Plus, idk if and what to actually collect in this use case.
				// noteBuilder.collect();
			}
		};
	}
}
