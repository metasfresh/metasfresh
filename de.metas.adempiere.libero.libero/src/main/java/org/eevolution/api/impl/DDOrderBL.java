package org.eevolution.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.IProcessor;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.process.DocAction;
import org.eevolution.api.IDDOrderBL;
import org.eevolution.api.IDDOrderDAO;
import org.eevolution.api.IDDOrderMovementBuilder;
import org.eevolution.api.IProductPlanningDAO;
import org.eevolution.exceptions.LiberoException;
import org.eevolution.exceptions.NoPlantForWarehouseException;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.eevolution.model.I_DD_OrderLine_Or_Alternative;
import org.eevolution.model.I_PP_Order;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;

import de.metas.document.engine.IDocActionBL;

public class DDOrderBL implements IDDOrderBL
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public BigDecimal getQtyToReceive(final I_DD_OrderLine ddOrderLine)
	{
		final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt = InterfaceWrapperHelper.create(ddOrderLine, I_DD_OrderLine_Or_Alternative.class);
		return getQtyToReceive(ddOrderLineOrAlt);
	}

	@Override
	public BigDecimal getQtyToReceive(final I_DD_OrderLine_Alternative ddOrderLineAlt)
	{
		final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt = InterfaceWrapperHelper.create(ddOrderLineAlt, I_DD_OrderLine_Or_Alternative.class);
		return getQtyToReceive(ddOrderLineOrAlt);
	}

	@Override
	public final BigDecimal getQtyToReceive(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt)
	{
		// QtyOrdered: Qty that we need to move from source to target locator
		final BigDecimal qtyOrdered = ddOrderLineOrAlt.getQtyOrdered();
		// QtyDelivered: Qty which arrived on target locator
		final BigDecimal qtyDelivered = ddOrderLineOrAlt.getQtyDelivered();

		// Qty (supply) on target warehouse
		final BigDecimal qtyToReceive = qtyOrdered // Qty that we need to move
				.subtract(qtyDelivered); // minus: Qty that we already moved (so it's there, on hand)

		return qtyToReceive;
	}

	@Override
	public BigDecimal getQtyToShip(final I_DD_OrderLine ddOrderLine)
	{
		final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt = InterfaceWrapperHelper.create(ddOrderLine, I_DD_OrderLine_Or_Alternative.class);
		return getQtyToShip(ddOrderLineOrAlt);
	}

	@Override
	public BigDecimal getQtyToShip(final I_DD_OrderLine_Alternative ddOrderLineAlt)
	{
		final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt = InterfaceWrapperHelper.create(ddOrderLineAlt, I_DD_OrderLine_Or_Alternative.class);
		return getQtyToShip(ddOrderLineOrAlt);
	}

	@Override
	public final BigDecimal getQtyToShip(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt)
	{
		// QtyOrdered: Qty that we need to move from source to target locator
		final BigDecimal qtyOrdered = ddOrderLineOrAlt.getQtyOrdered();
		// QtyDelivered: Qty which arrived on target locator
		final BigDecimal qtyDelivered = ddOrderLineOrAlt.getQtyDelivered();
		// QtyInTransit: Qty which left the source locator but not arrived yet on target locator
		final BigDecimal qtyInTransit = ddOrderLineOrAlt.getQtyInTransit();

		// Qty (demand) on source warehouse
		final BigDecimal qtyToShip = qtyOrdered // Qty that we need to move
				.subtract(qtyDelivered) // minus: Qty that we already moved (so it's there, on hand)
				.subtract(qtyInTransit); // minus: Qty that left source locator but did not arrived yet to target locator

		return qtyToShip;
	}

	@Override
	public IDDOrderMovementBuilder createMovementBuilder()
	{
		return new DD_Order_MovementBuilder();
	}

	@Override
	public void completeDDOrderIfNeeded(final I_DD_OrderLine ddOrderLine)
	{
		// Get DD Order and make sure it's fresh
		final I_DD_Order ddOrder = ddOrderLine.getDD_Order();
		InterfaceWrapperHelper.refresh(ddOrder);

		completeDDOrderIfNeeded(ddOrder);
	}

	@Override
	public void completeDDOrderIfNeeded(final I_DD_Order ddOrder)
	{
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		if (docActionBL.isStatusDraftedOrInProgress(ddOrder))
		{
			docActionBL.processEx(ddOrder, DocAction.ACTION_Complete, DocAction.STATUS_Completed);
		}

		if (!docActionBL.isStatusCompleted(ddOrder))
		{
			throw new LiberoException("@Invalid@ @DocStatus@=" + ddOrder.getDocStatus() + " (" + ddOrder + ")");
		}
	}

	@Override
	public void completeDDOrdersIfNeeded(final Collection<? extends I_DD_Order> ddOrders)
	{
		if (ddOrders == null || ddOrders.isEmpty())
		{
			return;
		}

		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);

		for (final I_DD_Order ddOrder : ddOrders)
		{
			if (docActionBL.isStatusDraftedOrInProgress(ddOrder))
			{
				docActionBL.processEx(ddOrder, DocAction.ACTION_Complete, DocAction.STATUS_Completed);
			}

			if (!docActionBL.isStatusCompleted(ddOrder))
			{
				throw new LiberoException("@Invalid@ @DocStatus@=" + ddOrder.getDocStatus() + " (" + ddOrder + ")");
			}
		}
	}

	@Override
	public void completeForwardDDOrdersIfNeeded(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(ppOrder, "ppOrder not null");

		final List<I_DD_Order> forwardDDOrders = Services.get(IDDOrderDAO.class)
				.retrieveForwardDDOrderLinesQuery(ppOrder)
				.andCollect(I_DD_OrderLine.COLUMN_DD_Order_ID)
				.create()
				.list();

		completeDDOrdersIfNeeded(forwardDDOrders);
	}

	@Override
	public I_S_Resource findPlantFromOrNull(final I_DD_OrderLine ddOrderLine)
	{
		Check.assumeNotNull(ddOrderLine, LiberoException.class, "ddOrderLine not null");

		//
		// First, if we were asked to keep the target plant, let's do it
		if (ddOrderLine.isKeepTargetPlant())
		{
			final I_S_Resource plantTo = ddOrderLine.getDD_Order().getPP_Plant();
			final I_S_Resource plantFrom = plantTo;
			return plantFrom;
		}

		//
		// Search for Warehouse's Plant
		final Properties ctx = InterfaceWrapperHelper.getCtx(ddOrderLine);
		final int adOrgId = ddOrderLine.getAD_Org_ID();
		final I_M_Locator locatorFrom = ddOrderLine.getM_Locator();
		Check.assumeNotNull(locatorFrom, "locatorFrom not null");
		final I_M_Warehouse warehouseFrom = locatorFrom.getM_Warehouse();
		final int productId = ddOrderLine.getM_Product_ID();
		try
		{
			final I_S_Resource plantFrom = Services.get(IProductPlanningDAO.class).findPlant(ctx, adOrgId, warehouseFrom, productId);
			return plantFrom;
		}
		catch (final NoPlantForWarehouseException e)
		{
			// just ignore it
			// NOTE: we are logging as FINE because it's a common case if u are not doing manufacturing to get this error
			logger.debug("No plant was found. Returning null.", e);
		}

		//
		// No Plant found => return null
		return null;
	}

	@Override
	public boolean isMovementReceipt(final I_M_MovementLine movementLine)
	{
		final I_DD_OrderLine ddOrderLine = movementLine.getDD_OrderLine();
		Check.assumeNotNull(ddOrderLine, LiberoException.class, "ddOrderLine not null");

		final I_DD_Order ddOrder = ddOrderLine.getDD_Order();
		final int warehouseInTransitId = ddOrder.getM_Warehouse_ID();

		final I_M_Locator locatorTo = movementLine.getM_LocatorTo();
		final int warehouseToId = locatorTo.getM_Warehouse_ID();

		if (warehouseToId == warehouseInTransitId)
		{
			// Movement-Shipment
			return false;
		}
		else
		{
			// Movement-Receipt
			return true;
		}
	}

	/**
	 * Retrieve and process backward and forward DD Orders of given <code>ddOrder</code>.
	 * 
	 * Only following DD Orders will be considered
	 * <ul>
	 * <li>only those which are not already processed
	 * <li>only those which are for same Plant as given <code>ddOrder</code> is
	 * </ul>
	 * 
	 * @param ddOrder
	 * @param ddOrderProcessor processor used to process those DD Orders
	 */
	private final void processForwardAndBackwardDraftDDOrders(final I_DD_Order ddOrder, final IProcessor<I_DD_Order> ddOrderProcessor)
	{
		Check.assumeNotNull(ddOrder, "ddOrder not null");
		Check.assumeNotNull(ddOrderProcessor, "ddOrderProcessor not null");

		// services
		final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);

		//
		// DD Order's Plant
		// We are getting it because we want to make sure that we will process only the DD Orders from the same plant
		final int currentPlantId = ddOrder.getPP_Plant_ID();

		//
		// Process all Forward DD Orders
		// i.e. Demanding DD Orderes
		{
			final IQueryBuilder<I_DD_OrderLine> forwardDDOrderLinesQuery = ddOrderDAO.retrieveForwardDDOrderLinesQuery(ddOrder);
			processDraftDDOrders(forwardDDOrderLinesQuery, currentPlantId, ddOrderProcessor);
		}

		//
		// Process all Backward DD Orders
		// i.e. Supplying DD Orderes
		{
			final IQueryBuilder<I_DD_OrderLine> backwardDDOrderLinesQuery = ddOrderDAO.retrieveBackwardDDOrderLinesQuery(ddOrder);
			processDraftDDOrders(backwardDDOrderLinesQuery, currentPlantId, ddOrderProcessor);
		}
	}

	private final void processDraftDDOrders(final IQueryBuilder<I_DD_OrderLine> ddOrderLinesQuery,
			final int currentPlantId,
			final IProcessor<I_DD_Order> ddOrderProcessor)
	{
		logger.debug("PP_Plant_ID: {}", currentPlantId);
		logger.debug("Processor: {}", ddOrderProcessor);

		//
		// Retrieve DD Orders which are
		// * not already processed
		// * are for given plant
		final IQuery<I_DD_Order> ddOrdersToProcessQuery = ddOrderLinesQuery
				//
				// Not already processed lines
				// NOTE: to avoid recursions, we relly on the fact that current DD Order which is about to be completed,
				// even if the DocStatus was not already set in database,
				// it's lines were already flagged as processed
				.addEqualsFilter(I_DD_OrderLine.COLUMN_Processed, false)
				//
				// Collect DD_Orders
				.andCollect(I_DD_OrderLine.COLUMN_DD_Order_ID)
				.addEqualsFilter(I_DD_Order.COLUMN_Processed, false) // only not processed DD_Orders
				.addNotEqualsFilter(I_DD_Order.COLUMN_Processing, true) // only those which are not currently processing
				.addInArrayOrAllFilter(I_DD_Order.COLUMN_DocStatus, DocAction.STATUS_Drafted) // only draft/not processed DD Orders
				.addEqualsFilter(I_DD_Order.COLUMN_PP_Plant_ID, currentPlantId) // same plant, we are not allow to cross plants (08059)
				//
				// Create query
				.create();
		logger.debug("DD Orders Query: {}", ddOrdersToProcessQuery);

		final List<I_DD_Order> ddOrdersToProcess = ddOrdersToProcessQuery.list();
		logger.debug("Selected DD Orders to process: {}", ddOrdersToProcess);

		//
		// Process DD Orders
		for (final I_DD_Order ddOrderToProcess : ddOrdersToProcess)
		{
			ddOrderProcessor.process(ddOrderToProcess);
		}
	}

	@Override
	public void completeForwardAndBackwardDDOrders(final I_DD_Order ddOrder)
	{
		//
		// Complete Forward and Backward DD Orders, if they are on the same plant (08059)
		processForwardAndBackwardDraftDDOrders(ddOrder, new IProcessor<I_DD_Order>()
		{
			@Override
			public void process(final I_DD_Order ddOrderToProcess)
			{
				completeDDOrderIfNeeded(ddOrderToProcess);
			}
		});
	}

	@Override
	public void disallowMRPCleanupOnForwardAndBackwardDDOrders(final I_DD_Order ddOrder)
	{
		processForwardAndBackwardDraftDDOrders(ddOrder, new IProcessor<I_DD_Order>()
		{
			@Override
			public void process(final I_DD_Order ddOrderToProcess)
			{
				// Skip this DD_Order if flag was already set to false
				if (!ddOrderToProcess.isMRP_AllowCleanup())
				{
					return;
				}

				// Set MRP_AlowCleanup to false and save it
				ddOrderToProcess.setMRP_AllowCleanup(false);
				InterfaceWrapperHelper.save(ddOrderToProcess);
			}
		});

	}

	@Override
	public void completeBackwardDDOrders(final I_M_Forecast forecast)
	{
		final IDDOrderDAO ddOrderDAO = Services.get(IDDOrderDAO.class);

		//
		// Retrive backward DD Orders
		final List<I_DD_Order> ddOrders = ddOrderDAO.retrieveBackwardDDOrderLinesQuery(forecast)
				//
				// Not already processed lines
				// NOTE: to avoid recursions, we relly on the fact that current DD Order which is about to be completed,
				// even if the DocStatus was not already set in database,
				// it's lines were already flagged as processed
				.addEqualsFilter(I_DD_OrderLine.COLUMN_Processed, false)
				//
				// Collect DD_Orders
				.andCollect(I_DD_OrderLine.COLUMN_DD_Order_ID)
				.addEqualsFilter(I_DD_Order.COLUMN_Processed, false) // only not processed DD_Orders
				//
				// Retrieve them
				.create()
				.list();

		completeDDOrdersIfNeeded(ddOrders);
	}
}
