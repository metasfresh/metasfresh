package org.eevolution.mrp.spi.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Loggables;
import org.adempiere.util.PlainStringLoggable;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.IMutable;
import org.compiere.Adempiere;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Locator;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alternative;
import org.eevolution.model.X_DD_Order;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPSourceEvent;
import org.eevolution.mrp.spi.impl.ddorder.DDOrderProducer;

import de.metas.material.event.ddorder.DDOrder;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.ddorder.DDOrderDemandMatcher;
import de.metas.material.planning.ddorder.DDOrderPojoSupplier;

public class DDOrderMRPSupplyProducer extends AbstractMRPSupplyProducer
{
	private final IDDOrderBL ddOrderBL = Services.get(IDDOrderBL.class);

	public DDOrderMRPSupplyProducer()
	{
		addSourceColumnNames(I_DD_Order.class,
				I_DD_Order.COLUMN_PP_Plant_ID,
				I_DD_Order.COLUMN_DocStatus);
		addSourceColumnNames(I_DD_OrderLine.class,
				I_DD_OrderLine.COLUMN_AD_Org_ID,
				I_DD_OrderLine.COLUMN_M_Product_ID,
				I_DD_OrderLine.COLUMN_C_UOM_ID,
				I_DD_OrderLine.COLUMN_DatePromised,
				I_DD_OrderLine.COLUMN_QtyOrdered,
				I_DD_OrderLine.COLUMN_QtyDelivered,
				I_DD_OrderLine.COLUMN_QtyInTransit,
				I_DD_OrderLine.COLUMN_M_Locator_ID,
				I_DD_OrderLine.COLUMN_M_LocatorTo_ID,
				I_DD_OrderLine.COLUMN_C_OrderLineSO_ID,
				I_DD_OrderLine.COLUMN_PP_Plant_From_ID);
		addSourceColumnNames(I_DD_OrderLine_Alternative.class,
				I_DD_OrderLine_Alternative.COLUMN_M_Product_ID,
				I_DD_OrderLine_Alternative.COLUMN_QtyOrdered,
				I_DD_OrderLine_Alternative.COLUMN_QtyDelivered,
				I_DD_OrderLine_Alternative.COLUMN_QtyInTransit);

	}

	@Override
	public Class<?> getDocumentClass()
	{
		return I_DD_Order.class;
	}

	@Override
	public boolean applies(final IMaterialPlanningContext mrpContext, final IMutable<String> notAppliesReason)
	{
		final PlainStringLoggable loggable = new PlainStringLoggable();
		final boolean matches;
		try (final IAutoCloseable closable = Loggables.temporarySetLoggable(loggable))
		{
			matches = new DDOrderDemandMatcher().matches(mrpContext);
		}
		if (!matches)
		{
			notAppliesReason.setValue(loggable.getConcatenatedMessages());
		}
		return matches;
	}

	@Override
	public void onRecordChange(final IMRPSourceEvent event)
	{
		final Object model = event.getModel();

		if (event.isChange() && InterfaceWrapperHelper.isInstanceOf(model, I_DD_Order.class))
		{
			// Do nothing if the DD Order was just created because there is nothing we can do
			if (event.isNew())
			{
				return;
			}

			final I_DD_Order ddOrder = InterfaceWrapperHelper.create(model, I_DD_Order.class);

			//
			// Create/update MRP records
			createUpdateMRPRecords(ddOrder);
		}
		else if (event.isChange() && InterfaceWrapperHelper.isInstanceOf(model, I_DD_OrderLine.class))
		{
			final I_DD_OrderLine ddOrderLine = InterfaceWrapperHelper.create(model, I_DD_OrderLine.class);
			createUpdateMRPRecords(ddOrderLine);
		}
		else if (event.isChange() && InterfaceWrapperHelper.isInstanceOf(model, I_DD_OrderLine_Alternative.class))
		{
			final I_DD_OrderLine_Alternative ddOrderLineAlt = InterfaceWrapperHelper.create(model, I_DD_OrderLine_Alternative.class);
			createUpdateMRPRecords(ddOrderLineAlt);
		}
	}

	@Override
	protected void onRecordDeleted(final IMRPSourceEvent event)
	{
		super.onRecordDeleted(event);

		//
		// Delete MRP alternatives, if any
		final Object model = event.getModel();
		if (InterfaceWrapperHelper.isInstanceOf(model, I_DD_OrderLine_Alternative.class))
		{
			final I_DD_OrderLine_Alternative ddOrderLineAlt = InterfaceWrapperHelper.create(model, I_DD_OrderLine_Alternative.class);
			deleteMRPAlternatives(ddOrderLineAlt);
		}
	}

	@Override
	public void onDocumentChange(final Object model, final DocTimingType timing)
	{
		// nothing
	}

	private final I_PP_MRP retrieveMRPRecord(final I_DD_OrderLine ddOrderLine, final String typeMRP)
	{
		// Optimization: in case the DD_OrderLine was just created, there is no point to query because there won't be any result for sure
		if (InterfaceWrapperHelper.isJustCreated(ddOrderLine))
		{
			return null;
		}

		final I_PP_MRP mrp = mrpDAO.retrieveQueryBuilder(ddOrderLine, typeMRP, X_PP_MRP.ORDERTYPE_DistributionOrder)
				.setSkipIfMRPExcluded(false)
				.firstOnly();
		return mrp;
	}

	/**
	 * Create/Update MRP record based in Distribution Order.
	 *
	 * Actally it is calling {@link #createUpdateMRPRecords(I_DD_OrderLine)} for each line.
	 *
	 * @param I_DD_Order Distribution Order
	 */
	private void createUpdateMRPRecords(final I_DD_Order ddOrder)
	{
		final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);
		final List<I_DD_OrderLine> ddOrderLines = ddOrderDAO.retrieveLines(ddOrder);
		for (final I_DD_OrderLine ddOrderLine : ddOrderLines)
		{
			createUpdateMRPRecords(ddOrderLine);
		}
	}

	/**
	 * Create/Update MRP record based in Distribution Order Line
	 *
	 * @param I_DD_OrderLine Distribution Order Line
	 */
	private void createUpdateMRPRecords(final I_DD_OrderLine ddOrderLine)
	{
		//
		// MRP Supply record (moving from InTransit to Destination Locator)
		createUpdateMRPRecord(ddOrderLine, X_PP_MRP.TYPEMRP_Supply);

		//
		// MRP Demand record (moving from Source Locator to InTransit)
		final I_PP_MRP mrpDemand = createUpdateMRPRecord(ddOrderLine, X_PP_MRP.TYPEMRP_Demand);
		notifyAffectedMRPSegment(mrpDemand); // notify that we changed this segment because it's in another warehouse then the warehouse on which we are planning now
	}

	private I_PP_MRP createUpdateMRPRecord(final I_DD_OrderLine ddOrderLine, final String typeMRP)
	{
		final I_DD_Order ddOrder = ddOrderLine.getDD_Order();
		final String docStatus = ddOrder.getDocStatus();
		final BigDecimal qtyTarget = ddOrderLine.getQtyOrdered();

		final int ppPlantId;
		BigDecimal qty;
		final I_M_Locator locator;
		final int attributeSetInstanceId;
		if (X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			// Supply goes on Destination Locator
			locator = ddOrderLine.getM_LocatorTo();

			// Qty (supply) on target warehouse
			qty = ddOrderBL.getQtyToReceive(ddOrderLine);

			// Supply plant (taken from DD Order header)
			ppPlantId = ddOrder.getPP_Plant_ID();

			attributeSetInstanceId = ddOrderLine.getM_AttributeSetInstanceTo_ID();
		}
		else if (X_PP_MRP.TYPEMRP_Demand.equals(typeMRP))
		{
			// Demand goes on Source Locator
			locator = ddOrderLine.getM_Locator();

			// Qty (demand) on source warehouse
			qty = ddOrderBL.getQtyToShip(ddOrderLine);

			// Demand plant (taken from line)
			ppPlantId = ddOrderLine.getPP_Plant_From_ID();

			attributeSetInstanceId = ddOrderLine.getM_AttributeSetInstance_ID();
		}
		else
		{
			throw new IllegalArgumentException("Unknown TypeMRP: " + typeMRP);
		}

		//
		// In case we moved more then it was required, we shall consider the MRP quantity as ZERO because there are no more movements to plan
		if (qty.signum() < 0)
		{
			qty = BigDecimal.ZERO;
		}

		//
		// Get/Create MRP Record
		I_PP_MRP mrp = retrieveMRPRecord(ddOrderLine, typeMRP);
		final boolean isNewMRPRecord = mrp == null;
		if (isNewMRPRecord)
		{
			mrp = mrpBL.createMRP(ddOrderLine);
		}

		//
		// Header
		mrp.setTypeMRP(typeMRP);
		mrp.setDescription(ddOrderLine.getDescription());

		//
		// Dimension (Org, Warehouse, Plant)
		mrp.setAD_Org_ID(locator.getAD_Org_ID());
		mrp.setM_Warehouse_ID(locator.getM_Warehouse_ID());
		mrp.setS_Resource_ID(ppPlantId);

		//
		// Document Reference
		mrp.setOrderType(X_PP_MRP.ORDERTYPE_DistributionOrder);
		mrp.setDD_Order_ID(ddOrderLine.getDD_Order_ID());
		mrp.setDD_OrderLine(ddOrderLine);
		mrp.setDocStatus(docStatus);
		mrp.setC_BPartner_ID(ddOrderLine.getC_BPartner_ID());
		mrp.setC_OrderLineSO_ID(ddOrderLine.getC_OrderLineSO_ID());

		//
		// Dates
		final Timestamp datePromised = ddOrderLine.getDatePromised();
		mrp.setDateOrdered(ddOrderLine.getDateOrdered());
		mrp.setDatePromised(datePromised);
		if (mrp.getDateFinishSchedule() == null)
		{
			// If DateFinishSchedule was not set, we assume DatePromised
			mrp.setDateFinishSchedule(datePromised);
		}
		if (mrp.getDateStartSchedule() == null)
		{
			// If DateStartSchedule was not set, we assume DateFinishSchedule
			// => leadtime = 0 because we don't have this information here
			mrp.setDateStartSchedule(mrp.getDateFinishSchedule());
		}

		//
		// Product & Qty
		mrp.setM_Product_ID(ddOrderLine.getM_Product_ID());
		mrp.setM_AttributeSetInstance_ID(attributeSetInstanceId);
		mrpBL.setQty(mrp, qtyTarget, qty, ddOrderLine.getC_UOM());

		InterfaceWrapperHelper.save(mrp);
		return mrp;
	}

	private void createUpdateMRPRecords(final I_DD_OrderLine_Alternative ddOrderLineAlt)
	{
		final I_DD_OrderLine ddOrderLine = ddOrderLineAlt.getDD_OrderLine();
		final I_PP_MRP mrpDemand = retrieveMRPRecord(ddOrderLine, X_PP_MRP.TYPEMRP_Demand);
		if (mrpDemand != null)
		{
			createUpdateMRPRecord(ddOrderLineAlt, mrpDemand);
		}

		final I_PP_MRP mrpSupply = retrieveMRPRecord(ddOrderLine, X_PP_MRP.TYPEMRP_Supply);
		if (mrpSupply != null)
		{
			createUpdateMRPRecord(ddOrderLineAlt, mrpSupply);
		}
	}

	private void createUpdateMRPRecord(final I_DD_OrderLine_Alternative ddOrderLineAlt, final I_PP_MRP ddOrderLineMRPRecord)
	{
		Check.assumeNotNull(ddOrderLineAlt, "ddOrderLineAlt not null");
		Check.assumeNotNull(ddOrderLineMRPRecord, "ddOrderLineMRPRecord not null");

		//
		// Get existing MRP Alternative
		I_PP_MRP_Alternative mrpAlternative = retrieveMRPAlternative(ddOrderLineAlt, ddOrderLineMRPRecord);

		//
		// If no MRP alternative found (or it was deleted)
		// we need to create a new one
		if (mrpAlternative == null)
		{
			mrpAlternative = mrpBL.createMRPAlternative(ddOrderLineMRPRecord);
			mrpAlternative.setPP_MRP(ddOrderLineMRPRecord);
			mrpAlternative.setDD_OrderLine_Alternative(ddOrderLineAlt);
		}

		//
		// Get MRP Quantity
		final String typeMRP = ddOrderLineMRPRecord.getTypeMRP();
		final BigDecimal qty;
		if (X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			qty = ddOrderBL.getQtyToReceive(ddOrderLineAlt);
		}
		else
		// if (X_PP_MRP.TYPEMRP_Demand.equals(typeMRP))
		{
			qty = ddOrderBL.getQtyToShip(ddOrderLineAlt);
		}

		//
		// Update MRP alternative record
		mrpAlternative.setAD_Org_ID(ddOrderLineAlt.getAD_Org_ID());
		mrpAlternative.setM_Product_ID(ddOrderLineAlt.getM_Product_ID());
		mrpAlternative.setQty(qty);
		mrpAlternative.setIsActive(true);
		InterfaceWrapperHelper.save(mrpAlternative);
	}

	@Override
	public void createSupply(final IMRPCreateSupplyRequest request)
	{
		final IMRPExecutor executor = request.getMRPExecutor();

		final DDOrderPojoSupplier ddOrderPojoSupplier = Adempiere.getBean(DDOrderPojoSupplier.class);

		final List<DDOrder> ddOrderPojos = ddOrderPojoSupplier.supplyPojos(
				request,
				executor.getMRPNotesCollector());

		final DDOrderProducer ddOrderProducer = new DDOrderProducer();
		for (final DDOrder ddOrderPojo : ddOrderPojos)
		{
			final I_DD_Order ddOrder = ddOrderProducer.createDDOrder(ddOrderPojo, request);
			executor.addGeneratedSupplyDocument(ddOrder);
		}
	}

	@Override
	public void cleanup(final IMaterialPlanningContext mrpContext, final IMRPExecutor executor)
	{
		// If DRP module is not activated, then skip the cleanup
		if (!mrpContext.isRequireDRP())
		{
			return;
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);

		//
		// Delete generated distribution orders
		// (i.e. Distribution Order with Draft Status)
		final ICompositeQueryFilter<I_DD_Order> filters = queryBL.createCompositeQueryFilter(I_DD_Order.class);
		filters.addEqualsFilter(I_DD_Order.COLUMNNAME_DocStatus, X_DD_Order.DOCSTATUS_Drafted);

		//
		// Only those which were generated by MRP
		filters.addEqualsFilter(I_DD_Order.COLUMN_MRP_Generated, true);
		// Only those which are allowed to be deleted by MRP cleanup
		filters.addEqualsFilter(I_DD_Order.COLUMN_MRP_AllowCleanup, true);

		//
		// Only for our AD_Client_ID
		filters.addEqualsFilter(I_DD_Order.COLUMNNAME_AD_Client_ID, mrpContext.getAD_Client_ID());
		//
		// Only for our AD_Org_ID
		filters.addEqualsFilter(I_DD_Order.COLUMNNAME_AD_Org_ID, mrpContext.getAD_Org().getAD_Org_ID());
		//
		// Only those DD Orders which are from our Plant or does not have a plant at all
		filters.addInArrayOrAllFilter(I_DD_Order.COLUMNNAME_PP_Plant_ID, null, mrpContext.getPlant().getS_Resource_ID());

		//
		// Only those which have a line with Destination Warehouse same as our warehouse
		final int targetWarehouseId = mrpContext.getM_Warehouse().getM_Warehouse_ID();
		filters.addFilter(Services.get(IDDOrderDAO.class).getDDOrdersForTargetWarehouseQueryFilter(targetWarehouseId));

		//
		// If we are running in an constrained MRP Context, filter only those documents
		if (mrpContext.getEnforced_PP_MRP_Demand_ID() > 0)
		{
			final IQuery<I_PP_MRP> mrpQuery = createMRPQueryBuilderForCleanup(mrpContext, executor)
					.createQueryBuilder()
					.addEqualsFilter(I_PP_MRP.COLUMN_TypeMRP, X_PP_MRP.TYPEMRP_Supply)
					.create();

			filters.addInSubQueryFilter(I_DD_Order.COLUMN_DD_Order_ID, I_PP_MRP.COLUMN_DD_Order_ID, mrpQuery);
		}

		deletePO(mrpContext, executor, I_DD_Order.class, filters);
	}

	/**
	 * Delete MRP Alternative records for given {@link I_DD_OrderLine_Alternative}.
	 *
	 * @param ddOrderLineAlt
	 */
	private void deleteMRPAlternatives(final I_DD_OrderLine_Alternative ddOrderLineAlt)
	{
		Check.assumeNotNull(ddOrderLineAlt, "ddOrderLineAlt not null");
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_PP_MRP_Alternative> queryBuilder = queryBL.createQueryBuilder(I_PP_MRP_Alternative.class, ddOrderLineAlt)
				.addEqualsFilter(I_PP_MRP_Alternative.COLUMN_DD_OrderLine_Alternative_ID, ddOrderLineAlt.getDD_OrderLine_Alternative_ID());

		queryBuilder.create().deleteDirectly();
	}

	/**
	 * Retrieves MRP Alternative record for given {@link I_DD_OrderLine_Alternative}.
	 *
	 * @param ddOrderLineAlt
	 * @param mrp
	 * @return MRP Alternative or <code>null</code>
	 */
	private I_PP_MRP_Alternative retrieveMRPAlternative(final I_DD_OrderLine_Alternative ddOrderLineAlt, final I_PP_MRP mrp)
	{
		// Optimization: in case the DD_OrderLine was just created, there is no point to query because there won't be any result for sure
		if (InterfaceWrapperHelper.isJustCreated(ddOrderLineAlt))
		{
			return null;
		}

		Check.assumeNotNull(ddOrderLineAlt, "ddOrderLineAlt not null");
		Check.assumeNotNull(mrp, "mrp not null");

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_PP_MRP_Alternative> queryBuilder = queryBL.createQueryBuilder(I_PP_MRP_Alternative.class, ddOrderLineAlt)
				.addEqualsFilter(I_PP_MRP_Alternative.COLUMN_DD_OrderLine_Alternative_ID, ddOrderLineAlt.getDD_OrderLine_Alternative_ID())
				.addEqualsFilter(I_PP_MRP_Alternative.COLUMN_PP_MRP_ID, mrp.getPP_MRP_ID());

		return queryBuilder.create().firstOnly(I_PP_MRP_Alternative.class);
	}

	/**
	 * If this DD Order's MRP demand record was fully allocated from QOH then complete forward DD Orders
	 *
	 * @task http://dewiki908/mediawiki/index.php/07961_Handelsware_DD_Order_automatisieren_%28101259925191%29
	 */
	@Override
	public void onQtyOnHandReservation(final IMaterialPlanningContext mrpContext,
			final IMRPExecutor mrpExecutor,
			final IMRPDemandToSupplyAllocation mrpDemandToSupplyAllocation)
	{
		final I_PP_MRP mrpDemand = mrpDemandToSupplyAllocation.getMRPDemand();
		final int ddOrderId = mrpDemand.getDD_Order_ID();
		if (ddOrderId <= 0)
		{
			return;
		}

		if (mrpBL.isReleased(mrpDemand))
		{
			return;
		}

		if (!mrpDemandToSupplyAllocation.isMRPDemandFullyAllocated())
		{
			return;
		}

		//
		// Restrictions
		final int ppPlantId = mrpDemand.getS_Resource_ID();
		if (ppPlantId <= 0)
		{
			// shall not happen
			return;
		}

		final DocumentsToCompleteAfterMRPExecution scheduler = DocumentsToCompleteAfterMRPExecution.getCreate(mrpExecutor);
		scheduler.enqueueDDOrderForMRPDemand(mrpDemand);
	}
}
