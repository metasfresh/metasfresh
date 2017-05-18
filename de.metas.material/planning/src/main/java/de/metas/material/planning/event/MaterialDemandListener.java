package de.metas.material.planning.event;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
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
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import de.metas.logging.LogManager;
import de.metas.material.event.DemandHandlerAuditEvent;
import de.metas.material.event.DistributionPlanEvent;
import de.metas.material.event.EventDescr;
import de.metas.material.event.MaterialDemandEvent;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ProductionPlanEvent;
import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.event.ddorder.DDOrderLine;
import de.metas.material.event.pporder.PPOrder;
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
 * metasfresh-mrp
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
/**
 * This listener is dedicated to handle {@link MaterialDemandEvent}s. It ignores and other {@link MaterialEvent}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Service
@Lazy // .. because MaterialEventService needs to be lazy
public class MaterialDemandListener implements MaterialEventListener
{
	private static final transient Logger logger = LogManager.getLogger(MaterialDemandListener.class);

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

	@Override
	public void onEvent(@NonNull final MaterialEvent event)
	{
		if (!(event instanceof MaterialDemandEvent))
		{
			return;
		}
		logger.info("Received event {}", event);

		final MaterialDemandEvent materialDemandEvent = (MaterialDemandEvent)event;

		final PlainStringLoggable plainStringLoggable = new PlainStringLoggable();
		try (final IAutoCloseable closable = Loggables.temporarySetLoggable(plainStringLoggable);)
		{
			handleMaterialDemandEvent(materialDemandEvent);
		}

		if (!plainStringLoggable.isEmpty())
		{
			final List<String> singleMessages = plainStringLoggable.getSingleMessages();

			final DemandHandlerAuditEvent demandHandlerAuditEvent = DemandHandlerAuditEvent.builder()
					.eventDescr(event.getEventDescr().createNew())
					.descr(materialDemandEvent.getDescr())
					.orderLineId(materialDemandEvent.getOrderLineId())
					.reference(materialDemandEvent.getReference())
					.messages(singleMessages)
					.build();

			materialEventService.fireEvent(demandHandlerAuditEvent);
		}
	}

	/**
	 * Invokes our {@link DDOrderPojoSupplier} and returns the resulting {@link DDOrder} pojo as {@link DistributionPlanEvent}
	 * 
	 * @param materialDemandEvent
	 */
	private void handleMaterialDemandEvent(@NonNull final MaterialDemandEvent materialDemandEvent)
	{
		final IMutableMRPContext mrpContext = mkMRPContext(materialDemandEvent);

		if (ddOrderDemandMatcher.matches(mrpContext))
		{
			final List<DDOrder> ddOrders = ddOrderPojoSupplier
					.supplyPojos(
							mkRequest(materialDemandEvent, mrpContext),
							mkMRPNotesCollector());

			for (final DDOrder ddOrder : ddOrders)
			{
				for (final DDOrderLine ddOrderLine : ddOrder.getLines())
				{
					final I_DD_NetworkDistributionLine networkLine = InterfaceWrapperHelper.create(mrpContext.getCtx(), ddOrderLine.getNetworkDistributionLineId(), I_DD_NetworkDistributionLine.class, mrpContext.getTrxName());

					final DistributionPlanEvent distributionPlanEvent = DistributionPlanEvent.builder()
							.eventDescr(materialDemandEvent.getEventDescr().createNew())
							.fromWarehouseId(networkLine.getM_WarehouseSource_ID())
							.toWarehouseId(networkLine.getM_Warehouse_ID())
							.reference(materialDemandEvent.getReference())
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
							mkRequest(materialDemandEvent, mrpContext),
							mkMRPNotesCollector());

			final ProductionPlanEvent event = ProductionPlanEvent.builder()
					.eventDescr(materialDemandEvent.getEventDescr().createNew())
					.reference(materialDemandEvent.getReference())
					.ppOrder(ppOrder)
					.build();

			materialEventService.fireEvent(event);
		}
	}

	private IMutableMRPContext mkMRPContext(@NonNull final MaterialDemandEvent materialDemandEvent)
	{
		final EventDescr eventDescr = materialDemandEvent.getEventDescr();

		final MaterialDescriptor materialDescr = materialDemandEvent.getDescr();

		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final I_AD_Org org = InterfaceWrapperHelper.create(ctx, eventDescr.getOrgId(), I_AD_Org.class, trxName);
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(ctx, materialDescr.getWarehouseId(), I_M_Warehouse.class, trxName);
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, materialDescr.getProductId(), I_M_Product.class, trxName);

		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

		final I_S_Resource plant = productPlanningDAO.findPlant(ctx,
				eventDescr.getOrgId(),
				warehouse,
				materialDescr.getProductId());

		final I_PP_Product_Planning productPlanning = productPlanningDAO.find(ctx,
				eventDescr.getOrgId(),
				materialDescr.getWarehouseId(),
				plant.getS_Resource_ID(),
				materialDescr.getProductId(),
				trxName);

		final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);
		final IMutableMRPContext mrpContext = mrpContextFactory.createInitialMRPContext();

		mrpContext.setM_Product(product);
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
			@NonNull final MaterialDemandEvent materialDemandEvent,
			@NonNull final IMaterialPlanningContext mrpContext)
	{
		return new IMaterialRequest()
		{

			@Override
			public BigDecimal getQtyToSupply()
			{
				return materialDemandEvent.getDescr().getQty();
			}

			@Override
			public int getMRPDemandOrderLineSOId()
			{
				return materialDemandEvent.getOrderLineId();
			}

			@Override
			public int getMRPDemandBPartnerId()
			{
				return -1;
			}

			@Override
			public IMaterialPlanningContext getMRPContext()
			{
				return mrpContext;
			}

			@Override
			public Date getDemandDate()
			{
				return materialDemandEvent.getDescr().getDate();
			}
		};
	}

	private IMRPNotesCollector mkMRPNotesCollector()
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
				noteBuilder.collect();
			}
		};
	}

}
