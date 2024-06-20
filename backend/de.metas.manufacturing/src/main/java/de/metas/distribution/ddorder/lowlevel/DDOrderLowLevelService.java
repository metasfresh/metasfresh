package de.metas.distribution.ddorder.lowlevel;

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

import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine_Or_Alternative;
import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.logging.LogManager;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.exception.NoPlantForWarehouseException;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.IProcessor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.IQuery;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Warehouse;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_DD_OrderLine_Alternative;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Service
public class DDOrderLowLevelService
{
	private final transient Logger logger = LogManager.getLogger(getClass());
	private final DDOrderLowLevelDAO ddOrderLowLevelDAO;
	private final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	private final IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	public DDOrderLowLevelService(
			@NonNull final DDOrderLowLevelDAO ddOrderLowLevelDAO)
	{
		this.ddOrderLowLevelDAO = ddOrderLowLevelDAO;
	}

	public I_DD_Order getById(final DDOrderId ddOrderId)
	{
		return ddOrderLowLevelDAO.getById(ddOrderId);
	}

	public final Quantity getQtyToReceive(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt)
	{
		// QtyOrdered: Qty that we need to move from source to target locator
		final BigDecimal qtyOrdered = ddOrderLineOrAlt.getQtyOrdered();
		// QtyDelivered: Qty which arrived on target locator
		final BigDecimal qtyDelivered = ddOrderLineOrAlt.getQtyDelivered();

		// Qty (supply) on target warehouse
		final BigDecimal qtyToReceiveBD = qtyOrdered // Qty that we need to move
				.subtract(qtyDelivered); // minus: Qty that we already moved (so it's there, on hand)

		return Quantitys.create(qtyToReceiveBD, UomId.ofRepoId(ddOrderLineOrAlt.getC_UOM_ID()));
	}

	public final Quantity getQtyToShip(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlt)
	{
		// QtyOrdered: Qty that we need to move from source to target locator
		final BigDecimal qtyOrdered = ddOrderLineOrAlt.getQtyOrdered();
		// QtyDelivered: Qty which arrived on target locator
		final BigDecimal qtyDelivered = ddOrderLineOrAlt.getQtyDelivered();
		// QtyInTransit: Qty which left the source locator but not arrived yet on target locator
		final BigDecimal qtyInTransit = ddOrderLineOrAlt.getQtyInTransit();

		// Qty (demand) on source warehouse
		final BigDecimal qtyToShipBD = qtyOrdered // Qty that we need to move
				.subtract(qtyDelivered) // minus: Qty that we already moved (so it's there, on hand)
				.subtract(qtyInTransit); // minus: Qty that left source locator but did not arrived yet to target locator

		return Quantitys.create(qtyToShipBD, UomId.ofRepoId(ddOrderLineOrAlt.getC_UOM_ID()));
	}

	public void completeDDOrderIfNeeded(final I_DD_Order ddOrder)
	{
		if (DocStatus.ofCode(ddOrder.getDocStatus()).isDraftedOrInProgress())
		{
			docActionBL.processEx(ddOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		}

		if (!DocStatus.ofCode(ddOrder.getDocStatus()).isCompleted())
		{
			throw new LiberoException("@Invalid@ @DocStatus@=" + ddOrder.getDocStatus() + " (" + ddOrder + ")");
		}
	}

	public void completeDDOrdersIfNeeded(final Collection<? extends I_DD_Order> ddOrders)
	{
		if (ddOrders == null || ddOrders.isEmpty())
		{
			return;
		}

		for (final I_DD_Order ddOrder : ddOrders)
		{
			if (DocStatus.ofCode(ddOrder.getDocStatus()).isDraftedOrInProgress())
			{
				docActionBL.processEx(ddOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
			}

			if (!DocStatus.ofCode(ddOrder.getDocStatus()).isCompleted())
			{
				throw new LiberoException("@Invalid@ @DocStatus@=" + ddOrder.getDocStatus() + " (" + ddOrder + ")");
			}
		}
	}

	@javax.annotation.Nullable
	public ResourceId findPlantFromOrNull(final I_DD_OrderLine ddOrderLine)
	{
		Check.assumeNotNull(ddOrderLine, LiberoException.class, "ddOrderLine not null");

		//
		// First, if we were asked to keep the target plant, let's do it
		if (ddOrderLine.isKeepTargetPlant())
		{
			final ResourceId plantToId = ResourceId.ofRepoIdOrNull(ddOrderLine.getDD_Order().getPP_Plant_ID());
			final ResourceId plantFromId = plantToId;
			return plantFromId;
		}

		//
		// Search for Warehouse's Plant
		final int adOrgId = ddOrderLine.getAD_Org_ID();
		final int fromLocatorRepoId = ddOrderLine.getM_Locator_ID();
		final I_M_Warehouse warehouseFrom = warehouseDAO.getWarehouseByLocatorRepoId(ddOrderLine.getM_Locator_ID());
		Check.assumeNotNull(warehouseFrom, "warehouseFrom not null");

		try
		{
			return productPlanningDAO.findPlant(
					adOrgId,
					warehouseFrom,
					ddOrderLine.getM_Product_ID(),
					ddOrderLine.getM_AttributeSetInstance_ID());
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

	public boolean isMovementReceipt(final I_M_MovementLine movementLine)
	{
		final I_DD_OrderLine ddOrderLine = movementLine.getDD_OrderLine();
		Check.assumeNotNull(ddOrderLine, LiberoException.class, "ddOrderLine not null");

		final I_DD_Order ddOrder = ddOrderLine.getDD_Order();
		final int warehouseInTransitId = ddOrder.getM_Warehouse_ID();

		final I_M_Locator locatorTo = movementLine.getM_LocatorTo();
		final int warehouseToId = locatorTo.getM_Warehouse_ID();

		return warehouseToId != warehouseInTransitId;
	}

	/**
	 * Retrieve and process backward and forward DD Orders of given <code>ddOrder</code>.
	 * <p>
	 * Only following DD Orders will be considered
	 * <ul>
	 * <li>only those which are not already processed
	 * <li>only those which are for same Plant as given <code>ddOrder</code> is
	 * </ul>
	 *
	 * @param ddOrderProcessor processor used to process those DD Orders
	 */
	private void processForwardAndBackwardDraftDDOrders(final I_DD_Order ddOrder, final IProcessor<I_DD_Order> ddOrderProcessor)
	{
		Check.assumeNotNull(ddOrder, "ddOrder not null");
		Check.assumeNotNull(ddOrderProcessor, "ddOrderProcessor not null");

		//
		// DD Order's Plant
		// We are getting it because we want to make sure that we will process only the DD Orders from the same plant
		final int currentPlantId = ddOrder.getPP_Plant_ID();

		//
		// Process all Forward DD Orders
		// i.e. Demanding DD Orderes
		{
			final IQueryBuilder<I_DD_OrderLine> forwardDDOrderLinesQuery = ddOrderLowLevelDAO.retrieveForwardDDOrderLinesQuery(ddOrder);
			processDraftDDOrders(forwardDDOrderLinesQuery, currentPlantId, ddOrderProcessor);
		}

		//
		// Process all Backward DD Orders
		// i.e. Supplying DD Orderes
		{
			final IQueryBuilder<I_DD_OrderLine> backwardDDOrderLinesQuery = ddOrderLowLevelDAO.retrieveBackwardDDOrderLinesQuery(ddOrder);
			processDraftDDOrders(backwardDDOrderLinesQuery, currentPlantId, ddOrderProcessor);
		}
	}

	private void processDraftDDOrders(final IQueryBuilder<I_DD_OrderLine> ddOrderLinesQuery,
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
				.addInArrayOrAllFilter(I_DD_Order.COLUMN_DocStatus, IDocument.STATUS_Drafted) // only draft/not processed DD Orders
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

	public void completeForwardAndBackwardDDOrders(final I_DD_Order ddOrder)
	{
		//
		// Complete Forward and Backward DD Orders, if they are on the same plant (08059)
		processForwardAndBackwardDraftDDOrders(ddOrder, this::completeDDOrderIfNeeded);
	}

	public void disallowMRPCleanupOnForwardAndBackwardDDOrders(final I_DD_Order ddOrder)
	{
		processForwardAndBackwardDraftDDOrders(ddOrder, ddOrderToProcess -> {
			// Skip this DD_Order if flag was already set to false
			if (!ddOrderToProcess.isMRP_AllowCleanup())
			{
				return;
			}

			// Set MRP_AlowCleanup to false and save it
			ddOrderToProcess.setMRP_AllowCleanup(false);
			ddOrderLowLevelDAO.save(ddOrderToProcess);
		});

	}

	public void completeBackwardDDOrders(final I_M_Forecast forecast)
	{

		//
		// Retrive backward DD Orders
		final List<I_DD_Order> ddOrders = ddOrderLowLevelDAO.retrieveBackwardDDOrderLinesQuery(forecast)
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

	public void save(final I_DD_Order ddOrder)
	{
		ddOrderLowLevelDAO.save(ddOrder);
	}

	public void save(final I_DD_OrderLine ddOrderline)
	{
		ddOrderLowLevelDAO.save(ddOrderline);
	}

	public void save(final I_DD_OrderLine_Or_Alternative ddOrderLineOrAlternative)
	{
		ddOrderLowLevelDAO.save(ddOrderLineOrAlternative);
	}

	public List<I_DD_OrderLine> retrieveLines(final I_DD_Order order)
	{
		return ddOrderLowLevelDAO.retrieveLines(order);
	}

	public List<I_DD_OrderLine_Alternative> retrieveAllAlternatives(final I_DD_OrderLine ddOrderLine)
	{
		return ddOrderLowLevelDAO.retrieveAllAlternatives(ddOrderLine);
	}

	public void updateUomFromProduct(final I_DD_OrderLine ddOrderLine)
	{
		final ProductId productId = ProductId.ofRepoIdOrNull(ddOrderLine.getM_Product_ID());
		if (productId == null)
		{
			// nothing to do
			return;
		}

		final UomId stockingUomId = productBL.getStockUOMId(productId);
		ddOrderLine.setC_UOM_ID(stockingUomId.getRepoId());
	}

	public void deleteOrders(@NonNull final DeleteOrdersQuery deleteOrdersQuery)
	{
		ddOrderLowLevelDAO.deleteOrders(deleteOrdersQuery);
	}
}
