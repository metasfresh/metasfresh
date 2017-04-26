package de.metas.material.planning.event;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.uom.api.IUOMConversionBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_PP_Product_Planning;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import de.metas.logging.LogManager;
import de.metas.material.event.DistributionPlanEvent;
import de.metas.material.event.MaterialDemandEvent;
import de.metas.material.event.MaterialDescriptor;
import de.metas.material.event.MaterialEvent;
import de.metas.material.event.MaterialEventListener;
import de.metas.material.event.MaterialEventService;
import de.metas.material.event.ProductionPlanEvent;
import de.metas.material.event.ProductionPlanEvent.ProductionPlanEventBuilder;
import de.metas.material.planning.IMRPContextFactory;
import de.metas.material.planning.IMRPNoteBuilder;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMaterialRequest;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ddorder.DDOrder;
import de.metas.material.planning.ddorder.DDOrderDemandMatcher;
import de.metas.material.planning.ddorder.DDOrderLine;
import de.metas.material.planning.ddorder.DDOrderPojoSupplier;
import de.metas.material.planning.impl.SimpleMRPNoteBuilder;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.PPOrder;
import de.metas.material.planning.pporder.PPOrderDemandMatcher;
import de.metas.material.planning.pporder.PPOrderLine;
import de.metas.material.planning.pporder.PPOrderPojoSupplier;

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
@Service
public class MaterialDemandListener implements MaterialEventListener
{
	private static final transient Logger logger = LogManager.getLogger(MaterialDemandListener.class);

	@Override
	public void onEvent(final MaterialEvent event)
	{
		if (!(event instanceof MaterialDemandEvent))
		{
			return;
		}
		logger.info("Received event {}", event);

		handleMaterialDemandEvent((MaterialDemandEvent)event);
	}

	private void handleMaterialDemandEvent(final MaterialDemandEvent materialDemandEvent)
	{

		final IMutableMRPContext mrpContext = mkMRPContext(materialDemandEvent);

		if (new DDOrderDemandMatcher().matches(mrpContext))
		{
			final List<DDOrder> ddOrders = new DDOrderPojoSupplier()
					.supplyPojos(
							mkRequest(materialDemandEvent, mrpContext),
							mkMRPNotesCollector());

			for (final DDOrder ddOrder : ddOrders)
			{
				for (final DDOrderLine ddOrderLine : ddOrder.getDdOrderLines())
				{
					final Timestamp orderLineStartDate = TimeUtil.addDays(ddOrder.getDatePromised(), ddOrderLine.getDurationDays() * -1);

					final I_M_Locator fromLocator = InterfaceWrapperHelper.create(mrpContext.getCtx(), ddOrderLine.getFromLocatorId(), I_M_Locator.class, mrpContext.getTrxName());
					final I_M_Locator toLocator = InterfaceWrapperHelper.create(mrpContext.getCtx(), ddOrderLine.getToLocatorId(), I_M_Locator.class, mrpContext.getTrxName());

					final DistributionPlanEvent distributionPlanEvent = DistributionPlanEvent.builder()
							.fromWarehouseId(fromLocator.getM_Warehouse_ID())
							.distributionStart(orderLineStartDate)
							.materialDescr(MaterialDescriptor.builder()
									.date(ddOrder.getDatePromised())
									.orgId(ddOrder.getOrgId())
									.productId(ddOrderLine.getProductId())
									.qty(ddOrderLine.getQty())
									.warehouseId(toLocator.getM_Warehouse_ID())
									.build())
							.reference(materialDemandEvent.getReference())
							.build();

					MaterialEventService.get().fireEvent(distributionPlanEvent);
				}
			}
		}
		else if (new PPOrderDemandMatcher().matches(mrpContext))
		{
			final PPOrder ppOrder = new PPOrderPojoSupplier()
					.supplyPPOrderPojoWithLines(
							mkRequest(materialDemandEvent, mrpContext),
							mkMRPNotesCollector());

			final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);

			final Integer orgId = ppOrder.getOrgId();
			final Integer warehouseId = ppOrder.getWarehouseId();

			final BigDecimal productQty = calculateProductQty(ppOrder.getProductId(), ppOrder.getUomId(), ppOrder.getQuantity());

			final ProductionPlanEventBuilder builder = ProductionPlanEvent.builder();
			builder.productionOutput(MaterialDescriptor.builder()
					.date(ppOrder.getDatePromised())
					.orgId(orgId)
					.productId(ppOrder.getProductId())
					.qty(productQty)
					.warehouseId(warehouseId)
					.build());

			for (final PPOrderLine ppOrderLine : ppOrder.getLines())
			{
				final BigDecimal lineProductQty = calculateProductQty(ppOrderLine.getProductId(), ppOrderLine.getUomId(), ppOrderLine.getQtyRequired());

				final MaterialDescriptor lineMaterialDescriptor = MaterialDescriptor.builder()
						.orgId(orgId)
						.date(ppOrder.getDateStartSchedule())
						.productId(ppOrderLine.getProductId())
						.qty(lineProductQty)
						.warehouseId(warehouseId)
						.build();
				if (ppOrderBOMBL.isIssue(ppOrderLine.getComponentType()))
				{
					builder.productionInput(lineMaterialDescriptor);
				}
				else
				{
					builder.productionOutput(lineMaterialDescriptor);
				}
			}

			MaterialEventService.get().fireEvent(builder.build());
		}
	}

	private BigDecimal calculateProductQty(final Integer productId, final Integer uomId, final BigDecimal quantity)
	{
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);

		final I_M_Product product = InterfaceWrapperHelper.create(Env.getCtx(), productId, I_M_Product.class, ITrx.TRXNAME_None);
		final I_C_UOM uomSource = InterfaceWrapperHelper.create(Env.getCtx(), uomId, I_C_UOM.class, ITrx.TRXNAME_None);

		// in material-dispo we currently don't care for UOMs, so it always has to be the product's UOM.
		final BigDecimal productQty = uomConversionBL.convertToProductUOM(Env.getCtx(), product, uomSource, quantity);
		return productQty;
	}

	private IMutableMRPContext mkMRPContext(final MaterialDemandEvent materialDemandEvent)
	{
		final MaterialDescriptor descr = materialDemandEvent.getDescr();

		final Properties ctx = Env.getCtx();
		final String trxName = ITrx.TRXNAME_ThreadInherited;

		final I_AD_Org org = InterfaceWrapperHelper.create(ctx, descr.getOrgId(), I_AD_Org.class, trxName);
		final I_M_Warehouse warehouse = InterfaceWrapperHelper.create(ctx, descr.getWarehouseId(), I_M_Warehouse.class, trxName);
		final I_M_Product product = InterfaceWrapperHelper.create(ctx, descr.getProductId(), I_M_Product.class, trxName);

		final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

		final I_S_Resource plant = productPlanningDAO.findPlant(ctx,
				descr.getOrgId(),
				warehouse,
				descr.getProductId());

		final I_PP_Product_Planning productPlanning = productPlanningDAO.find(ctx,
				descr.getOrgId(),
				descr.getWarehouseId(),
				plant.getS_Resource_ID(),
				descr.getProductId(),
				trxName);

		final IMRPContextFactory mrpContextFactory = Services.get(IMRPContextFactory.class);
		final IMutableMRPContext mrpContext = mrpContextFactory.createInitialMRPContext();

		mrpContext.setM_Product(product);
		mrpContext.setM_Warehouse(warehouse);
		mrpContext.setDate(descr.getDate());
		mrpContext.setCtx(ctx);
		mrpContext.setTrxName(trxName);
		mrpContext.setRequireDRP(true); // DRP means distribution resource planning? i.e. "consider making DD_Orders"?

		mrpContext.setProductPlanning(productPlanning);
		mrpContext.setPlant(plant);

		mrpContext.setAD_Client_ID(org.getAD_Client_ID());
		mrpContext.setAD_Org(org);
		return mrpContext;
	}

	private IMaterialRequest mkRequest(final MaterialDemandEvent materialDemandEvent, final IMaterialPlanningContext mrpContext)
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
				return -1;
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
